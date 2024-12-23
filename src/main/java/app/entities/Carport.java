package app.entities;

import app.exceptions.CalculatorException;
import app.services.CarportCalculator;
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
    double buyInPrice;
    double sellPrice;
    float profitMargin;


    public Carport(int length, int width, int shedLength, int shedWidth, RoofType roofType, CarportCalculator calculator) throws CalculatorException
    {
        this.length = length;
        this.width = width;
        this.shedLength = shedLength;
        this.shedWidth = shedWidth;
        this.roofType = roofType;
        this.shedPlacement = "right";
        this.calculator = calculator;
        this.materialsList = new ArrayList<>();
        this.boltsScrewsBracketsList = new ArrayList<>();
        calculateMaterials();
        calculateBoltsScrewsBrackets();
        this.numberOfPosts = this.calculator.calcNumberOfPosts();
        this.numberOfJoists = this.calculator.calcNumberOfJoists();
        this.workDrawing = new WorkDrawing(this, 600);
        this.buyInPrice = getBuyInPrice();
        this.sellPrice = getSellPrice();
    }

    public void calculateMaterials() throws CalculatorException
    {
        materialsList = new ArrayList<>();

        materialsList.addAll(calculator.calcUnderFascia());
        materialsList.addAll(calculator.calcOverFascia());
        materialsList.addAll(calculator.calcBeam());
        materialsList.addAll(calculator.calcPosts());
        materialsList.addAll(calculator.calcJoists());
        materialsList.addAll(calculator.calcBargeBoards());
        materialsList.addAll(calculator.calcRoof());

        if (hasShed())
        {
            materialsList.addAll(calculator.calcCladding());
            materialsList.addAll(calculator.calcHorizontalBraces());
        }

    }

    public void calculateBoltsScrewsBrackets() throws CalculatorException
    {
        boltsScrewsBracketsList = new ArrayList<>();

        boltsScrewsBracketsList.addAll(calculator.calcRoofScrews());
        boltsScrewsBracketsList.addAll(calculator.calcJoistBrackets());
        boltsScrewsBracketsList.addAll(calculator.calcJoistBracketScrews());
        boltsScrewsBracketsList.addAll(calculator.calcFasciaBargeScrews());
        boltsScrewsBracketsList.addAll(calculator.calcMetalStrap());
        boltsScrewsBracketsList.addAll(calculator.calcBeamBolts());

        if (hasShed())
        {
            boltsScrewsBracketsList.addAll(calculator.calcCladdingScrews());
            boltsScrewsBracketsList.addAll(calculator.calcHorizontalBracesBrackets());
            boltsScrewsBracketsList.addAll(calculator.doorHandleBrackets());
        }
    }

    public double getSellPrice()
    {
        profitMargin = 1.5f;
        return buyInPrice * profitMargin;
    }

    public double getBuyInPrice()
    {
        double price = getMaterialPrice() + getBoltsScrewsBracketsPrice();
        return price;
    }

    public int getMaterialPrice()
    {
        int price = 0;
        for (IMaterials material : materialsList)
        {
            price += material.getPrice() * material.getAmount();
        }
        return price;
    }

    public int getBoltsScrewsBracketsPrice()
    {
        int price = 0;
        for (IMaterials material : boltsScrewsBracketsList)
        {
            price += material.getPrice() * material.getAmount();
        }
        return price;
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
        return calculator.extraPostsForLongCarport();
    }

    public int getNumberOfJoists()
    {
        return this.numberOfJoists;
    }

    public int getNumberOfCladdingBoards()
    {
        /// TODO: SKAL LIGE GENTÆNKES HER, SKAL DETTE FLYTTES IND SOM EN PROPERTY?
        return calculator.calcNumberOfCladdingBoards();
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

    public float getProfitMargin()
    {
        return profitMargin;
    }

    public void setProfitMargin(float profitMargin)
    {
        this.profitMargin = profitMargin;
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
