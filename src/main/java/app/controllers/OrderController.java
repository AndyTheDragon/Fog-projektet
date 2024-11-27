package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class OrderController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    { app.get("bestil", ctx -> showUnassignedOrders(ctx, connectionPool));

    }

    private static void showUnassignedOrders(Context ctx, ConnectionPool connectionPool)
    {
    }
}
