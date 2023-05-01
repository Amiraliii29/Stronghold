package Model.Buildings;

import Model.Government;
import Model.Resources.Resource;

import java.util.ArrayList;

public abstract class Building {
    protected Government owner;
    protected String name;
    protected int width;
    protected int length;
    protected int xCoordinateLeft;
    protected int yCoordinateUp;
    protected ArrayList<String> lands;
    protected int hp;
    protected final int maximumHp;
    protected Resource resource;
    protected int numberOfResource;
    protected int cost;
    protected boolean canPass;

    // add each kind of building to users arraylist in database ! //TODO


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
        owner.addBuildings(this);
    }

    public void setCoordinate(int xCoordinateLeft, int yCoordinateUp) {
        this.xCoordinateLeft = xCoordinateLeft;
        this.yCoordinateUp = yCoordinateUp;
        //add building to square and remove last coordinate//TODO
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

    public int getMaximumHp(){
        return maximumHp;
    }
}
