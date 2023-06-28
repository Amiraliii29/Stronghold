package View;

import java.net.URL;

import Model.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginMenu extends Application {

    public static ImageView captcha;
    public static AnchorPane loginMenuPane;
    @Override
    public void start(Stage stage) throws Exception {
        loginMenuPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/LoginMenu.fxml").toExternalForm()));

        captcha = new ImageView(DataBase.getRandomCaptchaImageAddress());
        loginMenuPane.getChildren().add(captcha);
        captcha.setLayoutX(250);
        captcha.setLayoutY(200);

        Scene scene = new Scene(loginMenuPane);

        stage.setScene(scene);
        stage.show();
    }
}

