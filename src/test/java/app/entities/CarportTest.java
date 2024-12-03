package app.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarportTest
{
    Carport carport = new Carport();

    @BeforeEach
    void setUp()
    {
    }

    @AfterEach
    void tearDown()
    {
    }

    @Test
    void calcOptimalBeamMiddleTest()
    {
        //Arrange
        int totalLength = 2*780;
        int length1 = 480;
        int length2 = 600;
        int[] expected = new int[]{1, 2};
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);

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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);

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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);

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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);

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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);
        //Assert
        assertArrayEquals(expected, result);
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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);
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
        int length1 = 360;
        int length2 = 540;
        int[] expected = new int[]{2, 2};
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length2, length1);
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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);
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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);
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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);
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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);
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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);
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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, highPrioBoard, lowPrioBoard);
        //Assert
        assertArrayEquals(expected, result);
    }
    @Test
    void calcOptimalRafterMinimumTestA()
    {
        //Arrange
        int totalLength = 5*240;
        int length1 = 480;
        int length2 = 600;
        int[] expected = new int[]{0, 2};
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);
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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);
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
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);
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

        // Act
        int[] result = carport.calcOptimalWood(totalLength, shortPlateLength, longPlateLength);
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

        // Act
        int[] result = carport.calcOptimalWood(totalLength, shortPlateLength, longPlateLength);
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
        Carport carportA = new Carport(780, 600,0,0,RoofType.FLAT);
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
        Carport carportB = new Carport (780,600,210,530,RoofType.FLAT);
        // Arrange
        int totalBolts = 0;
        int boltsPerPost = 2;
        int extraBoltsPerSeam = 4;
        int postAmount = carportB.getNumberOfPosts(carportB.length, carportB.width, carportB.shedLength, carportB.shedWidth)-1;
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


}