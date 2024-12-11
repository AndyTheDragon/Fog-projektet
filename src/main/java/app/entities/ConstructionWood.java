package app.entities;

public class ConstructionWood implements IMaterials
{
    final String type;
    final int materialID;
    final int width;
    final int height;
    final int length;
    final int price;
    int amount;
    final String unit;
    final String description;
    int lineID;

    public ConstructionWood(int height, int width, int length, String unit, String type, String description, int amount, int materialID, int price)
    {
        this.width = width;
        this.height = height;
        this.length = length;
        this.unit = unit;
        this.type = type;
        this.description = description;
        this.amount = amount;
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

    public int getMaterialID()
    {
        return materialID;
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
    public int getLineID()
    {
        return lineID;
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
