package View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import Controller.GameMenuController;
import Model.Buildings.Building;
import Model.Buildings.Defence;
import Model.Buildings.Generator;
import Model.DataBase;
import Model.Government;
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
        Government government = new Government(10000);
        DataBase.setCurrentGovernment(government);


//        Map.loadMap("for test");
//        Map map = DataBase.getSelectedMap();


        Map map = new Map("for test", 200, 200);
        DataBase.setSelectedMap(map);
        GameMenuController.setMap(map);


        Troop.createTroop(government,"Lord", 2, 2);
        Troop.createTroop(government, "Slave", 0, 0);
        Troop.createTroop(government, "Slave", 0, 199);
        Troop.createTroop(government, "Slave", 199, 0);
        Troop.createTroop(government, "Slave", 199, 199);

        Troop.createTroop(government, "Slave", 1, 1);
        Troop.createTroop(government, "Slave", 1, 1);
        Troop.createTroop(government, "Slave", 1, 1);
        Troop.createTroop(government, "Slave", 1, 1);
        Troop.createTroop(government, "Slave", 1, 1);
        Troop.createTroop(government, "Slave", 1, 1);



        map.getSquareFromMap(180, 7).setBuilding(Defence.createDefence(government, 180, 7, "CircularTower"));
        Generator generator1 = Generator.createGenerator(government, 30, 20, "WoodCutter");
        map.getSquareFromMap(30, 20).setBuilding(generator1);
        map.getSquareFromMap(31, 20).setBuilding(generator1);
        map.getSquareFromMap(30, 21).setBuilding(generator1);
        map.getSquareFromMap(31, 21).setBuilding(generator1);

        map.getSquareFromMap(50, 25).setBuilding(Generator.createGenerator(government, 50, 25, "IronMine"));
        Game game = new Game();
        DataBase.setGame(game);
        game.start(stage);
    }
}