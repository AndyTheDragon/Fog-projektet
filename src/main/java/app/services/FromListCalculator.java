package app.services;

import app.entities.IMaterials;
import app.exceptions.CalculatorException;

import java.util.List;
import java.util.stream.Collectors;

public class FromListCalculator implements CarportCalculator
{
    private List<IMaterials> materialsList;

    FromListCalculator(List<IMaterials> materialsList)
    {
        this.materialsList = materialsList;
    }

    @Override
    public List<IMaterials> calcUnderFascia(int length, int width) throws CalculatorException {

        if (materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "understernbrædder".equals(material.getDescription()))
                .collect(Collectors.toList());
    }


    @Override
    public List<IMaterials> calcOverFascia(int length, int width) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcBeam(int length) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcRafters() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcPosts(int length, int width, int shedLength, int shedWidth) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public int calcNumberOfPosts(int length, int width, int shedLength, int shedWidth)
    {
        return 0;
    }

    @Override
    public boolean extraPostsForLongCarport(int length, int shedLength)
    {
        return false;
    }

    @Override
    public List<IMaterials> calcJoists(int length) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public int calcNumberOfJoists(int length)
    {
        return 0;
    }

    @Override
    public List<IMaterials> calcBargeBoards(int length, int width) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcBattern() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcCladding(int shedLength, int shedWidth) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public int calcNumberOfCladdingBoards(int shedLength, int shedWidth)
    {
        return 0;
    }

    @Override
    public List<IMaterials> calcHorizontalBraces(int length, int shedLength) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public int calcNumberOfHorizontalSideBraces(int shedWidth)
    {
        return 0;
    }

    @Override
    public int calcNumberOfHorizontalEndBraces(int shedLength)
    {
        return 0;
    }

    @Override
    public List<IMaterials> calcRoof(int length, int width) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcRoofScrews(int length, int width) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcJoistBrackets(int length) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcFasciaBargeScrews(int length, int width) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcJoistBracketScrews(int length) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcMetalStrap(int length, int width) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcBeamBolts(int length, int width, int shedLength, int shedWidth) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcCladdingScrews(int shedLength, int shedWidth) throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> doorHandleBrackets() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcHorizontalBracesBrackets(int shedLength, int shedWidth) throws CalculatorException
    {
        return List.of();
    }
}
