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
    private Land  land;
    private Resource resource;
    private Trees tree = null;
    private int treeAmount = 0;
    private int x;
    private int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        units = new ArrayList<>();
        this.land = Land.DEFAULT;
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

    public Land getLand() {
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

    public void setLand(Land land) {
        this.land = land;
    }
    public void removeAllTroops(){
        units.clear();
    }

    public Trees getTree() {
        return tree;
    }

    public void setTree(Trees tree) {
        this.tree = tree;
    }

    public void setTreeAmount(int treeAmount) {
        this.treeAmount = treeAmount;
    }
    public void changeTreeAmount(int amount){
        treeAmount += amount;
    }
}
