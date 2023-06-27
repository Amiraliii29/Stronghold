package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Alert {
    public static AnchorPane alertPane;
    public  static Label alertContent;
    public static void showAlert(String content) throws IOException {
        alertPane = FXMLLoader.load(
                new URL(SignUpMenu.class.getResource("/fxml/Alert.fxml").toExternalForm()));

        alertPane.setLayoutX(Game.leftX + 550);
        alertPane.setLayoutY(Game.screenHeight / 2);
        Game.mainPane.getChildren().add(alertPane);
    }

    public void closeAlert(MouseEvent mouseEvent) {
        Game.mainPane.getChildren().remove(alertPane);
    }
}
