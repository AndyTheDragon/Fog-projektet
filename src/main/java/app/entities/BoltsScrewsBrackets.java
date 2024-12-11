package app.entities;

public class BoltsScrewsBrackets implements IMaterials
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

    public BoltsScrewsBrackets(int width, int length, String type, int amount, String unit, String description, int materialID, int price)
    {
        this.amount = amount;
        this.description = description;
        this.length = length;
        this.type = type;
        this.unit = unit;
        this.width = width;
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

    @Override
    public int getPrice()
    {
        return price;
    }

    @Override
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
    public IMaterials setAmount(int i)
    {
        this.amount = i;
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
        return "BoltsScrewsBrackets{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
