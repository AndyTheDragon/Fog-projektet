package app.controllers;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    { app.get("orders", ctx -> showOrders(ctx, connectionPool));

    }

    private static void showOrders(Context ctx, ConnectionPool connectionPool)
    {
        try {
            List<Order> unassignedOrders = OrderMapper.getOrders(true);
            List<Order> assignedOrders = OrderMapper.getOrders(false);


            Map<String, Object> orders = new HashMap<>();
            orders.put("unassignedOrders", unassignedOrders);
            orders.put("assignedOrders", assignedOrders);

            // Returner ordrerne som JSON
            ctx.json(orders);
        } catch (Exception e) {

            ctx.attribute("error", "Der opstod en fejl under hentning af ordrer.");
            ctx.render("error.html");
            throw new RuntimeException(e);
        }
    }
}
