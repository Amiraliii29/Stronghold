package Controller;

import Model.DataBase;
import Model.Map;
import View.*;
import View.Controller.ChatController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainMenuController {

    public void startGame(MouseEvent mouseEvent) throws Exception {

        AnchorPane startGameMenu = FXMLLoader.load(
                new URL(SignUpMenu.class.getResource("/fxml/StartGameMenu.fxml").toExternalForm()));

        Scene scene = new Scene(startGameMenu);
        SignUpMenu.stage.setScene(scene);
        SignUpMenu.stage.show();
    }

    public void openMapMenu(MouseEvent mouseEvent) throws Exception {
        new CustomizeMapEntry().start(SignUpMenu.stage);
    }

    public void openProfileMenu(MouseEvent mouseEvent) throws Exception {
        // SignUpMenu.stage.setFullScreen(true);
        // new ProfileMenu().start(SignUpMenu.stage);
        SignUpMenu.stage.setFullScreen(true);

        new Lobby().start(SignUpMenu.stage);
        SignUpMenu.stage.setFullScreen(true);
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        new LoginMenu().start(SignUpMenu.stage);
    }

    public void openChatMenu(MouseEvent mouseEvent) throws IOException {
        ChatController.chatMenuPane = FXMLLoader
                .load(new URL(SignUpMenu.class.getResource("/fxml/ChatMenu.fxml").toExternalForm()));
        Stage stage = SignUpMenu.stage;
        Scene scene = new Scene(ChatController.chatMenuPane);
        stage.setScene(scene);
        stage.show();
    }
}
