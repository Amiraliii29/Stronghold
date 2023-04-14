package Model;

import Model.Buildings.Building;

import java.util.ArrayList;

public class Square {
    private ArrayList<Troop> troops;
    private Building building;
    private Land land;
    private Resource resource;
    private int x;
    private int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        troops = new ArrayList<>();
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void addTroop(Troop troop) {
        this.troops.add(troop);
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
