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
    public List<IMaterials> calcUnderFascia() throws CalculatorException {

        if (materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "understernbrædder".equals(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcOverFascia() throws CalculatorException
    {
        if (materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "oversternbrædder".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcBeam() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcRafters() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcPosts() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public int calcNumberOfPosts()
    {
        return 0;
    }

    @Override
    public boolean extraPostsForLongCarport()
    {
        return false;
    }

    @Override
    public List<IMaterials> calcJoists() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public int calcNumberOfJoists()
    {
        return 0;
    }

    @Override
    public List<IMaterials> calcBargeBoards() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcBattern() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcCladding() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public int calcNumberOfCladdingBoards()
    {
        return 0;
    }

    @Override
    public List<IMaterials> calcHorizontalBraces() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public int calcNumberOfHorizontalBraces()
    {
        return 0;
    }

    @Override
    public List<IMaterials> calcRoof() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcRoofScrews() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcJoistBrackets() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcFasciaBargeScrews() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcJoistBracketScrews() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcMetalStrap() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcBeamBolts() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcCladdingScrews() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> doorHandleBrackets() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcHorizontalBracesBrackets() throws CalculatorException
    {
        return List.of();
    }


}
