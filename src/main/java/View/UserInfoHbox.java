package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import Controller.UserInfoOperator;
import Model.User;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class UserInfoHbox {


    private static ArrayList<UserInfoHbox> userInfoHboxes;

    private User user;
    private ImageView userImageView;
    private Circle onlineStatus;
    private Text userNameText;
    private ImageView friendRequestButton;

    private Text userRankText;

    private  Text userScoreText;

    private HBox mainHbox;
    
    private void displayUserInfo(User targetUser){

        ImageView userAvatarImgView=getUserImageView(targetUser);
        userNameText=new Text(targetUser.getUsername());userRankText=new Text(""+targetUser.getRank()); userScoreText=new Text(""+targetUser.getHighScore());onlineStatus=new Circle(8);
        initializeFriendRequestButton();

        HBox temp0=new HBox(8, userAvatarImgView);
        temp0.setMinWidth(80);
        HBox temp1=new HBox(8,userNameText),temp2=new HBox(8,userScoreText),temp3=new HBox(8,userRankText),temp4=new HBox(8, friendRequestButton),temp5=new HBox(8,onlineStatus);
        temp1.setMinWidth(90); temp1.setMaxWidth(90);temp2.setMinWidth(90); temp2.setMaxWidth(90);temp3.setMinWidth(90); temp3.setMaxWidth(90);temp4.setMinWidth(90); temp4.setMaxWidth(90);temp5.setMinWidth(90); temp5.setMaxWidth(90);
        temp0.setAlignment(Pos.CENTER);
        temp1.setAlignment(Pos.CENTER);
        temp2.setAlignment(Pos.CENTER);
        temp3.setAlignment(Pos.CENTER);
        temp4.setAlignment(Pos.CENTER);
        temp5.setAlignment(Pos.CENTER);

        
        mainHbox.getChildren().addAll(temp0,temp1,temp2,temp3,temp4,temp5);
        mainHbox.setAlignment(Pos.CENTER);
        addStylesToHboxIfNeeded(mainHbox, targetUser);
    }

    public UserInfoHbox(User user){
        mainHbox=new HBox(8);
        this.user=user;
        displayUserInfo(user);
        updateUserStatus();
    }

    private ImageView getUserImageView(User user){
        Image userAvatarimg=null;
        try {
            userAvatarimg=new Image(new FileInputStream("src/main/resources/Images/avatars/"+user.getAvatarFileName()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView userAvatarImgView=new ImageView(userAvatarimg);
        userAvatarImgView.setFitWidth(40);
        userAvatarImgView.setFitHeight(40);
        userAvatarImgView.getStyleClass().add("hover-effect");
        userAvatarImgView.setOnMouseClicked(event -> {
            copyTargetAvatarToUserAvatar(user);
            try {
                UserInfoOperator.storeUserDataInJson(user, "src/main/resources/jsonData/Users.json");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
                });

        return userAvatarImgView;
    }

    private void initializeFriendRequestButton(){
        Image friendRequestImage=null;
        try {
            friendRequestImage=new Image(new FileInputStream("src/main/resources/Images/Icon/Friends.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        friendRequestButton= new ImageView(friendRequestImage);
        friendRequestButton.setFitWidth(40);
        friendRequestButton.setFitHeight(40);
        friendRequestButton.getStyleClass().add("hover-effect");

        friendRequestButton.setOnMouseClicked(event -> {
            User.getCurrentUser().sendFriendRequest(this.user);
        });
    }
   
    private void addStylesToHboxIfNeeded(HBox userInfoHbox,User targetUser){
        switch (targetUser.getRank()) {
            case 1 -> userInfoHbox.getStyleClass().add("gold-color");
            case 2 -> userInfoHbox.getStyleClass().add("silver-color");
            case 3 -> userInfoHbox.getStyleClass().add("bronze-color");
            default -> {
            }
        }

        if(targetUser.getUsername().equals(User.getCurrentUser().getUsername()) ||
           targetUser.isFriendsWithCurrentUser() ){
            userInfoHbox.getStyleClass().add("picked-field-styles");
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HBox getMainHbox() {
        return mainHbox;
    }

    public void setMainHbox(HBox mainHbox) {
        this.mainHbox = mainHbox;
    }

    public void setUserImageView(ImageView userImageView) {
        this.userImageView = userImageView;
    }

    public void setOnlineStatus(Circle onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public void setUserNameText(Text userNameText) {
        this.userNameText = userNameText;
    }

    public void setUserRankText(Text userRankText) {
        this.userRankText = userRankText;
    }

    public void setUserScoreText(Text userScoreText) {
        this.userScoreText = userScoreText;
    }

    private void copyTargetAvatarToUserAvatar(User targetUser){
        User.getCurrentUser().setAvatarFileName(targetUser.getAvatarFileName());
    }

    public User getUser() {
        return user;
    }

    public ImageView getUserImageView() {
        return userImageView;
    }

    public Circle getOnlineStatus() {
        return onlineStatus;
    }

    public Text getUserNameText() {
        return userNameText;
    }

    public Text getUserRankText() {
        return userRankText;
    }

    public Text getUserScoreText() {
        return userScoreText;
    }

    public UserInfoHbox getUserHboxByUser(User targetUser){

        for (UserInfoHbox infoHbox : userInfoHboxes) 
            if(infoHbox.getUser().getUsername().equals(targetUser.getUsername())) return infoHbox;
                
        return null;
    }

    public void updateUserStatus(){
        if(this.user.isOnline())
            this.onlineStatus.setFill(Color.GREEN);
        else this.onlineStatus.setFill(Color.GRAY);
    }

    public void setBackgroundColor(String color){
        mainHbox.setStyle(" -fx-background-color: "+color);
    }



}
