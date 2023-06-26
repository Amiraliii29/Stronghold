package View.Controller;

import Controller.GameMenuController;
import Model.*;
import Model.Buildings.Building;
import Model.Units.Unit;
import View.Game;
import View.MainMenu;
import View.SignUpMenu;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;

public class startGameController {
    public TextField mapName;
    public TextField user2Name;
    public TextField user3Name;
    public TextField user4Name;
    public TextField user5Name;
    public TextField user6Name;
    public TextField user7Name;
    public TextField user8Name;

    public void startGame(MouseEvent mouseEvent) throws Exception {
        String mapNameText = mapName.getText();
        String user2NameText = user2Name.getText();
        String user3NameText = user3Name.getText();
        String user4NameText = user4Name.getText();
        String user5NameText = user5Name.getText();
        String user6NameText = user6Name.getText();
        String user7NameText = user7Name.getText();
        String user8NameText = user8Name.getText();

        if(mapNameText == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Start Game error!");
            alert.setContentText("enter map name");
            alert.showAndWait();
        }
        Map selectedMap = DataBase.getMapByName(mapNameText);
        Game game = new Game();
        if(selectedMap == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Start Game error!");
            alert.setContentText("Invalid Map Name");
            alert.showAndWait();
        }

        game.map = selectedMap;
        DataBase.setSelectedMap(selectedMap);
        game.addToGovernmentsInGame(DataBase.getCurrentGovernment());

        if(user2NameText == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Start Game error!");
            alert.setContentText("No players Selected");
            alert.showAndWait();
        }
        else{
            Government government2 = DataBase.getGovernmentByUserName(user2NameText);
            if(government2 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User2 Name");
                alert.showAndWait();
            }
            game.addToGovernmentsInGame(government2);
        }
        if(user3NameText != null){
            Government government3 = DataBase.getGovernmentByUserName(user3NameText);
            if(government3 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User3 Name");
                alert.showAndWait();
            }
            game.addToGovernmentsInGame(government3);
        }
        if(user4NameText != null){
            Government government4 = DataBase.getGovernmentByUserName(user4NameText);
            if(government4 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User4 Name");
                alert.showAndWait();
            }
            game.addToGovernmentsInGame(government4);
        }
        if(user5NameText != null){
            Government government5 = DataBase.getGovernmentByUserName(user5NameText);
            if(government5 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User5 Name");
                alert.showAndWait();
            }
            game.addToGovernmentsInGame(government5);
        }
        if(user6NameText != null){
            Government government6 = DataBase.getGovernmentByUserName(user6NameText);
            if(government6 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User6 Name");
                alert.showAndWait();
            }
            game.addToGovernmentsInGame(government6);
        }
        if(user7NameText != null){
            Government government7 = DataBase.getGovernmentByUserName(user7NameText);
            if(government7 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User7 Name");
                alert.showAndWait();
            }
            game.addToGovernmentsInGame(government7);
        }
        if(user8NameText != null){
            Government government8 = DataBase.getGovernmentByUserName(user8NameText);
            if(government8 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User8 Name");
                alert.showAndWait();
            }
            game.addToGovernmentsInGame(government8);
        }
        //start that fucking game
        Unit.load();
        Building.load();
        Resource.load();
        Game.loadImages();

        DataBase.setGame(game);
        GameMenuController.setGame(game);
        game.start(SignUpMenu.stage);
    }

    public void backToMainMenu(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(SignUpMenu.stage);
    }
}
