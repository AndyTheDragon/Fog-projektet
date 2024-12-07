package app.entities;

import java.util.List;

public interface CarportCalculator
{
    public List<IMaterials> calcUnderFascia(int length, int width);
    public List<IMaterials> calcOverFascia(int length, int width);
    public List<IMaterials> calcBeam(int length);
    public List<IMaterials> calcRafters();
    public List<IMaterials> calcPosts(int length, int width, int shedLength, int shedWidth);
    public int calcNumberOfPosts(int length, int width, int shedLength, int shedWidth);
    public boolean extraPostsForLongCarport(int length, int shedLength);
    public List<IMaterials> calcJoists(int length);
    public int calcNumberOfJoists(int length);
    public List<IMaterials> calcBargeBoards(int length, int width);
    public List<IMaterials> calcBattern();
    public List<IMaterials> calcCladding(int shedLength, int shedWidth);
    public int calcNumberOfCladdingBoards(int shedLength, int shedWidth);
    public List<IMaterials> calcHorizontalBraces(int shedLength, int shedWidth);
    public int calcNumberOfHorizontalBraces(int shedLength, int shedWidth);
    public List<IMaterials> calcRoof(int length, int width);
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
