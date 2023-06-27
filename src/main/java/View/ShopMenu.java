package View;

import View.Controller.ShopMenuController;
import Model.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ShopMenu extends Application {
    public static AnchorPane shopPane = new AnchorPane();

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



    public static void openShopMenu(Stage stage) throws IOException {

        shopPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/ShopMenu.fxml").toExternalForm()));
        shopPane.setLayoutX(Game.leftX);
        shopPane.setLayoutY(0);
        shopPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//      ShopMenuController.setItemsAmount();
        Game.mainPane.getChildren().add(shopPane);
    }


}