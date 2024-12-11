package app.controllers;

import app.entities.*;
import app.exceptions.CalculatorException;
import app.persistence.ConnectionPool;
import app.persistence.CustomerMapper;
import app.persistence.MaterialMapper;
import app.persistence.OrderMapper;
import app.exceptions.DatabaseException;
import app.services.OptimalWoodCalculator;
import app.services.SendGrid;
import app.services.WorkDrawing;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class OrderController
{
    public static void addRoutes(Javalin app, ConnectionPool dbConnection)
    {
        app.get("/bestilling", ctx -> showRequestPage(ctx, dbConnection));
        app.post("/bestilling", ctx -> createOrder(ctx, dbConnection));
        app.get("/draw", ctx -> showDrawing(ctx, dbConnection));
        app.get("/orders", ctx -> showOrders(0, ctx, dbConnection));
        app.get("/orders/{salesId}", ctx -> showMyOrders(ctx, dbConnection));
        app.get("/order/{orderId}", ctx -> showOrderDetails(ctx,dbConnection));
        app.post("/order/accept", ctx -> acceptOrder(ctx,dbConnection));
        app.post("/order/requestchange", ctx -> requestChange(ctx,dbConnection));
        app.post("/order/assign",ctx -> assignOrder(ctx,dbConnection));
        app.get("/order/{orderId}/edit", ctx -> editOrder(ctx, dbConnection));
        app.post("/order/recalculate",ctx -> recalculateOrder(ctx,dbConnection));
        app.post("/order/update", ctx -> updateOrder(ctx,dbConnection));

    }

    public static void showRequestPage(Context ctx, ConnectionPool dbConnection)
    {
        ctx.render("bestilling.html");
    }

    public static void createOrder(Context ctx, ConnectionPool dbConnection)
    {
        try {
            int carportWidth = Integer.parseInt(Objects.requireNonNull(ctx.formParam("carportWidth")));
            int carportLength = Integer.parseInt(Objects.requireNonNull(ctx.formParam("carportLength")));
            int shedWidth = Integer.parseInt(Objects.requireNonNull(ctx.formParam("shedWidth")));
            int shedLength = Integer.parseInt(Objects.requireNonNull(ctx.formParam("shedLength")));

            RoofType carportRoof = "flat".equals(ctx.formParam("carportRoof")) ? RoofType.FLAT : RoofType.FLAT;
            boolean isPaid = false;

            Customer customer = new Customer(ctx.formParam("name"), ctx.formParam("address"), ctx.formParam("zipcode"), ctx.formParam("city"), ctx.formParam("phoneNumber"), ctx.formParam("email"));
            int customerID = CustomerMapper.createCustomer(customer, dbConnection);
            customer.setCustomerID(customerID);

            Order order = new Order(0, customer, new User(), carportWidth, carportLength,
                    shedWidth, shedLength, carportRoof, isPaid, LocalDateTime.now(), LocalDateTime.now(), OrderStatus.UNASSIGNED, new OptimalWoodCalculator(carportLength, carportWidth, shedLength, shedWidth, dbConnection));
            int orderId = OrderMapper.saveOrderToDatabase(order, dbConnection);
            MaterialMapper.createOrderLines(orderId, order.getCarport().getMaterialsList(), dbConnection);
            ctx.attribute("message", "Ordren blev oprettet med succes.");
            ctx.render("kvittering.html");
        } catch (NumberFormatException e) {
            ctx.attribute("message", "Ugyldige dimensioner");
            ctx.render("bestilling.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Databasefejl: " + e.getMessage());
            ctx.render("bestilling.html");
        } catch (CalculatorException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void showDrawing(Context ctx, ConnectionPool dbConnection)
    {
        try
        {
            Carport carport = new Carport(780,600,210,530, RoofType.FLAT, new OptimalWoodCalculator(780, 600, 210, 530, dbConnection));
            WorkDrawing drawing = new WorkDrawing(carport,1250);
            ctx.attribute("drawing", drawing.toString());
            Carport carport2 = new Carport(480,300,0,0, RoofType.FLAT, new OptimalWoodCalculator(480, 300, 0, 0, dbConnection));
            WorkDrawing noShed = new WorkDrawing(carport2, 600);
            ctx.attribute("noshed", noShed.toString());

            ctx.render("drawing.html");
        } catch (CalculatorException e)
        {
            throw new RuntimeException(e);
        }

    }

    private static void showMyOrders(Context ctx, ConnectionPool dbConnection)
    {
        int salesId = Integer.parseInt(ctx.pathParam("salesId"));
        showOrders(salesId, ctx, dbConnection);
    }

    private static void showOrders(int salesId, Context ctx, ConnectionPool connectionPool)
    {
        ctx.sessionAttribute("currentUser", new User());
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser != null)
        {
            try
            {

                Map<String, ArrayList<Order>> orders = OrderMapper.getOrders(salesId, connectionPool);

                ArrayList<Order> assigned = orders.get("assigned");
                ArrayList<Order> unassigned = orders.get("unassigned");

                ctx.attribute("assigned", assigned);
                ctx.attribute("unassigned", unassigned);


            }
            catch (DatabaseException e)
            {
                ctx.attribute("message", "Fejl ved hentning af ordrer: " + e.getMessage());
            }
            catch (NumberFormatException e)
            {
                ctx.attribute("message", "Ugyldigt sælger id");
            }
        }
        ctx.render("ordreoversigt.html");
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
        } catch (CalculatorException e)
        {
            throw new RuntimeException(e);
        }
        ctx.attribute("order", order);
        ctx.render("ordredetaljer.html");

    }

    private static void acceptOrder(@NotNull Context ctx, ConnectionPool dbConnection)
    {
        int orderId = 0;
        if (ctx.sessionAttribute("currentUser") != null)
        {
            ctx.attribute("message", "Du har ikke adgang til denne side");
            ctx.render("kvittering.html");

            //TODO: Der skal lige kigges på det her
        }
        try
        {
            orderId = Integer.parseInt(ctx.formParam("orderId"));
            OrderMapper.acceptOrder(orderId, dbConnection);
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
        } catch (CalculatorException e)
        {
            throw new RuntimeException(e);
        }
        showOrders(0, ctx, dbConnection);

    }

    private static void editOrder(@NotNull Context ctx, ConnectionPool dbConnection)
    {
        int orderId = 0;
        Order order = null;
        if (ctx.sessionAttribute("currentUser") != null)
        {
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
            } catch (CalculatorException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            ctx.attribute("message", "Du har ikke adgang til denne side");
            ctx.render("kvittering.html");
        }
        ctx.attribute("order", order);
        ctx.render("ordreredigering.html");
    }

    private static void recalculateOrder(Context ctx, ConnectionPool dbConnection)
    {
        int orderId = 0;
        if (ctx.sessionAttribute("currentUser") != null)
        {
            try
            {
            orderId = Integer.parseInt(ctx.formParam("orderId"));
            int carportLength = Integer.parseInt(ctx.formParam("carportLength"));
            int carportWidth = Integer.parseInt(ctx.formParam("carportWidth"));
            int shedLength = Integer.parseInt(ctx.formParam("shedLength"));
            int shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
            RoofType carportRoof = "flat".equals(ctx.formParam("carportRoof")) ? RoofType.FLAT : RoofType.FLAT;
            OrderMapper.updateOrder(orderId, carportLength, carportWidth, shedLength, shedWidth, carportRoof, dbConnection);
            ctx.redirect("/order/"+orderId);
            }
            catch (NumberFormatException e)
            {
                ctx.attribute("message", "Invalid order id");
            }
            catch (DatabaseException e)
            {
                ctx.attribute("message", "Database error. " + e.getMessage());
            }
        }
        else
        {
            ctx.attribute("message", "Du har ikke adgang til denne side");
        }
        ctx.render("kvittering.html");

    }

    private static void updateOrder(Context ctx, ConnectionPool dbConnection)
    {
        editOrder(ctx, dbConnection);
    }
}
