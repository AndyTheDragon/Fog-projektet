package app.entities;

import app.exceptions.DatabaseException;

import java.util.List;

public interface CarportCalculator
{
    public List<IMaterials> calcUnderFascia() throws DatabaseException;
    public List<IMaterials> calcOverFascia();
    public List<IMaterials> calcBeam();
    public List<IMaterials> calcRafters();
    public List<IMaterials> calcPosts();
    public int calcNumberOfPosts();
    public boolean extraPostsForLongCarport();
    public List<IMaterials> calcJoists();
    public int calcNumberOfJoists();
    public List<IMaterials> calcBargeBoards();
    public List<IMaterials> calcBattern();
    public List<IMaterials> calcCladding();
    public int calcNumberOfCladdingBoards();
    public List<IMaterials> calcHorizontalBraces();
    public int calcNumberOfHorizontalBraces();
    public List<IMaterials> calcRoof();
    public List<IMaterials> calcRoofScrews();
    public List<IMaterials> calcJoistBrackets();
    public List<IMaterials> calcFasciaBargeScrews();
    public List<IMaterials> calcJoistBracketScrews();
    public List<IMaterials> calcMetalStrap();
    public List<IMaterials> calcBeamBolts();
    public List<IMaterials> calcCladdingScrews();
    public List<IMaterials> doorHandleBrackets();
    public List<IMaterials> calcHorizontalBracesBrackets();



}
