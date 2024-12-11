package app.entities;

public class RoofCovering implements IMaterials
{
    final int length;
    final int width;
    int amount;
    final int price;
    final String type;
    final String unit;
    final String description;
    final int materialID;

    public RoofCovering(int length, int width, int amount, String type, String unit, String description, int materialID, int price)
    {
        this.length = length;
        this.width = width;
        this.amount = amount;
        this.type = type;
        this.unit = unit;
        this.description = description;
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
