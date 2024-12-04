package app.entities;

public class User
{
    int userID;
    String name;
    String email;
    String password;
    boolean isAdmin;

    public User(int userID, String name, String email, String password, boolean isAdmin)
    {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User()
    {
        this.userID = 1;
        this.name = "Martin";
        this.email = "Fog@fog.dk";
        this.password = "1234";
        this.isAdmin = true;
    }

    public int getUserID()
    {
        return userID;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }
}
