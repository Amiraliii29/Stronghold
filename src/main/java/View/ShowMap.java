package View;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ShowMap extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(pane, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);

        stage.show();
    }
}
