package View;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;

import Controller.Orders;
import Controller.ProfileMenuController;
import Model.User;
import View.Enums.Commands.ProfileMenuCommands;
import View.Enums.Messages.ProfileMenuMessages;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProfileMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane Pane = FXMLLoader.load(
                new URL(SignUpMenu.class.getResource("/FXML/ProfileMenu.fxml").toExternalForm()));
        Scene scene = new Scene(Pane);
        stage.setScene(scene);
        stage.show();
    }
    public static void run() throws NoSuchAlgorithmException {

        Input_Output.outPut("PROFILE MENU:");
        ProfileMenuController.setCurrentUser(User.getCurrentUser());
        String input;

        while (true) {
            Matcher matcher;
            input=Input_Output.getInput();
            if(input.equals("back"))
                break;

            if( (matcher=ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_USERNAME)) !=null)
                changeUsername(matcher);
            else if( (matcher=ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_EMAIL)) !=null)
                changeEmail(matcher);
            else if( (matcher=ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_PASSWORD)) !=null)
                changePassword(matcher);
            else if( (matcher=ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_NICKNAME)) !=null)
                changeNickname(matcher);
            else if( (matcher=ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_SLOGAN)) !=null)
                changeSlogan(matcher);
            else if( (matcher=ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.REMOVE_SLOGAN)) !=null)
                removeSlogan();
            else if( (matcher=ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.DISPLAY_ENTIRE_PROFILE)) !=null)
                displayEntireProfile();
            else if( (matcher=ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.DISPLAY_SLOGAN)) !=null)
                displaySlogan();
            else if( (matcher=ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.DISPLAY_RANK)) !=null)
                displayRank();
            else if( (matcher=ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.DISPLAY_HIGHSCORE)) !=null)
                displayHighscore();
            else Input_Output.outPut("error: invalid order!");
        }
        ProfileMenuController.setCurrentUser(null);
        LoginMenu.run();
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
