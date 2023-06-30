package Model;

import java.util.ArrayList;

public class Square {
    private transient ArrayList<UnitPrototype> units;
    private transient BuildingPrototype building;
    private Land land;
    private Trees tree;
    private int treeAmount;
    private final int x;
    private final int y;


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
}
