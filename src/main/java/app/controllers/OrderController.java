package app.controllers;

import app.entities.*;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.exceptions.DatabaseException;
import app.services.SendGrid;
import app.services.WorkDrawing;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class OrderController
{
    public static void addRoutes(Javalin app, ConnectionPool dbConnection)
    {
        app.get("/draw", ctx -> showDrawing(ctx, dbConnection));
        app.get("/orders", ctx -> showOrders(ctx, dbConnection));
        app.get("/order/{orderId}", ctx -> showOrderDetails(ctx,dbConnection));
        app.post("/order/accept", ctx -> acceptOrder(ctx,dbConnection));
        app.post("/order/requestchange", ctx -> requestChange(ctx,dbConnection));
        app.post("/order/assign",ctx -> assignOrder(ctx,dbConnection));
        //app.get("/login", ctx -> showLogin(ctx));
        //app.post("/login", ctx -> doLogin(ctx,dbConnection));
        app.get("/bestilling", ctx -> showOrderPage(ctx));
        app.post("/bestilling", ctx -> createOrder(ctx, dbConnection));

    }

    public static void showOrderPage(Context ctx)
    {
        Order currentOrder = ctx.sessionAttribute("currentOrder");
        if (currentOrder == null) {
            ctx.attribute("message", "Du har ikke nogen ordre i gang");
        } else {
            ctx.attribute("order", currentOrder);
        }
        ctx.render("bestilling.html");
    }

    public static void createOrder(Context ctx, ConnectionPool dbConnection)
    {
        try {

            int customerID = Integer.parseInt(ctx.formParam("customerID"));
            int salesID = Integer.parseInt(ctx.formParam("salesID"));
            int carportWidth = Integer.parseInt(ctx.formParam("carportWidth"));
            int carportLength = Integer.parseInt(ctx.formParam("carportLength"));
            int carportHeight = Integer.parseInt(ctx.formParam("carportHeight"));
            boolean carportShed = Boolean.parseBoolean(ctx.formParam("carportShed"));
            int shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
            int shedLength = Integer.parseInt(ctx.formParam("shedLength"));

            RoofType carportRoof = ctx.formParam("carportRoof").equals("flat") ? RoofType.FLAT : RoofType.FLAT;
            boolean isPaid = false;

            Order order = new Order(0, new Customer(), new User(), carportWidth, carportLength,
                    shedWidth, shedLength, carportRoof, isPaid, LocalDateTime.now(), LocalDateTime.now(), new OptimalWoodCalculator(dbConnection));

            OrderMapper.saveOrderToDatabase(order, dbConnection);
            ctx.status(201).result("Ordren blev oprettet med succes.");
        } catch (IllegalArgumentException e) {
            ctx.status(400).result("Fejl i input: " + e.getMessage());
        } catch (DatabaseException e) {
            ctx.status(500).result("Databasefejl: Kunne ikke oprette ordren.");
        }
    }

    private static void doLogin(@NotNull Context ctx, ConnectionPool dbConnection)
    {
        //US-3
    }

    private static void showLogin(@NotNull Context ctx)
    {
        //US-3
    }

    private static void assignOrder(@NotNull Context ctx, ConnectionPool dbConnection)
    {
        User currentUser = ctx.sessionAttribute("currentUser");

        try
        {
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            Order order = OrderMapper.getOrder(orderId,dbConnection);
            if (order.getSalesPerson() != null)
            {
                ctx.attribute("message", "Ordren er allerede tildelt");
            }
            else
            {
                int salesId = (currentUser == null ? 0 : currentUser.getUserID());
                if (salesId > 0)
                {
                    OrderMapper.asssignOrder(orderId, salesId, dbConnection);
                    ctx.attribute("message", "Ordren blev tildelt");
                    ctx.redirect("/order/"+orderId);
                }
                else
                {
                    ctx.attribute("message", "Du skal være logget ind for at tildel ordre");
                }
            }
        }
        catch (NumberFormatException e)
        {
            ctx.attribute("message", "Ugyldigt ordre id");
        }
        catch (DatabaseException e)
        {
            ctx.attribute("message", "Databasefejl: " + e.getMessage());
        }
        showOrders(ctx, dbConnection);

    }

    private static void showOrderDetails(@NotNull Context ctx, ConnectionPool dbConnection)
    {
        int orderId = 0;
        Order order = null;
        //ctx.sessionAttribute("currentUser", "Morten");
        if (ctx.sessionAttribute("currentUser") == null)
        {
            ctx.attribute("h1message", "Tilbuds oversigt");
            ctx.attribute("information", "Dimensioner");
            ctx.attribute("pmessage", "Herunder ses dimensionerne på den ønskede carport:");
            ctx.attribute("persinfo", "Dine kontaktoplysninger");
            ctx.attribute("persinfomessage", "Kontroller venligst disse er korrekte:");
        }
        else
        {
            ctx.attribute("h1message", "Ordredetaljer");
            ctx.attribute("information", "Kundens ønsker");
            ctx.attribute("pmessage", "Kunden har sendt følgende informationer ind via hjemmesiden:");
            ctx.attribute("persinfo", "Kundens kontaktoplysninger");
            ctx.attribute("persinfomessage", "Kunden har sendt følgende informationer ind via hjemmesiden:");
        }
        try
        {
            orderId = Integer.parseInt(ctx.pathParam("orderId"));
            order = OrderMapper.getOrder(orderId,dbConnection);
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
        ctx.render("ordredetaljer.html");


    }

    private static void acceptOrder(@NotNull Context ctx, ConnectionPool dbConnection)
    {
        int orderId = 0;
        Order order = null;
        if (ctx.sessionAttribute("currentUser") != null)
        {
            ctx.attribute("message", "Du har ikke adgang til denne side");
            ctx.render("kvittering.html");

            //Der skal lige kigges på det her
        }
        try
        {
            orderId = Integer.parseInt(ctx.formParam("orderId"));
            order = OrderMapper.acceptOrder(orderId, dbConnection);
            ctx.attribute("message", "Tilbuddet er accepteret");
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
        ctx.render("kvittering.html");

    }

    private static void requestChange(@NotNull Context ctx, ConnectionPool dbConnection)
    {
        int orderId = 0;
        String message = null;
        if (ctx.sessionAttribute("currentUser") != null)
        {
            ctx.attribute("message", "Du har ikke adgang til denne side");
            ctx.render("kvittering.html");
        }
        try
        {
            orderId = Integer.parseInt(ctx.formParam("orderId"));
            message = SendGrid.requestChangeEmail(orderId, message);
            ctx.attribute("message", "Ændringsforslag er sendt");
        }
        catch (NumberFormatException e)
        {
            ctx.attribute("message", "Invalid order id");
        }
        catch (DatabaseException e)
        {
            ctx.attribute("message", "Database error. " + e.getMessage());
        }
        ctx.attribute("message", message);
        ctx.render("afvisttilbud.html");
    }

    private static void showDrawing(Context ctx, ConnectionPool dbConnection)
    {
        Carport carport = new Carport(780,600,210,530, RoofType.FLAT, new OptimalWoodCalculator(dbConnection));
        WorkDrawing drawing = new WorkDrawing(carport,780);
        ctx.attribute("drawing", drawing.toString());
        Carport carport2 = new Carport(480,300,0,0, RoofType.FLAT, new OptimalWoodCalculator(dbConnection));
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
