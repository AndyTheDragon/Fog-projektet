package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        String sql = "SELECT * from carport_order";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int customerId = rs.getInt("customer_id");
                Integer salesId = (Integer) rs.getObject("sales_id");

                Order order = new Order(orderId, customerId, salesId);

                if (salesId == null) {
                    unassignedOrders.add(order);
                } else {
                    allorders.add(order);
                }
            }
        } catch (SQLException e) {
                throw new DatabaseException("Message"+ e.getMessage());
        }

        Map<String, ArrayList<Order>> result = new HashMap<>();
        result.put("assigned", allorders);
        result.put("unassigned", unassignedOrders);

        return result;
    }
}
