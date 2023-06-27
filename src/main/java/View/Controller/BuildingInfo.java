package View.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

import Controller.GameMenuController;

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


    public void archerBow(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("ArcherBow");
    }

    public void slave(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("Slave");
    }

    public void slinger(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("Slinger");
    }

    public void assassin(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("Assassin");
    }

    public void arabianSwordMan(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("ArabianSwordMan");
    }

    public void horseArcher(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("HorseArcher");
    }

    public void fireThrower(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("FireThrower");
    }

    public void archer(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("Archer");
    }

    public void spearMan(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("SpearMan");
    }

    public void maceMan(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("MaceMan");
    }

    public void crossBowMan(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("CrossBowMan");
    }

    public void pikeMan(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("PikeMan");
    }

    public void swordMan(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("SwordMan");
    }

    public void knight(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("Knight");
    }

    public void ladderMan(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("LadderMan");
    }

    public void engineer(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("Engineer");
    }

    public void tunneler(MouseEvent ignoredMouseEvent) {
        GameMenuController.createUnitController("Tunneler");
    }

    public void stanGround(MouseEvent ignoredMouseEvent) {
        GameMenuController.setUnitModeController("Standing");
    }

    public void defensive(MouseEvent ignoredMouseEvent) {
        GameMenuController.setUnitModeController("Defensive");
    }

    public void aggressive(MouseEvent ignoredMouseEvent) {
        GameMenuController.setUnitModeController("Aggressive");
    }

    public void stop(MouseEvent ignoredMouseEvent) {

    }

    public void patrol(MouseEvent ignoredMouseEvent) {

    }

    public void attack(MouseEvent ignoredMouseEvent) {

    }

    public void disband(MouseEvent ignoredMouseEvent) {
        GameMenuController.disbandUnit();
    }

    public void repair(MouseEvent ignoredMouseEvent) {
        GameMenuController.repair();
    }

    public void close(MouseEvent ignoredMouseEvent) {
        GameMenuController.modifyGates(false);
    }

    public void open(MouseEvent ignoredMouseEvent) {
        GameMenuController.modifyGates(true);
    }

    public void sword(MouseEvent ignoredMouseEvent) {
        GameMenuController.changeProduct("Sword");
    }

    public void mace(MouseEvent ignoredMouseEvent) {
        GameMenuController.changeProduct("Mace");
    }

    public void bow(MouseEvent ignoredMouseEvent) {
        GameMenuController.changeProduct("Bow");
    }

    public void crossBow(MouseEvent ignoredMouseEvent) {
        GameMenuController.changeProduct("CrossBow");
    }

    public void spear(MouseEvent ignoredMouseEvent) {
        GameMenuController.changeProduct("Spear");
    }

    public void pike(MouseEvent ignoredMouseEvent) {
        GameMenuController.changeProduct("Pike");
    }

    public void leatherArmour(MouseEvent ignoredMouseEvent) {
        GameMenuController.changeProduct("LeatherArmour");
    }

    public void cheese(MouseEvent ignoredMouseEvent) {
        GameMenuController.changeProduct("Cheese");
    }
}
