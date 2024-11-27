package app.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarportTest
{

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
        Carport carport = new Carport();
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
        Carport carport = new Carport();
        int totalLength = 2*480;
        int length1 = 480;
        int length2 = 600;
        int[] expected = new int[]{2, 0};
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);

        //Assert
        assertArrayEquals(expected, result);

    }
    @Test
    void calcOptimalBeamMinimumTestB()
    {
        //Arrange
        Carport carport = new Carport();
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
        Carport carport = new Carport();
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

    //Make test mathods for all types of wood below

    @Test
    void calcOptimalFasciaMiddleTest()
    {
        //Arrange
        Carport carport = new Carport();
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
        Carport carport = new Carport();
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
        Carport carport = new Carport();
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
        Carport carport = new Carport();
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
        Carport carport = new Carport();
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
        Carport carport = new Carport();
        int length = 2*780;
        int width = 600;
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
    void calcOptimalRisenBargeBoardMaximumTest()
    {
        //Arrange
        Carport carport = new Carport();
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
        Carport carport = new Carport();
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
        Carport carport = new Carport();
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
        Carport carport = new Carport();
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
        Carport carport = new Carport();
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
        Carport carport = new Carport();
        int totalLength = 15*600;
        int length1 = 600;
        int length2 = 480;
        int[] expected = new int[]{15,0};
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);
        //Assert
        assertArrayEquals(expected, result);
    }

}