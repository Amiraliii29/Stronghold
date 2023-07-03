package View;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;

import com.google.gson.Gson;

import Controller.Orders;
import Controller.ProfileMenuController;
import Controller.UserComparator;
import Controller.UserInfoOperator;
import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Model.User;
import View.Enums.Commands.ProfileMenuCommands;
import View.Enums.Messages.ProfileMenuMessages;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class ProfileMenu extends Application {

    public static boolean isProfileShown=true,isMenuDisplayed=true;
    TextField usernameField, emailField, nicknameField, sloganField;
    Text usernameText, emailText, nicknameText,selectAvatarText,avatarDisplayText;
    VBox usernameVbox, emailVbox, nicknameVbox;
    Button submitChanges, removeSlogan, changePassword,displayScoreBoard, back;
    Image image1,image2,image3,image4,userimg,selectCustomImg;
    ImageView imgview1,imgview2,imgview3,imgview4,userimgView=new ImageView(),selectCustomView;
    VBox mainVbox,avatarVbox,scoreBoardVBox, bigVbox;
    ScrollPane scoreboardPane;
    Label label;
    ArrayList<User> sortedUsers;
    FileChooser fileChooser;
    StackPane mainPane;
    Stage stage;
    public static ProfileMenu profileMenu;

    // {
    //     User.setCurrentUser(User.getUsers().get(0));
    //     ProfileMenuController.setCurrentUser(User.getUsers().get(0));
    // }

    @Override
    public void start(Stage stage) throws Exception {


        StackPane Pane = FXMLLoader.load(
                new URL(SignUpMenu.class.getResource("/FXML/ProfileMenu.fxml").toExternalForm()));
        Scene scene = new Scene(Pane);

        this.mainPane = Pane;
        this.stage = stage;
        ProfileMenu.profileMenu=this;
        isMenuDisplayed=true;
        stage.setFullScreen(true);
        stage.setScene(scene);

        if(isProfileShown)
            showProfileProtocol();
        else
            showScoreBoardProtocol();
        
        stage.show();
        showFriendRequests();
    }

    public void restart(){
        try {
            stage.setFullScreen(true);
            new ProfileMenu().start(stage);
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpperDetailOfScoreboardVbox(){
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

        HBox hb0=new HBox(avatartText),hb1=new HBox(usernameText),hb2=new HBox(highscoreText),hb3=new HBox(rankText),hb4=new HBox(FriendshipText),hb5=new HBox(OnlineStatusText);
        hb0.setMaxWidth(90);hb0.setMinWidth(90);hb1.setMaxWidth(90);hb1.setMinWidth(90);hb2.setMaxWidth(90);hb2.setMinWidth(90);hb3.setMaxWidth(90);hb3.setMinWidth(90);
        hb4.setMaxWidth(90);hb4.setMinWidth(90);hb5.setMaxWidth(90);hb5.setMinWidth(90);
        hb2.setAlignment(Pos.CENTER);
        hb3.setAlignment(Pos.CENTER);
        hb0.setAlignment(Pos.CENTER);
        hb1.setAlignment(Pos.CENTER);
        hb4.setAlignment(Pos.CENTER);
        hb5.setAlignment(Pos.CENTER);
        HBox parentHBox=new HBox(8, hb0,hb1,hb2,hb3,hb4,hb5);
        scoreBoardVBox.getChildren().addAll(parentHBox);
    }

    private void sortUsers(){
        sortedUsers=new ArrayList<>(User.getUsers());
        Collections.sort(sortedUsers, new UserComparator());
        UserComparator.updateUsersRank(sortedUsers);
    }

    public void showScoreBoardProtocol(){
        mainPane.setAlignment(Pos.CENTER);
        mainPane.getChildren().removeAll(mainVbox,avatarVbox);
        sortUsers();
        bigVbox=new VBox(8);
        bigVbox.setAlignment(Pos.CENTER);
        bigVbox.getChildren().add(new UserSearchBar().getMainVbox());
        label=new Label("ScoreBoard");
        scoreBoardVBox=new VBox(8);

        scoreboardPane= new ScrollPane(scoreBoardVBox);
        scoreboardPane.setFitToWidth(true);
        scoreBoardVBox.setAlignment(Pos.CENTER);
        scoreboardPane.setPannable(true);
        setUpperDetailOfScoreboardVbox();

        scoreboardPane.setMaxHeight(200);
        scoreboardPane.setMaxWidth(600);
        for (int i = 0; i < Math.min(10,sortedUsers.size()); i++) {
            scoreBoardVBox.getChildren().add(new UserInfoHbox(sortedUsers.get(i)).getMainHbox());
        }
        bigVbox.getChildren().addAll(label,scoreboardPane);
        setScoreboardButtons();
        setScoreBoardButtonListeners();
        mainPane.getChildren().add(bigVbox);
    }

    private void setScoreboardButtons(){
        displayScoreBoard=new Button("Show Profile");
        back = new Button("Back to main Menu");
        back.getStyleClass().add("yellow-warning-color");
        HBox hbox=new HBox(16, displayScoreBoard,back);
        hbox.setMinWidth(400); hbox.setMaxWidth(400);
        hbox.setAlignment(Pos.CENTER);
        bigVbox.getChildren().addAll(hbox);
    }

    private void setScoreBoardButtonListeners(){
        displayScoreBoard.setOnMouseClicked(event -> {
            if(isProfileShown)
                showScoreBoardProtocol();
            else
                showProfileProtocol();
                isProfileShown=!isProfileShown;
                });
        back.setOnMouseClicked(event -> {
            try {
                isMenuDisplayed=false;
                new MainMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void showProfileProtocol(){
        if(mainPane.getChildren().size()>0)
            mainPane.getChildren().remove(bigVbox);
        initializeMainVbox();
        initalizeLabel();
        initializeAvatarSection();
        setImageviewListeners();
        initializeFields();
        setAlignments();
        initializeButtons();
        setFieldListeners();
        setButtonListeners();
        setStartingTexts();
    }

    private void setFileChooser(){
        fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("save file");
        fileChooser.getExtensionFilters().addAll( new ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                InputStream inputStream = new BufferedInputStream(
                            Files.newInputStream(selectedFile.toPath()));

                uploadCustomAvatar(inputStream, selectedFile);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadCustomAvatar(InputStream inputStream,File selectedFile) {
        Path to = Paths.get("src/main/resources/Images/avatars/" + selectedFile.getName());
        User.getCurrentUser().setAvatarFileName(selectedFile.getName());
        try {
            Files.copy(
                    inputStream,
                    to,
                    StandardCopyOption.REPLACE_EXISTING
            );
            displayUserAvatar();
            UserInfoOperator.storeUserDataInJson(User.getCurrentUser(), "src/main/resources/jsonData/Users.json");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
        
    private void initializeAvatarSection(){
        avatarVbox=new VBox(8);
        avatarVbox.setAlignment(Pos.CENTER);
        setUserAvatarLayout();
        loadImagesSection();
        mainVbox.getChildren().add(avatarVbox);
    }

    private void loadImagesSection(){
        selectAvatarText=new Text("Change Avatar: (you can use custom images)");
        HBox temp=new HBox(selectAvatarText);
        temp.setMinWidth(296);
        temp.setMaxWidth(296);
        temp.setStyle("-fx-background-color: rgba(205, 195, 86, 0.473);");

        try {
            image1=new Image(new FileInputStream("src/main/resources/Images/avatars/1.png"));
            image2=new Image(new FileInputStream("src/main/resources/Images/avatars/2.png"));
            image3=new Image(new FileInputStream("src/main/resources/Images/avatars/3.png"));
            image4=new Image(new FileInputStream("src/main/resources/Images/avatars/4.png"));
            selectCustomImg=new Image(new FileInputStream("src/main/resources/Images/avatars/images.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imgview1=new ImageView(image1); imgview2=new ImageView(image2); imgview3=new ImageView(image3); imgview4=new ImageView(image4);selectCustomView=new ImageView(selectCustomImg);
       
        imgview1.setFitWidth(64);imgview2.setFitWidth(64);imgview3.setFitWidth(64);imgview4.setFitWidth(64);selectCustomView.setFitWidth(48);
        imgview1.setFitHeight(64);imgview2.setFitHeight(64);imgview3.setFitHeight(64);imgview4.setFitHeight(64);selectCustomView.setFitHeight(48);

        imgview1.getStyleClass().add("hover-effect");   
        imgview2.getStyleClass().add("hover-effect"); 
        imgview3.getStyleClass().add("hover-effect"); 
        imgview4.getStyleClass().add("hover-effect"); 
        selectCustomView.getStyleClass().add("hover-effect");      


        HBox imagesHbox=new HBox(16, imgview1,imgview2,imgview3,imgview4,selectCustomView);
        imagesHbox.setAlignment(Pos.CENTER);
        avatarVbox.getChildren().addAll(temp,imagesHbox);
    }

    private void setUserAvatarLayout(){
        displayUserAvatar();
        userimgView.setFitWidth(96);
        userimgView.setFitHeight(96);
        avatarDisplayText=new Text("Your Current Avatar:");
        HBox temp=new HBox(avatarDisplayText);
        temp.setMaxWidth(120);
        temp.setStyle("-fx-background-color: rgba(205, 195, 86, 0.473);");
        avatarVbox.getChildren().addAll(temp,userimgView);
    }

    private void displayUserAvatar(){
        try {
            userimg=new Image(new FileInputStream("src/main/resources/Images/avatars/"+User.getCurrentUser().getAvatarFileName()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        userimgView.setImage(userimg);;
    }

    private void setImageviewListeners(){
        User currentUser=User.getCurrentUser();

        imgview1.setOnMouseClicked(event -> {
            currentUser.setAvatarFileName("1.png");
            displayUserAvatar();
            try {
                UserInfoOperator.storeUserDataInJson(currentUser, "src/main/resources/jsonData/Users.json");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
                });

        imgview2.setOnMouseClicked(event -> {
                currentUser.setAvatarFileName("2.png");
                displayUserAvatar();
            try {
                UserInfoOperator.storeUserDataInJson(currentUser, "src/main/resources/jsonData/Users.json");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
                });

        imgview3.setOnMouseClicked(event -> {
                currentUser.setAvatarFileName("3.png");
                displayUserAvatar();
            try {
                UserInfoOperator.storeUserDataInJson(currentUser, "src/main/resources/jsonData/Users.json");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
                });

        imgview4.setOnMouseClicked(event -> {
                currentUser.setAvatarFileName("4.png");
                displayUserAvatar();
            try {
                UserInfoOperator.storeUserDataInJson(currentUser, "src/main/resources/jsonData/Users.json");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
                });
    
        selectCustomView.setOnMouseClicked(event -> {
            setFileChooser();
            displayUserAvatar();
            try {
                UserInfoOperator.storeUserDataInJson(currentUser, "src/main/resources/jsonData/Users.json");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
                });
    }

    private void setButtonListeners() {
        submitChanges.setOnMouseClicked(event -> submitChanges());

        removeSlogan.setOnMouseClicked(event -> {
            sloganField.setText("");
            sloganField.setPromptText("slogan is empty");
            User.getCurrentUser().setSlogan("");
            Client.client.sendRequestToServer(new Request(NormalRequest.REMOVE_SLOGAN),false);
            Client.client.updateUserData();
        });

        displayScoreBoard.setOnMouseClicked(event -> {
            if(isProfileShown)
                showScoreBoardProtocol();
            else 
                showProfileProtocol();
                
                isProfileShown=!isProfileShown;
                });

        changePassword.setOnMouseClicked(event -> {
            createChangePasswordDialog();
            });
        back.setOnMouseClicked(event -> {
            try {
                isMenuDisplayed=false;
                new MainMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void createChangePasswordDialog(){
        TextInputDialog dialog = new TextInputDialog();  // create an instance
        dialog.initOwner(stage);
        dialog.setTitle("Change Password:");
        dialog.setHeaderText("Please Fill Content Below:");
        // other formatting etc
        DialogPane dialogPane = dialog.getDialogPane();
        PasswordField oldField=new PasswordField();
        PasswordField newField=new PasswordField();
        PasswordField newFieldrepeat=new PasswordField();
        oldField.setPromptText("Old Password");
        newField.setPromptText("New Password");
        newFieldrepeat.setPromptText("Repeat New Password");

        dialogPane.setContent(new VBox(8, oldField, newField,newFieldrepeat));

        Optional<String> result = dialog.showAndWait();  
            // this shows the dialog, waits until it is closed, and stores the result 


        //if the result is present (i.e. if a string was entered) call doSomething() on it
        result.ifPresent(string -> {
           changePassword(oldField.getText(), newField.getText(), newFieldrepeat.getText());
        });
    }
    
    private void changePassword(String oldPassword,String newPssword, String passwordRepeat){
        
        ProfileMenuMessages result=null;
        try {
            result=ProfileMenuController.changePassword(oldPassword, passwordRepeat, oldPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        switch (result) {
            case EMPTY_FIELDS_ERROR:
                Orders.createNotificationDialog("Result","Password Change Result:","Error: empty input fields",Orders.redNotifErrorColor);
                break;

            case INCORRECT_PASSWORD_ERROR:
                Orders.createNotificationDialog("Result","Password Change Result:","Error: incorrect old password",Orders.redNotifErrorColor);
                break;

            case WEAK_PASSWORD_ERROR:
                Orders.createNotificationDialog("Result","Password Change Result:","Error: new password is weak",Orders.redNotifErrorColor);
                break;
            
            case UNCHANGED_FIELD_ERROR:
                Orders.createNotificationDialog("Result","Password Change Result:","Error: password is not new!",Orders.redNotifErrorColor);
                break;

            case INCORRECT_PASSWORD_VERIFICATION_ERROR:
                Orders.createNotificationDialog("Result","Password Change Result:","Error: incorrect repetition",Orders.redNotifErrorColor);
                break;

            default:
                Request request=new Request(NormalRequest.CHANGE_PASSWORD);
                request.addToArguments("New_Password", newPssword);
                request.addToArguments("Old_Password", oldPassword);
                Client.client.sendRequestToServer(request,false);
                Client.client.updateUserData();
                Orders.createNotificationDialog("Result","Password Change Result:","Password was changed Succesfully",Orders.greenNotifSuccesColor);
                break;
        }
    }

    private void setStartingTexts() {
        Orders.sendTextNotification(usernameText, "username", Orders.greenNotifSuccesColor, usernameVbox);
        Orders.sendTextNotification(emailText, "email", Orders.greenNotifSuccesColor, emailVbox);
        Orders.sendTextNotification(nicknameText, "nickname", Orders.greenNotifSuccesColor, nicknameVbox);
    }

    private void initalizeLabel() {
        label = new Label("PROFILE MENU:");
        mainVbox.getChildren().add(label);
    }

    private void submitChanges() {

        Request request=new Request(NormalRequest.CHANGE_PROFILE_FIELDS);

        if (checkUsernameValue(usernameField.getText(), true))
            request.addToArguments("Username", usernameField.getText());
                
        if (checkEmailValue(emailField.getText(), true))
            request.addToArguments("Email", emailField.getText());

        if (checkNicknameValue(nicknameField.getText(), true))
            request.addToArguments("Nickname", nicknameField.getText());

        request.addToArguments("Slogan", sloganField.getText());
            
        Client.client.sendRequestToServer(request,true);
        String result= Client.client.getRecentResponse();


        String output="The fields Below Changed Succesfuly:\n";

        if(result.contains("Username"))
            output=output.concat("Username Field\n");
            

        if(result.contains("Email")) 
            output=output.concat("Email Field\n");


        if(result.contains("Nickname")) 
            output=output.concat("Nickname Field\n");


        if(result.contains("Slogan")) 
            output=output.concat("Slogan Field\n");

        Orders.createNotificationDialog("Result","Change Fileds Result:", output,Orders.yellowNotifErrorColor);
        
        if(result.contains("Username"))
            User.getCurrentUser().setUsername(usernameField.getText());

        Client.client.updateUserData();
    }

    private void setFieldListeners() {
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkUsernameValue(newValue, false);
        });

        nicknameField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkNicknameValue(newValue, false);
        });

        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkEmailValue(newValue, false);
        });
    }

    private boolean checkEmailValue(String value, boolean isOnSignUpProcess) {
        String color = Orders.yellowNotifErrorColor;
        if (isOnSignUpProcess)
            color = Orders.redNotifErrorColor;

        if (!UserInfoOperator.isEmailFormatValid(value)) {
            Orders.sendTextNotification(emailText, "Invalid email format", color, emailVbox);
            return false;
        }

        if (User.getUserByEmail(value) != null) {
            Orders.sendTextNotification(emailText, "Email already in use", color, emailVbox);
            return false;
        }

        return true;
    }

    private boolean checkNicknameValue(String value, boolean isOnSignUpProcess) {
        String color = Orders.yellowNotifErrorColor;
        if (isOnSignUpProcess)
            color = Orders.redNotifErrorColor;

        if (value == null) {
            Orders.sendTextNotification(nicknameText, "Shouldn't be left empty", color, nicknameVbox);
            return false;
        }
        if (value.length() == 0) {
            Orders.sendTextNotification(nicknameText, "Shouldn't be left empty", color, nicknameVbox);
            return false;
        }

        return true;
    }

    private boolean checkUsernameValue(String value, boolean isOnSignUpProcess) {
        String color = Orders.yellowNotifErrorColor;
        if (isOnSignUpProcess)
            color = Orders.redNotifErrorColor;

        if (!UserInfoOperator.isUsernameFormatValid(value)) {
            Orders.sendTextNotification(usernameText, "[a-zA-z0-9] and '_' +min 3 size", color,
                    usernameVbox);
            return false;
        }

        else if (User.getUserByUserName(value) != null) {
            Orders.sendTextNotification(usernameText,
                    "Username in use, suggest:" + UserInfoOperator.addRandomizationToString(value),
                    color, usernameVbox);
            return false;
        }
        return true;
    }

    private void initializeMainVbox() {
        mainVbox = new VBox(16);
        mainVbox.setMaxWidth(350);
        mainVbox.setMinWidth(350);
        mainVbox.setStyle("-fx-border-width: 0 3 3 0;");
        mainVbox.setAlignment(Pos.CENTER);

        mainPane.setAlignment(Pos.CENTER);
        mainPane.getChildren().add(mainVbox);
    }

    private void initializeButtons() {
        submitChanges = new Button("Submit changes!");
        removeSlogan = new Button("Remove Slogan");
        changePassword = new Button("Change Password");
        displayScoreBoard=new Button("Show ScoreBoard");
        back = new Button("Back to main Menu");
        back.getStyleClass().add("yellow-warning-color");
        mainVbox.getChildren().addAll(submitChanges, removeSlogan, changePassword,displayScoreBoard, back);
    }

    private void initializeFields() {
        usernameField = new TextField();
        emailField = new TextField();
        nicknameField = new TextField();
        sloganField = new TextField();
        usernameText = new Text();
        emailText = new Text();
        nicknameText = new Text();

        usernameField.setPromptText("change username");
        usernameField.setText(User.getCurrentUser().getUsername());
        usernameVbox = new VBox(0, usernameField, usernameText);

        emailField.setText(User.getCurrentUser().getEmail());
        emailField.setPromptText("change email");
        emailVbox = new VBox(0, emailField, emailText);

        nicknameField.setText(User.getCurrentUser().getNickName());
        nicknameField.setPromptText("change nickname");
        nicknameVbox = new VBox(0, nicknameField, nicknameText);

        sloganField.setPromptText("change slogan");
        sloganField.setText(User.getCurrentUser().getSlogan());
        VBox sloganTempVbox = new VBox(sloganField);
        sloganTempVbox.setMinWidth(200);
        sloganTempVbox.setMaxWidth(200);

        mainVbox.getChildren().addAll(usernameVbox, emailVbox, nicknameVbox, sloganTempVbox);
    }

    public void setAlignments() {
        usernameVbox.setMaxWidth(200);
        usernameVbox.setMinWidth(200);

        emailVbox.setMinWidth(200);
        emailVbox.setMaxWidth(200);

        nicknameVbox.setMaxWidth(200);
        nicknameVbox.setMinWidth(200);
    }

    public void showFriendRequests(){
        Request request=new Request(NormalRequest.GET_USER_BY_USERNAME);
        request.addToArguments("Username", User.getCurrentUser().getUsername());
        Client.client.sendRequestToServer(request, true);
        User.setCurrentUser(new Gson().fromJson(Client.client.getRecentResponse(), User.class));

        ArrayList<String> usersWithReq=User.getCurrentUser().getUsersWithFriendRequest();
        for (String username : usersWithReq) {
            showFriendRequestAlert(username);
        }
        User.getCurrentUser().getUsersWithFriendRequest().clear();
    }

    public void showFriendRequestAlert(String userUsername){
        Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Friend Request");
            alert.setHeaderText("Friend Request Recieved!");
            alert.setContentText("From: "+userUsername);
            alert.showAndWait().ifPresent((btnType) -> {
                    if (btnType == ButtonType.OK) 
                        handleRequestAccept(userUsername,true);
                    else handleRequestAccept(userUsername,false);
                });
    }

    private void handleRequestAccept(String newFriendUsername,boolean isAccepted){
        User targetFriend=User.getUserByUserName(newFriendUsername);
        User.submitFriendship(targetFriend,isAccepted);
    }

}
