package View;

import Controller.Orders;
import Controller.ShopMenuController;
import Model.Buildings.Building;
import Model.DataBase;
import Model.Government;
import Model.Resource;
import Model.Units.Unit;
import View.Enums.Commands.ShopMenuCommands;
import View.Enums.Messages.ShopMenuMessages;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane shopPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/ShopMenu.fxml").toExternalForm()));


        Resource.load();
        Scene scene = new Scene(shopPane);
        // todo uncomment when code finished     amirali
//        ShopMenuController.setItemsAmount();
        stage.setScene(scene);
        stage.show();
    }



    public static void openShopMenu(){

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth();
        double screenLength = primaryScreenBounds.getHeight();

        AnchorPane shopPane = new AnchorPane();
        shopPane.setLayoutX(0);
        shopPane.setLayoutY(screenLength-200);
        shopPane.setPrefSize(screenWidth, 200);

        ImageView background = new ImageView(new Image(ShopMenu.class.getResource("/Images/menu-backgrounds/shopMenu.png").toString(), screenWidth
                ,200, false, false));
        shopPane.getChildren().add(background);

        GameMenu.gamePane.getChildren().add(shopPane);
    }


    private static void showItems(){
        String toPrint = ShopMenuController.showItemsController();
        System.out.print(toPrint);
    }
}