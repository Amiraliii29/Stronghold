package Model;

import Model.Buildings.Building;

import java.util.ArrayList;

public class DataBase {
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Map> maps = new ArrayList<>();
    private static ArrayList<User> playersInGame = new ArrayList<>();
    private static User currentUser;
    private static Map selectedMap;
    private static Building selectedBuilding;
    private static ArrayList<Troop> selectedUnit = new ArrayList<>();

    public static void setCurrentUser(User currentUser) {
        DataBase.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public User getUserByUserName(String userName) {
    }

    public void addUser(User user) {
        users.add(user);
    }

    public static Building getSelectedBuilding() {
        return selectedBuilding;
    }

    public static void setSelectedBuilding(Building selectedBuilding) {
        DataBase.selectedBuilding = selectedBuilding;
    }


    public static ArrayList<Troop> getSelectedUnit() {
        return selectedUnit;
    }

    public static void addSelectedUnit(Troop troop) {
        selectedUnit.add(troop);
    }

    public static void addMap(Map map) {
        maps.add(map);
    }

    public static Map getMapByName(String name) {
    }

    public static Map getSelectedMap() {
        return selectedMap;
    }

    public static void setSelectedMap(Map selectedMap) {
        DataBase.selectedMap = selectedMap;
    }

    public static ArrayList<User> getPlayersInGame() {
        return playersInGame;
    }

    public static void addPlayersInGame(User playerInGame) {
        playersInGame.add(playerInGame);
    }
}
