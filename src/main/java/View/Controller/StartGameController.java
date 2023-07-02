package View.Controller;

import Controller.GameMenuController;
import Model.*;
import View.Game;
import View.MainMenu;
import View.SignUpMenu;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class StartGameController {
    public TextField mapName;
    public TextField user2Name;
    public TextField user3Name;
    public TextField user4Name;
    public TextField user5Name;
    public TextField user6Name;
    public TextField user7Name;
    public TextField user8Name;
    private static int startGameFlag = 1;

    public void startGame(MouseEvent mouseEvent) throws Exception {

        Resource.load();
        Game.loadImages();

        String mapNameText = mapName.getText();
        String user2NameText = user2Name.getText();
        String user3NameText = user3Name.getText();
        String user4NameText = user4Name.getText();
        String user5NameText = user5Name.getText();
        String user6NameText = user6Name.getText();
        String user7NameText = user7Name.getText();
        String user8NameText = user8Name.getText();

        Government government1 = new Government(1000);
        government1.setOwner(User.getCurrentUser());
        DataBase.setCurrentGovernment(government1);
        DataBase.addGovernment(government1);
        GameMenuController.setCurrentGovernment();

        if(mapNameText == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Start Game error!");
            alert.setContentText("enter map name");
            alert.showAndWait();
        }
        Map.loadMap(mapNameText);
        if(DataBase.getSelectedMap() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Start Game error!");
            alert.setContentText("Invalid Map Name");
            alert.showAndWait();
        }
        GameMenuController.setMap(DataBase.getSelectedMap());
        Game game = new Game();
        DataBase.setGame(game);
        game.addToGovernmentsInGame(government1);
        GameMenuController.setGame(game);

        if(user2NameText != ""){
            User user2 = User.getUserByUserName(user2NameText);
            if(user2 == null){
                startGameFlag = 0;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User2 Name");
                alert.showAndWait();
            }else {
                Government government2 = new Government(1000);
                government2.setOwner(user2);
                DataBase.addGovernment(government2);
                game.addToGovernmentsInGame(government2);
                startGameFlag = 1;
            }
        }
        if(user3NameText != ""){
            User user3 = User.getUserByUserName(user3NameText);
            if(user3 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User3 Name");
                alert.showAndWait();
            }else {
                Government government3 = new Government(1000);
                government3.setOwner(user3);
                DataBase.addGovernment(government3);
                game.addToGovernmentsInGame(government3);
                government3.addToStockpile(Resource.getResourceByName("Stone") , 100);
                government3.addToStockpile(Resource.getResourceByName("Bread") , 25);
                government3.addToStockpile(Resource.getResourceByName("Wood") , 100);
            }
        }
        if(user4NameText != ""){
            User user4 = User.getUserByUserName(user4NameText);
            if(user4 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User4 Name");
                alert.showAndWait();
            }else {
                Government government4 = new Government(1000);
                government4.setOwner(user4);
                DataBase.addGovernment(government4);
                game.addToGovernmentsInGame(government4);
                government4.addToStockpile(Resource.getResourceByName("Stone") , 100);
                government4.addToStockpile(Resource.getResourceByName("Bread") , 25);
                government4.addToStockpile(Resource.getResourceByName("Wood") , 100);
            }
        }
        if(user5NameText != ""){
            User user5 = User.getUserByUserName(user5NameText);
            if(user5 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User5 Name");
                alert.showAndWait();
            }else {
                Government government5 = new Government(1000);
                government5.setOwner(user5);
                DataBase.addGovernment(government5);
                game.addToGovernmentsInGame(government5);
                government5.addToStockpile(Resource.getResourceByName("Stone") , 100);
                government5.addToStockpile(Resource.getResourceByName("Bread") , 25);
                government5.addToStockpile(Resource.getResourceByName("Wood") , 100);
            }
        }
        if(user6NameText != ""){
            User user6 = User.getUserByUserName(user6NameText);
            if(user6 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User6 Name");
                alert.showAndWait();
            }else {
                Government government6 = new Government(1000);
                government6.setOwner(user6);
                DataBase.addGovernment(government6);
                game.addToGovernmentsInGame(government6);
                government6.addToStockpile(Resource.getResourceByName("Stone") , 100);
                government6.addToStockpile(Resource.getResourceByName("Bread") , 25);
                government6.addToStockpile(Resource.getResourceByName("Wood") , 100);
            }
        }
        if(user7NameText != ""){
            User user7 = User.getUserByUserName(user7NameText);
            if(user7 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User7 Name");
                alert.showAndWait();
            }else {
                Government government7 = new Government(1000);
                government7.setOwner(user7);
                DataBase.addGovernment(government7);
                game.addToGovernmentsInGame(government7);
                government7.addToStockpile(Resource.getResourceByName("Stone") , 100);
                government7.addToStockpile(Resource.getResourceByName("Bread") , 25);
                government7.addToStockpile(Resource.getResourceByName("Wood") , 100);
            }
        }
        if(user8NameText != ""){
            User user8 = User.getUserByUserName(user6NameText);
            if(user8 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Game error!");
                alert.setContentText("Invalid User6 Name");
                alert.showAndWait();
            }else {
                Government government8 = new Government(1000);
                government8.setOwner(user8);
                DataBase.addGovernment(government8);
                game.addToGovernmentsInGame(government8);
                government8.addToStockpile(Resource.getResourceByName("Stone") , 100);
                government8.addToStockpile(Resource.getResourceByName("Bread") , 25);
                government8.addToStockpile(Resource.getResourceByName("Wood") , 100);
            }
        }

        //start that fucking game
        if(startGameFlag == 1)
            game.start(SignUpMenu.stage);
    }

    public void backToMainMenu(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(SignUpMenu.stage);
    }
}
