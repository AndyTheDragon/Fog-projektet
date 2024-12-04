package app.entities;

public class BoltsScrewsBrackets implements IMaterials
{
    private int length;
    private int width;
    private int amount;
    private String type;
    private String unit;
    private String description;
    private int fogProductID;

    public BoltsScrewsBrackets(int width, int length,String type, int amount,  String unit, String description, int fogProductID)
    {
        this.amount = amount;
        this.description = description;
        this.length = length;
        this.type = type;
        this.unit = unit;
        this.width = width;
        this.fogProductID = fogProductID;
    }

    @Override
    public String toString()
    {
        return "BoltsScrewsBrackets{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
