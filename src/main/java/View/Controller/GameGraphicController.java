package View.Controller;

import Model.Buildings.Building;
import Model.Buildings.Defence;
import Model.DataBase;
import View.Game;
import View.Main;
import View.ShopMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


// this class has been created by amirali for bottom menu controller
public class GameGraphicController {
    public static Label popularity;
    public static Label golds;
    public static Label population;
    private static Stage popularityFactorsStage = new Stage();


    public static void setPopularityGoldPopulation(){
        int popularityInt = DataBase.getCurrentGovernment().getPopularity();
        popularity.setText(Integer.toString(popularityInt));

        if(popularityInt > 75)
            popularity.setTextFill(Color.GREEN);
        else if(popularityInt < 25)
            popularity.setTextFill(Color.RED);

        golds.setText(Integer.toString((int) DataBase.getCurrentGovernment().getMoney()));

        population.setText(DataBase.getCurrentGovernment().getPopulation() + "/128");

    }
    public void openPopularityReport(MouseEvent mouseEvent) {
        try {

            AnchorPane popularityFactorsPane = FXMLLoader.load(
                    new URL(Main.class.getResource("/fxml/PopularityFactorsMenu.fxml").toExternalForm()));
            Scene scene = new Scene(popularityFactorsPane);
            popularityFactorsStage.setScene(scene);
            popularityFactorsStage.show();
        }
        catch (Exception e){}
        throw new RuntimeException("heyo");
    }

    public void openConstructorBuildingsMenu(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/ConstructorBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);
    }

    public void openFoodBuildingsMenu(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/FoodBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);
    }

    public void openVillageBuildingsMenu(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/VillageBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);
    }

    public void openKitchenBuildingsMenu(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/KitchenBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);
    }

    public void openDefenceBuildingsMenu(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/DefenceBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);
    }

    public void openWallBuildingsMenu(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/BottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);
    }

    public void openTowersBottomMenu(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/towersBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);
    }
    

    public void stairDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "Stair";
    }

    public void smallWallDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "SmallWall";
    }

    public void wallDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "Wall";
    }

    public void strongWallDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "StrongWall";
    }

    public void barackDragDetected(MouseEvent mouseEvent) {
        Game.barrackBuildingToCreateName = "Barrack";
    }

    public void mercenaryPostDragDetected(MouseEvent mouseEvent) {
        Game.barrackBuildingToCreateName = "MercenaryPost";
    }

    public void stockPileDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "Stockpile";
    }

    public void woodCutterDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "WoodCutter";
    }

    public void stoneMineDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "Quarry";
    }

    public void cowDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "StoneCow";
    }

    public void ironMineDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "IronMine";
    }

    public void pitchRigDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "PitchRig";
    }

    public void shopDragDetected(MouseEvent mouseEvent) {
        Game.townBuildingToCreateName = "Shop";
    }

    public void armouryDragDetected(MouseEvent mouseEvent) {
        Game.barrackBuildingToCreateName = "Armoury";
    }

    public void fletcherDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "Fletcher";
    }

    public void armourerDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "Armourer";
    }

    public void poleTurnerDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "PoleTurner";
    }

    public void blackSmithDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "BlackSmith";
    }

    public void dairyFarmDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "DairyFarm";
    }

    public void millDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "Mill";
    }

    public void bakeryDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "Bakery";
    }

    public void breweryDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "Brewery";
    }

    public void granaryDragDetected(MouseEvent mouseEvent) {
        Game.stockPileBuildingToCreateName = "Granary";
    }

    public void innDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "Inn";
    }

    public void cheeseDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "DairyFarm";
    }

    public void appleFarmDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "Orchard";
    }

    public void wheatFarmDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "WheatFarm";
    }

    public void hopsFarmDragDetected(MouseEvent mouseEvent) {
        Game.generatorBuildingToCreateName = "HopsFarm";
    }

    public void homeDragDetected(MouseEvent mouseEvent) {
        Game.townBuildingToCreateName = "Home";
    }

    public void churchDragDetected(MouseEvent mouseEvent) {
        Game.townBuildingToCreateName = "Church";
    }

    public void cathedralDragDetected(MouseEvent mouseEvent) {
        Game.barrackBuildingToCreateName = "Cathedral";
    }

    public void wellDragDetected(MouseEvent mouseEvent) {
    }

    public void lookOutTowerDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "LookOutTower";
    }

    public void perimeterTowerDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "PerimeterTower";
    }

    public void defenciveTowerDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "DefenciveTower";
    }

    public void squareTowerDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "SquareTower";
    }

    public void circularTowerDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "CircularTower";
    }

    public void smallStoneGateDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "SmallStoneGate";
    }

    public void bigStoneGateDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "BigStoneGate";
    }

    public void drawBridgeDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "DrawBridge";
    }

    public void trapDragDetected(MouseEvent mouseEvent) {
        Game.defenceBuildingToCreateName = "Trap";
    }

    public void ditchDragDetected(MouseEvent mouseEvent) {
        //todo
    }

    public void fillDitchDragDetected(MouseEvent mouseEvent) {
        //todo
    }
}
