package Model.Buildings;

import Model.Government;
import Model.Resource;

import java.util.ArrayList;

public abstract class Building {
    protected Government owner;
    protected String name;
    protected int width;
    protected int length;
    protected int xCoordinateLeft;
    protected int yCoordinateUp;
    protected ArrayList<String> lands;
    protected final int maximumHp;
    protected int hp;
    protected Resource resource;
    protected int numberOfResource;
    protected int cost;
    protected boolean canPass;


    public Building(Government owner, String name, int width, int length, int xCoordinateLeft, int yCoordinateUp,
                    ArrayList<String> lands, int hp, Resource resource, int numberOfResource, int cost, boolean canPass) {
        this.owner = owner;
        this.name = name;
        this.width = width;
        this.length = length;
        this.xCoordinateLeft = xCoordinateLeft;
        this.yCoordinateUp = yCoordinateUp;
        this.lands = lands;
        this.hp = hp;
        this.resource = resource;
        this.numberOfResource = numberOfResource;
        this.cost = cost;
        this.canPass = canPass;
        this.maximumHp=hp;
    }

    public static String getBuildingCategoryByName(String buildingName){
        for (String name : Generator.getGeneratorsName()) 
            if(buildingName.equals(name)) return "Generator";
                
        for (String name : Barrack.getBarracksName()) 
            if(buildingName.equals(name)) return "Barrack";
                
        for(String name : Defence.getDefencesName())
            if(buildingName.equals(name)) return "Defence";

        for(String name : TownBuilding.getTownBuildingsName())
            if(buildingName.equals(name)) return "TownBuilding";

        for(String name : Stockpile.getStockpilesName())
            if(buildingName.equals(name)) return "Stockpile";

        return null;
    }

    public int getMaximumHp(){
        return maximumHp;
    }

    public int changeHP(int damage) {
        hp -= damage;
        return hp;
    }

    public Government getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public int getXCoordinateLeft() {
        return xCoordinateLeft;
    }

    public int getYCoordinateUp() {
        return yCoordinateUp;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<String> getLands() {
        return lands;
    }

    public int getHp() {
        return hp;
    }

    public Resource getResource() {
        return resource;
    }

    public int getNumberOfResource() {
        return numberOfResource;
    }

    public int getCost() {
        return cost;
    }

    public boolean getCanPass() {
        return canPass;
    }

    public static void readBuildingsFromFile() {
        Defence.createDefence(null, -1, -1, "");
        Generator.createGenerator(null, -1, -1, "");
        Barrack.createBarrack(null, -1, -1, "");
        Stockpile.createStockpile(null, -1, -1, "");
        TownBuilding.createTownBuilding(null, -1, -1, "");
    }
}
