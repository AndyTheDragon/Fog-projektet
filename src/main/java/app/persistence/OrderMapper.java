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
import java.util.List;
import java.util.Map;



public class OrderMapper
{

    public static Order createOrder(Order order, ConnectionPool dbConnection) throws DatabaseException
    {
        String sql = "INSERT INTO carport_order (customer_id, sales_id, carport_width, carport_length, shed_width, shed_length, carport_roof, is_paid, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Order newOrder;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(2, order.getCustomerID().getCustomerID());
            ps.setInt(3, order.getSalesID().getUserID());
            ps.setInt(3, order.getCarportWidth());
            ps.setInt(4, order.getCarportLength());
            ps.setInt(5, order.getShedWidth());
            ps.setInt(6, order.getShedLength());
            ps.setObject(7, order.getCarportRoof());
            ps.setBoolean(8, order.isPaid());
            ps.setTimestamp(10, Timestamp.valueOf(order.getCreatedAt()));
            ps.setTimestamp(11, Timestamp.valueOf(order.getUpdatedAt()));

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int newOrderId = rs.getInt(1);
                newOrder = new Order(newOrderId, order.getCustomerID(), order.getSalesID(), order.getCarportWidth(), order.getCarportLength(), order.getShedWidth(), order.getShedLength(), order.getCarportRoof(), order.isPaid(), order.getCreatedAt(), order.getUpdatedAt());
            } else {
                throw new DatabaseException("Error creating order");
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error creating order "+ e.getMessage());
        }
        return newOrder;

    }


    public static Map<String, ArrayList<Order>> getOrders(ConnectionPool connectionPool) throws DatabaseException
    {
        ArrayList<Order> allorders = new ArrayList<>();
        ArrayList<Order> unassignedOrders = new ArrayList<>();

        String sql = "SELECT * from carport_order";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int customerId = rs.getInt("customer_id");
                Integer salesId = (Integer) rs.getObject("sales_id");
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
                        new Customer(),
                        new User(),
                        carportWidth,
                        carportLength,
                        shedWidth,
                        shedLength,
                        roofType,
                        isPaid,
                        createdAt,
                        updatedAt);

                if (salesId == null) {
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

    public static void saveOrderToDatabase(Order order, ConnectionPool dbConnection) throws DatabaseException
    {
    }
}
