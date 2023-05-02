package Model;

import Model.Buildings.Building;

import java.util.ArrayList;

import Controller.JsonConverter;
import Model.Units.Troop;
import Model.Units.Unit;

public class DataBase {
    private static final ArrayList<Government> governments;
    private static final ArrayList<Map> maps;
    private static Government currentGovernment;
    private static Map selectedMap;
    private static Building selectedBuilding;
    private static ArrayList<Unit> selectedUnit;

    static {
        JsonConverter.fillFormerUsersDatabase("src/main/java/jsonData/Users.json");
        governments = new ArrayList<>();
        maps = new ArrayList<>();
        selectedUnit = new ArrayList<>();
    }



    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        DataBase.currentGovernment = currentGovernment;
    }

    public static Government getGovernmentByUserName(String userName) {
        for (Government government:governments)
            if (government.getOwner().getUsername().equals(userName))
                return government;

        return null;
    }


    public static void addGovernment(Government government) {
        governments.add(government);
    }

    public static Building getSelectedBuilding() {
        return selectedBuilding;
    }

    public static void setSelectedBuilding(Building selectedBuilding) {
        DataBase.selectedBuilding = selectedBuilding;
    }

    public static ArrayList<Unit> getSelectedUnit() {
        return selectedUnit;
    }

    public static void addSelectedUnit(Troop selectedUnit) {
        DataBase.selectedUnit.add(selectedUnit);
    }

    public static void setSelectedUnit(ArrayList<Unit> selectedUnit) {
        DataBase.selectedUnit = selectedUnit;
    }

    public static void addMap(Map map) {
        maps.add(map);
    }

    public static Map getMapByName(String name) {
        return null;
    }

    public static Map getSelectedMap() {
        return selectedMap;
    }

    public static void setSelectedMap(Map selectedMap) {
        DataBase.selectedMap = selectedMap;
    }
}