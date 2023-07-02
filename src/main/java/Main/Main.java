package Main;


import java.io.FileNotFoundException;
import java.io.IOException;

import View.Game;
import View.SignUpMenu;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{

    public static void main(String[] args) throws Exception {

        try {
            Client.client = new Client("localhost", 8080);
        } catch (IOException ignored) {
        }
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
       new SignUpMenu().start(stage);
    }


    private static void load() throws FileNotFoundException {
        Game.loadImages();
    }
}
