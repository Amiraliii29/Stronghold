package View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import Model.Buildings.Building;
import Model.Buildings.Defence;
import Model.DataBase;
import Model.Map;
import Model.Resource;
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
        Unit.load();
        Building.load();
        Resource.load();
        Game.loadImages();
        Map map = new Map("for show test", 200, 200);
        DataBase.setSelectedMap(map);
        Troop.createTroop(null,"Lord", 3, 4);
        Troop.createTroop(null, "Slave", 0, 0);
        Troop.createTroop(null, "Slave", 0, 199);
        Troop.createTroop(null, "Slave", 199, 0);
        Troop.createTroop(null, "Slave", 199, 199);

        map.getSquareFromMap(180, 7).setBuilding(Defence.createDefence(null, 180, 7, "CircularTower"));
        new Game().start(stage);
    }
}