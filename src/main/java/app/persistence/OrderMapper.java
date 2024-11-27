package app.persistence;

import app.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper
{


    public static List<Order> getOrders(boolean unassigned)
    {   List<Order> orders = new ArrayList<>();

        // Dummy data for unassigned orders
        if (unassigned) {
            orders.add(new Order(1001, "Unassigned", "John Doe", 250.0));
            orders.add(new Order(1002, "Unassigned", "Jane Smith", 300.0));
        } else {
            // Dummy data for assigned orders
            orders.add(new Order(2001, "Assigned to Sarah", "Alice Brown", 450.0));
            orders.add(new Order(2002, "Assigned to Alex", "Bob Green", 600.0));
        }

        return orders;
    }
}
