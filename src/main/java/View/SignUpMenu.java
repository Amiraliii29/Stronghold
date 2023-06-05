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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SignUpMenu extends Application {

    TextField usernameField,nicknameField,sloganField,emailField,securityField,passwordVisibleField,repeatPasswordVisibleField;
    PasswordField passwordField,repeatPasswordField;
    VBox userVbox,passwordVbox,emailVbox,nicknameVbox,questionVbox,sloganVBox;
    Button signupButton,loginButton;
    Label label,securityQuestion;
    Text userText,nicknameText,emailText,passwordText,securityText;
    CheckBox randomSloganBox,randomPasswordBox,changeQuestionBox,visiblePasswordsBox;
    VBox componentsVbox;
    StackPane mainPane;

    Stage stage;
    int questionIndex=1;

    @Override
    public void start(Stage stage) throws Exception {
        StackPane Pane = FXMLLoader.load(SignUpMenu.class.getResource("/fxml/SignUpMenu.fxml"));
            mainPane=Pane;
            this.stage=stage;
        Scene scene = new Scene(Pane);
        stage.setFullScreen(true);
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
        setAlignments();
        stage.show();

    }

    private void sendTextNotification(Text text,String output,String VboxColor,VBox vbox){
        vbox.setStyle("-fx-background-color:"+VboxColor);
        text.setVisible(true);
        double minWidth=vbox.getMinWidth();
        double maxWidth=vbox.getMaxWidth();
        text.setText(output);
        text.setOpacity(1);
        FadeTransition fadeTrans=new FadeTransition(Duration.seconds(3),text);
        fadeTrans.setDelay(Duration.seconds(1));
        fadeTrans.setFromValue(1);
        fadeTrans.setToValue(0.2);
        fadeTrans.setOnFinished(event -> {vbox.setStyle("");
                                          vbox.setMinWidth(minWidth);
                                          vbox.setMaxWidth(maxWidth);
                                          text.setText("");
                                          text.setVisible(false);});
        fadeTrans.play();
    }


    private void initializeMainVbox(){
        mainPane.setAlignment( Pos.CENTER);
        
        componentsVbox=new VBox(16);
        componentsVbox.setMaxWidth(350);
        componentsVbox.setMinWidth(350);
        componentsVbox.setAlignment(Pos.CENTER);
        componentsVbox.setStyle("-fx-border-width: 0 3 3 0;");
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
                                         Orders.greenNotifSuccesColor, passwordVbox);
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
            sendTextNotification(securityText, "Shouldn't be left empty",color, questionVbox);
            return false;
        }

        return true;
    }

    private boolean checkEmailValue(String value, boolean isOnSignUpProcess){
        String color=Orders.yellowNotifErrorColor;
        if(isOnSignUpProcess) color=Orders.redNotifErrorColor;

        if(!UserInfoOperator.isEmailFormatValid(value)){
            sendTextNotification(emailText, "Invalid email format",color, emailVbox);
            return false;
        }

        if(User.getUserByEmail(value) != null){
            sendTextNotification(emailText, "Email already in use",color, emailVbox);
            return false;
        }

        return true;
    }

    private boolean checkNicknameValue(String value, boolean isOnSignUpProcess){
        String color=Orders.yellowNotifErrorColor;
        if(isOnSignUpProcess) color=Orders.redNotifErrorColor;

        if(value.length()==0){
            sendTextNotification(nicknameText, "Shouldn't be left empty",color, nicknameVbox);
            return false;
        }

        return true;
    }

    private boolean checkPasswordValue(String value, boolean isOnSignUpProcess){
        String color=Orders.yellowNotifErrorColor;
        if(isOnSignUpProcess) color=Orders.redNotifErrorColor;

        if(!UserInfoOperator.isPasswordStrong(value)){
            sendTextNotification(passwordText, "Your password is weak",color, passwordVbox);
            return false;
        }

        if(isOnSignUpProcess)
        if(!repeatPasswordField.getText().equals(passwordField.getText())){
            sendTextNotification(passwordText, "Password is not repeated correctly",color, passwordVbox);
            return false;
        }
        return true;
    }

    private boolean checkUsernameValue(String value, boolean isOnSignUpProcess){
        String color=Orders.yellowNotifErrorColor;
        if(isOnSignUpProcess) color=Orders.redNotifErrorColor;

        if(!UserInfoOperator.isUsernameFormatValid(value)){
            sendTextNotification(userText, "[a-zA-z0-9] and '_' +min 3 size",color, userVbox);
            return false;
        }

        else if(User.getUserByUserName(value) != null){
            sendTextNotification(userText, "username already in use, suggest:"+UserInfoOperator.addRandomizationToString(value),Orders.yellowNotifErrorColor, userVbox);
            return false;
        }
        return true;
    }

    private void initializeFields(){
        usernameField=new TextField();
        usernameField.setPromptText("username");

       
        userText=new Text("");
        userVbox=new VBox(0, usernameField,userText);


        sloganField=new TextField();
        sloganField.setPromptText("slogan");
        randomSloganBox=new CheckBox("choose randomly");
        usernameField.setStyle("-fx-text-fill: rgba(15, 11, 90, 0.694);");
        HBox hb1= new HBox(8, sloganField, randomSloganBox);
        hb1.setMaxWidth(350);
        hb1.setMinWidth(350);
        sloganVBox=new VBox(0, hb1);

        nicknameField=new TextField();
        nicknameField.setPromptText("nickname");

        nicknameText=new Text();
        nicknameVbox=new VBox(0, nicknameField,nicknameText);


        emailField=new TextField();
        emailField.setPromptText("email");
        emailText=new Text();
        emailVbox=new VBox(0, emailField,emailText);


        passwordField=new PasswordField();
        passwordField.setPromptText("password");
        passwordVisibleField=new TextField();
        passwordVisibleField.setPromptText("password");

        passwordText=new Text();

        randomPasswordBox=new CheckBox("choose randomly");
        HBox hb2=new HBox(8, passwordField,passwordVisibleField,randomPasswordBox);
        hb2.setMaxWidth(350);
        hb2.setMinWidth(350);


        passwordVbox=new VBox(0, hb2,passwordText);


        repeatPasswordField=new PasswordField();
        repeatPasswordField.setPromptText("repeat password");

        repeatPasswordVisibleField=new TextField();
        repeatPasswordVisibleField.setPromptText("repeat password");

        securityQuestion=new Label();
        securityQuestion.setStyle("-fx-font-size: 16px;");
        securityField=new TextField();
        changeQuestionBox=new CheckBox("Next Question");
        securityField.setPromptText("Security answer");

        securityText=new Text();
        HBox hb3=new HBox(8, securityField,changeQuestionBox);
        hb3.setMaxWidth(350);
        hb3.setMinWidth(350);

        questionVbox=new VBox(0, hb3,securityText);
        componentsVbox.getChildren().addAll(userVbox,passwordVbox,repeatPasswordField,repeatPasswordVisibleField,emailVbox,nicknameVbox,sloganVBox,securityQuestion,questionVbox);
    }

    private void initializeButtons(){
        signupButton=new Button("Sign Up!");
        loginButton=new Button("Already have account? Login!");
        visiblePasswordsBox=new CheckBox("visible password");
        HBox hBox= new HBox(16, signupButton,visiblePasswordsBox);
        VBox vbox=new VBox(16,hBox,loginButton);
        vbox.setMaxWidth(200);
        vbox.setMinWidth(200);
        componentsVbox.getChildren().addAll(vbox);
    }

    private void chooseNewSecurityQuestion(){
        securityQuestion.setText(UserInfoOperator.getSecurityQuestionByIndex(questionIndex%4+1));
        questionIndex++;
    }

    private void setAlignments(){
        userVbox.setMinWidth(200);
        userVbox.setMaxWidth(200);

        emailVbox.setMinWidth(200);
        emailVbox.setMaxWidth(200);

        nicknameVbox.setMinWidth(200);
        nicknameVbox.setMaxWidth(200);

        questionVbox.setMinWidth(200);
        questionVbox.setMaxWidth(200);

        passwordVbox.setMinWidth(200);
        passwordVbox.setMaxWidth(200);

        sloganVBox.setMinWidth(200);
        sloganVBox.setMaxWidth(200);

        // passwordField.setMaxWidth(140);
        // passwordField.setMinWidth(140);
        // passwordVisibleField.setMinWidth(140);
        // passwordVisibleField.setMaxWidth(140);


        userText.setVisible(false);
        passwordText.setVisible(false);
        emailText.setVisible(false);
        nicknameText.setVisible(false);
        securityText.setVisible(false);
    }



}