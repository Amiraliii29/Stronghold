package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.google.gson.Gson;

import Controller.GameRoomDatabase;
import Controller.Orders;
import Controller.UserInfoOperator;
import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Model.User;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class GameRoomInfoHbox {
    private static ArrayList<UserInfoHbox> userInfoHboxes;

    private GameRoomDatabase gameRoomdatabase;
    private Text usersNicknameText;
    private Text GameRoomNameText;
    //TODO
    private Text capacityText;
    private Button joinRoomButton;
    private HBox mainHbox;
    

    
    public GameRoomInfoHbox(GameRoomDatabase gameRoomDatabase){
        mainHbox=new HBox(8);
        this.gameRoomdatabase=gameRoomDatabase;
        InitializeRoomInfo();
    }

    private void InitializeRoomInfo(){

        GameRoomNameText=new Text("Room: "+gameRoomdatabase.getRoomId());
        initializeNicknameText();
        initalizeCapacity();
        initializeJoinButton();

        HBox temp0=new HBox(8, GameRoomNameText);
        temp0.setMinWidth(100);
        HBox temp1=new HBox(8,usersNicknameText),temp2=new HBox(8,capacityText),temp3=new HBox(8,joinRoomButton);
        temp1.setMinWidth(200); temp1.setMaxWidth(200);temp2.setMinWidth(90); temp2.setMaxWidth(90);temp3.setMinWidth(90); temp3.setMaxWidth(90);
        temp0.setAlignment(Pos.CENTER);
        temp1.setAlignment(Pos.CENTER);
        temp2.setAlignment(Pos.CENTER);
        temp3.setAlignment(Pos.CENTER);

        
        mainHbox.getChildren().addAll(temp0,temp1,temp2,temp3);
        mainHbox.setAlignment(Pos.CENTER);
    }

    private void initializeNicknameText(){
        String text="";
        for (User user : gameRoomdatabase.getUsersInRoom()) {
            text=text.concat(user.getNickName()+", ");
        }
        usersNicknameText=new Text(text);
    }

    private void initalizeCapacity(){
        capacityText=new Text(""+gameRoomdatabase.getUsersInRoom().size()+"/"+gameRoomdatabase.getRoomCapacity());
    }

    private void initializeJoinButton(){
        joinRoomButton=new Button("Join Room");

        joinRoomButton.setOnMouseClicked(event -> {
            joinRoom();
        });
    }

    private void joinRoom(){
        Request request=new Request(NormalRequest.JOIN_GAMEROOM);
            request.addToArguments("Username", User.getCurrentUser().getUsername());
            request.addToArguments("RoomId", this.gameRoomdatabase.getRoomId());
            Client.client.sendRequestToServer(request, true);
            if(Client.client.getRecentResponse().equals("full")){
                Orders.createNotificationDialog("Error", "Capacity", "Error while joining: the room is full", Orders.redNotifErrorColor);
                return;
            }

            gameRoomdatabase=GameRoomDatabase.getDatabasesByRoomId(this.gameRoomdatabase.getRoomId());
            GameRoom.setDatabase(gameRoomdatabase);
            new GameRoom().start(SignUpMenu.stage);
    }

    public HBox getMainHbox() {
        return mainHbox;
    }

    public void setMainHbox(HBox mainHbox) {
        this.mainHbox = mainHbox;
    }

    public UserInfoHbox getUserHboxByUser(User targetUser){

        for (UserInfoHbox infoHbox : userInfoHboxes) 
            if(infoHbox.getUser().getUsername().equals(targetUser.getUsername())) return infoHbox;
                
        return null;
    }

    public void setBackgroundColor(String color){
        mainHbox.setStyle(" -fx-background-color: "+color);
    }


}
