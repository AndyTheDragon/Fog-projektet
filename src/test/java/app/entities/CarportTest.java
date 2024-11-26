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
        int[] expected = new int[]{0, 4};
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);

        //Assert
        assertArrayEquals(expected, result);

        //In this test spillage of 5% has been added to our totalt length of wood which affects the expected
        //result to be 4x600cm.
    }

    //Make test mathods for all types of wood below

    @Test
    void calcOptimalUnderFasciaMiddleTest()
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
}