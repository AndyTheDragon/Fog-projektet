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
