package Model;

import Model.Buildings.Building;
import Model.Resources.Resource;

import Model.Units.Troop;
import Model.Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class Square {
    private ArrayList<Unit> units;
    private Building building;
    private String land;
    private Resource resource;
    private int x;
    private int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        units = new ArrayList<>();
        this.land = "defaultLand";
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void addTroop(Unit unit) {
        this.units.add(unit);
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
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

    public String getLand() {
        return land;
    }

    public HashMap<Unit, Integer> getUnitsTypeAndCount() {
        HashMap<Unit, Integer> troopsTypeAndCount = new HashMap<>();
        for (Unit unit : units) {
            if (!troopsTypeAndCount.keySet().contains(unit))
                troopsTypeAndCount.put(unit, 1);
            else
                troopsTypeAndCount.put(unit, troopsTypeAndCount.get(unit) + 1);
        }
        return troopsTypeAndCount;
    }

    public boolean canPass() {
        //TODO
    }
}
