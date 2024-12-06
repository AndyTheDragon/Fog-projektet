package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper
{

    public static User login(String email, String password, ConnectionPool dbconnection) throws DatabaseException
    {
        String sql = "SELECT * FROM account WHERE email=? AND user_password=?";

        try (Connection connection = dbconnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                boolean isAdmin = rs.getBoolean("is_admin");
                return new User(userId, userName, email, password, isAdmin);
            }
            else
            {
                throw new DatabaseException("Forkert email eller adgangskode");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Message "+ e.getMessage());
        }
    }
}
