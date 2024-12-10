package app.entities;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;

import java.util.ArrayList;
import java.util.List;



public class OptimalWoodCalculator implements CarportCalculator
{
    private final ConnectionPool dbConnection;
    private int carportLength;
    private int carportWidth;
    private int carportHeight;
    private int shedLength;
    private int shedWidth;
    private String shedPlacement;

    public OptimalWoodCalculator(int carportLength, int carportWidth, int shedLength, int shedWidth, ConnectionPool dbConnection)
    {
        this.dbConnection = dbConnection;
        this.carportLength = carportLength;
        this.carportWidth = carportWidth;
        this.shedLength = shedLength;
        this.shedWidth = shedWidth;
        this.carportHeight = 230;
        this.shedPlacement = "right";
    }

    public int[] calcOptimalWood(int totalLength, int highPrioBoard, int lowPrioBoard)
    {
        double wastePercentage = 1.05;
        if (totalLength % highPrioBoard == 0 || totalLength % lowPrioBoard == 0)
        {
            wastePercentage = 1.00;
        }
        double totalLengthInclWaste = totalLength * wastePercentage;
        int maxPieces = 20;     // Max pieces of each length

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

    public int[] calcOptimalWood(int totalLength, List<IMaterials> conWoodList)
    {
        double wastePercentage = 1.05;
        boolean exactMatchFound = conWoodList.stream().anyMatch(board -> totalLength % board.getLength() == 0);
        if (exactMatchFound)
        {
            wastePercentage = 1.0;
        }
        double totalLengthInclWaste = totalLength * wastePercentage;
        int maxPieces = 10;

        int[] bestResult = new int[conWoodList.size()];
        double bestWaste = Double.MAX_VALUE;

        int[] indices = new int[conWoodList.size()];

        while(true)
        {
        double curentLength = 0;
            for (int i = 0; i < conWoodList.size(); i++)
            {
                curentLength += indices[i] * conWoodList.get(i).getLength();
            }
            if (curentLength >= totalLengthInclWaste)
            {
                double waste = curentLength - totalLengthInclWaste;
                if (waste < bestWaste)
                {
                    bestWaste = waste;
                    System.arraycopy(indices, 0, bestResult, 0, indices.length);
                }
            }
            int index = 0;
            while (index < conWoodList.size())
            {
                if (indices[index] < maxPieces)
                {
                    indices[index]++;
                    break;
                } else
                {
                    indices[index] = 0;
                    index++;
                }
            }
            if (index == conWoodList.size())
            {
                break;
            }
        }
        return bestResult;
    }

    public List<IMaterials> calcUnderFascia() throws DatabaseException
    {
        List<IMaterials> allFasciaList = MaterialMapper.getMaterialOfType("understernbrædder", dbConnection );
        List <IMaterials> fasciaList = new ArrayList<>();
        int totalLength = (carportLength + carportWidth)*2;
        int highPrioBoard = 360;
        int lowPrioBoard = 540;
        int[] optimalWood = calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);

        int highPrioAmount = optimalWood[0];
        int lowPrioAmount = optimalWood[1];

        IMaterials underFascia;
        if (highPrioAmount > 0)
        {
            underFascia = new ConstructionWood(25, 200, 3600, "stk", "trykimp. Brædt", "understernbrædder til for, bag og siderne", highPrioAmount, 0);
            fasciaList.add( underFascia);
        }
        if(lowPrioAmount > 0)
        {
            underFascia = new ConstructionWood(25, 200, 5400, "stk", "trykimp. Brædt", "understernbrædder til for, bag og siderne", lowPrioAmount, 0);
            fasciaList.add(underFascia);
        }

        return fasciaList;
    }
    public List<IMaterials> calcOverFascia()
    {
        List <IMaterials> fasciaList = new ArrayList<>();
        int totalLength = (carportLength + carportWidth)*2+5;
        int highPrioBoard = 360;
        int lowPrioBoard = 540;
        int[] optimalWood = calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);

        int highPrioAmount = optimalWood[0];
        int lowPrioAmount = optimalWood[1];

        IMaterials overFascia;
        if (highPrioAmount > 0)
        {
            overFascia = new ConstructionWood(25, 125, 3600, "stk", "trykimp. Brædt", "oversternbrædder til for, bag og siderne", highPrioAmount, 0);
            fasciaList.add(overFascia);
        }
        if(lowPrioAmount > 0)
        {
            overFascia = new ConstructionWood(25, 125, 5400, "stk", "trykimp. Brædt", "oversternbrædder til for, bag og siderne", lowPrioAmount, 0);
            fasciaList.add(overFascia);
        }

