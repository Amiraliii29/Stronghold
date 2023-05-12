package Model;

import Model.Buildings.Building;

import Model.Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class Square {
    private static final HashMap<String, Boolean> canPass;
    private ArrayList<Unit> units;
    private Building building;
    private Land land;
    private String cliffDirection;
    private Trees tree;
    private int treeAmount;
    private final int x;
    private final int y;

    static {
        canPass = new HashMap<>();
        for (Land land : Land.values()) {
            if (Land.getName(land).equals("rock") || Land.getName(land).equals("ditch")
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
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).equals(unit) && unit.getHitPoint() == units.get(i).getHitPoint()){
                units.remove(i);
                break;
            }
        }
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Land getLand() {
        return land;
    }

    public HashMap<String, Integer> getUnitsTypeAndCount() {
        HashMap<String, Integer> troopsTypeAndCount = new HashMap<>();
        for (Unit unit : units) {
            if (!troopsTypeAndCount.containsKey(unit.getName()))
                troopsTypeAndCount.put(unit.getName(), 1);
            else
                troopsTypeAndCount.put(unit.getName(), troopsTypeAndCount.get(unit.getName()) + 1);
        }
        return troopsTypeAndCount;
    }

    public boolean canPass() {
        return canPass.get(Land.getName(land));
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
        treeAmount -= amount;
        if (treeAmount <= 0) {
            treeAmount = 0;
            tree = null;
        }
    }

    public int getTreeAmount() {
        return treeAmount;
    }

    public void setCliffDirection(String cliffDirection) {
        this.cliffDirection = cliffDirection;
    }

    public String getCliffDirection() {
        return cliffDirection;
    }
}
