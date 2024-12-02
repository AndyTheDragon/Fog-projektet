package app.controllers;

import app.persistence.ConnectionPool;
import app.services.WorkDrawing;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class OrderController
{
    public static void addRoutes(Javalin app, ConnectionPool dbConnection)
    {
        app.get("/draw", ctx -> showDrawing(ctx, dbConnection));
        app.get("/order/{id}", ctx -> showOrderDetails(ctx,dbConnection));
        app.post("/order/assign",ctx -> assignOrder(ctx,dbConnection));
        app.get("/login", ctx -> showLogin(ctx));
        app.post("/login", ctx -> doLogin(ctx,dbConnection));

    }

    private static void doLogin(@NotNull Context ctx, ConnectionPool dbConnection)
    {
    }

    private static void showLogin(@NotNull Context ctx)
    {
        
    }

    private static void assignOrder(@NotNull Context ctx, ConnectionPool dbConnection)
    {
    }

    private static void showOrderDetails(@NotNull Context ctx, ConnectionPool dbConnection)
    {

    }

    private static void showDrawing(Context ctx, ConnectionPool dbConnection)
    {
        WorkDrawing drawing = new WorkDrawing(600, 780, 230, 530,210, 14);
        ctx.attribute("drawing", drawing.toString());
        WorkDrawing noShed = new WorkDrawing(300, 480, 230, 10);
        ctx.attribute("noshed", noShed.toString());

        ctx.render("drawing.html");

    }

}
