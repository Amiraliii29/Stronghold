package Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import View.SignUpMenu;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main {
    public static void main(String[] args) throws Exception {
        try {
            Client.client = new Client("localhost", 8000);
        } catch (IOException ignored) {
        }
        new SignUpMenu().start(new Stage());
    }
}


//public class Main extends Application {
//    public static void main(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        new SignUpMenu().start(stage);
//    }
//}