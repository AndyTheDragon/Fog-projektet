package app.services;

import app.entities.*;
import app.exceptions.CalculatorException;
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

    public OptimalWoodCalculator(int carportLength, int carportWidth, int shedLength, int shedWidth , ConnectionPool dbConnection)
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

        while (true)
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

    public List<IMaterials> calcUnderFascia() throws CalculatorException
    {
        try
        {
            List<IMaterials> allFasciaList = MaterialMapper.getMaterialOfType("understernbrædder", dbConnection);
            List<IMaterials> fasciaList = new ArrayList<>();
            int totalLength = (carportLength + carportWidth) * 2;

            int[] optimalWood = calcOptimalWood(totalLength, allFasciaList);

            for (int i = 0; i < optimalWood.length; i++)
            {
                if (optimalWood[i] > 0)
                {
                    IMaterials underFascia = allFasciaList.get(i);
                    underFascia.setAmount(optimalWood[i]);
                    fasciaList.add(underFascia);
                }
            }
            return fasciaList;
        } catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcOverFascia() throws CalculatorException
    {
        try
        {
            List<IMaterials> fasciaList = new ArrayList<>();
            List<IMaterials> allFasciaList = MaterialMapper.getMaterialOfType("oversternbrædder", dbConnection);
            int totalLength = (carportLength + carportWidth) * 2 + 5;

            int[] optimalWood = calcOptimalWood(totalLength, allFasciaList);

            for (int i = 0; i < optimalWood.length; i++)
                if (optimalWood[i] > 0)
                {
                    int amount = optimalWood[i];
                    IMaterials underFascia = allFasciaList.get(i).setAmount(amount);
                    fasciaList.add(underFascia);
                }
            return fasciaList;
        } catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcBeam() throws CalculatorException
    {
        try
        {
            List<IMaterials> beamList = new ArrayList<>();
            List<IMaterials> allBeamList = MaterialMapper.getMaterialOfTypeAndLength("Remme i sider, sadles ned i stolper", (carportLength / 2), dbConnection);
            int totalLength = carportLength * 2;

            int[] optimalWood = calcOptimalWood(totalLength, allBeamList);

            for (int i = 0; i < optimalWood.length; i++)
                if (optimalWood[i] > 0)
                {
                    IMaterials beam = (allBeamList.get(i).setAmount(optimalWood[i]));
                    beamList.add(beam);
                }
            return beamList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcRafters() throws CalculatorException
    {
        return null;
    }

    public List<IMaterials> calcPosts() throws CalculatorException
    {
        try
        {
            List<IMaterials> postList = new ArrayList<>();
            List<IMaterials> allPostList = MaterialMapper.getMaterialOfType("Stolper", (dbConnection));
            int totalPosts = calcNumberOfPosts();

            IMaterials post = allPostList.get(0).setAmount(totalPosts);
            postList.add(post);

            return postList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
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
        if (shedLength > 0 && shedWidth > 0)
        {
            totalPosts += extraPostsForShed + doorPost;
            if (carportWidth > 300)
            {
                totalPosts += extraPostsForWideShed;
            }
        }

        // Check if carport is longer than 5 meters
        if (extraPostsForLongCarport())
        {
            totalPosts += extraPostsForLongCarport;
        }
        return totalPosts;
    }

    public boolean extraPostsForLongCarport()
    {
        return carportLength - 130 - shedLength > 350;
    }

    public List<IMaterials> calcJoists() throws CalculatorException
    {
        try
        {
            List<IMaterials> joistList = new ArrayList<>();
            List<IMaterials> allJoistList = MaterialMapper.getMaterialOfType("Spær, monteres på rem", dbConnection);
            int amountOfJoists = calcNumberOfJoists();

            IMaterials joistBoard = allJoistList.get(0).setAmount(amountOfJoists);
            joistList.add(joistBoard);

            return joistList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public int calcNumberOfJoists()
    {
        int minimumSpacing = 45;
        int maximumSpacing = 60;
        int maxGaps = Math.floorDiv(carportLength, minimumSpacing);
        int minGaps = Math.ceilDiv(carportLength, maximumSpacing);
        double error = 1;
        int gaps = 0;
        for (int i = minGaps; i <= maxGaps; i++)
        {
            double currentGap = (double) carportLength / i;
            double currentError = Math.ceil(currentGap) - currentGap;
            if (currentError <= error)
            {
                error = currentError;
                gaps = i;
            }
        }
        return gaps;
    }

    public List<IMaterials> calcBargeBoards() throws CalculatorException
    {
        try
        {
            List<IMaterials> bargeBoardList = new ArrayList<>();
            List<IMaterials> allBargeBoardList = MaterialMapper.getMaterialOfType("Vandbrædt", (dbConnection));
            int totalLength = (carportLength + carportWidth) * 2 + 20;

            int[] optimalWood = calcOptimalWood(totalLength, allBargeBoardList);

            for (int i = 0; i < optimalWood.length; i++)
                if (optimalWood[i] > 0)
                {
                    IMaterials bargeBoard = (allBargeBoardList.get(i).setAmount(optimalWood[i]));
                    bargeBoardList.add(bargeBoard);
                }
            return bargeBoardList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcBattern() throws CalculatorException
    {
        return null;
    }

    public List<IMaterials> calcCladding() throws CalculatorException
    {
        try
        {
            List<IMaterials> claddingList = new ArrayList<>();
            List<IMaterials> allCladdingList = MaterialMapper.getMaterialOfType("beklædning", (dbConnection));
            IMaterials cladding;

            int claddingBoardRounded = calcNumberOfCladdingBoards();
            cladding = allCladdingList.get(0).setAmount(claddingBoardRounded);
            claddingList.add(cladding);

            return claddingList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public int calcNumberOfCladdingBoards()
    {
        double totalShedLength = (shedLength + shedWidth) * 2;
        double claddingBoardAmount = totalShedLength / 7.4;

        return (int) Math.ceil(claddingBoardAmount);
    }

    public List<IMaterials> calcHorizontalBraces() throws CalculatorException
    {
        try
        {
            List<IMaterials> horizontalBraceList = new ArrayList<>();
            List<IMaterials> allHorizontalSideBraceList = MaterialMapper.getMaterialOfType("løsholter til skur sider", (dbConnection));
            List<IMaterials> allHorizontalEndBraceList = MaterialMapper.getMaterialOfType("løsholter til skur gavle", (dbConnection));

            int totalSideBraces = calcNumberOfHorizontalSideBraces();
            int totalEndBraces = calcNumberOfHorizontalEndBraces();

            IMaterials horizontalSideBraces = allHorizontalSideBraceList.get(0).setAmount(totalSideBraces);
            IMaterials horizontalEndBraces = allHorizontalEndBraceList.get(0).setAmount(totalEndBraces);
            horizontalBraceList.add(horizontalEndBraces);
            horizontalBraceList.add(horizontalSideBraces);

            return horizontalBraceList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    @Override
    public int calcNumberOfHorizontalBraces()
    {
        return 0;
    }

    public int calcNumberOfHorizontalSideBraces()
    {
        int totalBraces = 0;
        int bracesPerSection = 2;
        int extraBracesForWideness = 4;

        totalBraces += 4 * bracesPerSection;
        if (shedLength > 300)
        {
            totalBraces += extraBracesForWideness;
        }
        return totalBraces;
    }

    public int calcNumberOfHorizontalEndBraces()
    {
        int totalBraces = 0;
        int bracesPerSection = 2;
        int extraBracesForWideness = 4;

        totalBraces += 4 * bracesPerSection;
        if (shedWidth > 300)
        {
            totalBraces += extraBracesForWideness;
        }
        return totalBraces;
    }

    public List<IMaterials> calcRoof() throws CalculatorException
    {
        try
        {
            List<IMaterials> roofList = new ArrayList<>();
            List<IMaterials> allRoofList = MaterialMapper.getMaterialOfTypeAndLength("tagplader%", 0, (dbConnection));

            int plateWidth = 109;
            int overlap = 20;

            int platesForWidth = (int) Math.ceil((double) carportWidth / plateWidth);
            int totalLength = carportLength + overlap;
            int[] optimalRoof = calcOptimalWood(totalLength, allRoofList);
            for (int i = 0; i < optimalRoof.length; i++)
            {
                if (optimalRoof[i] > 0)
                {
                    IMaterials roof = allRoofList.get(i);
                    roof.setAmount(optimalRoof[i] * platesForWidth);
                    roofList.add(roof);
                }
            }
            return roofList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcRoofScrews() throws CalculatorException
    {
        try
        {
            List<IMaterials> roofScrewList = new ArrayList<>();
            List<IMaterials> allRoofScrewList = MaterialMapper.getMaterialOfType("Skruer til tagplader", (dbConnection));

            int screwsPerSqrMeter = 12;
            int roofArea = carportLength * carportWidth;

            int totalScrews = (roofArea * screwsPerSqrMeter) / 10000;
            int screwPacks = Math.ceilDiv(totalScrews, 200);
            IMaterials roofScrews = allRoofScrewList.get(0);
            roofScrews.setAmount(screwPacks);
            roofScrewList.add(roofScrews);

            return roofScrewList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcJoistBrackets() throws CalculatorException
    {
        try
        {
            List<IMaterials> joistBracketList = new ArrayList<>();
            List<IMaterials> allRightJoistBracketList = MaterialMapper.getMaterialOfType("Til montering af spær", (dbConnection));
            List<IMaterials> allLeftJoistBracketList = MaterialMapper.getMaterialOfType("Til montering af spær", (dbConnection));


            int joistAmount = calcNumberOfJoists();
            int rightBracketAmount = joistAmount;
            int leftBracketAmount = joistAmount;

            IMaterials rightJoistBracket = (allRightJoistBracketList.get(0).setAmount(rightBracketAmount));
            IMaterials leftJoistBracket = (allLeftJoistBracketList.get(0).setAmount(leftBracketAmount));

            joistBracketList.add(rightJoistBracket);
            joistBracketList.add(leftJoistBracket);

            return joistBracketList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcFasciaBargeScrews() throws CalculatorException
    {
        try
        {
            List<IMaterials> fasciaBargeScrewList = new ArrayList<>();
            List<IMaterials> allFasciaBargeScrewList = MaterialMapper.getMaterialOfType("Til montering af stern&vandbrædt", (dbConnection));

            int totalLength = ((carportLength * 6) + (carportWidth * 4));
            int totalScrews = totalLength / 70;
            int screwPacks = Math.ceilDiv(totalScrews, 200);

            IMaterials fasciaBargeScrews = (allFasciaBargeScrewList.get(0).setAmount(screwPacks));
            fasciaBargeScrewList.add(fasciaBargeScrews);
            return fasciaBargeScrewList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcJoistBracketScrews() throws CalculatorException
    {
        try
        {
            List<IMaterials> bracketScrewList = new ArrayList<>();
            List<IMaterials> allBracketScrewList = MaterialMapper.getMaterialOfType("Til montering af universalbeslag + hulbånd", (dbConnection));

            int joistAmount = calcNumberOfJoists();
            int screwsPerJoist = 25;
            int totalScrews = joistAmount * screwsPerJoist;
            int screwPacks = Math.ceilDiv(totalScrews, 200);

            IMaterials bracketScrews = (allBracketScrewList.get(0).setAmount(screwPacks));
            bracketScrewList.add(bracketScrews);

            return bracketScrewList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcMetalStrap() throws CalculatorException
    {
        try
        {
            List<IMaterials> metalStrapList = new ArrayList<>();
            List<IMaterials> allMetalStrapList = MaterialMapper.getMaterialOfType("Til vindkryds", (dbConnection));
            IMaterials perfMetalStrap;
            double crossLength = Math.sqrt(carportLength ^ 2 + carportWidth ^ 2);
            if (crossLength < 500)
            {
                int totalStraps = 1;
                perfMetalStrap = (allMetalStrapList.get(0).setAmount(totalStraps));
                metalStrapList.add(perfMetalStrap);
            }
            if (crossLength > 500)
            {
                int totalStraps = 2;
                perfMetalStrap = (allMetalStrapList.get(0).setAmount(totalStraps));
                metalStrapList.add(perfMetalStrap);
            }
            return metalStrapList;
        }
            catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcBeamBolts() throws CalculatorException
    {
        try
        {
            List<IMaterials> beamBoltList = new ArrayList<>();
            List<IMaterials> allBeamBoltList = MaterialMapper.getMaterialOfType("Til montering af rem", (dbConnection));
            List<IMaterials> allBoltDiscList = MaterialMapper.getMaterialOfType("Til montering af rem", (dbConnection));

            int totalBolts = 0;
            int boltPerPost = 2;
            int extraBoltPerSeam = 4;
            totalBolts += boltPerPost * calcNumberOfPosts();
            if (carportLength > 600)
            {
                totalBolts += extraBoltPerSeam;
            }
            IMaterials boltDisc = (allBoltDiscList.get(0).setAmount(totalBolts));
            IMaterials beamBolt = (allBeamBoltList.get(0).setAmount(totalBolts));
            beamBoltList.add(boltDisc);
            beamBoltList.add(beamBolt);

            return beamBoltList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcCladdingScrews() throws CalculatorException
    {
        try
        {
            List<IMaterials> claddingScrewList = new ArrayList<>();
            List<IMaterials> allOuterCladdingScrewList = MaterialMapper.getMaterialOfType("yderste beklædning", (dbConnection));
            List<IMaterials> allInnerCladdingScrewList = MaterialMapper.getMaterialOfType("inderste beklædning", (dbConnection));

            int screwsPerCladding = 6;
            int totalInnerScrews = screwsPerCladding * (calcNumberOfCladdingBoards() / 2);
            int totalOuterScrews = screwsPerCladding * (calcNumberOfCladdingBoards() / 2);

            int innerScrewsPacks = Math.ceilDiv(totalInnerScrews, 300);
            int outerScrewsPacks = Math.ceilDiv(totalOuterScrews, 400);

            IMaterials innerCladdingScrews = (allOuterCladdingScrewList.get(0).setAmount(innerScrewsPacks));
            IMaterials outerCladdingScrews = (allInnerCladdingScrewList.get(0).setAmount(outerScrewsPacks));

            claddingScrewList.add(innerCladdingScrews);
            claddingScrewList.add(outerCladdingScrews);

            return claddingScrewList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> doorHandleBrackets() throws CalculatorException
    {
        try
        {
            List<IMaterials> doorHandleBracketList = new ArrayList<>();
            List<IMaterials> allDoorHandleList = MaterialMapper.getMaterialOfType("Til lås", (dbConnection));
            List<IMaterials> allDoorBracketList = MaterialMapper.getMaterialOfType("Til skurdør", (dbConnection));

            IMaterials doorHandle = (allDoorHandleList.get(0).setAmount(1));
            IMaterials doorBracket = (allDoorBracketList.get(0).setAmount(2));

            doorHandleBracketList.add(doorHandle);
            doorHandleBracketList.add(doorBracket);

            return doorHandleBracketList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }

    public List<IMaterials> calcHorizontalBracesBrackets() throws CalculatorException
    {
        try
        {
            List<IMaterials> horizontalBraceBracketList = new ArrayList<>();
            List<IMaterials> allHorizontalBraceBracketList = MaterialMapper.getMaterialOfType("Til montering af løsholter", (dbConnection));

            int totalBraceBrackets = 2 * (calcNumberOfHorizontalSideBraces() + calcNumberOfHorizontalEndBraces());

            IMaterials horizontalBraceBracket = (allHorizontalBraceBracketList.get(0).setAmount(totalBraceBrackets));
            horizontalBraceBracketList.add(horizontalBraceBracket);

            return horizontalBraceBracketList;
        }
        catch (DatabaseException e)
        {
            throw new CalculatorException(e.getMessage());
        }
    }
}
