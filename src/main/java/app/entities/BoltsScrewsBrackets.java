package app.entities;

public class BoltsScrewsBrackets implements IMaterials
{
    final int length;
    final int width;
    int amount;
    final int price;
    final String type;
    final String unit;
    final String description;
    final int materialID;

    public BoltsScrewsBrackets(int width, int length, String type, int amount, String unit, String description, int materialID, int price)
    {
        this.amount = amount;
        this.description = description;
        this.length = length;
        this.type = type;
        this.unit = unit;
        this.width = width;
        this.materialID = materialID;
        this.price = price;
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
