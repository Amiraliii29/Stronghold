package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;

public class Square {
    private static final HashMap<String, Boolean> canPass;
    @JsonIgnore
    private transient ArrayList<UnitPrototype> units;
    @JsonIgnore
    private transient BuildingPrototype building;
    private Land land;
    private Trees tree;
    private int treeAmount;
    private int x;
    private int y;


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



    public BuildingPrototype getBuilding() {
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

    public ArrayList<UnitPrototype> getUnits() {
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

    public void setLand(Land land) {
        this.land = land;
    }

    public void setBuilding(BuildingPrototype building) {
        this.building = building;
    }

    public void newUnits() {
        units = new ArrayList<>();
    }




    public void addUnit(UnitPrototype unit) {
        this.units.add(unit);
    }

    public void removeUnit(UnitPrototype unit) {
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).equals(unit) && unit.getHP() == units.get(i).getHP()){
                units.remove(i);
                break;
            }
        }
    }

    public void changeTreeAmount(int amount) {
        treeAmount -= amount;
        if (treeAmount <= 0) {
            treeAmount = 0;
            tree = null;
        }
    }

    public HashMap<String, Integer> getUnitsTypeAndCount() {
        return null;
    }
}
