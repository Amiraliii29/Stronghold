package Model;

import Model.Buildings.Building;

import java.util.ArrayList;

public class Land {
    private String name;
    private boolean canPass;
    private ArrayList<Building> buildings;

    public Land(String name, boolean canPass) {
        this.name = name;
        this.canPass = canPass;
    }

    public String getName() {
        return name;
    }

    public boolean isCanPass() {
        return canPass;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addBuilding(Building building) {
       buildings.add(building);
    }
}
