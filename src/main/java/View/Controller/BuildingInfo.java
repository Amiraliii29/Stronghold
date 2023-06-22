package View.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

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


    static {
        one = new TextField();
        two = new TextField();
        three = new TextField();
        four = new TextField();
        five = new TextField();
        six = new TextField();
        seven = new TextField();
        eight = new TextField();
    }


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
    }

    public void slave(MouseEvent mouseEvent) {
    }

    public void slinger(MouseEvent mouseEvent) {
    }

    public void assassin(MouseEvent mouseEvent) {
    }

    public void arabianSwordMan(MouseEvent mouseEvent) {
    }

    public void horseArcher(MouseEvent mouseEvent) {
    }

    public void fireThrower(MouseEvent mouseEvent) {
    }

    public void archer(MouseEvent mouseEvent) {
    }

    public void spearMan(MouseEvent mouseEvent) {
    }

    public void maceMan(MouseEvent mouseEvent) {
    }

    public void crossBowMan(MouseEvent mouseEvent) {
    }

    public void pikeMan(MouseEvent mouseEvent) {
    }

    public void swordMan(MouseEvent mouseEvent) {
    }

    public void knight(MouseEvent mouseEvent) {
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
    }

    public void defensive(MouseEvent mouseEvent) {
    }

    public void aggressive(MouseEvent mouseEvent) {
    }
}
