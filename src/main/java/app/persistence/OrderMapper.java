package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;

public class OrderMapper
{
    public static Order getOrder(int orderId) throws DatabaseException
    {
        return new Order();
    }
}
