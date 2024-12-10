package app.entities;

import app.exceptions.DatabaseException;

import java.util.List;

public interface CarportCalculator
{
    public List<IMaterials> calcUnderFascia(int length, int width) throws DatabaseException;
    public List<IMaterials> calcOverFascia(int length, int width) throws DatabaseException;
    public List<IMaterials> calcBeam(int length) throws DatabaseException;
    public List<IMaterials> calcRafters() throws DatabaseException;
    public List<IMaterials> calcPosts(int length, int width, int shedLength, int shedWidth) throws DatabaseException;
    public int calcNumberOfPosts(int length, int width, int shedLength, int shedWidth);
    public boolean extraPostsForLongCarport(int length, int shedLength);
    public List<IMaterials> calcJoists(int length) throws DatabaseException;
    public int calcNumberOfJoists(int length);
    public List<IMaterials> calcBargeBoards(int length, int width) throws DatabaseException;
    public List<IMaterials> calcBattern() throws DatabaseException;
    public List<IMaterials> calcCladding(int shedLength, int shedWidth) throws DatabaseException;
    public int calcNumberOfCladdingBoards(int shedLength, int shedWidth);
    public List<IMaterials> calcHorizontalBraces(int shedLength, int shedWidth) throws DatabaseException;
    public int calcNumberOfHorizontalBraces(int shedLength, int shedWidth);
    public List<IMaterials> calcRoof(int length, int width) throws DatabaseException;
    public List<IMaterials> calcRoofScrews(int length, int width);
    public List<IMaterials> calcJoistBrackets(int length);
    public List<IMaterials> calcFasciaBargeScrews(int length, int width);
    public List<IMaterials> calcJoistBracketScrews(int length);
    public List<IMaterials> calcMetalStrap(int length, int width);
    public List<IMaterials> calcBeamBolts(int length, int width, int shedLength, int shedWidth);
    public List<IMaterials> calcCladdingScrews(int shedLength, int shedWidth);
    public List<IMaterials> doorHandleBrackets();
    public List<IMaterials> calcHorizontalBracesBrackets(int shedLength, int shedWidth);



}
