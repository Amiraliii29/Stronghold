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
        GameMenuController.createUnitController("ArcherBow");
    }

    public void slave(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("Slave");
    }

    public void slinger(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("Slinger");
    }

    public void assassin(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("Assassin");
    }

    public void arabianSwordMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("ArabianSwordMan");
    }

    public void horseArcher(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("HorseArcher");
    }

    public void fireThrower(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("FireThrower");
    }

    public void archer(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("Archer");
    }

    public void spearMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("SpearMan");
    }

    public void maceMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("MaceMan");
    }

    public void crossBowMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("CrossBowMan");
    }

    public void pikeMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("PikeMan");
    }

    public void swordMan(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("SwordMan");
    }

    public void knight(MouseEvent mouseEvent) {
        GameMenuController.createUnitController("Knight");
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

    public void stop(MouseEvent mouseEvent) {

    }

    public void patrol(MouseEvent mouseEvent) {

    }

    public void attack(MouseEvent mouseEvent) {

    }

    public void disband(MouseEvent mouseEvent) {

    }

    public void repair(MouseEvent mouseEvent) {
    }

    public void close(MouseEvent mouseEvent) {
    }

    public void open(MouseEvent mouseEvent) {
    }

    public void sword(MouseEvent mouseEvent) {
    }

    public void mace(MouseEvent mouseEvent) {
    }

    public void bow(MouseEvent mouseEvent) {
    }

    public void crossBow(MouseEvent mouseEvent) {
    }

    public void spear(MouseEvent mouseEvent) {
    }

    public void pike(MouseEvent mouseEvent) {
    }

    public void leatherArmour(MouseEvent mouseEvent) {
    }

    public void cheese(MouseEvent mouseEvent) {
    }
}
