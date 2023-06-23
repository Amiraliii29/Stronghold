package View.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

import Controller.GameMenuController;
import Model.DataBase;

public class BuildingInfo {
    public static ArrayList<String> imagesOrder;
    public static TextField one;
    public static TextField two;
    public static TextField three;
    public static TextField four;
    public static TextField five;
    public static TextField six;
    public static TextField seven;
    public static TextField eight;


    public static ArrayList<TextField> getTextFields () {
        ArrayList<TextField> textFields = new ArrayList<>();
        textFields.add(one);
        textFields.add(two);
        textFields.add(three);
        textFields.add(four);
        textFields.add(five);
        textFields.add(six);
        textFields.add(seven);
        textFields.add(eight);
        return textFields;
    }


    public void archerBow(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("ArcherBow", "1");
    }

    public void slave(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("Slave", "1");
    }

    public void slinger(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("Slinger", "1");
    }

    public void assassin(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("Assassin", "1");
    }

    public void arabianSwordMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("ArabianSwordMan", "1");
    }

    public void horseArcher(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("HorseArcher", "1");
    }

    public void fireThrower(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("FireThrower", "1");
    }

    public void archer(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("Archer", "1");
    }

    public void spearMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("SpearMan", "1");
    }

    public void maceMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("MaceMan", "1");
    }

    public void crossBowMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("CrossBowMan", "1");
    }

    public void pikeMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("PikeMan", "1");
    }

    public void swordMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("SwordMan", "1");
    }

    public void knight(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("Knight", "1");
    }

    public void disband(MouseEvent mouseEvent) {

    }

    public void stop(MouseEvent mouseEvent) {

    }

    public void patrol(MouseEvent mouseEvent) {

    }

    public void attack(MouseEvent mouseEvent) {

    }

    public void stanGround(MouseEvent mouseEvent) {
        GameMenuController.setUnitModeController("stanGround");
    }

    public void defensive(MouseEvent mouseEvent) {
        GameMenuController.setUnitModeController("defensive");
    }

    public void aggressive(MouseEvent mouseEvent) {
        GameMenuController.setUnitModeController("Aggressive");
    }
}
