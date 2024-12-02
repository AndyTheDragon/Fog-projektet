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
            Map<String, ArrayList<Order>> orders = OrderMapper.getOrders(connectionPool);

            ArrayList<Order> assigned = orders.get("assigned");
            ArrayList<Order> unassigned = orders.get("unassigned");

            ctx.attribute("assigned", assigned);
            ctx.attribute("unassigned", unassigned);

            ctx.render("ordreoversigt.html");
        } catch (Exception e) {
            ctx.attribute("errorMessage", "Fejl ved hentning af ordrer: " + e.getMessage());
            ctx.render("ordreoversigt.html");
        }
    }
}
