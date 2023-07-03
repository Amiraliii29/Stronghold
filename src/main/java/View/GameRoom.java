package View;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import Controller.UserInfoOperator;
import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Model.User;
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
    private ScrollPane scoreboardPane;
    private VBox scoreBoardVBox, bigVbox;
    private Label label;
    private Button enterChat, startGame, back,Refresh;
    private final String gamekey;
    private User admin;
    private HashMap<String,Boolean> usersPlayingStatus; 
    private HashMap<String,HBox> UserHboxes;
    private ArrayList<User> usersInRoom;
    private CheckBox privacy;
    private boolean isRoomPublic;
    private int capacity;
    private Stage stage;


    public void start(Stage stage){
        this.stage=stage;
        StackPane Pane=null;
        try {
            Pane = FXMLLoader.load(
                    new URL(GameRoom.class.getResource("/FXML/GameRoom.fxml").toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(Pane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        this.stage.show();
    }

    public GameRoom(User admin,String roomId,int capacity){
        this.capacity=capacity;
        this.gamekey=roomId;
        usersPlayingStatus=new HashMap<>();
        UserHboxes=new HashMap<>();
        usersInRoom=new ArrayList<>();

        showGameRoomProtocol();
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
        scoreboardPane.setFitToWidth(true);
        scoreBoardVBox.setAlignment(Pos.CENTER);
        scoreboardPane.setPannable(true);

        setUpperDetailOfVbox();

        scoreboardPane.setMaxHeight(200);
        scoreboardPane.setMaxWidth(680);
        // for (int i = 0; i < Math.min(10,sortedUsers.size()); i++) {
        //     scoreBoardVBox.getChildren().add(new UserInfoHbox(sortedUsers.get(i)).getMainHbox());
        // }
        bigVbox.getChildren().addAll(label,scoreboardPane);
        setButtons();
        setButtonListeners();
        addPersonToRoom(admin.getUsername());
    }

    private void setPrivacyCheckBox(){
        privacy=new CheckBox("Make Room Private");
        privacy.selectedProperty().addListener(event -> {
            isRoomPublic=privacy.isSelected();
        });
        bigVbox.getChildren().add(privacy);
    }

    private String generateGameKey(){
        return UserInfoOperator.addRandomizationToString(admin.getNickName());
    }

    private void setButtons(){
        enterChat=new Button("Enter GameRoom Chat");
        startGame=new Button("Start Game");
        back=new Button("Exit");
        Refresh=new Button("Refresh List");
        HBox hb=new HBox(8, startGame,back);
        bigVbox.getChildren().addAll(enterChat,Refresh,hb);
    }

    private void setButtonListeners(){
        //TODO
    }

    public void back(){
        //TODO:SEND REQUEST TO REMOVE USER FOR EVERYONE
        if(User.getCurrentUser().getUsername().equals(admin.getUsername()));
            //TODO: MAKE S.ONE ELSE ADMIN, IF EMPTY, CLOSE ROOM;
    }

    public void closeRoom(){
        //TODO
    }

    public void addPersonToRoom(String username){
        User targetUser=User.getUserByUserName(username);

        CheckBox isUserPlaying=new CheckBox();
        UserInfoHbox infoHbox=new UserInfoHbox(targetUser);

        HBox targetUserHbox=new HBox(8,infoHbox.getMainHbox(),isUserPlaying);
        UserHboxes.put(username, targetUserHbox);
        usersInRoom.add(targetUser);
        scoreBoardVBox.getChildren().add(targetUserHbox);

        isUserPlaying.setSelected(true);
        isUserPlaying.selectedProperty().addListener(event -> makePlayerSpectator(targetUser,isUserPlaying.isSelected()));
    }

    private void makePlayerSpectator(User user,boolean isPlaying){
        //TODO
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
        hb4.setMaxWidth(90);hb4.setMinWidth(90);hb5.setMaxWidth(90);hb6.setMinWidth(90);hb5.setMaxWidth(90);hb6.setMinWidth(90);
        hb2.setAlignment(Pos.CENTER);
        hb3.setAlignment(Pos.CENTER);
        hb0.setAlignment(Pos.CENTER);
        hb1.setAlignment(Pos.CENTER);
        hb4.setAlignment(Pos.CENTER);
        hb5.setAlignment(Pos.CENTER);
        HBox parentHBox=new HBox(8, hb0,hb1,hb2,hb3,hb4,hb5,hb6);
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
}
