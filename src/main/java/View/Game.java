package View;

import Model.Land;
import Model.Map;
import Model.Square;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private Map map;
    private int squareI;
    private int squareJ;
    private int leftX;
    private int upY;


    static {
        tiles = new HashMap<>();
        units = new HashMap<>();
        buildings = new HashMap<>();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        screenWidth = Math.ceil(screenBounds.getWidth());
        screenHeight = Math.ceil(screenBounds.getHeight());
    }

    public Game(Map map) {
        this.map = map;
        squareI = 0;
        squareJ = 0;
        leftX = 0;
        upY = 0;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();

        Scene scene = new Scene(pane, screenWidth, screenHeight);
        stage.setScene(scene);

        blockPixel = 20;
        blockWidth = ((int) Math.ceil(screenWidth / blockPixel)) + 3;
        blockHeight = ((int) Math.ceil(screenHeight / blockPixel)) + 3;

        keys(stage, scene, pane);

        drawMap(pane);

        stage.setFullScreen(true);
        stage.show();
    }

    public void keys(Stage stage, Scene scene, Pane pane) {
        //move :
        scene.setOnMouseMoved(event -> {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            if (mouseX < 10) {
                if (squareI > 0) {
//                    leftX -= 5;
//                    if (leftX < -blockPixel) {
//                        leftX += blockPixel;
//                    }
                    squareI--;
                    drawMap(pane);
                }
            } else if (mouseX > screenWidth - 10) {
                if (squareI < map.getWidth() - blockWidth) {
//                    leftX += 5;
//                    if (leftX > blockPixel) {
//                        leftX -= blockPixel;
//                    }
                    squareI++;
                    drawMap(pane);
                }
            }

            if (mouseY < 10) {
                if (squareJ > 0) {
//                    upY -= 5;
//                    if (upY < -blockPixel) {
//                        upY += blockPixel;
//                    }
                    squareJ--;
                    drawMap(pane);
                }
            } else if (mouseY > screenHeight - 10) {
                if (squareJ < map.getLength() - blockHeight) {
//                    upY += 5;
//                    if (upY > blockPixel) {
//                        upY -= blockPixel;
//                    }
                    squareJ++;
                    drawMap(pane);
                }
            }
        });


        //other keys :
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.I) {
                if (blockPixel < 30) blockPixel += 5;
                blockWidth = ((int) Math.ceil(screenWidth / blockPixel)) + 3;
                blockHeight = ((int) Math.ceil(screenHeight / blockPixel)) + 3;
                drawMap(pane);
            } else if (event.getCode() == KeyCode.O) {
                if (blockPixel > 15) blockPixel -= 5;
                blockWidth = ((int) Math.ceil(screenWidth / blockPixel)) + 3;
                blockHeight = ((int) Math.ceil(screenHeight / blockPixel)) + 3;
                drawMap(pane);
            }
        });
    }

    public void drawMap(Pane pane) {
        Square[][] squares = map.getSquares();

        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/Images/tiles/sea.jpg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int k = leftX, l = upY;
        for (int i = squareI; i < squareI + blockWidth; i++) {
            for (int j = squareJ; j < squareJ + blockHeight; j++) {
                ImageView imageView;
                if (i == 0 || j == 0 || j == map.getLength() - 1 || i == map.getWidth() - 1) {
                     imageView = new ImageView(image);
                } else imageView = new ImageView(tiles.get(squares[i][j].getLand()));
                imageView.setLayoutX(k);
                imageView.setLayoutY(l);
                imageView.setFitHeight(blockPixel);
                imageView.setFitWidth(blockPixel);
                pane.getChildren().add(imageView);
                l += blockPixel;
            }
            k += blockPixel;
            l = upY;
        }
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
