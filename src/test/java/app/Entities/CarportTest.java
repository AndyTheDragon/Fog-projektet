package app.Entities;

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
    void calcOptimalWoodMiddleTest()
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
    void calcOptimalWoodMinimumTestA()
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
    void calcOptimalWoodMinimumTestB()
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
    void calcOptimalWoodMaximumTest()
    {
        //Arrange
        Carport carport = new Carport();
        int totalLength = 2*870;
        int length1 = 480;
        int length2 = 600;
        int[] expected = new int[]{0, 3};
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);

        //Assert
        assertArrayEquals(expected, result);

    }
}