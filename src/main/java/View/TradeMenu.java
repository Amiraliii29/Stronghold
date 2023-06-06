package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class TradeMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        AnchorPane tradeMenuPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/TradeMenu.fxml").toExternalForm()));

        Scene scene = new Scene(tradeMenuPane);

        stage.setScene(scene);
        stage.show();
    }


}
