package View.Controller;

import Model.DataBase;
import View.Game;
import View.Main;
import View.ShopMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
}
