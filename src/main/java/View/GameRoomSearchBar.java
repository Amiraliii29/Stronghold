package View;

import com.google.gson.Gson;

import Controller.GameRoomDatabase;
import Controller.Orders;
import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Model.User;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameRoomSearchBar {
        private VBox mainVbox;
    private GameRoomInfoHbox targetGameRoomInfoHbox;
    private TextField searchingField;
    private Button searchButton;
    private Text outputText;
    

    public GameRoomSearchBar(){
        this.mainVbox= new VBox(4);
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setMaxWidth(280);
        mainVbox.setMinWidth(280);
        initalizeSearchField();
    }

    public VBox getMainVbox(){
        return mainVbox;
    }

    private void initalizeSearchField(){
        this.searchingField= new TextField();
        searchingField.setPromptText("Friend Username to search");
        initializeSerachButton();

        HBox temp2=new HBox(8, searchingField,searchButton);
        temp2.setMaxWidth(100);temp2.setMaxWidth(100);
        temp2.setAlignment(Pos.CENTER);
        outputText=new Text();
        outputText.setVisible(false);

        mainVbox.getChildren().addAll(temp2,outputText);
    }

    private void initializeSerachButton(){
        searchButton=new Button("Search");
        searchButton.setMinWidth(64);
        searchButton.setMaxWidth(64);
        searchButton.setMinHeight(48);
        searchButton.setMaxHeight(48);
        searchButton.setOnMouseClicked(event -> searchForGameRoom());
    }

    private void searchForGameRoom(){

        if(targetGameRoomInfoHbox!=null)
            mainVbox.getChildren().remove(targetGameRoomInfoHbox.getMainHbox());

        String targetGameRoomId=searchingField.getText();
        Request request=new Request(NormalRequest.TRANSFER_GAMEROOMS_DATA);
        Client.client.sendRequestToServer(request, true);

        GameRoomDatabase targetDatabase=GameRoomDatabase.getDatabasesByRoomId(targetGameRoomId);
        if(targetDatabase==null) 
            Orders.sendTextNotification(outputText, "GameRoom Not Found", Orders.redNotifErrorColor, mainVbox);
    
        else{
            GameRoomInfoHbox targetGameRoomInfoHbox=new GameRoomInfoHbox(targetDatabase);
            targetGameRoomInfoHbox.setBackgroundColor(Orders.yellowNotifErrorColor);
            mainVbox.getChildren().add(targetGameRoomInfoHbox.getMainHbox());
        }
    }

}
