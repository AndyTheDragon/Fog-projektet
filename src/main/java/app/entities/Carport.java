package app.entities;

import app.services.WorkDrawing;

import java.util.ArrayList;
import java.util.List;

public class Carport
{
    CarportCalculator calculator;
    WorkDrawing workDrawing;
    List<IMaterials> materialsList;
    List<IMaterials> boltsScrewsBracketsList;
    int length;
    int width;
    int height;
    int shedLength;
    int shedWidth;
    RoofType roofType;
    String shedPlacement;
    int numberOfPosts;
    int numberOfJoists;


    public Carport(int length, int width, int shedLength, int shedWidth, RoofType roofType)
    {
        this.length = length;
        this.width = width;
        this.shedLength = shedLength;
        this.shedWidth = shedWidth;
        this.roofType = roofType;
        this.shedPlacement = "right";
        this.calculator = new OptimalWoodCalculator();
        this.materialsList = new ArrayList<>();
        this.boltsScrewsBracketsList = new ArrayList<>();
        calculateMaterials();
        calculateBoltsScrewsBrackets();
        this.numberOfPosts = calculator.calcNumberOfPosts(length, width, shedLength, shedWidth);
        this.numberOfJoists = calculator.calcNumberOfJoists(length);
        this.workDrawing = new WorkDrawing(this, 640);
    }

    public void calculateMaterials()
    {
        materialsList = new ArrayList<>();

        materialsList.addAll(calculator.calcUnderFascia(length, width));
        materialsList.addAll(calculator.calcOverFascia(length, width));
        materialsList.addAll(calculator.calcBeam(length));
        materialsList.addAll(calculator.calcPosts(length, width, shedLength, shedWidth));
        materialsList.addAll(calculator.calcJoists(length));
        materialsList.addAll(calculator.calcBargeBoards(length, width));
        materialsList.addAll(calculator.calcRoof(length, width));

        if (hasShed())
        {
            materialsList.addAll(calculator.calcCladding(shedLength, shedWidth));
            materialsList.addAll(calculator.calcHorizontalBraces(shedLength, shedWidth));
        }

    }

    public void calculateBoltsScrewsBrackets()
    {
        boltsScrewsBracketsList = new ArrayList<>();

        boltsScrewsBracketsList.addAll(calculator.calcRoofScrews(length, width));
        boltsScrewsBracketsList.addAll(calculator.calcJoistBrackets(length));
        boltsScrewsBracketsList.addAll(calculator.calcJoistBracketScrews(length));
        boltsScrewsBracketsList.addAll(calculator.calcFasciaBargeScrews(length, width));
        boltsScrewsBracketsList.addAll(calculator.calcMetalStrap(length, width));
        boltsScrewsBracketsList.addAll(calculator.calcBeamBolts(length, width, shedLength, shedWidth));

        if (hasShed())
        {
            boltsScrewsBracketsList.addAll(calculator.calcCladdingScrews(shedLength, shedWidth));
            boltsScrewsBracketsList.addAll(calculator.calcHorizontalBracesBrackets(shedLength, shedWidth));
            boltsScrewsBracketsList.addAll(calculator.doorHandleBrackets());
        }
    }

    public boolean hasShed()
    {
        return shedLength > 0 && shedWidth > 0;
    }

    public int getNumberOfPosts()
    {
        return this.numberOfPosts;
    }

    public boolean extraPostsForLongCarport()
    {
        return calculator.extraPostsForLongCarport(length, width);
    }

    public int getNumberOfJoists()
    {
        return this.numberOfJoists;
    }

    public int getNumberOfCladdingBoards()
    {
        /// TODO: SKAL LIGE GENTÆNKES HER, SKAL DETTE FLYTTES IND SOM EN PROPERTY?
        return calculator.calcNumberOfCladdingBoards(length, width);
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

    public List<IMaterials> getBoltsScrewsBracketsList()
    {
        return boltsScrewsBracketsList;
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

    public CarportCalculator getCalculator()
    {
        return calculator;
    }
}
