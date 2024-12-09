package app.persistence;

import app.entities.Customer;
import app.entities.Order;
import app.entities.RoofType;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class OrderMapper
{

 /*   public static Order createOrder(Order order, ConnectionPool dbConnection) throws DatabaseException
    {
        String sql = "INSERT INTO carport_order (customer_id, sales_id, carport_width, carport_length, carport_height, carport_shed, shed_length, carport_roof, is_paid, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Order newOrder;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, order.getCustomerID().getCustomerID());
            ps.setInt(2, order.getSalesPerson().getUserID());
            ps.setInt(3, order.getCarportWidth());
            ps.setInt(4, order.getCarportLength());
            ps.setInt(5, order.getCarportHeight());
            ps.setBoolean(6, order.getCarport().hasShed());
            ps.setInt(7, order.getShedWidth());
            ps.setInt(8, order.getShedLength());
            ps.setString(9, order.getCarportRoof().toString());
            ps.setBoolean(10, order.isPaid());
            ps.setTimestamp(11, Timestamp.valueOf(order.getCreatedAt()));
            ps.setTimestamp(12, Timestamp.valueOf(order.getUpdatedAt()));

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int newOrderId = rs.getInt(1);
                newOrder = new Order(newOrderId, order.getCustomerID(), order.getSalesPerson(), order.getCarportWidth(), order.getCarportLength(), order.getCarportHeight(), order.getCarportShed(), order.getShedWidth(), order.getShedLength(), order.getCarportRoof(), order.isPaid());
            } else {
                throw new DatabaseException("Error creating order");
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error creating order "+ e.getMessage());
        }
        return newOrder;

    }*/


    public static Map<String, ArrayList<Order>> getOrders(ConnectionPool connectionPool) throws DatabaseException
    {
        ArrayList<Order> allorders = new ArrayList<>();
        ArrayList<Order> unassignedOrders = new ArrayList<>();

        String sql = "SELECT order_id, sales_id, carport_width, carport_length, carport_height, shed_width, shed_length, carport_roof, is_paid, created_at, updated_at," +
                " c.customer_name, c.address, c.zipcode, c.city, c.phone_number, c.email," +
                " a.user_name, a.email" +
                " from carport_order" +
                " INNER JOIN customer AS c on carport_order.customer_id = c.customer_id" +
                " INNER JOIN account AS a ON carport_order.sales_id = a.user_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int customerId = rs.getInt("customer_id");
                Customer customer = new Customer(rs.getString("c.customer_name"),
                        rs.getString("c.address"),
                        rs.getString("c.zipcode"),
                        rs.getString("c.city"),
                        rs.getString("c.phone_number"),
                        rs.getString("c.email"));
                User salesPerson;
                int salesId = rs.getInt("sales_id");
                if (salesId==0)
                {
                    salesPerson = new User("Ingen sælger tildelt endnu", "fog@fog.dk");
                }
                else
                {
                    salesPerson = new User(rs.getString("a.user_name"), rs.getString("a.email"));
                }
                int carportWidth = rs.getInt("carport_width");
                int carportLength = rs.getInt("carport_length");
                int carportHeight = rs.getInt("carport_height");
                int shedWidth = rs.getInt("shed_width");
                int shedLength = rs.getInt("shed_length");
                RoofType roofType = (RoofType) rs.getObject("carport_roof");
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
                        updatedAt);

                if (salesId == 0) {
                    unassignedOrders.add(order);
                } else {
                    allorders.add(order);
                }
            }
        } catch (SQLException e) {
                throw new DatabaseException("Message "+ e.getMessage());
        }

        Map<String, ArrayList<Order>> result = new HashMap<>();
        result.put("assigned", allorders);
        result.put("unassigned", unassignedOrders);

        return result;
    }
    public static Order getOrder(int orderId) throws DatabaseException
    {
        return new Order(1,
                new Customer(),
                new User(),
                600,
                780,
                530,
                210,
                RoofType.FLAT,
                false,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public static void asssignOrder(int orderId, int salesId, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "UPDATE carport_order SET sales_id = ? WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
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

    public static Order acceptOrder(int orderId) throws DatabaseException
    {
        return new Order(1,
                new Customer(),
                new User(),
                600,
                780,
                530,
                210,
                RoofType.FLAT,
                true,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public static Order saveOrderToDatabase(Order order, ConnectionPool dbConnection) throws DatabaseException
    {
        String sql = "INSERT INTO carport_order (customer_id, carport_width, carport_length, carport_height, carport_shed,shed_width, shed_length, carport_roof) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Order newOrder;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, order.getCustomerID().getCustomerID());
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
                newOrder = new Order(newOrderId, order.getCustomerID(), order.getSalesPerson(), order.getCarportWidth(), order.getCarportLength(), order.getCarportHeight(), order.getCarportShed(), order.getShedWidth(), order.getShedLength(), order.getCarportRoof(), order.isPaid());
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
