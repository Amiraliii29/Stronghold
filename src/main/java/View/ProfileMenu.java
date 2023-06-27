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


import Controller.Orders;
import Controller.ProfileMenuController;
import Controller.UserComparator;
import Controller.UserInfoOperator;
import Model.User;
import View.Enums.Commands.ProfileMenuCommands;
import View.Enums.Messages.ProfileMenuMessages;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

    boolean isProfileShown;
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

    // {
    //     User.setCurrentUser(User.getUsers().get(0));
    //     ProfileMenuController.setCurrentUser(User.getUsers().get(0));
    // }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane Pane = FXMLLoader.load(
                new URL(SignUpMenu.class.getResource("/FXML/ProfileMenu.fxml").toExternalForm()));
        Scene scene = new Scene(Pane);
        isProfileShown=true;
        ProfileMenuController.setCurrentUser(User.getCurrentUser());
        this.mainPane = Pane;
        this.stage = stage;
        stage.setFullScreen(true);
        stage.setScene(scene);
        showProfileProtocol();
        stage.show();
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

        HBox hb0=new HBox(avatartText),hb1=new HBox(usernameText),hb2=new HBox(highscoreText),hb3=new HBox(rankText);
        hb0.setMaxWidth(100);hb0.setMinWidth(100);hb1.setMaxWidth(100);hb1.setMinWidth(100);hb2.setMaxWidth(100);hb2.setMinWidth(100);hb3.setMaxWidth(100);hb3.setMinWidth(100);
        hb2.setAlignment(Pos.CENTER);
        hb3.setAlignment(Pos.CENTER);
        hb0.setAlignment(Pos.CENTER);
        hb1.setAlignment(Pos.CENTER);
        HBox parentHBox=new HBox(8, hb0,hb1,hb2,hb3);
        scoreBoardVBox.getChildren().addAll(parentHBox);
    }

    private void sortUsers(){
        sortedUsers=new ArrayList<>(User.getUsers());
        Collections.sort(sortedUsers, new UserComparator());
        UserComparator.updateUsersRank(sortedUsers);
    }

    private void showScoreBoardProtocol(){
        mainPane.setAlignment(Pos.CENTER);
        mainPane.getChildren().removeAll(mainVbox,avatarVbox);
        sortUsers();
        bigVbox=new VBox(8);
        bigVbox.setAlignment(Pos.CENTER);
        label=new Label("ScoreBoard");
        scoreBoardVBox=new VBox(8);
        scoreboardPane= new ScrollPane(scoreBoardVBox);
        scoreBoardVBox.setAlignment(Pos.CENTER);
        scoreboardPane.setPannable(true);
        setUpperDetailOfScoreboardVbox();

        scoreboardPane.setMaxHeight(200);
        scoreboardPane.setMaxWidth(450);
        for (int i = 0; i < 10; i++) {
            displayUserInfo(i+1);
        }

        bigVbox.getChildren().addAll(label,scoreboardPane);
        setProfileButtons();
        setProfileButtonListeners();
        mainPane.getChildren().add(bigVbox);
    }

    private void setProfileButtons(){
        displayScoreBoard=new Button("Show Profile");
        back = new Button("Back to main Menu");
        back.getStyleClass().add("yellow-warning-color");
        HBox hbox=new HBox(16, displayScoreBoard,back);
        hbox.setMinWidth(300); hbox.setMaxWidth(300);
        hbox.setAlignment(Pos.CENTER);
        bigVbox.getChildren().addAll(hbox);
    }

    private void setProfileButtonListeners(){
        displayScoreBoard.setOnMouseClicked(event -> {
            if(isProfileShown)
                showScoreBoardProtocol();
            else
                showProfileProtocol();
                isProfileShown=!isProfileShown;
                });
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

    private void addStylesToHboxIfNeeded(HBox userInfoHbox,User targetUser){
        switch (targetUser.getRank()) {
            case 1 -> userInfoHbox.getStyleClass().add("gold-color");
            case 2 -> userInfoHbox.getStyleClass().add("silver-color");
            case 3 -> userInfoHbox.getStyleClass().add("bronze-color");
            default -> {
            }
        }

        if(targetUser.getUsername().equals(User.getCurrentUser().getUsername())){
            userInfoHbox.getStyleClass().add("picked-field-styles");
        }
            
        
    }

    private void copyTargetAvatarToUserAvatar(User targetUser){
        User.getCurrentUser().setAvatarFileName(targetUser.getAvatarFileName());
    }

    private void displayUserInfo(int userRank){
        if(sortedUsers.size()+1<=userRank) return;
        User targetUser=sortedUsers.get(userRank-1);
        HBox userInfoHbox=new HBox(8);
        ImageView userAvatarImgView=getUserImageView(targetUser);
        Text userNameText=new Text(targetUser.getUsername());Text userRankText=new Text(""+targetUser.getRank());Text userScoreText=new Text(""+targetUser.getHighScore());
        
        HBox temp0=new HBox(8, userAvatarImgView);
        temp0.setMinWidth(100);
        HBox temp1=new HBox(8,userNameText),temp2=new HBox(8,userScoreText),temp3=new HBox(8,userRankText);
        temp1.setMinWidth(100); temp1.setMaxWidth(100);temp2.setMinWidth(100); temp2.setMaxWidth(100);temp3.setMinWidth(100); temp3.setMaxWidth(100);
        temp0.setAlignment(Pos.CENTER);
        temp1.setAlignment(Pos.CENTER);
        temp2.setAlignment(Pos.CENTER);
        temp3.setAlignment(Pos.CENTER);
        
        userInfoHbox.getChildren().addAll(temp0,temp1,temp2,temp3);
        userInfoHbox.setAlignment(Pos.CENTER);
        addStylesToHboxIfNeeded(userInfoHbox, targetUser);
        scoreBoardVBox.getChildren().add(userInfoHbox);
    }

    private void showProfileProtocol(){
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
            System.out.println("Unable to upload custom avatar");
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
            try {
                UserInfoOperator.storeUserDataInJson(User.getCurrentUser(), "src/main/resources/jsonData/Users.json");
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        displayScoreBoard.setOnMouseClicked(event -> {
            if(isProfileShown)
                showScoreBoardProtocol();
            else showScoreBoardProtocol();
                isProfileShown=!isProfileShown;
                });

        changePassword.setOnMouseClicked(event -> {
            createChangePasswordDialog();
            });
        back.setOnMouseClicked(event -> {
            try {
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

    private void createNotificationDialog(String title,String header,String outputText,String Color){
        Dialog dialog=new Dialog<>();
        dialog.initOwner(stage);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        DialogPane dialogPane = dialog.getDialogPane();
        Text output=new Text(outputText);
        VBox vbox=new VBox(8, output);
        vbox.setStyle("-fx-background-color:"+Color);
        dialogPane.setContent(vbox);
        dialogPane.getButtonTypes().addAll(ButtonType.OK);
        dialog.showAndWait();
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
                createNotificationDialog("Result","Password Change Result:","Error: empty input fields",Orders.redNotifErrorColor);
                break;

            case INCORRECT_PASSWORD_ERROR:
                createNotificationDialog("Result","Password Change Result:","Error: incorrect old password",Orders.redNotifErrorColor);
                break;

            case WEAK_PASSWORD_ERROR:
                createNotificationDialog("Result","Password Change Result:","Error: new password is weak",Orders.redNotifErrorColor);
                break;
            
            case UNCHANGED_FIELD_ERROR:
                createNotificationDialog("Result","Password Change Result:","Error: password is not new!",Orders.redNotifErrorColor);
                break;

            case INCORRECT_PASSWORD_VERIFICATION_ERROR:
                createNotificationDialog("Result","Password Change Result:","Error: incorrect repetition",Orders.redNotifErrorColor);
                break;

            default:
                createNotificationDialog("Result","Password Change Result:","Password was changed Succesfully",Orders.greenNotifSuccesColor);
                break;
        }
    }

    private void setStartingTexts() {
        sendTextNotification(usernameText, "username", Orders.greenNotifSuccesColor, usernameVbox);
        sendTextNotification(emailText, "email", Orders.greenNotifSuccesColor, emailVbox);
        sendTextNotification(nicknameText, "nickname", Orders.greenNotifSuccesColor, nicknameVbox);
    }

    private void initalizeLabel() {
        label = new Label("PROFILE MENU:");
        mainVbox.getChildren().add(label);
    }

    private void submitChanges() {
        ProfileMenuMessages usernameResult=null,emailResult=null,nicknameResult=null,sloganResult=null;

            try {
                if (checkUsernameValue(usernameField.getText(), true))
                   usernameResult=ProfileMenuController.changeUsername(usernameField.getText());
                
                if (checkEmailValue(emailField.getText(), true))
                    emailResult=ProfileMenuController.changeEmail(emailField.getText());

                if (checkNicknameValue(nicknameField.getText(), true))
                    nicknameResult=ProfileMenuController.changeNickname(nicknameField.getText());

                sloganResult=ProfileMenuController.changeSlogan(sloganField.getText());

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        String output="The fields Below Changed Succesfuly:\n";

                if(usernameResult!=null)
            if(usernameResult.equals(ProfileMenuMessages.SUCCESFUL_CHANGE_USERNAME)) 
                output=output.concat("Username Field\n");
            
                if(emailResult!=null)
            if(emailResult.equals(ProfileMenuMessages.SUCCESFUL_CHANGE_EMAIL)) 
                output=output.concat("Email Field\n");

                if(nicknameResult!=null)
            if(nicknameResult.equals(ProfileMenuMessages.SUCCESFUL_CHANGE_NICKNAME)) 
                output=output.concat("Nickname Field\n");

                if(sloganResult!=null)
            if(sloganResult.equals(ProfileMenuMessages.SUCCESFUL_REMOVE_SLOGAN)) 
                output=output.concat("Slogan Field\n");

        createNotificationDialog("Result","Change Fileds Result:", output,Orders.yellowNotifErrorColor);
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
            sendTextNotification(emailText, "Invalid email format", color, emailVbox);
            return false;
        }

        if (User.getUserByEmail(value) != null) {
            sendTextNotification(emailText, "Email already in use", color, emailVbox);
            return false;
        }

        return true;
    }

    private boolean checkNicknameValue(String value, boolean isOnSignUpProcess) {
        String color = Orders.yellowNotifErrorColor;
        if (isOnSignUpProcess)
            color = Orders.redNotifErrorColor;

        if (value == null) {
            sendTextNotification(nicknameText, "Shouldn't be left empty", color, nicknameVbox);
            return false;
        }
        if (value.length() == 0) {
            sendTextNotification(nicknameText, "Shouldn't be left empty", color, nicknameVbox);
            return false;
        }

        return true;
    }

    private boolean checkUsernameValue(String value, boolean isOnSignUpProcess) {
        String color = Orders.yellowNotifErrorColor;
        if (isOnSignUpProcess)
            color = Orders.redNotifErrorColor;

        if (!UserInfoOperator.isUsernameFormatValid(value)) {
            sendTextNotification(usernameText, "[a-zA-z0-9] and '_' +min 3 size", color,
                    usernameVbox);
            return false;
        }

        else if (User.getUserByUserName(value) != null) {
            sendTextNotification(usernameText,
                    "Username in use, suggest:" + UserInfoOperator.addRandomizationToString(value),
                    color, usernameVbox);
            return false;
        }
        return true;
    }

    private void sendTextNotification(Text text, String output, String VboxColor, VBox vbox) {
        vbox.setStyle("-fx-background-color:" + VboxColor);
        text.setVisible(true);
        double minWidth = vbox.getMinWidth();
        double maxWidth = vbox.getMaxWidth();
        text.setText(output);
        text.setOpacity(1);
        FadeTransition fadeTrans = new FadeTransition(Duration.seconds(3), text);
        fadeTrans.setDelay(Duration.seconds(1));
        fadeTrans.setFromValue(1);
        fadeTrans.setToValue(0.2);
        fadeTrans.setOnFinished(event -> {
            vbox.setStyle("");
            vbox.setMinWidth(minWidth);
            vbox.setMaxWidth(maxWidth);
            text.setText("");
            text.setVisible(false);
        });
        fadeTrans.play();
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


}
