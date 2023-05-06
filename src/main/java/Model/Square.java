package Model;

import Model.Buildings.Building;

import Model.Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class Square {
    private static HashMap<String, Boolean> canPass;
    private ArrayList<Unit> units;
    private Building building;
    private Land land;
    private String cliffDirection;
    private Resource resource;
    private Trees tree;
    private int treeAmount;
    private int x;
    private int y;

    static {
        canPass = new HashMap<>();
        for (Land land : Land.values()) {
            if (Land.getName(land).equals("rock") || Land.getName(land).equals("lowDepthWater")
                    || Land.getName(land).equals("river") || Land.getName(land).equals("smallLake")
                    || Land.getName(land).equals("bigLake") || Land.getName(land).equals("sea")
                    || Land.getName(land).equals("cliff"))
                canPass.put(Land.getName(land), false);
            else
                canPass.put(Land.getName(land), true);
        }
    }

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        units = new ArrayList<>();
        this.land = Land.DEFAULT;
        this.building = null;
        this.tree = null;
        this.cliffDirection = null;
        this.treeAmount = 0;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    public void removeUnit(Unit unit) {
        this.units.remove(unit);
    }

    public void removeAllUnit(Unit unit) {
        int cnt = 0;
        for (Unit u : units) {
            if (u.equals(unit)) cnt++;
        }
        for (int i = 0; i < cnt; i++) {
            units.remove(unit);
        }
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
        return canPass.get(land.name());
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public void newSelectedUnit() {
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

    public void changeTreeAmount(int amount) {
        treeAmount += amount;
    }

    public void setCliffDirection(String cliffDirection) {
        this.cliffDirection = cliffDirection;
    }

    public String getCliffDirection() {
        return cliffDirection;
    }
}
