package app.entities;

public class Customer
{
    int customerID;
    String name;
    String address;
    String zipcode;
    String city;
    String phoneNumber;
    String email;

    public Customer(String name, String address, String zipcode, String city, String phoneNumber, String email)
    {
        this.name = name;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public String getName()
    {
        return name;
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
}



