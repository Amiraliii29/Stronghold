package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class MainMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane mainMenuPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/MainMenu.fxml").toExternalForm()));

        Scene scene = new Scene(mainMenuPane);
        stage.setScene(scene);
        stage.show();
    }
}
