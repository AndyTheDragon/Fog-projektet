package app.persistence;

import app.entities.Customer;
import app.entities.Order;
import app.entities.RoofType;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrderMapper
{
    public static Order getOrder(int orderId) throws DatabaseException
    {
        return new Order(1,
                new Customer(),
                new User(),
                600,
                780,
                530,
                210,
                RoofType.FLAT,
                false,
                LocalDateTime.now(),
                LocalDateTime.now());
    }
}
