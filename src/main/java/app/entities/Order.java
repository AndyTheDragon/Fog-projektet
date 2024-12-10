package app.entities;

import java.time.LocalDateTime;

public class Order
{
    private int orderID;
    private Customer customer;
    private User salesPerson;
    private int carportWidth;
    private int carportLength;
    private int carportHeight;
    private boolean carportShed;
    private int shedWidth;
    private int shedLength;
    private RoofType carportRoof;
    private boolean isPaid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStatus orderStatus;
    private Carport carport;

    public Order(int orderID, Customer customer, User salesPerson, int carportWidth, int carportLength, int shedWidth, int shedLength, RoofType carportRoof, boolean isPaid, LocalDateTime createdAt, LocalDateTime updatedAt, CarportCalculator calculator)
    {
        this.orderID = orderID;
        this.customer = customer;
        this.salesPerson = salesPerson;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.carportHeight =230;
        this.carportShed = (shedWidth>0&&shedLength>0);
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.carportRoof = carportRoof;
        this.isPaid = isPaid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.carport = new Carport(carportLength, carportWidth, shedLength, shedWidth, carportRoof,calculator);
    }

    public Order(int newOrderId, Customer customer, User salesPerson, int carportWidth, int carportLength, int carportHeight, boolean carportShed, int shedWidth, int shedLength, RoofType carportRoof, boolean paid)
    {
        this.orderID = newOrderId;
        this.customer = customer;
        this.salesPerson = salesPerson;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.carportHeight = carportHeight;
        this.carportShed = carportShed;
        this.shedWidth = shedWidth;
        this.shedLength = shedLength;
        this.carportRoof = carportRoof;
        this.isPaid = paid;
    }


    public Customer getCustomer()
    {
        return customer;
    }

    public User getSalesPerson()
    {
        return salesPerson;
    }

    public int getCarportHeight()
    {
        return carportHeight;
    }
    public int getSalesID()
    {
        return salesPerson.getUserID();
    }

    public int getCarportWidth()
    {
        return carportWidth;
    }

    public int getCarportLength()
    {
        return carportLength;
    }

    public boolean getCarportShed()
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

}




