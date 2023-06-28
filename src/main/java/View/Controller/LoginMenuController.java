package View.Controller;

import Controller.SignUpMenuController;
import Controller.UserInfoOperator;
import Model.DataBase;
import Model.Government;
import Model.Map;
import Model.User;
import View.Enums.Messages.LoginMenuMessages;
import View.Enums.Messages.SignUpMenuMessages;
import View.Input_Output;
import View.LoginMenu;
import View.MainMenu;
import View.SignUpMenu;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class LoginMenuController {
    public TextField username;
    public PasswordField password;
    public TextField captcha;
    private Button confirm;
    private TextField securityAnswer;
    private Label securityQuestionLabel;



    public void login(MouseEvent mouseEvent) throws Exception {
        String userName = username.getText();
        String passWord = UserInfoOperator.encodeStringToSha256(password.getText());
        if (userName == null || passWord == null){
            LoginMenu.captcha.setImage(new Image(DataBase.getRandomCaptchaImageAddress()));
            username.setText("");
            password.setText("");
            captcha.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("login error");
            alert.setContentText("please fill username and password fields");
            alert.showAndWait();
        }

        User targetUser = User.getUserByUserName(userName);

        if (targetUser == null){
            LoginMenu.captcha.setImage(new Image(DataBase.getRandomCaptchaImageAddress()));
            username.setText("");
            password.setText("");
            captcha.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("login error");
            alert.setContentText("invalid username");
            alert.showAndWait();
        }

        else if (!passWord.equals(targetUser.getPassword())){
            LoginMenu.captcha.setImage(new Image(DataBase.getRandomCaptchaImageAddress()));
            username.setText("");
            password.setText("");
            captcha.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("login error");
            alert.setContentText("incorrect password");
            alert.showAndWait();
            SignUpMenuController.setNewPenalty();
        }

        else if(Integer.parseInt(captcha.getText()) != DataBase.getCaptchaNumber()){
            LoginMenu.captcha.setImage(new Image(DataBase.getRandomCaptchaImageAddress()));
            username.setText("");
            password.setText("");
            captcha.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("login error");
            alert.setContentText("incorrect captcha");
            alert.showAndWait();
        }

//        if (SignUpMenuController.stayLoggedInoption == true) {
//            targetUser.setStayLoggedIn(true);
//            UserInfoOperator.storeUserDataInJson(targetUser, "src/main/resources/jsonData/Users.json");
//        }
        else {
            User.setCurrentUser(targetUser);
            new MainMenu().start(SignUpMenu.stage);
        }
    }

    public void forgotPassword(MouseEvent mouseEvent) {
        String userName = username.getText();
        if(userName == null){
            username.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("login error");
            alert.setContentText("please first enter your username");
            alert.showAndWait();
            return;
        }
        User user = User.getUserByUserName(userName);

        if(user == null){
            username.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("login error");
            alert.setContentText("invalid username");
            alert.showAndWait();
            return;
        }

        String questionText = "";

        String securityQuestion;
        int i = 1;
        while (true) {
            securityQuestion = UserInfoOperator.getSecurityQuestionByIndex(i);
            if (securityQuestion != null)
               questionText += i + ") " + securityQuestion + "\n";
            else break;
            i++;
        }
        securityQuestionLabel = new Label(questionText);
        LoginMenu.loginMenuPane.getChildren().add(securityQuestionLabel);
        securityQuestionLabel.setTextFill(Color.BLUEVIOLET);
        securityQuestionLabel.setLayoutX(225);
        securityQuestionLabel.setLayoutY(260);

        securityAnswer = new TextField();
        securityAnswer.setPromptText("Enter Answer to Security question");
        LoginMenu.loginMenuPane.getChildren().add(securityAnswer);
        securityAnswer.setLayoutX(225);
        securityAnswer.setLayoutY(330);

        confirm = new Button("confirm");
        confirm.setLayoutX(260);
        confirm.setLayoutY(370);
        LoginMenu.loginMenuPane.getChildren().add(confirm);
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!user.getSecurityQuestion().equals(securityAnswer.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("login error");
                    alert.setContentText("incorrect answer to security question");
                    alert.showAndWait();
                    LoginMenu.loginMenuPane.getChildren().remove(securityAnswer);
                    LoginMenu.loginMenuPane.getChildren().remove(securityQuestionLabel);
                    LoginMenu.loginMenuPane.getChildren().remove(confirm);
                }
                else{

                    LoginMenu.loginMenuPane.getChildren().remove(securityAnswer);
                    LoginMenu.loginMenuPane.getChildren().remove(securityQuestionLabel);
                    LoginMenu.loginMenuPane.getChildren().remove(confirm);

                    try {
                        DataBase.setCurrentGovernment(DataBase.getGovernmentByUserName(userName));
                        new MainMenu().start(SignUpMenu.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        Stage stage = SignUpMenu.stage;
        new SignUpMenu().start(stage);
    }
}
