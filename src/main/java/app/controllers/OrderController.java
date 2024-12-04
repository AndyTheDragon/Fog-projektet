package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.persistence.OrderMapper;
import app.entities.Carport;
import app.entities.RoofType;
import app.persistence.ConnectionPool;
import app.exceptions.DatabaseException;
import app.services.WorkDrawing;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Map;



import app.entities.Order;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import app.persistence.OrderMapper;

import static app.Main.connectionPool;
import static app.persistence.OrderMapper.saveOrderToDatabase;

public static app.persistence.OrderMapper.parseIntFormParam;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool dbConnection)
    {
        app.get("/draw", ctx -> showDrawing(ctx, dbConnection));
        app.get("/orders", ctx -> showOrders(ctx, dbConnection));
        app.get("/order/{orderId}", ctx -> showOrderDetails(ctx,dbConnection));
        app.post("/order/assign",ctx -> assignOrder(ctx,dbConnection));
        app.get("/login", ctx -> showLogin(ctx));
        app.post("/login", ctx -> doLogin(ctx,dbConnection));
        app.get("/bestilling", ctx -> showOrderPage(ctx));
        app.post("/bestilling", ctx -> createOrder(ctx, dbConnection));

    }

    public static void showOrderPage(Context ctx) {
        Order currentOrder = ctx.sessionAttribute("currentOrder");
        if (currentOrder == null) {
            ctx.attribute("message", "Du har ikke nogen ordre i gang");
        } else {
            ctx.attribute("order", currentOrder);
        }
        ctx.render("/templates/bestilling.html");
    }

    public static void createOrder(Context ctx, ConnectionPool dbConnection) {
        try {
            if (currentOrder == null){
                currentOrder = OrderMapper.createOrder(currentUser, dbConnection);
                ctx.sessionAttribute("currentOrder", currentOrder);
            }
            int customerID = Integer.parseInt(ctx.formParam("customerID"));
            int salesID = Integer.parseInt(ctx.formParam("salesID"));
            int carportWidth = Integer.parseInt(ctx.formParam("carportWidth"));
            int carportLength = Integer.parseInt(ctx.formParam("carportLength"));
            int carportHeight = Integer.parseInt(ctx.formParam("carportHeight"));
            boolean carportShed = Boolean.parseBoolean(ctx.formParam("carportShed"));
            int shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
            int shedLength = Integer.parseInt(ctx.formParam("shedLength"));

            String carportRoof = ctx.formParam("carportRoof");
            boolean isPaid = Boolean.parseBoolean(ctx.formParam("isPaid"));
            Timestamp now = new Timestamp(System.currentTimeMillis());

            Order order = new Order(0, customerID, salesID, carportWidth, carportLength, carportHeight, carportShed,
                    shedWidth, shedLength, carportRoof, isPaid, now, now);

            saveOrderToDatabase(order, dbConnection);
            ctx.status(201).result("Ordren blev oprettet med succes.");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result("Fejl i input: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500).result("Databasefejl: Kunne ikke oprette ordren.");
        }
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
        int orderId = 0;
        Order order = null;
        ctx.sessionAttribute("user", "Morten");
        if (ctx.sessionAttribute("user") == null)
        {
            ctx.attribute("message", "You need to login first");
        }
        else
        {
            try
            {
                orderId = Integer.parseInt(ctx.pathParam("orderId"));
                order = OrderMapper.getOrder(orderId);
            }
            catch (NumberFormatException e)
            {
                ctx.attribute("message", "Invalid order id");
            }
            catch (DatabaseException e)
            {
                ctx.attribute("message", "Database error. " + e.getMessage());
            }
            ctx.attribute("order", order);
        }
        ctx.render("ordredetaljer.html");


    }

    private static void showDrawing(Context ctx, ConnectionPool dbConnection)
    {
        Carport carport = new Carport(780,600,210,530, RoofType.FLAT);
        WorkDrawing drawing = new WorkDrawing(carport,780);
        ctx.attribute("drawing", drawing.toString());
        Carport carport2 = new Carport(480,300,0,0, RoofType.FLAT);
        WorkDrawing noShed = new WorkDrawing(carport2, 480);
        ctx.attribute("noshed", noShed.toString());

        ctx.render("drawing.html");

    }

    private static void showOrders(Context ctx, ConnectionPool connectionPool)
    {
        User currentUser = ctx.sessionAttribute("currentUser");
        currentUser = new User();
        if (currentUser != null)
        {
            try
            {
                Map<String, ArrayList<Order>> orders = OrderMapper.getOrders(connectionPool);

                ArrayList<Order> assigned = orders.get("assigned");
                ArrayList<Order> unassigned = orders.get("unassigned");

                ctx.attribute("assigned", assigned);
                ctx.attribute("unassigned", unassigned);


            }
            catch (DatabaseException e)
            {
                ctx.attribute("message", "Fejl ved hentning af ordrer: " + e.getMessage());
            }
        }
        ctx.render("ordreoversigt.html");
    }

}
