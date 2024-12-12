package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class UserController
{
    public static void addRoutes(Javalin app, ConnectionPool dbconnection) {
        app.get("/", ctx -> ctx.render("login.html"));
        app.post("/login", ctx -> login(ctx, dbconnection));
        app.get("/logout", UserController::logout);

    }

    private static void logout(@NotNull Context ctx)
    {
        ctx.sessionAttribute("currentUser", null);
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }

    public static void login(Context ctx, ConnectionPool dbconnection) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(email, password, dbconnection);
            ctx.sessionAttribute("currentUser", user);

            ctx.redirect("/orders/" + user.getUserID());

        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }
    }
}
