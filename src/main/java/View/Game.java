package View;

import Model.Land;
import Model.Map;
import Model.Square;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;

public class Game extends Application{
    private static final HashMap<Land, Image> tiles;
    private static final HashMap<String, Image> units;
    private static final HashMap<String, Image> buildings;
    private static int blockPixel;
    private static final double screenWidth;
    private static final double screenHeight;
    private static int blockWidth;
    private static int blockHeight;
    private static final int pixelFromEdge;
    private Map map;
    private Stage stage;
    private Pane pane;
    private Scene scene;
    private int squareI;
    private int squareJ;
    private Timeline moveTimeLine;


    static {
        tiles = new HashMap<>();
        units = new HashMap<>();
        buildings = new HashMap<>();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        screenWidth = Math.ceil(screenBounds.getWidth());
        screenHeight = Math.ceil(screenBounds.getHeight()) - 150;
        pixelFromEdge = 5;
    }

    public Game(Map map) {
        this.map = map;
        squareI = 0;
        squareJ = 0;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.pane = new Pane();
        this.stage = stage;

        this.scene = new Scene(pane, screenWidth, screenHeight);
        stage.setScene(scene);

        blockPixel = 30;
        blockWidth = ((int) Math.ceil(screenWidth / blockPixel));
        blockHeight = ((int) Math.ceil(screenHeight / blockPixel));

        drawMap();
        drawBottom();
        keys();

        stage.setFullScreen(true);
        stage.show();
    }

    public void keys() {
        //move :
        scene.setOnMouseMoved(event -> {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            if (mouseX < pixelFromEdge) {
                if (squareI > 0) {
                    moveTimeLine(-1, 0);
                }
            } else if (mouseX > screenWidth - pixelFromEdge) {
                if (squareI < map.getWidth() - blockWidth) {
                    moveTimeLine(1, 0);
                }
            }

            if (mouseY < pixelFromEdge) {
                if (squareJ > 0) {
                    moveTimeLine(0, -1);
                }
            } else if (mouseY > screenHeight - pixelFromEdge) {
                if (squareJ < map.getLength() - blockHeight) {
                    moveTimeLine(0, 1);
                }
            }
        });

        //other keys :
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.I) {
                if (blockPixel < 35) {
                    blockPixel += 5;
                    blockWidth = ((int) Math.ceil(screenWidth / blockPixel));
                    blockHeight = ((int) Math.ceil(screenHeight / blockPixel));
                    drawMap();
                }
            } else if (event.getCode() == KeyCode.O) {
                if (blockPixel > 25) {
                    blockPixel -= 5;
                    blockWidth = ((int) Math.ceil(screenWidth / blockPixel));
                    blockHeight = ((int) Math.ceil(screenHeight / blockPixel));
                    drawMap();
                }
            }
        });
    }

    private void moveTimeLine(int i, int j) {
        moveTimeLine = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            if ((i < 0 && squareI > 0) || (i > 0 && squareI < map.getWidth() - blockWidth))
                squareI += i;
            if ((j < 0 && squareJ > 0) || (j > 0 && squareJ < map.getLength() - blockHeight))
                squareJ += j;
            drawMap();
        }));
        moveTimeLine.setCycleCount(1);
        moveTimeLine.play();
    }

    private void drawMap() {
        Square[][] squares = map.getSquares();

        double time = System.currentTimeMillis();
        int k = 0, l = 0;
        for (int i = squareI; i < squareI + blockWidth; i++) {
            for (int j = squareJ; j < squareJ + blockHeight; j++) {
                ImageView imageView = new ImageView(tiles.get(squares[i][j].getLand()));
                imageView.setLayoutX(k);
                imageView.setLayoutY(l);
                imageView.setFitHeight(blockPixel);
                imageView.setFitWidth(blockPixel);
                pane.getChildren().add(imageView);
                l += blockPixel;
            }
            k += blockPixel;
            l = 0;
        }
        double time2 = System.currentTimeMillis();
        System.out.println(time2 - time);
    }

    private void drawBottom() {

    }

    public static void loadImages() throws FileNotFoundException {
        //tiles :
        Image image = new Image(new FileInputStream("src/main/resources/Images/tiles/default.jpg"));
        Image image1 = new Image(new FileInputStream("src/main/resources/Images/tiles/cliff.jpg"));
        tiles.put(Land.DEFAULT, image);
        tiles.put(Land.CLIFF, image1);
        // TODO
    }

    public static HashMap<Land, Image> getTiles() {
        return tiles;
    }

    public static HashMap<String, Image> getUnits() {
        return units;
    }

    public static HashMap<String, Image> getBuildings() {
        return buildings;
    }
}
