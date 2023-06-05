package View;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;

import Controller.Orders;
import Controller.ProfileMenuController;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ProfileMenu extends Application {

    TextField usernameField,emailField,nicknameField,sloganField;
    Text usernameText,emailText,nicknameText;
    HBox usernameHbox,emailHbox,nicknameHbox;
    Button submitChanges,removeSlogan,changePassword,back;
    VBox mainVbox;
    Label label;

    Pane mainPane;

    {
        User.setCurrentUser(User.getUsers().get(0));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane Pane = FXMLLoader.load(
                new URL(SignUpMenu.class.getResource("/FXML/ProfileMenu.fxml").toExternalForm()));
        Scene scene = new Scene(Pane);
        this.mainPane=Pane;
        stage.setScene(scene);
        initializeMainVbox();
        initializeFields();
        initializeButtons();
        setFieldListeners();
        setButtonListeners();
        setStartingTexts();
        stage.show();
    }

    private void setButtonListeners(){
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
    }

    private void setStartingTexts(){
        sendTextNotification(usernameText, "username", Orders.greenNotifSuccesColor, usernameHbox);
        sendTextNotification(emailText, "email", Orders.greenNotifSuccesColor, emailHbox);
        sendTextNotification(nicknameText, "nickname", Orders.greenNotifSuccesColor, nicknameHbox);
    }

    private void submitChanges(){
        
        if(checkUsernameValue(usernameField.getText(), true))
            User.getCurrentUser().setUsername(usernameField.getText());
        
        if(checkEmailValue(emailField.getText(), true))
            User.getCurrentUser().setEmail(emailField.getText());
        
        if(checkNicknameValue(nicknameField.getText(), true))
            User.getCurrentUser().setNickName(nicknameField.getText());
        
        User.getCurrentUser().setSlogan(sloganField.getText());
        try {
            UserInfoOperator.storeUserDataInJson(User.getCurrentUser(), "src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void setFieldListeners(){
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkUsernameValue(newValue,false);});
            
        nicknameField.textProperty().addListener((observable, oldValue, newValue) -> {
                    checkNicknameValue(newValue,false);});
        
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
                        checkEmailValue(newValue,false);});
    }

    private boolean checkEmailValue(String value, boolean isOnSignUpProcess){
        String color=Orders.yellowNotifErrorColor;
        if(isOnSignUpProcess) color=Orders.redNotifErrorColor;

        if(!UserInfoOperator.isEmailFormatValid(value)){
            sendTextNotification(emailText, "Invalid email format",color, emailHbox);
            return false;
        }

        if(User.getUserByEmail(value) != null){
            sendTextNotification(emailText, "Email already in use",color, emailHbox);
            return false;
        }

        return true;
    }

    private boolean checkNicknameValue(String value, boolean isOnSignUpProcess){
        String color=Orders.yellowNotifErrorColor;
        if(isOnSignUpProcess) color=Orders.redNotifErrorColor;

        if(value.length()==0){
            sendTextNotification(nicknameText, "Shouldn't be left empty",color, nicknameHbox);
            return false;
        }

        return true;
    }

    private boolean checkUsernameValue(String value, boolean isOnSignUpProcess){
        String color=Orders.yellowNotifErrorColor;
        if(isOnSignUpProcess) color=Orders.redNotifErrorColor;

        if(!UserInfoOperator.isUsernameFormatValid(value)){
            sendTextNotification(usernameText, "Must contain alphabet, numbers, '_' and 3 characters",color, usernameHbox);
            return false;
        }

        else if(User.getUserByUserName(value) != null){
            sendTextNotification(usernameText, "username already in use, suggest:"+UserInfoOperator.addRandomizationToString(value),Orders.yellowNotifErrorColor, usernameHbox);
            return false;
        }
        return true;
    }


    private void sendTextNotification(Text text,String output,String VboxColor,HBox hbox){
        hbox.setStyle("-fx-background-color:"+VboxColor);
        double minWidth=hbox.getMinWidth();
        double maxWidth=hbox.getMaxWidth();
        text.setText(output);
        text.setOpacity(1);
        FadeTransition fadeTrans=new FadeTransition(Duration.seconds(3),text);
        fadeTrans.setDelay(Duration.seconds(1));
        fadeTrans.setFromValue(1);
        fadeTrans.setToValue(0.2);
        fadeTrans.setOnFinished(event -> {hbox.setStyle("");
                                          hbox.setMinWidth(minWidth);
                                          hbox.setMaxWidth(maxWidth);
                                          text.setText("");});
        fadeTrans.play();
    }

    private void initializeMainVbox(){
        mainVbox=new VBox(16);
        mainVbox.setLayoutX(360);
        mainVbox.setLayoutY(80);
        
        mainPane.getChildren().add(mainVbox);
    }

    private void initializeButtons(){
        submitChanges=new Button("Submit changes!");
        removeSlogan= new Button("Remove Slogan");
        changePassword= new Button("Change Password");
        back= new Button("Back to main Menu");
        back.setStyle("-fx-color: rgba(221, 142, 14, 0.708);");
        mainVbox.getChildren().addAll(submitChanges,removeSlogan,changePassword,back);
    }

    private void initializeFields(){
        usernameField=new TextField(); emailField=new TextField(); nicknameField= new TextField(); sloganField= new TextField();
        usernameText=new Text(); emailText= new Text(); nicknameText= new Text();

        usernameField.setPromptText("change username");
        usernameField.setText(User.getCurrentUser().getUsername());
        usernameField.setMaxWidth(160);
        usernameField.setMinWidth(160);
        usernameHbox=new HBox(8, usernameField,usernameText);

        emailField.setText(User.getCurrentUser().getEmail());
        emailField.setPromptText("change email");
        emailField.setMaxWidth(160);
        emailField.setMinWidth(160);
        emailHbox=new HBox(8, emailField,emailText);

        nicknameField.setText(User.getCurrentUser().getNickName());
        nicknameField.setPromptText("change nickname");
        nicknameField.setMaxWidth(160);
        nicknameField.setMinWidth(160);
        nicknameHbox=new HBox(8, nicknameField,nicknameText);

        sloganField.setPromptText("change slogan");
        sloganField.setText(User.getCurrentUser().getSlogan());
        sloganField.setMaxWidth(160);
        sloganField.setMinWidth(160);

        mainVbox.getChildren().addAll(usernameHbox,emailHbox,nicknameHbox,sloganField);
    }


    private static void displayEntireProfile(){
        Input_Output.outPut("==displaying full info:");
        displaySlogan();
        displayRank();
        displayHighscore();
    }

    private static void displaySlogan(){
        Input_Output.outPut("displaying slogan:");
        Input_Output.outPut(ProfileMenuController.displaySolgan());
    }

    private static void displayRank(){
        Input_Output.outPut("displaying rank:");
        Input_Output.outPut(ProfileMenuController.displayRank());
    }

    private static void displayHighscore(){
        Input_Output.outPut("displaying highscore:");
        Input_Output.outPut(ProfileMenuController.displayHighscore());
    }

    private static void removeSlogan() throws NoSuchAlgorithmException{
        ProfileMenuMessages result=ProfileMenuController.removeSlogan();
        
        switch (result) {
            case SUCCESFUL_REMOVE_SLOGAN:
                Input_Output.outPut("your slogan was removed!");
                break;
        
            default:
                Input_Output.outPut("error removing slogan!");
                break;
        }
    }

    private static void changeSlogan(Matcher matcher) throws NoSuchAlgorithmException{
        String newSlogan=matcher.group("newSlogan");
        newSlogan=Orders.trimIfNeeded(newSlogan);

        ProfileMenuMessages result=ProfileMenuController.changeSlogan(newSlogan);
        switch (result) {
            case EMPTY_FIELDS_ERROR:
                Input_Output.outPut("error: empty slogan field!");
                break;

            case UNCHANGED_FIELD_ERROR:
                Input_Output.outPut("error: this is already set as your slogan!");
                break;

            case SUCCESFUL_CHANGE_SLOGAN:
                Input_Output.outPut("your slogan was changed succesfully!");
                break;

            default:
                Input_Output.outPut("error changing slogan!");
                break;
        }
    }

    private static void changeNickname(Matcher matcher) throws NoSuchAlgorithmException{
        String newNickname=matcher.group("newNickname");
            newNickname=Orders.trimIfNeeded(newNickname);
        
        ProfileMenuMessages result=ProfileMenuController.changeNickname(newNickname);
        switch (result) {
            case UNCHANGED_FIELD_ERROR:
                Input_Output.outPut("error: this is already set as your nickname!");
                break;

            case EMPTY_FIELDS_ERROR:
                Input_Output.outPut("error: empty nickname field!");
                break;
            
            case SUCCESFUL_CHANGE_NICKNAME:
                Input_Output.outPut("nickname was changed succesfully!");
                break;

            default:
                Input_Output.outPut("error changing nickname!");
                break;
        }
    }

    private static void changeEmail(Matcher matcher) throws NoSuchAlgorithmException{
        String newEmail=matcher.group("newEmail");

        ProfileMenuMessages result=ProfileMenuController.changeEmail(newEmail);
        
        switch (result) {
            case EMPTY_FIELDS_ERROR:
                Input_Output.outPut("error: empty email field!");
                break;

            case UNCHANGED_FIELD_ERROR:
                Input_Output.outPut("error: this is already set as your email!");
                break;

            case DUPLICATE_EMAIL_ERROR:
                Input_Output.outPut("error: this email is already in use!");
                break;

            case INVALID_EMAIL_ERROR:
                Input_Output.outPut("error: invalid email format!");
                break;

            case SUCCESFUL_CHANGE_EMAIL:
                Input_Output.outPut("your email was succesfully changed to: "+newEmail+"!");
                break;
        
            default:
                Input_Output.outPut("error changing email!");
                break;
        }
    }

    private static void changePassword(Matcher matcher) throws NoSuchAlgorithmException{
        String components=matcher.group("changePasswordComponents");
        String oldPassword=Orders.findFlagOption("-op", components);
        String newPassword=Orders.findFlagOption("-np", components);
        if(Orders.isOrderJunky(components ,false, "-op", "-np")){
            Input_Output.outPut("error: invalid input fields");
            return ;
        }


        ProfileMenuMessages result=ProfileMenuController.changePassword(oldPassword, newPassword);
        switch (result) {
            case SUCCESFUL_CHANGE_PASSWORD:
                Input_Output.outPut("password was changed succesfuly!");
                break;
            
            case UNCHANGED_FIELD_ERROR:
                Input_Output.outPut("error: this is already set as your password!");
                break;
            case EMPTY_FIELDS_ERROR:
                Input_Output.outPut("error: empty password fields!");
                break;
            
            case WEAK_PASSWORD_ERROR:
                Input_Output.outPut("error: your new password is weak!");
                break;    

            case INCORRECT_PASSWORD_VERIFICATION_ERROR:
                Input_Output.outPut("error: incorrect password repetition!");
                break;

            default:
                Input_Output.outPut("error: incorrect old password!");
                break;
        }
    }

    private static void changeUsername(Matcher matcher) throws NoSuchAlgorithmException{
        String newUsername=matcher.group("newUsername");
        
        ProfileMenuMessages result=ProfileMenuController.changeUsername(newUsername);
        switch (result) {
            case EMPTY_FIELDS_ERROR:
                Input_Output.outPut("error: empty username field!");
                break;
            
            case INVALID_USERNAME_ERROR:
                Input_Output.outPut("error: new username format is invalid!");
                break;
            
            case UNCHANGED_FIELD_ERROR:
                Input_Output.outPut("error: this is already set as your username!");
                break;

            case DUPLICATE_USERNAME_ERROR:
                Input_Output.outPut("error: this username is already in use!");
                break;

            case SUCCESFUL_CHANGE_USERNAME:
                Input_Output.outPut("your username has succesfully changed to: "+newUsername+"!");
                break;
            default:
                Input_Output.outPut("error while changing username!");
                break;
        }
    }

    public static String getPasswordConfirmationFromUser(){
        Input_Output.outPut("please verify your password: ");
        String passwordConfirmation=Input_Output.getInput();

        return passwordConfirmation;
    }
}
