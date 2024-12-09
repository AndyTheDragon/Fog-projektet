package app.entities;

public class RoofCovering implements IMaterials
{
    private int length;
    private int width;
    private int amount;
    private String type;
    private String unit;
    private String description;
    private int fogProductID;

    public RoofCovering(int length, int width, int amount, String type, String unit, String description, int fogProductID)
    {
        this.length = length;
        this.width = width;
        this.amount = amount;
        this.type = type;
        this.unit = unit;
        this.description = description;
        this.fogProductID = fogProductID;
    }

    public int getAmount()
    {
        return amount;
    }

    public String getDescription()
    {
        return description;
    }

    public int getLength()
    {
        return length;
    }

    public String getType()
    {
        return type;
    }

    public String getUnit()
    {
        return unit;
    }

    public int getFogProductID()
    {
        return fogProductID;
    }

    @Override
    public int getHeight()
    {
        return 0;
    }

    public int getWidth()
    {
        return width;
    }

    @Override
    public String toString()
    {
        return "RoofCovering{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", length=" + length +
                ", width=" + width +
                '}';
    }
}
