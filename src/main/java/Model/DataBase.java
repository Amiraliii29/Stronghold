package Model;

import Model.Buildings.Building;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controller.JsonConverter;

public class DataBase {
    private static final ArrayList<User> users = new ArrayList<User>();
    private static final ArrayList<Map> maps = new ArrayList<Map>();
    private static User currentUser;
    private static Map selectedMap;
    private static Building selectedBuilding;
    private static ArrayList<Troop> selectedUnit= new ArrayList<Troop>();


    static{
        JsonConverter.fillFormerUsersDatabase("src/main/java/jsonData/Users.json");
    }

    public static ArrayList<User> getUsers(){
        return users;
    }

    public static void setCurrentUser(User currentUser) {
        DataBase.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static User getUserByUserName(String userName){
        for (User user : users) 
            if(user.getUsername().equals(userName))
                return user;

        return null;
    }

    public static User getUserByEmail(String email){
        for (User user : users) 
            if(user.getEmail().equals(email))
                return user;

        return null;
    }


    public static void addUser(User user){
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

    public static void addSelectedUnit(Troop selectedUnit) {
        DataBase.selectedUnit.add( selectedUnit);
    }

    public static  void addMap(Map map){
        maps.add(map);
    }

    public static Map getMapByName(String name){
    }

    public static Map getSelectedMap() {
        return selectedMap;
    }

    public static void setSelectedMap(Map selectedMap) {
        DataBase.selectedMap = selectedMap;
    }

    public static String getCaptcha(){
    }


}