package Model.Buildings;

import Model.Land;
import Model.Resource;

import java.util.ArrayList;

public abstract class Building {
    protected Resource resource;
    protected String name;
    protected int xCoordinateLeft;
    protected int xCoordinateRight;
    protected int yCoordinateDown;
    protected int yCoordinateUp;
    protected ArrayList<Land> lands;
    protected int hp;
    protected int numberOfResource;
    protected int cost;
    protected boolean canPass;

    // add each kind of building to users arraylist in database ! //TODO
    public Building(String name, int hp, Resource resource, int numberOfResource, int cost) {
        this.name = name;
        this.hp = hp;
        this.resource = resource;
        this.numberOfResource = numberOfResource;
        this.cost = cost;
        lands = new ArrayList<>();
    }

    public void setCoordinate(int xCoordinateLeft, int xCoordinateRight, int yCoordinateDown, int yCoordinateUp) {
        this.xCoordinateLeft = xCoordinateLeft;
        this.xCoordinateRight = xCoordinateRight;
        this.yCoordinateDown = yCoordinateDown;
        this.yCoordinateUp = yCoordinateUp;
    }

    public String getName() {
        return name;
    }

    public int getXCoordinateLeft() {
        return xCoordinateLeft;
    }

    public int getXCoordinateRight() {
        return xCoordinateRight;
    }

    public int getYCoordinateDown() {
        return yCoordinateDown;
    }

    public int getYCoordinateUp() {
        return yCoordinateUp;
    }

    public ArrayList<Land> getLands() {
        return lands;
    }

    public void setLands(ArrayList<Land> lands) {
        this.lands = lands;
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
}
