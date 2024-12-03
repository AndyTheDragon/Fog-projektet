package app.entities;

public class BoltsScrewsBrackets
{
    private int length;
    private int width;
    private int amount;
    private String type;
    private String unit;
    private String description;
    private int fogProductID;

    public BoltsScrewsBrackets(int amount, String description, int length, String type, String unit, int width)
    {
        this.amount = amount;
        this.description = description;
        this.length = length;
        this.type = type;
        this.unit = unit;
        this.width = width;
    }

    @Override
    public String toString()
    {
        return "BoltsScrewsBrackets{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", length=" + length +
                ", type='" + type + '\'' +
                ", width=" + width +
                ", unit='" + unit + '\'' +
                '}';
    }
}