        return fasciaList;
    }

    public List<IMaterials> calcBeam()
    {
        List <IMaterials> beamList = new ArrayList<>();
        int totalLength = carportLength*2;
        int highPrioBoard = 480;
        int lowPrioBoard = 600;
        int[] optimalWood = calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);

        int highPrioAmount = optimalWood[0];
        int lowPrioAmount = optimalWood[1];

        IMaterials beam;
        if (highPrioAmount > 0)
        {
            beam = new ConstructionWood(45, 195, 4800, "stk", "spærtræ ubh.", "Remme i sider, sadles ned i stolper (skur del, deles)", highPrioAmount, 0);
            beamList.add(beam);
        }
        if(lowPrioAmount > 0)
        {
            beam = new ConstructionWood(45, 195, 6000, "stk", "spærtræ ubh.", "Remme i sider, sadles ned i stolper", lowPrioAmount, 0);
            beamList.add(beam);
        }
        return beamList;
    }

    public List<IMaterials> calcRafters()
    {
        return null;
    }

    public List<IMaterials> calcPosts()
    {
        List <IMaterials> postList = new ArrayList<>();
        int totalPosts = calcNumberOfPosts();

        IMaterials post = new ConstructionWood(97, 97, 3000, "stk", "trykimp. Stolpe", "Stolper nedgraves 90 cm. i jord", totalPosts, 0);
        postList.add(post);

        return postList;
    }
    public int calcNumberOfPosts()
    {
        int basePosts = 4;
        int extraPostsForShed = 2;
        int doorPost = 1;
        int extraPostsForWideShed = 2;
        int extraPostsForLongCarport = 2;
        int totalPosts = basePosts;

        // Check if carport has a shed
        if (shedLength > 0 && shedWidth > 0) {
            totalPosts += extraPostsForShed + doorPost;
            if (carportWidth > 300) {
                totalPosts += extraPostsForWideShed;
            }
        }

        // Check if carport is longer than 5 meters
        if (extraPostsForLongCarport()) {
            totalPosts += extraPostsForLongCarport;
        }
        return totalPosts;
    }
    public boolean extraPostsForLongCarport()
    {
        return carportLength - 130 - shedLength > 350;
    }

    public List<IMaterials> calcJoists()
    {
        List <IMaterials> joistList = new ArrayList<>();
        IMaterials joistBoard;
        // *********************** //
        // TODO: SKal refactores til at bruge calcNumberOfJoists
        // ********************** //
        int joistSpacing = 55;
        int amountOfJoists = (carportLength/joistSpacing) + 1;
        joistBoard = new ConstructionWood(45, 195, 6000, "stk", "Spærtræ ubh.", "Spær, monteres på rem", amountOfJoists, 0);
        joistList.add(joistBoard);

        return joistList;
    }
    public int calcNumberOfJoists()
    {
        int joistThickness = 45; //obs mm
        int minimumSpacing = 45;
        int maximumSpacing = 60;
        int maxGaps = Math.floorDiv(carportLength, minimumSpacing);
        int minGaps = Math.ceilDiv(carportLength, maximumSpacing);
        double error = 1;
        int gaps = 0;
        for (int i= minGaps; i <= maxGaps; i++)
        {
            double currentGap = (double)carportLength/i;
            double currentError = Math.ceil(currentGap)-currentGap;
            if (currentError <= error)
            {
                error = currentError;
                gaps = i;
            }
        }
        return gaps;
    }

    public List<IMaterials> calcBargeBoards()
    {
        List <IMaterials> bargeBoardList = new ArrayList<>();
        int totalLength = (carportLength + carportWidth)*2+20;
        int highPrioBoard = 360;
        int lowPrioBoard = 540;
        int[] optimalWood = calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);

        int highPrioAmount = optimalWood[0];
        int lowPrioAmount = optimalWood[1];

        IMaterials bargeBoard;
        if (highPrioAmount > 0)
        {
            bargeBoard = new ConstructionWood(19, 100, 3600, "stk", "trykimp. Brædt", "Vandbrædt på stern i siderne", highPrioAmount, 0);
            bargeBoardList.add(bargeBoard);
        }
        if(lowPrioAmount > 0)
        {
            bargeBoard = new ConstructionWood(19, 100, 5400, "stk", "trykimp. Brædt", "Vandbrædt på stern i siderne", lowPrioAmount, 0);
            bargeBoardList.add(bargeBoard);
        }
        return bargeBoardList;
    }

    public List<IMaterials> calcBattern()
    {
        return null;
    }

    public List<IMaterials> calcCladding()
    {
        List<IMaterials> claddingList = new ArrayList<>();
        IMaterials cladding;

        int claddingBoardRounded = calcNumberOfCladdingBoards();
        cladding = new ConstructionWood(19, 100, 2100, "stk", "trykimp. Brædt", "Til beklædning af skur", claddingBoardRounded, 0);
        claddingList.add(cladding);

        return claddingList;
    }

    public int calcNumberOfCladdingBoards()
    {
        double totalShedLength = (shedLength + shedWidth)*2;
        double claddingBoardAmount = totalShedLength / 7.4;

        return (int) Math.ceil(claddingBoardAmount);
    }

    public List<IMaterials> calcHorizontalBraces()
    {
        List<IMaterials> horizontalBraceList = new ArrayList<>();
        IMaterials horizontalBrace;

        int totalBraces = calcNumberOfHorizontalBraces();

        horizontalBrace = new ConstructionWood(45, 95,3000, "stk", "reglar ubh.", "løsholter til skur.", totalBraces, 0);
        horizontalBraceList.add(horizontalBrace);

        return horizontalBraceList;
    }

    public int calcNumberOfHorizontalBraces()
    {
        int totalBraces = 0;
        int bracesPerSection = 2;
        int extraBracesForWideness = 4;

        totalBraces += 4*bracesPerSection;
        if (shedWidth > 300)
        {
            totalBraces += extraBracesForWideness;
        }
        if (shedLength > 300)
        {
            totalBraces += extraBracesForWideness;
        }
        return totalBraces;
    }

    public List<IMaterials> calcRoof()
    {
        List<IMaterials>roofList = new ArrayList<>();



        int plateWidth = 109;
        int shortPlateLength = 360;
        int longPlateLength = 600;
        int overlap = 20;

        int platesForWidth = (int) Math.ceil((double) carportWidth / plateWidth);
        int totalLength = carportLength + overlap;
        int [] optimalRoof = calcOptimalWood(totalLength, shortPlateLength, longPlateLength);
        int shortPlatesForLength = optimalRoof[0];
        int longPlatesForLength = optimalRoof[1];

        IMaterials roofCovering;
        if(shortPlatesForLength > 0)
        {
            roofCovering = new RoofCovering(3600, 109, shortPlatesForLength*platesForWidth, "stk", "Plastmo Ecolite blåtonet", "Tagplader monteres på spær", 0);
            roofList.add(roofCovering);
        }
        if(longPlatesForLength > 0)
        {
            roofCovering = new RoofCovering(6000, 109, longPlatesForLength*platesForWidth, "stk", "Plastmo Ecolite blåtonet", "Tagplader monteres på spær", 0);
            roofList.add(roofCovering);
        }

        return roofList;
    }

    public List<IMaterials> calcRoofScrews()
    {
        List <IMaterials> roofScrewList = new ArrayList<>();
        IMaterials roofScrews;
        int screwsPerSqrMeter = 12;
        int roofArea = carportLength * carportWidth;

        int totalScrews = (roofArea * screwsPerSqrMeter)/10000;
        int screwPacks = Math.ceilDiv( totalScrews,200);
        roofScrews = new BoltsScrewsBrackets(4,50,"plastmo bundskruer, 200 stk", screwPacks,"pakkke","Til montering af tagplader", 0);
        roofScrewList.add(roofScrews);

        return roofScrewList;
    }

    public List<IMaterials> calcJoistBrackets()
    {
        List<IMaterials> joistBracketList = new ArrayList<>();
        IMaterials rightJoistBracket;
        IMaterials leftJoistBracket;

        int joistAmount = calcNumberOfJoists();
        int rightBracketAmount = joistAmount;
        int leftBracketAmount = joistAmount;

        rightJoistBracket = new BoltsScrewsBrackets(0,0,"universalbeslag højre", rightBracketAmount,"stk","Til montering af spær på rem", 0);
        leftJoistBracket = new BoltsScrewsBrackets(0,0,"universalbeslag left", leftBracketAmount,"stk","Til montering af spær på rem", 0);

        joistBracketList.add(rightJoistBracket);
        joistBracketList.add(leftJoistBracket);

        return joistBracketList;
    }

    public List<IMaterials> calcFasciaBargeScrews()
    {
        List<IMaterials> fasciaBargeScrewList = new ArrayList<>();
        IMaterials fasciaBargeScrews;
        int totalLength = ((carportLength*6)+(carportWidth*4));
        int totalScrews = totalLength/70;
        int screwPacks = Math.ceilDiv( totalScrews,200);

        fasciaBargeScrews = new BoltsScrewsBrackets((int) 4.5,50,"skruer 200stk", screwPacks,"pakke","Til montering af stern & vandbrædt", 0);
        fasciaBargeScrewList.add(fasciaBargeScrews);
        return fasciaBargeScrewList;
    }

    public List<IMaterials> calcJoistBracketScrews()
    {
        List<IMaterials> bracketScrewList = new ArrayList<>();
        IMaterials bracketScrews;

        int joistAmount = calcNumberOfJoists();
        int screwsPerJoist =  25;
        int totalScrews = joistAmount * screwsPerJoist;
        int screwPacks = Math.ceilDiv( totalScrews,200);

        bracketScrews = new BoltsScrewsBrackets(4,50,"beslagskruer, 250stk", screwPacks,"pakke","Til montering af beslag på spær", 0);
        bracketScrewList.add(bracketScrews);

        return bracketScrewList;
    }

    public List<IMaterials> calcMetalStrap()
    {
        List<IMaterials> metalStrapList = new ArrayList<>();
        IMaterials perfMetalStrap;
        double crossLength = Math.sqrt(carportLength^2+carportWidth^2);
        if (crossLength < 500)
        {
            int totalStraps = 1;
            perfMetalStrap = new BoltsScrewsBrackets(10,120,"perforeret stålbånd",totalStraps,"rulle","Til vindkryds på spær",0);
            metalStrapList.add(perfMetalStrap);
        }
        if(crossLength > 500 )
        {
            int totalStraps = 2;
            perfMetalStrap = new BoltsScrewsBrackets(10,120,"perforeret stålbånd",totalStraps,"rulle","Til vindkryds på spær",0);
            metalStrapList.add(perfMetalStrap);
        }
        return metalStrapList;
    }

    public List<IMaterials> calcBeamBolts()
    {
        List<IMaterials> beamBoltList = new ArrayList<>();
        IMaterials beamBolt;
        IMaterials boltDisc;
        int totalBolts = 0;
        int boltPerPost = 2;
        int extraBoltPerSeam = 4;
        totalBolts += boltPerPost * calcNumberOfPosts();
        if (carportLength > 600)
        {
            totalBolts += extraBoltPerSeam;
        }
        boltDisc = new BoltsScrewsBrackets(10, 120, "firkantskiver", totalBolts, "stk", "til montering af rem på stolper", 0);
        beamBolt = new BoltsScrewsBrackets(10, 120, "bræddebolt", totalBolts, "stk", "til montering af rem på stolper", 0);
        beamBoltList.add(boltDisc);
        beamBoltList.add(beamBolt);

        return beamBoltList;
    }

    public List<IMaterials> calcCladdingScrews()
    {
        List<IMaterials> claddingScrewList = new ArrayList<>();
        IMaterials innerCladdingScrews;
        IMaterials outerCladdingScrews;

        int screwsPerCladding = 6;
        int totalInnerScrews = screwsPerCladding * (calcNumberOfCladdingBoards() / 2);
        int totalOuterScrews = screwsPerCladding * (calcNumberOfCladdingBoards() / 2);

        int innerScrewsPacks = Math.ceilDiv( totalInnerScrews, 300);
        int outerScrewsPacks = Math.ceilDiv( totalOuterScrews, 400);

        innerCladdingScrews = new BoltsScrewsBrackets((int)4.5,50,"skruer 300stk", innerScrewsPacks,"pakke","Til montering af inderste beklædning", 0);
        outerCladdingScrews = new BoltsScrewsBrackets((int)4.5,70,"skruer 400stk", outerScrewsPacks,"pakke","Til montering af  yderste beklædning", 0);

        claddingScrewList.add(innerCladdingScrews);
        claddingScrewList.add(outerCladdingScrews);

        return claddingScrewList;
    }

    public List<IMaterials> doorHandleBrackets()
    {
        List<IMaterials> doorHandleBracketList = new ArrayList<>();
        IMaterials doorHandle;
        IMaterials doorBracket;

        doorHandle = new BoltsScrewsBrackets(50,75,"stalddørsgreb", 1,"sæt","Til lås på dør i skur", 0);
        doorBracket = new BoltsScrewsBrackets(390,0,"t hængsel", 2,"stk","Til skurdør", 0);

        doorHandleBracketList.add(doorHandle);
        doorHandleBracketList.add(doorBracket);

        return doorHandleBracketList;
    }

    public List<IMaterials> calcHorizontalBracesBrackets()
    {
        List<IMaterials> horizontalBraceBracketList = new ArrayList<>();
        IMaterials horizontalBraceBracket;
        int totalBraceBrackets = 2*calcNumberOfHorizontalBraces();

        horizontalBraceBracket = new BoltsScrewsBrackets(35,0,"vinkelbeslag 35", totalBraceBrackets,"pakke","Til montering af løsholter", 0);
        horizontalBraceBracketList.add(horizontalBraceBracket);

        return horizontalBraceBracketList;
    }
}
