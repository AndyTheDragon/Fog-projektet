package app.entities;

public class ConstructionWood implements IMaterials
{
    final String type;
    final int fogProductID;
    final int width;
    final int height;
    final int length;
    final int price;
    int amount;
    final String unit;
    final String description;

    public ConstructionWood(int height, int width, int length, String unit, String type, String description, int amount, int fogProductID, int price)
    {
        this.width = width;
        this.height = height;
        this.length = length;
        this.unit = unit;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.fogProductID = fogProductID;
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

    public int getFogProductID()
    {
        return fogProductID;
    }

    public int getHeight()
    {
        return height;
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

    public IMaterials setAmount(int amount)
    {
        this.amount = amount;
        return this;
    }

    @Override
    public String toString()
    {
        return "ConstructionWood{" +
                "amount=" + amount +
                ", length=" + length +
                ", description='" + description + '\'' +
                '}';
    }
}
