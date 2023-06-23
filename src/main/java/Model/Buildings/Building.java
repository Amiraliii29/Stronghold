package Model.Buildings;

import Model.Government;
import Model.Resource;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Building {
    protected static LinkedList<String> buildingsNames;
    protected static LinkedList<Building> buildings;
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

    static {
        buildings = new LinkedList<>();
        buildingsNames = new LinkedList<>();
        Defence.load();
        Generator.load();
        Stockpile.load();
        TownBuilding.load();
        Barrack.load();
    }

    public void setxCoordinateLeft(int xCoordinateLeft) {
        this.xCoordinateLeft = xCoordinateLeft;
    }

    public void setyCoordinateUp(int yCoordinateUp) {
        this.yCoordinateUp = yCoordinateUp;
    }

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

    public boolean getCanPass(Boolean up) {
        if (!up) return canPass;
        return true;
    }

    public void changeCanPass(boolean state){
        this.canPass=state;
    }

    public static LinkedList<String> getBuildingsNames() {
        return buildingsNames;
    }

    public static LinkedList<Building> getBuildings() {
        return buildings;
    }

    public static Building getBuildingByName(String buildingName) {
        for (Building building : buildings)
            if (building.name.equals(buildingName))
                return building;
        return null;
    }

    public static void load() {
        return;
    }
}
