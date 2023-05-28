package View;

import Model.Buildings.Building;
import Model.Map;
import Model.Square;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ShowMap extends Application {
    private static int blockPixel;
    private static double screenWidth;
    private static double screenHeight;
    private static int blockWidth;
    private static int blockHeight;
    private Map map;

    static {
        blockPixel = 20;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight();

        blockWidth = ((int) Math.ceil(screenWidth / blockPixel));
        blockHeight = ((int) Math.ceil(screenHeight / blockPixel));
    }

    public ShowMap(Map map) {
        this.map = map;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();

        Scene scene = new Scene(pane, screenWidth, screenHeight);
        stage.setScene(scene);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.setFullScreen(false);
            }
        });
        map = new Map("testMap", 100 ,100);
        drawMap(pane);

        stage.show();
    }

    public void drawMap(Pane pane) {
        Square[][] squares = map.getSquares();

        for (int i = 0; i < blockWidth; i++) {
            for (int j = 0; j < blockHeight; j++) {
                ImageView imageView = new ImageView(squares[i][j].getImage());
                imageView.setLayoutX(i * blockPixel);
                imageView.setLayoutY(j * blockPixel);
                imageView.setFitHeight(blockPixel);
                imageView.setFitWidth(blockPixel);
                pane.getChildren().add(imageView);
            }
        }

//        for (String building : Building.getBuildings()) {
//            Rectangle rectangle = new Rectangle(building.getX(), building.getY(), building.getWidth(), building.getHeight());
//            rectangle.setFill(Color.RED);
//            pane.getChildren().add(rectangle);
//        }
    }
}
