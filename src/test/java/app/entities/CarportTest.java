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
        int[] expected = new int[]{0, 3};
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);

        //Assert
        assertArrayEquals(expected, result);

    }

    //Make test mathods for all types of wood below

    @Test
    void calcOptimalFasciaMiddleTest()
    {
        //Arrange
        Carport carport = new Carport();
        int totalLength = 2*600;
        int length1 = 360;
        int length2 = 540;
        int[] expected = new int[]{2, 1};
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);
        //Assert
        assertArrayEquals(expected, result);
    }

    @Test
    void calcOptimalFasciaMinimumTestA()
    {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void calcOptimalFasciaMinimumTestB()
    {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void calcOptimalFasciaMaximumTest()
    {
        //Arrange
        //Act
        //Assert
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
        int[] expected = new int[]{4, 1};
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
        int length1 = 480;
        int length2 = 600;
        int[] expected = new int[]{15,0};
        //Act
        int[] result = carport.calcOptimalWood(totalLength, length1, length2);
        //Assert
        assertArrayEquals(expected, result);
    }

}