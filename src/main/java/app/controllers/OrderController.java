package app.controllers;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    { app.get("bestil", ctx -> showOrders(ctx, connectionPool));

    }

    private static void showOrders(Context ctx, ConnectionPool connectionPool)
    {
        try {
            ArrayList<Order> orders = OrderMapper.getOrders(connectionPool);

            ctx.attribute("bottoms", orders);
            ctx.render("bestil.html");

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "Der var et problem ved at hente siden pga. fejl ved at hente data.");
            ctx.render("errorAlreadyLogin.html");
        }
    }
}
