package app.persistence;

import app.entities.Customer;
import app.entities.Order;
import app.entities.RoofType;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class OrderMapper
{


    public static Map<String, ArrayList<Order>> getOrders(ConnectionPool connectionPool) throws DatabaseException
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

        try (Connection connection = connectionPool.getConnection();
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
                Integer salesId = rs.getObject("sales_id") != null ? null : rs.getInt("sales_id");
                if (salesId == null)
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

                Order order = new Order(orderId, customer, salesPerson, carportWidth, carportLength, shedWidth,
                        shedLength, roofType, isPaid, createdAt, updatedAt);

                if (salesId == null)
                {
                    unassignedOrders.add(order);
                } else
                {
                    allorders.add(order);
                }
            }
        } catch (SQLException e)
        {
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

    public static void saveOrderToDatabase(Order order, ConnectionPool dbConnection) throws DatabaseException
    {
    }

    public static ArrayList<Order> getOderById(int orderId, ConnectionPool dbConnectionpool) throws DatabaseException
    {
        ArrayList<Order> orderById = new ArrayList<>();

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
                "LEFT JOIN public.account a ON carport_order.sales_id = a.user_id;";

        try (Connection connection = dbConnectionpool.getConnection();
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
                Integer salesId = rs.getObject("sales_id") != null ? null : rs.getInt("sales_id");
                if (salesId == null)
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

                Order order = new Order(orderId, customer, salesPerson, carportWidth, carportLength, shedWidth,
                        shedLength, roofType, isPaid, createdAt, updatedAt);
                orderById.add(order);

            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Message "+ e.getMessage());
        }

        return orderById;
    }
}