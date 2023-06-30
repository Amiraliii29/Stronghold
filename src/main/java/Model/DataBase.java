package Model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import Model.Units.Unit;
import View.Game;
import View.LoginMenu;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class DataBase {
    private static Game game;
    private static Government myGovernment;
    private static final Clipboard clipboard;
    private static Map selectedMap;
    private static BuildingPrototype selectedBuilding;
    private static ArrayList<Unit> selectedUnit;
    private static int captchaNumber;


    static {
        clipboard= Clipboard.getSystemClipboard();
        selectedUnit = new ArrayList<>();
    }



    public static BuildingPrototype getSelectedBuilding() {
        return selectedBuilding;
    }

    public static ArrayList<Unit> getSelectedUnit() {
        return selectedUnit;
    }

    public static Map getSelectedMap() {
        return selectedMap;
    }

    public static int getCaptchaNumber() {
        return captchaNumber;
    }

    public static Game getGame() {
        return game;
    }

    public static String getRandomCaptchaImageAddress() throws MalformedURLException {
        int[] numbers = {1181 , 1381 , 1491 , 1722 , 1959 , 2163 , 8692 , 8776 , 8972 , 8996 , 9061 ,
                9386 , 9582 , 9633};
        Random random = new Random();
        int randomIndex  = random.nextInt(numbers.length - 1);
        captchaNumber = numbers[randomIndex];
        return String.valueOf(new URL(LoginMenu.class.getResource("/Images/Captcha/" + captchaNumber + ".png")
                .toExternalForm()));
    }

    public static Government getMyGovernment() {
        return myGovernment;
    }




    public static void setSelectedBuilding(BuildingPrototype selectedBuilding) {
        DataBase.selectedBuilding = selectedBuilding;
    }

    public static void setSelectedMap(Map selectedMap) {
        DataBase.selectedMap = selectedMap;
    }

    public static void setSelectedUnit(ArrayList<Unit> selectedUnit) {
        DataBase.selectedUnit = selectedUnit;
    }

    public static void setGame(Game game) {
        DataBase.game = game;
    }

    public static void setMyGovernment(Government myGovernment) {
        DataBase.myGovernment = myGovernment;
    }






    public static boolean isUnitFriendly(Government owner, Unit unit) {
        return owner.equals(unit.getOwner());
    }

    public static boolean isBuildingFriendly(Government owner, BuildingPrototype building) {
        String ownerUsername = owner.getOwner().getUsername();
        if (ownerUsername.equals(building.getOwner().getOwner().getUsername()))
            return true;
        return false;
    }

    public static boolean areSelectedUnitsRanged() {
        if (selectedUnit.get(0).getAttackRange() > 1)
            return true;
        else return false;
    }

    public static void copyStringToClipboard(String targetString){
        ClipboardContent content=new ClipboardContent();
        content.putString(targetString);
        clipboard.setContent(content);
    }

    public static String readClipboardString() {
        return clipboard.getString();
    }
}