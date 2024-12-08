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
                Customer customer = new Customer(rs.getString("customer_name"),
                        rs.getString("address"),
                        rs.getString("zipcode"),
                        rs.getString("city"),
                        rs.getString("phone_number"),
                        rs.getString("email"));
                User salesPerson;
                Integer salesId = rs.getObject("sales_id") != null ? null : rs.getInt("sales_id");
                if (salesId == null)
                {
                    salesPerson = new User("Ingen sælger tildelt endnu", "fog@fog.dk");
                }
                else
                {
                    salesPerson = new User(rs.getString("user_name"), rs.getString("email"));
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

                System.out.println(unassignedOrders);
                System.out.println(allorders);
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

    public static void saveOrderToDatabase(Order order, ConnectionPool dbConnection) throws DatabaseException
    {
    }
}
