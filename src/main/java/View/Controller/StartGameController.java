package View.Controller;

import Controller.GameMenuController;
import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Main.ResultEnums;
import Model.*;
import View.Game;
import View.MainMenu;
import View.SignUpMenu;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.DataInputStream;

public class StartGameController {
    public TextField mapName;
    public TextField user2Name;

    private static int startGameFlag = 1;

    public void startGame(MouseEvent mouseEvent) throws Exception {
        Resource.load();
        Game.loadImages();

        String mapNameText = mapName.getText();
        String user2NameText = user2Name.getText();


        Government government1 = new Government(1000);
        government1.setOwner(User.getCurrentUser());
        DataBase.setMyGovernment(government1);
        GameMenuController.setCurrentGovernment();

        if(mapNameText == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Start Game error!");
            alert.setContentText("enter map name");
            alert.showAndWait();
        }

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

                Request request = new Request(NormalRequest.START_GAME);
                request.argument.put("user", user2NameText);

                Client.client.sendRequestToServer(request, true);

                Request response = Request.fromJson(Client.client.getRecentResponse());

                if (response.resultEnums.equals(ResultEnums.SUCCESS)) {

                    Request request1 = new Request(NormalRequest.GET_MAP);
                    request1.addToArguments("name", mapNameText);

                    Client.client.serverResponseListener.changeSpecific();
                    Client.client.sendRequestToServer(request1, false);

                    DataInputStream dataInputStream = Client.client.dataInputStream;


                    int jsonLength = dataInputStream.readInt();
                    if (jsonLength == 0) return;

                    byte[] jsonBytes = new byte[jsonLength];

                    dataInputStream.readFully(jsonBytes);


                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.readValue(jsonBytes, String.class);
                    Gson gson = new Gson();
                    Map map = gson.fromJson(json, Map.class);
                    Client.client.serverResponseListener.changeSpecific();

                    DataBase.setSelectedMap(map);

                    Square[][] squares = map.getSquares();
                    for (int i = 0; i < map.getWidth() + 1; i++) {
                        for (int j = 0; j < map.getLength() + 1; j++) {
                            squares[i][j].newUnits();
                        }
                    }

                    startGameFlag = 1;
                } else startGameFlag = 0;
            }
        }


        if(startGameFlag == 1) {
            GameMenuController.setMap(DataBase.getSelectedMap());
            Game game = new Game();
            DataBase.setGame(game);
            GameMenuController.setGame(game);
            game.start(SignUpMenu.stage);
        }
    }

    public void backToMainMenu(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(SignUpMenu.stage);
    }
}
