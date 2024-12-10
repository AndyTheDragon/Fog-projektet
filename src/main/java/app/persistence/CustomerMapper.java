package app.persistence;

import app.entities.Customer;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper
{
    public static int createCustomer (Customer customer, ConnectionPool dbConnection) throws DatabaseException
    {
        String sql = "INSERT INTO customer (customer_name, address, zipcode, city, phone_number, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getZipcode());
            ps.setString(4, customer.getCity());
            ps.setString(5, customer.getPhoneNumber());
            ps.setString(6, customer.getEmail());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1)
            {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            }
            else
            {
                return -1;
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Error creating customer "+ e.getMessage());
        }
    }

}
