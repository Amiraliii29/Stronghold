package View;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import Controller.GameRoomDatabase;
import Controller.Orders;
import Controller.UserInfoOperator;
import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Model.User;
import View.Controller.ChatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameRoom extends Application {

    private static GameRoomDatabase gameRoomDatabase;
    public static GameRoom CurrentRoom;
    private ScrollPane scoreboardPane;
    private VBox scoreBoardVBox, bigVbox;
    private Label label;
    private Button enterChat, startGame, back,Refresh;
    private String gamekey;
    private User admin;
    private HashMap<String,Boolean> usersPlayingStatus; 
    private HashMap<String,HBox> UserHboxes;
    private ArrayList<User> usersInRoom;
    private CheckBox privacy;
    private boolean isRoomPublic;
    private int capacity;
    private Stage stage;
    

    private int errorRecurrence=0;

    public void start(Stage stage){
        this.stage=stage;
        StackPane Pane=null;
        try {
            Pane = FXMLLoader.load(
                    new URL(SignUpMenu.class.getResource("/FXML/GameRoom.fxml").toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(Pane);
        stage.setScene(scene);
        stage.setFullScreen(true);

        CurrentRoom=this;
        this.capacity=gameRoomDatabase.getRoomCapacity();
        this.gamekey=gameRoomDatabase.getRoomId();
        this.admin=gameRoomDatabase.getAdmin();
        usersPlayingStatus=new HashMap<>();
        UserHboxes=new HashMap<>();
        usersInRoom=new ArrayList<>();

        showGameRoomProtocol();
        Pane.getChildren().add(bigVbox);


        this.stage.show();
    }

    public int getCapacity(){
        return capacity;
    }

    public void setCapacity(int val){
        capacity=val;
    }

    public ArrayList<User> getUsersInRoom(){
        return usersInRoom;
    }

    public void setRoomPrivacy(boolean status){
        this.isRoomPublic=status;
    }

    public boolean isRoomPublic(){
        return isRoomPublic;
    }

    public void showGameRoomProtocol(){

        bigVbox=new VBox(8);
        bigVbox.setAlignment(Pos.CENTER);
        bigVbox.getChildren().add(new UserSearchBar().getMainVbox());
        label=new Label("Game Room: "+gamekey);
        setPrivacyCheckBox();
        scoreBoardVBox=new VBox(8);

        scoreboardPane= new ScrollPane(scoreBoardVBox);
        scoreBoardVBox.setAlignment(Pos.CENTER);
        scoreboardPane.setPannable(true);

        setUpperDetailOfVbox();

        scoreboardPane.setMaxHeight(200);
        scoreboardPane.setMaxWidth(740);
        // for (int i = 0; i < Math.min(10,sortedUsers.size()); i++) {
        //     scoreBoardVBox.getChildren().add(new UserInfoHbox(sortedUsers.get(i)).getMainHbox());
        // }
        bigVbox.getChildren().addAll(label,scoreboardPane);
        setButtons();
        setButtonListeners();
        addFormerUsers();
    }

    private void setPrivacyCheckBox(){
        privacy=new CheckBox("Make Room Private");
        privacy.setSelected(!gameRoomDatabase.isPublic());
        privacy.selectedProperty().addListener(event -> changeRoomPrivacy(privacy.isSelected()));
        bigVbox.getChildren().add(privacy);
    }

    private void changeRoomPrivacy(boolean isprivate){
        isRoomPublic=!isprivate;
        String sstate="false";
        if(!isprivate) sstate="true";
        Request request=new Request(NormalRequest.CHANGE_GAMEROOM_PRIVACY);
        request.addToArguments("RoomId", gameRoomDatabase.getRoomId());
        request.addToArguments("State", sstate);
        Client.client.sendRequestToServer(request, false);
    }

    private void setButtons(){
        enterChat=new Button("Enter GameRoom Chat");
        startGame=new Button("Start Game");
        back=new Button("Exit");
        Refresh=new Button("Refresh List");
        HBox hb=new HBox(8, startGame,back);
        hb.setMaxWidth(300);
        hb.setAlignment(Pos.CENTER);
        bigVbox.getChildren().addAll(enterChat,Refresh,hb);
    }

    private void setButtonListeners(){
        Refresh.setOnMouseClicked(event -> refresh());
        back.setOnMouseClicked(event -> back());
        enterChat.setOnMouseClicked(event -> {
            Request request = new Request(NormalRequest.CREATE_ROOM);
            for (int i = 0; i < usersInRoom.size(); i++) {
                request.argument.put("user" + i , usersInRoom.get(i).getUsername());
            }
            Client.client.sendRequestToServer(request , false);

            try {
                 ChatController.chatRoomMenuPane = FXMLLoader.load(new URL(SignUpMenu.class.
                        getResource("/fxml/CreateRoomMenu.fxml").toExternalForm()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage stage = SignUpMenu.stage;
            Scene scene = new Scene(ChatController. chatRoomPane);
            stage.setScene(scene);
            stage.show();
        });
    }

    private void refresh(){
        Request request=new Request(NormalRequest.TRANSFER_GAMEROOMS_DATA);
        Client.client.sendRequestToServer(request, true);
        this.setDatabase(GameRoomDatabase.getDatabasesByRoomId(gameRoomDatabase.getRoomId()));
        new GameRoom().start(stage);
    }

    public void back(){
        Request request=new Request(NormalRequest.LEAVE_GAMEROOM);
        request.addToArguments("Username", User.getCurrentUser().getUsername());
        request.addToArguments("RoomId", gameRoomDatabase.getRoomId());
        Client.client.sendRequestToServer(request, false);
        exitRoom();
 
    }

    public void exitRoom(){
        try {
            gameRoomDatabase=null;
            CurrentRoom=null;
            new Lobby().start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFormerUsers(){
        for (User user : gameRoomDatabase.getUsersInRoom()) {
            usersInRoom.add(user);
            displayPersonInRoom(user.getUsername());
        }
    }

    public void displayPersonInRoom(String username){
        User targetUser=User.getUserByUserName(username);

        CheckBox isUserSpectating=new CheckBox();
        HBox hb1=new HBox(8, isUserSpectating);
        hb1.setMaxWidth(90);
        hb1.setMinWidth(90);
        hb1.setAlignment(Pos.CENTER);
        UserInfoHbox infoHbox=new UserInfoHbox(targetUser);

        HBox targetUserHbox=new HBox(0,infoHbox.getMainHbox(),hb1);
        addStylesToHboxIfNeeded(targetUserHbox, targetUser);
        targetUserHbox.setAlignment(Pos.CENTER);
        UserHboxes.put(username, targetUserHbox);
        scoreBoardVBox.getChildren().add(targetUserHbox);

        isUserSpectating.setSelected(!gameRoomDatabase.isUserPlaying(targetUser));
        isUserSpectating.selectedProperty().addListener(event -> makePlayerSpectator(targetUser,isUserSpectating.isSelected(),isUserSpectating));
    }

    public void AddUserToRoom(User user){
        usersInRoom.add(user);
        if(!gameRoomDatabase.getUsersInRoom().contains(user))
            gameRoomDatabase.AddtoUsers(user);

        displayPersonInRoom(user.getUsername());
    }

    private void makePlayerSpectator(User user,boolean isSpectating,CheckBox originBox){
        if(errorRecurrence>0){
            errorRecurrence=0;
            return;
        }
        if(!user.getUsername().equals(User.getCurrentUser().getUsername())){
            Orders.createNotificationDialog("Error", "Accessibility", "You can only change your own spectating status", Orders.yellowNotifErrorColor);
            errorRecurrence++;
            originBox.setSelected(!isSpectating);
        }
        Request request=new Request(NormalRequest.CHANGE_PLAYER_SPECTATING_STATUS);
        request.addToArguments("Username", User.getCurrentUser().getUsername());
        request.addToArguments("RoomId", gameRoomDatabase.getRoomId());
        String status="false";
        if(!isSpectating) status="true";
        request.addToArguments("Status", status);

        Client.client.sendRequestToServer(request, false);
    }

    public void removePersonFromRoom(String username){
        scoreBoardVBox.getChildren().remove(UserHboxes.get(username));
        UserHboxes.remove(username);
        usersInRoom.remove(User.getUserByUserName(username));
    }

    private void setUpperDetailOfVbox(){
        Text avatartText=new Text("Avatar");
        avatartText.setOpacity(0.75);

        Text usernameText=new Text("Username");
        usernameText.setOpacity(0.75);

        Text highscoreText=new Text("Highscore");
        highscoreText.setOpacity(0.75);

        Text rankText=new Text("Rank");
        rankText.setOpacity(0.75);

        Text FriendshipText=new Text("Friendship");
        FriendshipText.setOpacity(0.75);

        Text OnlineStatusText=new Text("Status");
        OnlineStatusText.setOpacity(0.75);

        Text Spectate=new Text("Spectate");
        Spectate.setOpacity(0.75);

        HBox hb0=new HBox(avatartText),hb1=new HBox(usernameText),hb2=new HBox(highscoreText),hb3=new HBox(rankText),hb4=new HBox(FriendshipText),hb5=new HBox(OnlineStatusText),hb6=new HBox(Spectate);
        hb0.setMaxWidth(90);hb0.setMinWidth(90);hb1.setMaxWidth(90);hb1.setMinWidth(90);hb2.setMaxWidth(90);hb2.setMinWidth(90);hb3.setMaxWidth(90);hb3.setMinWidth(90);
        hb4.setMaxWidth(90);hb4.setMinWidth(90);hb5.setMaxWidth(90);hb6.setMinWidth(90);hb5.setMaxWidth(90);hb6.setMinWidth(90);hb6.setMaxWidth(90);
        hb2.setAlignment(Pos.CENTER);
        hb3.setAlignment(Pos.CENTER);
        hb0.setAlignment(Pos.CENTER);
        hb1.setAlignment(Pos.CENTER);
        hb4.setAlignment(Pos.CENTER);
        hb5.setAlignment(Pos.CENTER);
        hb6.setAlignment(Pos.CENTER);
        HBox parentHBox=new HBox(8, hb0,hb1,hb2,hb3,hb4,hb5,hb6);
        parentHBox.setAlignment(Pos.CENTER);
        scoreBoardVBox.getChildren().addAll(parentHBox);
    }

    public void setScoreboardPane(ScrollPane scoreboardPane) {
        this.scoreboardPane = scoreboardPane;
    }

    public void setScoreBoardVBox(VBox scoreBoardVBox) {
        this.scoreBoardVBox = scoreBoardVBox;
    }

    public void setBigVbox(VBox bigVbox) {
        this.bigVbox = bigVbox;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setEnterChat(Button enterChat) {
        this.enterChat = enterChat;
    }

    public void setStartGame(Button startGame) {
        this.startGame = startGame;
    }

    public void setBack(Button back) {
        this.back = back;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Label getLabel() {
        return label;
    }

    public String getGamekey() {
        return gamekey;
    }

    public User getAdmin() {
        return admin;
    }

    private void addStylesToHboxIfNeeded(HBox MainUserHbox,User targetUser){
        if(targetUser.getUsername().equals(admin.getUsername()) )
            MainUserHbox.getStyleClass().add("gold-color");
    }

    public static void setDatabase(GameRoomDatabase database){
        GameRoom.gameRoomDatabase=database;
    }
}
