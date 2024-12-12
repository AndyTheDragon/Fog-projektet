package app.services;

import app.entities.IMaterials;
import app.exceptions.CalculatorException;

import java.util.List;

public interface CarportCalculator
{
    public List<IMaterials> calcUnderFascia() throws CalculatorException;
    public List<IMaterials> calcOverFascia() throws CalculatorException;
    public List<IMaterials> calcBeam() throws CalculatorException;
    public List<IMaterials> calcRafters() throws CalculatorException;
    public List<IMaterials> calcPosts() throws CalculatorException;
    public int calcNumberOfPosts();
    public boolean extraPostsForLongCarport();
    public List<IMaterials> calcJoists() throws CalculatorException;
    public int calcNumberOfJoists();
    public List<IMaterials> calcBargeBoards() throws CalculatorException;
    public List<IMaterials> calcBattern() throws CalculatorException;
    public List<IMaterials> calcCladding() throws CalculatorException;
    public int calcNumberOfCladdingBoards();
    public List<IMaterials> calcHorizontalBraces() throws CalculatorException;
    public int calcNumberOfHorizontalBraces();
    public List<IMaterials> calcRoof() throws CalculatorException;
    public List<IMaterials> calcRoofScrews() throws CalculatorException;
    public List<IMaterials> calcJoistBrackets() throws CalculatorException;
    public List<IMaterials> calcFasciaBargeScrews() throws CalculatorException;
    public List<IMaterials> calcJoistBracketScrews() throws CalculatorException;
    public List<IMaterials> calcMetalStrap() throws CalculatorException;
    public List<IMaterials> calcBeamBolts() throws CalculatorException;
    public List<IMaterials> calcCladdingScrews() throws CalculatorException;
    public List<IMaterials> doorHandleBrackets() throws CalculatorException;
    public List<IMaterials> calcHorizontalBracesBrackets() throws CalculatorException;

}
