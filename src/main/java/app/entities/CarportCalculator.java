package app.entities;

import app.exceptions.CalculatorException;
import app.exceptions.DatabaseException;

import java.util.List;

public interface CarportCalculator
{
    public List<IMaterials> calcUnderFascia(int length, int width) throws CalculatorException;
    public List<IMaterials> calcOverFascia(int length, int width) throws CalculatorException;
    public List<IMaterials> calcBeam(int length) throws CalculatorException;
    public List<IMaterials> calcRafters() throws CalculatorException;
    public List<IMaterials> calcPosts(int length, int width, int shedLength, int shedWidth) throws CalculatorException;
    public int calcNumberOfPosts(int length, int width, int shedLength, int shedWidth);
    public boolean extraPostsForLongCarport(int length, int shedLength);
    public List<IMaterials> calcJoists(int length) throws CalculatorException;
    public int calcNumberOfJoists(int length);
    public List<IMaterials> calcBargeBoards(int length, int width) throws CalculatorException;
    public List<IMaterials> calcBattern() throws CalculatorException;
    public List<IMaterials> calcCladding(int shedLength, int shedWidth) throws CalculatorException;
    public int calcNumberOfCladdingBoards(int shedLength, int shedWidth);
    public List<IMaterials> calcHorizontalBraces(int length, int shedLength) throws CalculatorException;
    public int calcNumberOfHorizontalSideBraces(int shedWidth);
    public int calcNumberOfHorizontalEndBraces(int shedLength);
    public List<IMaterials> calcRoof(int length, int width) throws CalculatorException;
    public List<IMaterials> calcRoofScrews(int length, int width) throws CalculatorException;
    public List<IMaterials> calcJoistBrackets(int length) throws CalculatorException;
    public List<IMaterials> calcFasciaBargeScrews(int length, int width) throws CalculatorException;
    public List<IMaterials> calcJoistBracketScrews(int length) throws CalculatorException;
    public List<IMaterials> calcMetalStrap(int length, int width) throws CalculatorException;
    public List<IMaterials> calcBeamBolts(int length, int width, int shedLength, int shedWidth) throws CalculatorException;
    public List<IMaterials> calcCladdingScrews(int shedLength, int shedWidth) throws CalculatorException;
    public List<IMaterials> doorHandleBrackets() throws CalculatorException;
    public List<IMaterials> calcHorizontalBracesBrackets(int shedLength, int shedWidth) throws CalculatorException;



}
