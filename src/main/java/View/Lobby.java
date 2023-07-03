package View;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

import Controller.GameRoomDatabase;
import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Lobby extends Application {

    private ArrayList<GameRoom> gameRooms;
    private ScrollPane scoreboardPane;
    private VBox gameRoomInfosVBox, bigVbox;
    private Label label;
    private Button startRoom,back;
    private Stage stage;


    @Override
    public void start(Stage stage){
        this.stage=stage;
        gameRooms=new ArrayList<>();
        this.stage=stage;
        StackPane Pane=null;
        try {
            Pane = FXMLLoader.load(
                    new URL(SignUpMenu.class.getResource("/FXML/Lobby.fxml").toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setFullScreen(true);
        Pane.setAlignment(Pos.CENTER);

        showLobbyProtocol();
        Pane.getChildren().add(bigVbox);






        Scene scene = new Scene(Pane);
        stage.setScene(scene);
        this.stage.show();
    }

    private void displayOngoingRooms(){
        Request request= new Request(NormalRequest.TRANSFER_GAMEROOMS_DATA);
        Client.client.sendRequestToServer(request, true);

        for (GameRoomDatabase database : GameRoomDatabase.getAllRoomDatabases()) {
            if(!database.isPublic()) continue;
            GameRoomInfoHbox roomHbox=new GameRoomInfoHbox(database);
            gameRoomInfosVBox.getChildren().add(roomHbox.getMainHbox());
        }
    }

    public void showLobbyProtocol(){

        bigVbox=new VBox(8);
        bigVbox.setAlignment(Pos.CENTER);
        bigVbox.getChildren().add(new GameRoomSearchBar().getMainVbox());
        label=new Label("LOBBY:");
        gameRoomInfosVBox=new VBox(8);

        scoreboardPane= new ScrollPane(gameRoomInfosVBox);
        scoreboardPane.setFitToWidth(true);
        gameRoomInfosVBox.setAlignment(Pos.CENTER);
        scoreboardPane.setPannable(true);

        scoreboardPane.setMaxHeight(200);
        scoreboardPane.setMinHeight(200);
        scoreboardPane.setMaxWidth(600);
        scoreboardPane.setMinWidth(600);
        
        displayOngoingRooms();

        bigVbox.getChildren().addAll(label,scoreboardPane);
        setButtons();
        setButtonListeners();
    }

    private void setButtons(){
        startRoom=new Button("Start New GameRoom");
        back=new Button("Back");
        HBox hb=new HBox(8,startRoom,back);
        hb.setMaxWidth(300);
        hb.setMinWidth(300);
        hb.setAlignment(Pos.CENTER);
        bigVbox.getChildren().add(hb);
    }

    private void setButtonListeners(){
        startRoom.setOnMouseClicked(event -> showStartRoomDialog());
        back.setOnMouseClicked(event -> {
            try {
                new MainMenu().start(SignUpMenu.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void showStartRoomDialog(){
        
        TextInputDialog dialog = new TextInputDialog();  // create an instance
        dialog.initOwner(stage);
        dialog.setTitle("Start GameRoom:");
        dialog.setHeaderText("Please Fill Content Below:");
        // other formatting etc
        DialogPane dialogPane = dialog.getDialogPane();
        TextField CapacityField=new TextField(),roomId=new TextField();
        CapacityField.setPromptText("Number Of Players");
        roomId.setPromptText("GameRoom Id");
        dialogPane.setContent(new VBox(8,roomId,CapacityField));

        Optional<String> result = dialog.showAndWait();  
            // this shows the dialog, waits until it is closed, and stores the result 


        //if the result is present (i.e. if a string was entered) call doSomething() on it
        result.ifPresent(string -> {
           startGameRoom(CapacityField.getText(), roomId.getText());
        });
    }

    private void startGameRoom(String capacity,String roomId){
        
        Request request=new Request(NormalRequest.CREATE_GAMEROOM);
        request.addToArguments("RoomId", roomId);
        request.addToArguments("Admin", User.getCurrentUser().getUsername());
        request.addToArguments("Capacity", capacity);

        Client.client.sendRequestToServer(request, true);
        GameRoomDatabase associatedDatabase=GameRoomDatabase.getDatabaseByAdmin(User.getCurrentUser());
        GameRoom.setDatabase(associatedDatabase);
        new GameRoom().start(stage);
    }

}
