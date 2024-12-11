package app.entities;

public interface IMaterials
{
    int getAmount();
    String getDescription();
    int getLength();
    String getType();
    String getUnit();
    int getMaterialID();
    int getHeight();
    int getWidth();
    int getPrice();
    IMaterials setAmount(int i);
}
