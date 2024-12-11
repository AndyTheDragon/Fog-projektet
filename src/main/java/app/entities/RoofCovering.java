package app.entities;

public class RoofCovering implements IMaterials
{
    private int length;
    private int width;
    private int amount;
    private int price;
    private String type;
    private String unit;
    private String description;
    private int materialID;
    private int lineID;

    public RoofCovering(int length, int width, int amount, String type, String unit, String description, int materialID, int price)
    {
        this.length = length;
        this.width = width;
        this.amount = amount;
        this.type = type;
        this.unit = unit;
        this.description = description;
        this.materialID = materialID;
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

    public int getMaterialID()
    {
        return materialID;
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
    public int getPrice()
    {
        return price;
    }

    @Override
    public IMaterials setAmount(int amount)
    {
        this.amount = amount;
        return this;
    }

    @Override
    public int getLineID()
    {
        return lineID;
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
