package app.entities;


public class Order {
    private int orderId;
    private int customerId;
    private Integer salesId; // Nullable, derfor Integer

    public Order(int orderId, int customerId, Integer salesId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.salesId = salesId;
    }


    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }

    public Integer getSalesId()
    {
        return salesId;
    }

    public void setSalesId(Integer salesId)
    {
        this.salesId = salesId;
    }
}
