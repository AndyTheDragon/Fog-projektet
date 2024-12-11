package app.entities;

public interface IMaterials
{
    int getAmount();
    String getDescription();
    int getLength();
    String getType();
    String getUnit();
    int getFogProductID();
    int getHeight();
    int getWidth();
    IMaterials setAmount(int i);
}
