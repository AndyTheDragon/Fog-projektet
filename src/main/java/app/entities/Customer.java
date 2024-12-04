package app.entities;

import java.sql.Timestamp;

public class Customer
{
    private String customerName;
    private int customerId;
    private String address;
    private String zipcode;
    private String city;
    private String phoneNumber;
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Customer(String customerName, int customerId, String address, String zipcode, String city, String phoneNumber, String email, Timestamp createdAt, Timestamp updatedAt)
    {
        this.customerName = customerName;
        this.customerId = customerId;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public String getAddress()
    {
        return address;
    }

    public String getZipcode()
    {
        return zipcode;
    }

    public String getCity()
    {
        return city;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public Timestamp getCreatedAt()
    {
        return createdAt;
    }

    public Timestamp getUpdatedAt()
    {
        return updatedAt;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setZipcode(String zipcode)
    {
        this.zipcode = zipcode;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setCreatedAt(Timestamp createdAt)
    {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt)
    {
        this.updatedAt = updatedAt;
    }
}
