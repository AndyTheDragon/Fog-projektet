package app.entities;

import app.exceptions.CalculatorException;
import app.persistence.ConnectionPool;
import app.services.OptimalWoodCalculator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarportTest
{
    Carport carport;
    private final String USER = "carport";
    private final String PASSWORD = "wDj+e5V&0YOx5kE";
    private final String URL = "jdbc:postgresql://165.22.74.93:5432/%s?currentSchema=public";
    private final String DB = "fog_carport";

    private final ConnectionPool dbConnection = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    @BeforeEach
    void setUp()
    {
        carport = new Carport(780,600,210,530, RoofType.FLAT, new OptimalWoodCalculator(780, 600, 210, 530, dbConnection));
    }

    @AfterEach
    void tearDown()
    {
    }

    @Test
    void getNumberOfJoistsTest()
    {
        // Arrange
        Carport carportA = new Carport(780,600,210,530, RoofType.FLAT, new OptimalWoodCalculator(780, 600, 210, 530, dbConnection));
        int expectedA = 15;
        Carport carportB = new Carport(480,300,0,0, RoofType.FLAT, new OptimalWoodCalculator(480, 300, 0, 0, dbConnection));
        int expectedB = 10;
        // Act
        int actualA = carportA.getNumberOfJoists();
        int actualB = carportB.getNumberOfJoists();

        // Assert
        assertEquals(expectedA, actualA);
        assertEquals(expectedB, actualB);

    }
    @Test
    void calcOptimalBeamMiddleTest()
    {
        //Arrange
        Carport carport = new Carport(780,600,210,530, RoofType.FLAT, new OptimalWoodCalculator(780, 600, 210, 530, dbConnection));
        int totalLength = 2*780;
        int length1 = 480;
        int length2 = 600;
        int[] expected = new int[]{1, 2};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, length1, length2);

        //Assert
        assertArrayEquals(expected, result);

    }
    @Test
    void calcOptimalBeamMinimumTestA()
    {
        //Arrange
        int totalLength = 2*480;
        int length1 = 480;
        int length2 = 600;
        int[] expected = new int[]{2,0};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, length1, length2);

        //Assert
        assertArrayEquals(expected, result);

    }
    @Test
    void calcOptimalBeamMinimumTestB()
    {
        //Arrange
        int totalLength = 2*240;
        int length1 = 480;
        int length2 = 600;
        int[] expected = new int[]{1, 0};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, length1, length2);

        //Assert
        assertArrayEquals(expected, result);

    }
    @Test
    void calcOptimalBeamMaximumTest()
    {
        //Arrange
        int totalLength = 2*870;
        int length1 = 480;
        int length2 = 600;
        int[] expected = new int[]{4, 0};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, length1, length2);

        //Assert
        assertArrayEquals(expected, result);

        //In this test spillage of 5% has been added to our totalt length of wood which affects the expected
        //result to be 4x480cm.
    }
    @Test
    void calcOptimalFasciaMiddleTest()
    {
        //Arrange
        int length = 2*780;
        int width = 2*600;
        int totalLength = length + width;
        int length1 = 360;
        int length2 = 540;
        int[] expected = new int[]{7, 1};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, length1, length2);
        //Assert
        assertArrayEquals(expected, result);
    }
    @Test
    void calcOptimalFasciaMiddleTestNew()
    {
        //Arrange
        int length = 2*780;
        int width = 2*600;
        int totalLength = length + width;
        List<IMaterials> materialsList = new ArrayList<>();
        materialsList.add(new ConstructionWood(20, 20, 1380, "stk", "Fascia", "Fascia", 0, 3));
        materialsList.add(new ConstructionWood(20, 20, 780, "stk", "Fascia", "Fascia", 0, 1));
        materialsList.add(new ConstructionWood(20, 20, 360, "stk", "Fascia", "Fascia", 0, 1));
        materialsList.add(new ConstructionWood(20, 20, 540, "stk", "Fascia", "Fascia", 0, 2));
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int [] result = calc.calcOptimalWood(totalLength, materialsList);
        //Assert
        assertArrayEquals(new int[]{2,0,0,0}, result);

    }
    @Test
    void calcOptimalFasciaMinimumTestA()
    {
        //Arrange
        int length = 2*480;
        int width = 2*360;
        int totalLength = length + width;
        int length1 = 360;
        int length2 = 540;
        int[] expected = new int[]{5, 0};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, length1, length2);
        //Assert
        assertArrayEquals(expected, result);
    }
    @Test
    void calcOptimalFasciaMinimumTestB()
    {
        //Arrange
        int length = 2*540;
        int width = 2*360;
        int totalLength = length + width;
        int length1 = 540;
        int length2 = 360;
        int[] expected = new int[]{2, 2};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, length1, length2);
        //Assert
        assertArrayEquals(expected, result);

        //In this test the length1 and length2 are switched around, which changes the prio of the boards.
        //This is because we would like to fint the 540cm boards first to avoid spill %.
    }
    @Test
    void calcOptimalFasciaMaximumTest()
    {
        //Arrange
        int length = 2*870;
        int width = 2*600;
        int totalLength = length + width;
        int length1 = 360;
        int length2 = 540;
        int[] expected = new int[]{9, 0};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, length1, length2);
        //Assert
        assertArrayEquals(expected, result);
    }
    @Test
    void calcOptimalFlatBargeBoardMinimumTestA()
    {
        //Arrange
        int length = 2*240;
        int width = 240;
        int totalLength = length + width;
        int highPrioBoard = 360;
        int lowPrioBoard = 540;
        int[] expected = new int[]{2, 0};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);
        //Assert
        assertArrayEquals(expected, result);
    }
    @Test
    void calcOptimalFlatBargeBoardMiddleTest()
    {
        //Arrange
        int length = 2*780;
        int width = 2*600;
        int totalLength = length + width;
        int highPrioBoard = 360;
        int lowPrioBoard = 540;
        int[] expected = new int[]{7, 1};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);
        //Assert
        assertArrayEquals(expected, result);
    }
    @Test
    void calcOptimalRisenBargeBoardMaximumTest()
    {
        //Arrange
        int length = 2*870;
        int width = 4*345;
        int totalLength = length + width;
        int highPrioBoard = 360;
        int lowPrioBoard = 540;
        int[] expected = new int[]{8, 1};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);
        //Assert
        assertArrayEquals(expected, result);
    }
    @Test
    void calcOptimalRisenbargeBoardMinimumTest()
    {
        //Arrange
        int length = 2*540;
        int width = 4*209;
        int totalLength = length + width;
        int highPrioBoard = 360;
        int lowPrioBoard = 540;
        int[] expected = new int[]{6, 0};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);
        //Assert
        assertArrayEquals(expected, result);
    }
    @Test
    void calcOptimalRisenBargeBoardMiddleTest()
    {
        //Arrange
        int length = 2*780;
        int width = 4*219;
        int totalLength = length + width;
        int highPrioBoard = 360;
        int lowPrioBoard = 540;
        int[] expected = new int[]{6, 1};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);
        //Assert
        assertArrayEquals(expected, result);
    }
    @Test
    void calcOptimalHorizontalBracesMaximumTest()
    {
        //Arrange
        Carport carport = new Carport(780, 600, 210, 530, RoofType.FLAT, new OptimalWoodCalculator(780, 600, 210, 530, dbConnection));
        int totalBraces = 0;
        int bracesPerSection = 2;
        int extraBracesForWideness = 4;
        int expectedBraces = 16;

        //Act
        totalBraces += 4*bracesPerSection;
        if (carport.width > 300)
        {
            totalBraces += extraBracesForWideness;
        }
        if (carport.length > 300)
        {
            totalBraces += extraBracesForWideness;
        }
        int actualBraces = totalBraces;

        //Assert
        assertEquals(expectedBraces, actualBraces);
    }
    @Test
    void calcOptimalRafterMinimumTestA()
    {
        //Arrange
        int totalLength = 5*240;
        int length1 = 480;
        int length2 = 600;
        int[] expected = new int[]{0, 2};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, length1, length2);
        //Assert
        assertArrayEquals(expected, result);
    }
    @Test
    void calcOptimalRafterMinimumTestB()
    {
        //Arrange
        int totalLength = 7*360;
        int length1 = 480;
        int length2 = 600;
        int[] expected = new int[]{7, 0};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, length1, length2);
        //Assert
        assertArrayEquals(expected, result);
    }

    @Test
    void calcOptimalRafterMaximumTest()
    {
        //Arrange
        int totalLength = 15*600;
        int length1 = 600;
        int length2 = 480;
        int[] expected = new int[]{15,0};
        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, length1, length2);
        //Assert
        assertArrayEquals(expected, result);
    }
    @Test
    void calcOptimalFlatRoofMinimumTest()
    {
        // Arrange
        int carportWidth = 240;
        int carportLength = 240;
        int plateWidth = 109;
        int shortPlateLength = 360;
        int longPlateLength = 600;
        int overlap = 20;

        int platesForWidth = (int) Math.ceil((double) carportWidth / plateWidth);

        int totalLength = carportLength + overlap;

        int expectedPlatesForWidth = 3;
        int expectedShortPlatesForLength = 1;
        int expectedLongPlatesForLength = 0;

        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, shortPlateLength, longPlateLength);
        int shortPlatesForLength = result[0] ;
        int longPlatesForLength = result[1];



        // Assert
        assertEquals(expectedPlatesForWidth, platesForWidth);
        assertEquals(expectedShortPlatesForLength, shortPlatesForLength);
        assertEquals(expectedLongPlatesForLength, longPlatesForLength);
    }
    @Test
    void calcOptimalFlatRoofMaxTest()
    {
        // Arrange
        int carportWidth = 600;
        int carportLength = 780;
        int plateWidth = 109;
        int shortPlateLength = 360;
        int longPlateLength = 600;
        int overlap = 20;

        int platesForWidth = (int) Math.ceil((double) carportWidth / plateWidth);

        int totalLength = carportLength + overlap;

        int expectedPlatesForWidth = 6;
        int expectedShortPlatesForLength = 1;
        int expectedLongPlatesForLength = 1;

        OptimalWoodCalculator calc = (OptimalWoodCalculator) carport.getCalculator();
        //Act
        int[] result = calc.calcOptimalWood(totalLength, shortPlateLength, longPlateLength);
        int shortPlatesForLength = result[0] ;
        int longPlatesForLength = result[1];



        // Assert
        assertEquals(expectedPlatesForWidth, platesForWidth);
        assertEquals(expectedShortPlatesForLength, shortPlatesForLength);
        assertEquals(expectedLongPlatesForLength, longPlatesForLength);
    }
    @Test
    void calcJoistBracketsTest()
    {
        Carport carportA = new Carport(780, 600,0,0,RoofType.FLAT, new OptimalWoodCalculator(780, 600, 0, 0, dbConnection));
        //Arrange
        int expectedJoistAmount = 15;
        int expectedBracketAmount = expectedJoistAmount *2;
        //Act
        int actualJoistAmount = carportA.getNumberOfJoists();
        int actualBracketAmount = actualJoistAmount * 2;

        //Assert
        assertEquals(expectedJoistAmount, actualJoistAmount);
        assertEquals(expectedBracketAmount, actualBracketAmount);

    }
    @Test
    void calcBeamBoltsTest()
    {
        Carport carportB = new Carport (780,600,210,530,RoofType.FLAT, new OptimalWoodCalculator(780, 600, 210, 530, dbConnection));
        // Arrange
        int totalBolts = 0;
        int boltsPerPost = 2;
        int extraBoltsPerSeam = 4;
        int postAmount = carportB.getNumberOfPosts()-1;
        if(carportB.shedWidth > 300){
            postAmount -= 2;
        }

        int expectedBoltAmount = 20;

        // Act
        totalBolts += boltsPerPost*postAmount;
        if (carportB.length > 600){
            totalBolts += extraBoltsPerSeam;
        }
        int actualBoltsAmount = totalBolts;

        // Assert
        assertEquals(expectedBoltAmount,actualBoltsAmount);
    }
    @Test
    void calcShedScrewsTest()
    {
        Carport carportC = new Carport(780,600,210,530,RoofType.FLAT, new OptimalWoodCalculator(780, 600, 210, 530, dbConnection));
        // Arrange
        int expectedInnerScrews = 600;
        int expectedOuterScrews = 600;
        int screwsPerCladding = 6;
        int totalInnerScrews = screwsPerCladding * (carportC.getNumberOfCladdingBoards() / 2);
        int totalOuterScrews = screwsPerCladding * (carportC.getNumberOfCladdingBoards() / 2);
        // Act
        int actualInnerScrews = totalInnerScrews;
        int actualOuterScrews = totalOuterScrews;
        // Assert
        assertEquals(expectedInnerScrews, actualInnerScrews);
        assertEquals(expectedOuterScrews, actualOuterScrews);

    }

    @Test
    void calcRoofScrewsTest()
    {
        // Arrange
        Carport carportD = new Carport(780,600,210,530,RoofType.FLAT, new OptimalWoodCalculator(780, 600, 210, 530, dbConnection));
        int screwsPerSqrMeter = 12;

        // Act
        int roofArea = (carportD.length/100) * (carportD.width/100);

        int totalScrews = roofArea * screwsPerSqrMeter;
        int expectedScrewPacks = Math.ceilDiv(totalScrews,200);
        int actualScrewPacks = 0;
        try
        {
            actualScrewPacks = carportD.calculator.calcRoofScrews().getFirst().getAmount();
        } catch (CalculatorException e)
        {
            throw new RuntimeException(e);
        }

        // Assert
        assertEquals(expectedScrewPacks, actualScrewPacks);
    }

    @Test
    void  calcFasciaBargeScrewsTest()
    {
        // Arrange
        int expectedScrewPacks = 1;
        Carport carportD = new Carport(780,600,210,530,RoofType.FLAT, new OptimalWoodCalculator(780, 600, 210, 530, dbConnection));
        int totalLength = ((carportD.length*6)+(carportD.width*4));

        // Act
        int totalScrews = totalLength/70;
        int ActualScrewPacks = Math.ceilDiv( totalScrews,200);

        // Assert
        assertEquals(expectedScrewPacks, ActualScrewPacks);
    }


}