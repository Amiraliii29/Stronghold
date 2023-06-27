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

//        kiarashStart(stage);
       new SignUpMenu().start(stage);
    }

    private void kiarashStart(Stage stage) throws Exception {
       Unit.load();
       Building.load();
       Resource.load();
       Game.loadImages();
       Government government = new Government(10000);
       government.setOwner(User.getUsers().get(0));
       Government government2 = new Government(10000);
       government.setOwner(User.getUsers().get(1));
       DataBase.setCurrentGovernment(government);
       GameMenuController.setCurrentGovernment();

       Map.loadMap("for test");


//       Map map = new Map("for test", 200, 200);
//       DataBase.setSelectedMap(map);
//       GameMenuController.setMap(map);

       Game game = new Game();
       DataBase.setGame(game);
       GameMenuController.setGame(game);
       game.start(stage);


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


        Troop.createTroop(government, "Assassin", 5, 5);
        Troop.createTroop(government, "Knight", 8, 8);
        Troop.createTroop(government, "HorseArcher", 12, 12);
        Siege.createSiege(government,"BatteringRam",16,16);


        // map.getSquareFromMap(4, 4).addUnit(Troop.createTroop(government2,"Knight", 4, 4));

    //   map.getSquareFromMap(180, 7).setBuilding(Defence.createDefence(government, 180, 7, "CircularTower"));
    //   Generator generator1 = Generator.createGenerator(government, 30, 20, "WoodCutter");
//       map.getSquareFromMap(30, 20).setBuilding(generator1);
//       map.getSquareFromMap(31, 20).setBuilding(generator1);
//       map.getSquareFromMap(30, 21).setBuilding(generator1);
//       map.getSquareFromMap(31, 21).setBuilding(generator1);
//
//       Generator generator2 = Generator.createGenerator(government, 10, 10, "DairyFarm");
//       map.getSquareFromMap(10, 10).setBuilding(generator2);
//
//       Generator generator3 = Generator.createGenerator(government, 20, 10, "HopsFarm");
//       map.getSquareFromMap(20, 10).setBuilding(generator3);
//
//       Stockpile stockpile1 = Stockpile.createStockpile(government, 5, 5, "Stockpile");
//       map.getSquareFromMap(5, 5).setBuilding(stockpile1);
//
//       Barrack barrack = Barrack.createBarrack(government, 20, 20, "MercenaryPost");
//       map.getSquareFromMap(20,20).setBuilding(barrack);
//
//       map.getSquareFromMap(50, 25).setBuilding(Generator.createGenerator(government, 50, 25, "IronMine"));


       
    }
}