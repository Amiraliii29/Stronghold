package View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import Model.Buildings.Building;
import Model.Map;
import Model.Resource;
import Model.Units.Unit;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Building.readBuildingsFromFile();
//        Unit.readUnitsFromFile();
        Resource.readResourcesFromFile();
       new ProfileMenu().start(stage);
    }
}