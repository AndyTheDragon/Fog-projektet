package app.entities;

public class ConstructionWood
{
    final String type;
    final int fogProductID;
    final int width;
    final int height;
    final int length;
    final int amount;
    final String unit;
    final String description;

    public ConstructionWood(int height, int width,  int length, String unit, String type, String description, int amount, int fogProductID)
    {
        this.width = width;
        this.height = height;
        this.length = length;
        this.unit = unit;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.fogProductID = fogProductID;
    }
}
