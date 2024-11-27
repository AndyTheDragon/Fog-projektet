package app.entities;

import java.util.List;

public class Carport
{
    List<IMaterials> materialsList;
    int length;
    int width;
    int height;
    int shedLength;
    int shedWidth;
    RoofType roofType;

    public Carport()
    {
    }

    public Carport(int length, int width, int height, int shedLength, int shedWidth, RoofType roofType)
    {
        this.length = length;
        this.width = width;
        this.height = height;
        this.shedLength = shedLength;
        this.shedWidth = shedWidth;
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

    private List<IMaterials> calcUnderFascia(int length, int width)
    {
        int totalLength = (length + width)*2;
        int highPrioBoard = 360;
        int lowPrioBoard = 540;
        int[] optimalWood = calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);

        int highPrioAmount = optimalWood[0];
        int lowPrioAmount = optimalWood[1];

        ConstructionWood underFascia;
        if (highPrioAmount > 0)
        {
            underFascia = new ConstructionWood(25, 200, 3600, "stk", "trykimp. Brædt", "understernbrædder til for, bag og siderne", highPrioAmount, 0);
            materialsList.add((IMaterials) underFascia);
        }
        if(lowPrioAmount > 0)
        {
            underFascia = new ConstructionWood(25, 200, 5400, "stk", "trykimp. Brædt", "understernbrædder til for, bag og siderne", lowPrioAmount, 0);
            materialsList.add((IMaterials) underFascia);
        }

        return materialsList;
    }
    private List<IMaterials> calcOverFascia(int length, int width)
    {
        int totalLength = (length + width)*2+5;
        int highPrioBoard = 360;
        int lowPrioBoard = 540;
        int[] optimalWood = calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);

        int highPrioAmount = optimalWood[0];
        int lowPrioAmount = optimalWood[1];

        ConstructionWood overFascia;
        if (highPrioAmount > 0)
        {
            overFascia = new ConstructionWood(25, 125, 3600, "stk", "trykimp. Brædt", "oversternbrædder til for, bag og siderne", highPrioAmount, 0);
            materialsList.add((IMaterials) overFascia);
        }
        if(lowPrioAmount > 0)
        {
            overFascia = new ConstructionWood(25, 125, 5400, "stk", "trykimp. Brædt", "oversternbrædder til for, bag og siderne", lowPrioAmount, 0);
            materialsList.add((IMaterials) overFascia);
        }

        return materialsList;
    }

    private List<IMaterials> calcBeam(int length)
    {
        int totalLength = length*2;
        int highPrioBoard = 480;
        int lowPrioBoard = 600;
        int[] optimalWood = calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);

        int highPrioAmount = optimalWood[0];
        int lowPrioAmount = optimalWood[1];

        ConstructionWood beam;
        if (highPrioAmount > 0)
        {
            beam = new ConstructionWood(45, 195, 4800, "stk", "spærtræ ubh.", "Remme i sider, sadles ned i stolper (skur del, deles)", highPrioAmount, 0);
            materialsList.add((IMaterials) beam);
        }
        if(lowPrioAmount > 0)
        {
            beam = new ConstructionWood(45, 195, 6000, "stk", "spærtræ ubh.", "Remme i sider, sadles ned i stolper", lowPrioAmount, 0);
            materialsList.add((IMaterials) beam);
        }
        return materialsList;
    }

    private List<IMaterials> calcRafters()
    {
        return null;
    }

    private List<IMaterials> calcPosts()
    {
        ConstructionWood post;
        if(shedLength && shedWidth)
        return null;
    }

    private List<IMaterials> calcJoists(int length)
    {
        ConstructionWood joistBoard;
        int joistSpacing = 55;
        int amountOfJoists = (length/joistSpacing) + 1;
        joistBoard = new ConstructionWood(45, 195, 6000, "stk", "Spærtræ ubh.", "Spær, monteres på rem", amountOfJoists, 0);
        materialsList.add((IMaterials) joistBoard);

        return materialsList;
    }

    private List<IMaterials> calcBargeBoards()
    {
        return null;
    }

    private List<IMaterials> calcBattern()
    {
        return null;
    }

    private List<IMaterials> calcCladding(int shedLength, int shedWidth)
    {
        ConstructionWood cladding;
        double totalShedLength = (shedLength + shedWidth)*2;
        double claddingBoardAmount = totalShedLength / 15;
        int claddingBoardRounded = (int) Math.ceil(claddingBoardAmount);
        cladding = new ConstructionWood(19, 100, 2100, "stk", "trykimp. Brædt", "Til beklædning af skur", claddingBoardRounded, 0);
        materialsList.add((IMaterials) cladding);

        return materialsList;
    }

    public int getHeight()
    {
        return height;
    }

    public int getLength()
    {
        return length;
    }

    public List<IMaterials> getMaterialsList()
    {
        return materialsList;
    }

    public RoofType getRoofType()
    {
        return roofType;
    }

    public int getShedLength()
    {
        return shedLength;
    }

    public int getShedWidth()
    {
        return shedWidth;
    }

    public int getWidth()
    {
        return width;
    }
}
