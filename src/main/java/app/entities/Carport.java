package app.entities;

import java.util.List;

public class Carport
{
    List<IMaterials> materialsList;
    int length;
    int width;
    int height;
    boolean hasShed;
    RoofType roofType;

    public Carport()
    {
    }

    public Carport(int length, int width, int height, boolean hasShed, RoofType roofType)
    {
        this.length = length;
        this.width = width;
        this.height = height;
        this.hasShed = hasShed;
        this.roofType = roofType;
    }

    public int[] calcOptimalWood(int totalLength, int highPrioBoard, int lowPrioBoard)
    {
        double wastePercentage = 1.05;
        if (totalLength % highPrioBoard == 0 || totalLength % lowPrioBoard == 0)
        {
            wastePercentage = 1.0;
        }
        double totalLengthInclWaste = totalLength * wastePercentage;
        int maxPieces = 10;     // Max pieces of each length

        // Variables to store best result
        int bestX = 0, bestY = 0;
        double bestWaste = Double.MAX_VALUE;

        // Loop to find best combination
        for (int x = 0; x <= maxPieces; x++)
        {
            for (int y = 0; y <= maxPieces; y++)
            {
                // calculate total length of this combination
                double calculatedLength = x * lowPrioBoard + y * highPrioBoard;

                // Control if total length is reached
                if (calculatedLength >= totalLengthInclWaste)
                {
                    // Calculate waste
                    double waste = calculatedLength - totalLengthInclWaste;

                    // update best result if this is better
                    if (waste < bestWaste)
                    {
                        bestX = x;
                        bestY = y;
                        bestWaste = waste;
                    }
                }
            }

        }
        return new int[]{bestY, bestX};
    }

    private List<IMaterials> calcFascia(int length, int width)
    {
        return null;
    }

    private List<IMaterials> calcBeam()
    {
        return null;
    }

    private List<IMaterials> calcRafters()
    {
        return null;
    }

    private List<IMaterials> calcPosts()
    {
        return null;
    }

    private List<IMaterials> calcJoists(int length)
    {
        int joistSpacing = 55;
        int amountOfJoists = (length/joistSpacing) + 1;


        return null;
    }

    private List<IMaterials> calcBargeBoards()
    {
        return null;
    }

    private List<IMaterials> calcBattern()
    {
        return null;
    }

    private List<IMaterials> calcCladding()
    {
        return null;
    }
}
