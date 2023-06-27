package View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import Controller.GameMenuController;
import Model.Buildings.*;
import Model.DataBase;
import Model.Government;
import Model.Map;
import Model.Resource;
import Model.User;
import Model.Units.Siege;
import Model.Units.Troop;
import Model.Units.Unit;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new SignUpMenu().start(stage);
    }
}