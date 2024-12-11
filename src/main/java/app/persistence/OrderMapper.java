package app.persistence;

import app.entities.*;
import app.exceptions.CalculatorException;
import app.exceptions.DatabaseException;
import app.services.OptimalWoodCalculator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class OrderMapper
{


    public static Map<String, ArrayList<Order>> getOrders(int limitToSalesId, ConnectionPool dbConnection) throws DatabaseException
    {
        ArrayList<Order> allorders = new ArrayList<>();
        ArrayList<Order> unassignedOrders = new ArrayList<>();

        String sql = "SELECT carport_order.*, " +
                "c.customer_name AS customer_name, " +
                "c.address AS customer_address, " +
                "c.zipcode AS customer_zipcode, " +
                "c.city AS customer_city, " +
                "c.phone_number AS customer_phone, " +
                "c.email AS customer_email, " +
                "a.user_name AS sales_name, " +
                "a.email AS sales_email " +
                "FROM public.carport_order " +
                "LEFT JOIN public.customer c ON carport_order.customer_id = c.customer_id " +
                "LEFT JOIN public.account a ON carport_order.sales_id = a.user_id;";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int orderId = rs.getInt("order_id");
                int customerId = rs.getInt("customer_id");
                Customer customer = new Customer(rs.getString("customer_name"),
                        rs.getString("customer_address"),
                        rs.getString("customer_zipcode"),
                        rs.getString("customer_city"),
                        rs.getString("customer_phone"),
                        rs.getString("customer_email"));
                User salesPerson;
                int salesId = rs.getInt("sales_id");
                if (salesId==0)
                {
                    salesPerson = new User("Ingen sælger tildelt endnu", "fog@fog.dk");
                }
                else
                {
                    salesPerson = new User(rs.getString("sales_name"), rs.getString("sales_email"));
                }
                int carportWidth = rs.getInt("carport_width");
                int carportLength = rs.getInt("carport_length");
                int carportHeight = rs.getInt("carport_height");
                int shedWidth = rs.getInt("shed_width");
                int shedLength = rs.getInt("shed_length");
                RoofType roofType = RoofType.valueOf(rs.getString("carport_roof").toUpperCase());
                boolean isPaid = rs.getBoolean("is_paid");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

                Order order = new Order(orderId,
                        customer,
                        salesPerson,
                        carportWidth,
                        carportLength,
                        shedWidth,
                        shedLength,
                        roofType,
                        isPaid,
                        createdAt,
                        updatedAt, OrderStatus.UNASSIGNED, new OptimalWoodCalculator(carportLength, carportWidth, shedLength, shedWidth, dbConnection));

                if (salesId == 0)
                {
                    unassignedOrders.add(order);
                } else if (0 == limitToSalesId || salesId == limitToSalesId)
                {
                    allorders.add(order);
                }
            }
        } catch (SQLException e)
        {
                throw new DatabaseException("Message "+ e.getMessage());
        } catch (app.exceptions.CalculatorException e)
        {
            throw new RuntimeException(e);
        }

        Map<String, ArrayList<Order>> result = new HashMap<>();
        result.put("assigned", allorders);
        result.put("unassigned", unassignedOrders);

        return result;
    }

    public static Order getOrder(int orderId, ConnectionPool dbConnection) throws DatabaseException, CalculatorException
    {
        Order order = null;

        String sql = "SELECT carport_order.*, " +
                "c.customer_name AS customer_name, " +
                "c.address AS customer_address, " +
                "c.zipcode AS customer_zipcode, " +
                "c.city AS customer_city, " +
                "c.phone_number AS customer_phone, " +
                "c.email AS customer_email, " +
                "a.user_name AS user_name, " +
                "a.email AS user_email " +
                "FROM public.carport_order " +
                "LEFT JOIN public.customer c ON carport_order.customer_id = c.customer_id " +
                "LEFT JOIN public.account a ON carport_order.sales_id = a.user_id "+
                "WHERE carport_order.order_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();


            while (rs.next())
            {
                orderId = rs.getInt("order_id");
                int customerId = rs.getInt("customer_id");
                Customer customer = new Customer(rs.getString("customer_name"),
                        rs.getString("customer_address"),
                        rs.getString("customer_zipcode"),
                        rs.getString("customer_city"),
                        rs.getString("customer_phone"),
                        rs.getString("customer_email"));
                User salesPerson;
                int salesId = rs.getInt("sales_id");
                if (salesId == 0)
                {
                    salesPerson = new User("Ingen sælger tildelt endnu", "fog@fog.dk");
                }
                else
                {
                    salesPerson = new User(rs.getString("user_name"), rs.getString("user_email"));
                }
                int carportWidth = rs.getInt("carport_width");
                int carportLength = rs.getInt("carport_length");
                int carportHeight = rs.getInt("carport_height");
                int shedWidth = rs.getInt("shed_width");
                int shedLength = rs.getInt("shed_length");
                RoofType roofType = RoofType.valueOf(rs.getString("carport_roof").toUpperCase());
                boolean isPaid = rs.getBoolean("is_paid");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
                OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status").toUpperCase());

                order = new Order(orderId,
                        customer,
                        salesPerson,
                        carportWidth,
                        carportLength,
                        shedWidth,
                        shedLength,
                        roofType,
                        isPaid,
                        createdAt,
                        updatedAt, orderStatus, new OptimalWoodCalculator(carportLength, carportWidth, shedLength, shedWidth, dbConnection));
            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Message "+ e.getMessage());
        } catch (CalculatorException e)
        {
            throw new CalculatorException(e.getMessage());
        }

        return order;
    }

    public static void asssignOrder(int orderId, int salesId, ConnectionPool dbConnection) throws DatabaseException
    {
        String sql = "UPDATE carport_order SET sales_id = ? WHERE order_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, salesId);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e)
        {
            throw new DatabaseException("Message " + e.getMessage());
        }
    }

    public static void acceptOrder(int orderId, ConnectionPool dbConnection) throws DatabaseException
    {
        String sql = "UPDATE carport_order SET order_status = ? WHERE order_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, OrderStatus.OFFER_ACCEPTED.toString());
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new DatabaseException("No order found with id: " + orderId);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error: " + e.getMessage());
        }
    }

    public static Order saveOrderToDatabase(Order order, ConnectionPool dbConnection) throws DatabaseException
    {
        String sql = "INSERT INTO carport_order (customer_id, carport_width, carport_length, carport_height, carport_shed,shed_width, shed_length, carport_roof) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Order newOrder;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, order.getCustomer().getCustomerID());
            ps.setInt(2, order.getCarportWidth());
            ps.setInt(3, order.getCarportLength());
            ps.setInt(4, order.getCarportHeight());
            ps.setBoolean(5, order.getCarport().hasShed());
            ps.setInt(6, order.getShedWidth());
            ps.setInt(7, order.getShedLength());
            ps.setString(8, order.getCarportRoof().toString());

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected == 1)
            {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int newOrderId = rs.getInt(1);
                newOrder = new Order(newOrderId, order.getCustomer(), order.getSalesPerson(), order.getCarportWidth(), order.getCarportLength(), order.getCarportHeight(), order.getCarportShed(), order.getShedWidth(), order.getShedLength(), order.getCarportRoof(), order.isPaid());
            } else {
                throw new DatabaseException("Error creating order");
            }
            ps.executeUpdate();
        } catch (SQLException e)
        {
            throw new DatabaseException("Message " + e.getMessage());
        }
        return newOrder;
    }

}