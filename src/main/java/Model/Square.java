package Model;

import Model.Buildings.Building;
import Model.Units.Unit;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;

public class Square {
    public static final HashMap<String, Boolean> canPass;
    @JsonIgnore
    public transient ArrayList<Unit> units;
    @JsonIgnore
    public transient Building building;
    public Land land;
    public Trees tree;
    public int treeAmount;
    public int x;
    public int y;



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
        this.treeAmount = 0;
    }



    public Building getBuilding() {
        return building;
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

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public Trees getTree() {
        return tree;
    }

    public int getTreeAmount() {
        return treeAmount;
    }

    public boolean canPass() {
        return canPass.get(Land.getName(land));
    }



    public void setTree(Trees tree) {
        this.tree = tree;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public void setTreeAmount(int treeAmount) {
        this.treeAmount = treeAmount;
    }

    public void newUnits() {
        units = new ArrayList<>();
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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

    public void changeTreeAmount(int amount) {
        treeAmount -= amount;
        if (treeAmount <= 0) {
            treeAmount = 0;
            tree = null;
        }
    }

    public void newSelectedUnit() {
        units.clear();
    }
}
