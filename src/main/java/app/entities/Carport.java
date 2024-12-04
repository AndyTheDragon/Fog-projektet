package app.entities;

import app.services.WorkDrawing;

import java.util.ArrayList;
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
    WorkDrawing workDrawing;

    public Carport()
    {
    }

    public Carport(int length, int width, int shedLength, int shedWidth, RoofType roofType)
    {
        this.length = length;
        this.width = width;
        this.shedLength = shedLength;
        this.shedWidth = shedWidth;
        this.roofType = roofType;
        calculateMaterials();
        this.workDrawing = new WorkDrawing(this, 640);
    }

    public boolean hasShed()
    {
        if (shedLength > 0 && shedWidth > 0) return true;
        else return false;
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
        List <IMaterials> fasciaList = new ArrayList<>();
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
            fasciaList.add((IMaterials) underFascia);
        }
        if(lowPrioAmount > 0)
        {
            underFascia = new ConstructionWood(25, 200, 5400, "stk", "trykimp. Brædt", "understernbrædder til for, bag og siderne", lowPrioAmount, 0);
            fasciaList.add((IMaterials) underFascia);
        }

        return fasciaList;
    }
    private List<IMaterials> calcOverFascia(int length, int width)
    {
        List <IMaterials> fasciaList = new ArrayList<>();
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
            fasciaList.add((IMaterials) overFascia);
        }
        if(lowPrioAmount > 0)
        {
            overFascia = new ConstructionWood(25, 125, 5400, "stk", "trykimp. Brædt", "oversternbrædder til for, bag og siderne", lowPrioAmount, 0);
            fasciaList.add((IMaterials) overFascia);
        }

        return fasciaList;
    }

    private List<IMaterials> calcBeam(int length)
    {
        List <IMaterials> beamList = new ArrayList<>();
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
            beamList.add((IMaterials) beam);
        }
        if(lowPrioAmount > 0)
        {
            beam = new ConstructionWood(45, 195, 6000, "stk", "spærtræ ubh.", "Remme i sider, sadles ned i stolper", lowPrioAmount, 0);
            beamList.add((IMaterials) beam);
        }
        return beamList;
    }

    private List<IMaterials> calcRafters()
    {
        return null;
    }

    private List<IMaterials> calcPosts(int length, int width, int shedLength, int shedWidth)
    {
        List <IMaterials> postList = new ArrayList<>();
        int basePosts = 4;
        int extraPostsForShed = 2;
        int doorPost = 1;
        int extraPostsForWideShed = 2;
        int extraPostsForLongCarport = 2;
        int totalPosts = basePosts;

        // Check if carport has a shed
        if (shedLength > 0 && shedWidth > 0) {
            totalPosts += extraPostsForShed + doorPost;
            if (width > 300) {
                totalPosts += extraPostsForWideShed;
            }
        }

        // Check if carport is longer than 5 meters
        if (extraPostsForLongCarport()) {
            totalPosts += extraPostsForLongCarport;
        }

        ConstructionWood post = new ConstructionWood(97, 97, 3000, "stk", "trykimp. Stolpe", "Stolper nedgraves 90 cm. i jord", totalPosts, 0);
        postList.add((IMaterials) post);

        return postList;
    }
    public boolean extraPostsForLongCarport()
    {
        if (length-130-shedLength > 350) {
            return true;
        }
        return false;
    }

    private List<IMaterials> calcJoists(int length)
    {
        List <IMaterials> joistList = new ArrayList<>();
        ConstructionWood joistBoard;
        int joistSpacing = 55;
        int amountOfJoists = (length/joistSpacing) + 1;
        joistBoard = new ConstructionWood(45, 195, 6000, "stk", "Spærtræ ubh.", "Spær, monteres på rem", amountOfJoists, 0);
        joistList.add((IMaterials) joistBoard);

        return joistList;
    }
    public int getNumberOfJoists()
    {
        int joistThickness = 45; //obs mm
        int minimumSpacing = 45;
        int maximumSpacing = 60;
        int maxGaps = Math.floorDiv(length, minimumSpacing);
        int minGaps = Math.ceilDiv(length, maximumSpacing);
        double error = 1;
        int gaps = 0;
        for (int i= minGaps; i <= maxGaps; i++)
        {
            double currentGap = (double)length/i;
            double currentError = Math.ceil(currentGap)-currentGap;
            if (currentError <= error)
            {
                error = currentError;
                gaps = i;
            }
        }
        return gaps;
    }

    private List<IMaterials> calcBargeBoards(int length, int width)
    {
        List <IMaterials> bargeBoardList = new ArrayList<>();
        int totalLength = (length + width)*2+20;
        int highPrioBoard = 360;
        int lowPrioBoard = 540;
        int[] optimalWood = calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);

        int highPrioAmount = optimalWood[0];
        int lowPrioAmount = optimalWood[1];

        ConstructionWood bargeBoard;
        if (highPrioAmount > 0)
        {
            bargeBoard = new ConstructionWood(19, 100, 3600, "stk", "trykimp. Brædt", "Vandbrædt på stern i siderne", highPrioAmount, 0);
            bargeBoardList.add((IMaterials) bargeBoard);
        }
        if(lowPrioAmount > 0)
        {
            bargeBoard = new ConstructionWood(19, 100, 5400, "stk", "trykimp. Brædt", "Vandbrædt på stern i siderne", lowPrioAmount, 0);
            bargeBoardList.add((IMaterials) bargeBoard);
        }
        return bargeBoardList;
    }

    private List<IMaterials> calcBattern()
    {
        return null;
    }

    private List<IMaterials> calcCladding(int shedLength, int shedWidth)
    {
        List <IMaterials> claddingList = new ArrayList<>();
        ConstructionWood cladding;
        double totalShedLength = (shedLength + shedWidth)*2;
        double claddingBoardAmount = totalShedLength / 7.4;
        int claddingBoardRounded = (int) Math.ceil(claddingBoardAmount);
        cladding = new ConstructionWood(19, 100, 2100, "stk", "trykimp. Brædt", "Til beklædning af skur", claddingBoardRounded, 0);
        claddingList.add((IMaterials) cladding);

        return claddingList;
    }

    private List<IMaterials> calcRoof(int length, int width)
    {
        List<IMaterials>roofList = new ArrayList<>();

        int carportWidth = width;
        int carportLength = length;
        int plateWidth = 109;
        int shortPlateLength = 360;
        int longPlateLength = 600;
        int overlap = 20;

        int platesForWidth = (int) Math.ceil((double) carportWidth / plateWidth);
        int totalLength = carportLength + overlap;
        int [] optimalRoof = calcOptimalWood(totalLength, shortPlateLength, longPlateLength);
        int shortPlatesForLength = optimalRoof[0];
        int longPlatesForLength = optimalRoof[1];

        RoofCovering roofCovering;
        if(shortPlatesForLength > 0)
        {
            roofCovering = new RoofCovering(3600, 109, shortPlatesForLength*platesForWidth, "stk", "Plastmo Ecolite blåtonet", "Tagplader monteres på spær", 0);
            roofList.add((IMaterials) roofCovering);
        }
        if(longPlatesForLength > 0)
        {
            roofCovering = new RoofCovering(6000, 109, longPlatesForLength*platesForWidth, "stk", "Plastmo Ecolite blåtonet", "Tagplader monteres på spær", 0);
            roofList.add((IMaterials) roofCovering);
        }

        return roofList;
    }

    public void calculateMaterials() {
        materialsList = new ArrayList<>();

        materialsList.addAll(calcUnderFascia(length, width));
        materialsList.addAll(calcOverFascia(length, width));
        materialsList.addAll(calcBeam(length));
        materialsList.addAll(calcPosts(length, width, shedLength, shedWidth));
        materialsList.addAll(calcJoists(length));
        materialsList.addAll(calcBargeBoards(length, width));
        materialsList.addAll(calcCladding(shedLength, shedWidth));
        materialsList.addAll(calcRoof(length, width));

        //System.out.println(materialsList);
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

    public void setRoofType(RoofType roofType)
    {
        this.roofType = roofType;
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

    public WorkDrawing getWorkDrawing()
    {
        return workDrawing;
    }
}
