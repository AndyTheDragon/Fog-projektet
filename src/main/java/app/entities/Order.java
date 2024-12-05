package app.entities;

import java.time.LocalDateTime;

public class Order
{
    private int orderID;
    private Customer customer;
    private int salesID;
    private int carportWidth;
    private int carportLength;
    private boolean carportShed;
    private int shedWidth;
    private int shedLength;
    private RoofType carportRoof;
    private boolean isPaid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Carport carport;

    public Order(int orderID, Customer customer, int salesID, int carportWidth, int carportLength, int shedWidth, int shedLength, RoofType carportRoof, boolean isPaid, LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        this.orderID = orderID;
        this.customer = customer;
        this.salesID = salesID;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.carportShed = (shedWidth>0&&shedLength>0);
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.carportRoof = carportRoof;
        this.isPaid = isPaid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.carport = new Carport(carportLength, carportWidth, shedLength, shedWidth, carportRoof);
    }

    public Customer getCustomer()
    {
        return customer;
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

    public RoofType getCarportRoof()
    {
        return carportRoof;
    }

    public boolean isPaid()
    {
        return isPaid;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public int getOrderID()
    {
        return orderID;
    }

    public Carport getCarport()
    {
        return carport;
    }

    public void setSalesID(int salesID)
    {
        this.salesID = salesID;
    }
}




