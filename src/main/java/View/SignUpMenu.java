package View;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;

import Controller.SignUpMenuController;
import Controller.UserInfoOperator;
import Model.User;
import Controller.Orders;
import View.Enums.Commands.SignUpMenuCommands;
import View.Enums.Messages.SignUpMenuMessages;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SignUpMenu extends Application {

    TextField usernameField,nicknameField,sloganField,emailField,securityField,passwordVisibleField,repeatPasswordVisibleField;
    PasswordField passwordField,repeatPasswordField;
    HBox userHbox,passwordHbox,emailHbox,nicknameHbox,questionHbox;
    Button signupButton,loginButton;
    Label label,securityQuestion;
    Text userText,nicknameText,emailText,passwordText,securityText;
    CheckBox randomSloganBox,randomPasswordBox,changeQuestionBox,visiblePasswordsBox;
    VBox componentsVbox;
    Pane mainPane;

    Stage stage;
    int questionIndex=1;

    @Override
    public void start(Stage stage) throws Exception {
        Pane Pane =FXMLLoader.load(SignUpMenu.class.getResource("/fxml/SignUpMenu.fxml"));
            mainPane=Pane;
            this.stage=stage;
        Scene scene = new Scene(Pane);
        stage.setScene(scene);
        initializeMainVbox();
        initializeLabel();
        initializeFields();
        initializeButtons();
        setFieldListeners();
        setVisibleFieldsBoinds();
        setButtonListeners();
        chooseNewSecurityQuestion();
        setCheckboxListeners();
        stage.show();
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

        componentsVbox=new VBox(16);
        componentsVbox.setLayoutX(360);
        componentsVbox.setLayoutY(80);
        componentsVbox.setStyle("-fx-background-color:rgba(170, 215, 213, 0.296);");
        mainPane.getChildren().add(componentsVbox);
    }

    private void initializeLabel(){
        label=new Label("SignUp Menu:");
        componentsVbox.getChildren().add(label);
    }

    private void setButtonListeners(){
        signupButton.setOnMouseClicked(event -> signup());
        loginButton.setOnMouseClicked(event -> {
            try {
                new LoginMenu().start(stage);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    private void signup(){
        String username=usernameField.getText();
        String passWord=passwordField.getText();
        String email=emailField.getText();
        String nickname=nicknameField.getText();
        String slogan=sloganField.getText();
        String securityAnswer=securityField.getText();

        boolean proceed=true;
        if(!checkUsernameValue(username, true))
            proceed=false;
        if(!checkPasswordValue(passWord, true))
            proceed=false;
        if(!checkEmailValue(email, true))
            proceed=false;
        if(!checkNicknameValue(nickname, true))
            proceed=false;
        if(!checkSecurityValue(securityAnswer, true))
            proceed=false;
        
        if(proceed)
            try {
                SignUpMenuController.createUserController(username, passWord, nickname, passWord, email, slogan, securityAnswer);
                new LoginMenu().start(stage);
            } catch ( Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    private void setVisibleFieldsBoinds(){
        passwordVisibleField.managedProperty().bind(visiblePasswordsBox.selectedProperty());
        passwordVisibleField.visibleProperty().bind(visiblePasswordsBox.selectedProperty());
        
        passwordField.managedProperty().bind(visiblePasswordsBox.selectedProperty().not());
        passwordField.visibleProperty().bind(visiblePasswordsBox.selectedProperty().not());

        passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());



        repeatPasswordVisibleField.managedProperty().bind(visiblePasswordsBox.selectedProperty());
        repeatPasswordVisibleField.visibleProperty().bind(visiblePasswordsBox.selectedProperty());

        repeatPasswordField.managedProperty().bind(visiblePasswordsBox.selectedProperty().not());
        repeatPasswordField.visibleProperty().bind(visiblePasswordsBox.selectedProperty().not());

        repeatPasswordField.textProperty().bindBidirectional(repeatPasswordVisibleField.textProperty());
    }

    private void setCheckboxListeners(){
        randomSloganBox.selectedProperty().addListener(event -> {
            sloganField.setText(UserInfoOperator.getRandomSlogan());
            randomSloganBox.setSelected(false);
                }); 

        randomPasswordBox.selectedProperty().addListener(event ->{
            String password=UserInfoOperator.generateRandomPassword();
            passwordField.setText(password);
            sendTextNotification(passwordText, "Your password is: "+password,
                                         Orders.greenNotifSuccesColor, passwordHbox);
            randomPasswordBox.setSelected(false);
                }); 
        
        changeQuestionBox.selectedProperty().addListener( event ->{
            chooseNewSecurityQuestion();
            changeQuestionBox.setSelected(false);
                }); 
    }

    private void setFieldListeners(){
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkUsernameValue(newValue,false);});

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
                checkPasswordValue(newValue,false);
            });

            
        nicknameField.textProperty().addListener((observable, oldValue, newValue) -> {
                    checkNicknameValue(newValue,false);});
        
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
                        checkEmailValue(newValue,false);});
        
        securityField.textProperty().addListener((observable, oldValue, newValue) -> {
                        checkSecurityValue(newValue,false);});
    }

    private boolean checkSecurityValue(String value,boolean isOnSignUpProcess){
        String color=Orders.yellowNotifErrorColor;
        if(isOnSignUpProcess) color=Orders.redNotifErrorColor;

        if(value.length()==0){
            sendTextNotification(securityText, "Shouldn't be left empty",color, questionHbox);
            return false;
        }

        return true;
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

    private boolean checkPasswordValue(String value, boolean isOnSignUpProcess){
        String color=Orders.yellowNotifErrorColor;
        if(isOnSignUpProcess) color=Orders.redNotifErrorColor;

        if(!UserInfoOperator.isPasswordStrong(value)){
            sendTextNotification(passwordText, "Your password is weak",color, passwordHbox);
            return false;
        }

        if(isOnSignUpProcess)
        if(!repeatPasswordField.getText().equals(passwordField.getText())){
            sendTextNotification(passwordText, "Password is not repeated correctly",color, passwordHbox);
            return false;
        }
        return true;
    }

    private boolean checkUsernameValue(String value, boolean isOnSignUpProcess){
        String color=Orders.yellowNotifErrorColor;
        if(isOnSignUpProcess) color=Orders.redNotifErrorColor;

        if(!UserInfoOperator.isUsernameFormatValid(value)){
            sendTextNotification(userText, "Must contain alphabet, numbers, '_' and 3 characters",color, userHbox);
            return false;
        }

        else if(User.getUserByUserName(value) != null){
            sendTextNotification(userText, "username already in use, suggest:"+UserInfoOperator.addRandomizationToString(value),Orders.yellowNotifErrorColor, userHbox);
            return false;
        }
        return true;
    }

    private void initializeFields(){
        usernameField=new TextField();
        usernameField.setPromptText("username");

       
        userText=new Text("");
        userHbox=new HBox(8, usernameField,userText);


        sloganField=new TextField();
        sloganField.setPromptText("slogan");
        sloganField.setMinWidth(150);
        randomSloganBox=new CheckBox("choose a random slogan");
        usernameField.setStyle("-fx-text-fill: rgba(15, 11, 90, 0.694);");
        HBox hb1= new HBox(8, sloganField, randomSloganBox);

        nicknameField=new TextField();
        nicknameField.setPromptText("nickname");

        nicknameText=new Text();
        nicknameHbox=new HBox(8, nicknameField,nicknameText);


        emailField=new TextField();
        emailField.setPromptText("email");
        emailField.setMaxWidth(160);
        emailText=new Text();
        emailHbox=new HBox(8, emailField,emailText);


        passwordField=new PasswordField();
        passwordField.setPromptText("password");
        passwordVisibleField=new TextField();
        passwordVisibleField.setPromptText("password");

        passwordText=new Text();

        randomPasswordBox=new CheckBox("choose randomly");
        HBox hb2=new HBox(24, passwordField,passwordVisibleField,randomPasswordBox);

        passwordHbox=new HBox(8, hb2,passwordText);


        repeatPasswordField=new PasswordField();
        repeatPasswordField.setPromptText("repeat password");
        repeatPasswordField.setMaxWidth(150);

        repeatPasswordVisibleField=new TextField();
        repeatPasswordVisibleField.setPromptText("repeat password");
        repeatPasswordVisibleField.setMaxWidth(150);

        securityQuestion=new Label();
        securityQuestion.setStyle("-fx-font-size: 16px;-fx-background-color: rgba(203, 189, 105, 0.55);");
        securityField=new TextField();
        changeQuestionBox=new CheckBox("Choose another question");
        securityField.setPromptText("Security answer");

        securityText=new Text();
        questionHbox=new HBox(8, securityField,changeQuestionBox,securityText);
        componentsVbox.getChildren().addAll(userHbox,passwordHbox,repeatPasswordField,repeatPasswordVisibleField,emailHbox,nicknameHbox,hb1,securityQuestion,questionHbox);
    }

    private void initializeButtons(){
        signupButton=new Button("Sign Up!");
        loginButton=new Button("Already have account? Login!");
        visiblePasswordsBox=new CheckBox("visible password");
        HBox hBox= new HBox(16, signupButton,visiblePasswordsBox);
        VBox vbox=new VBox(16,hBox,loginButton);
        componentsVbox.getChildren().addAll(vbox);
    }

    private void chooseNewSecurityQuestion(){
        securityQuestion.setText(UserInfoOperator.getSecurityQuestionByIndex(questionIndex%4+1));
        questionIndex++;
    }




}