package Model.Buildings;

import Model.Government;
import Model.Resources.Resource;

import java.util.ArrayList;

public abstract class Building {
    protected Resource resource;
    protected Government owner;
    protected String name;
    protected int width;
    protected int length;
    protected int xCoordinateLeft;
    protected int yCoordinateUp;
    protected ArrayList<String> lands;
    protected int hp;
    protected int numberOfResource;
    protected int cost;
    protected boolean canPass;

    // add each kind of building to users arraylist in database ! //TODO
    public Building(String name, int hp, Resource resource, int numberOfResource, int cost, boolean canPass, int width, int length) {
        this.name = name;
        this.hp = hp;
        this.resource = resource;
        this.numberOfResource = numberOfResource;
        this.cost = cost;
        this.canPass = canPass;
        this.width = width;
        this.length = length;
        lands = new ArrayList<>();
    }

    public void setWidthAndLength(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public void setCoordinate(int xCoordinateLeft, int yCoordinateUp) {
        this.xCoordinateLeft = xCoordinateLeft;
        this.yCoordinateUp = yCoordinateUp;
    }

    public int changeHP(int damage) {
        hp -= damage;
        return hp;
    }

    public Government getOwner() {
        return owner;
    }

    public void setOwner(Government owner) {
        this.owner = owner;
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

    public void addLands(String  land) {
        this.lands.add(land);
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
}
