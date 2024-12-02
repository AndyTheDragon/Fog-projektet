package app.entities;

import java.sql.Timestamp;

public class Order
{
    private int orderID;
    private int customerID;
    private int salesID;
    private int carportWidth;
    private int carportLength;
    private boolean carportShed;
    private int shedWidth;
    private int shedLength;
    private String carportRoof;
    private boolean isPaid;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Order(int orderID, int customerID, int salesID, int carportWidth, int carportLength, boolean carportShed, int shedWidth, int shedLength, String carportRoof, boolean isPaid, Timestamp createdAt, Timestamp updatedAt)
    {
        this.orderID = orderID;
        this.customerID = customerID;
        this.salesID = salesID;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.carportShed = carportShed;
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.carportRoof = carportRoof;
        this.isPaid = isPaid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public int getSalesID()
    {
        return salesID;
    }

    public int getCarportWidth()
    {
        return carportWidth;
    }

    public int getCarportLength()
    {
        return carportLength;
    }

    public boolean isCarportShed()
    {
        return carportShed;
    }

    public int getShedWidth()
    {
        return shedWidth;
    }

    public int getShedLength()
    {
        return shedLength;
    }

    public String getCarportRoof()
    {
        return carportRoof;
    }

    public boolean isPaid()
    {
        return isPaid;
    }

    public Timestamp getCreatedAt()
    {
        return createdAt;
    }

    public Timestamp getUpdatedAt()
    {
        return updatedAt;
    }

    public int getOrderID()
    {
        return orderID;
    }
}




