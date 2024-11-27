package app.entities;

public class Order
{   private int id;
    private String status;
    private String customer;
    private double total;


    public Order(int id, String status, String customer, double total)
    {
        this.id = id;
        this.status = status;
        this.customer = customer;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomer() {
        return customer;
    }

    public double getTotal() {
        return total;
    }
}
