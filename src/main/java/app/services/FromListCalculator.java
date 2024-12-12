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
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Remme i sider, sadles ned i stolper".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcRafters() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcPosts() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Stolper".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public int calcNumberOfPosts()
    {
        if (materialsList == null || materialsList.isEmpty())
        {
            return 0;
        }

        return (int) materialsList.stream()
                .filter(material -> "Stolper".equals(material.getDescription()))
                .count();
    }

    @Override
    public boolean extraPostsForLongCarport()
    {
        return false;
    }

    @Override
    public List<IMaterials> calcJoists() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Spær, monteres på rem".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public int calcNumberOfJoists()
    {
        return 0;
    }

    @Override
    public List<IMaterials> calcBargeBoards() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Vandbrædt".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcBattern() throws CalculatorException
    {
        return List.of();
    }

    @Override
    public List<IMaterials> calcCladding() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "beklædning".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public int calcNumberOfCladdingBoards()
    {
        if (materialsList == null || materialsList.isEmpty())
        {
            return 0;
        }

        return (int) materialsList.stream()
                .filter(material -> "beklædning".equals(material.getDescription()))
                .count();
    }

    @Override
    public List<IMaterials> calcHorizontalBraces() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "løsholter til skur sider".equalsIgnoreCase(material.getDescription())
                        && "løsholter til skur gavle".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public int calcNumberOfHorizontalBraces()
    {
        if (materialsList == null || materialsList.isEmpty())
        {
            return 0;
        }

        return (int) materialsList.stream()
                .filter(material -> "beklædning".equals(material.getDescription())
                        && "løsholter til skur gavle".equalsIgnoreCase(material.getDescription()))
                .count();
    }

    @Override
    public List<IMaterials> calcRoof() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Tagplader".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcRoofScrews() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Skruer til tagplader".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcJoistBrackets() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Til montering af spær".equalsIgnoreCase(material.getDescription())
                        && "Til montering af spær".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcFasciaBargeScrews() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Til montering af stern&vandbrædt".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcJoistBracketScrews() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Til montering af universalbeslag + hulbånd".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcMetalStrap() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Til vindkryds".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcBeamBolts() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Til montering af rem".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcCladdingScrews() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "yderste beklædning".equalsIgnoreCase(material.getDescription())
                        && "inderste beklædning".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> doorHandleBrackets() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Til lås".equalsIgnoreCase(material.getDescription())
                        && "Til skurdør".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IMaterials> calcHorizontalBracesBrackets() throws CalculatorException
    {
        if(materialsList == null || materialsList.isEmpty()) throw new CalculatorException("materialList is null or empty");
        return materialsList.stream()
                .filter(material -> "Til montering af løsholter".equalsIgnoreCase(material.getDescription()))
                .collect(Collectors.toList());
    }


}
