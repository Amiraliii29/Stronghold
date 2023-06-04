package View;

import Model.Buildings.Building;
import Model.Land;
import Model.Map;
import Model.Square;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class ShowMap extends Application{
    private static final HashMap<Land, Image> tiles;
    private static final HashMap<String, Image> units;
    private static final HashMap<String, Image> buildings;
    private static final int blockPixel;
    private static final double screenWidth;
    private static final double screenHeight;
    private static final int blockWidth;
    private static final int blockHeight;
    private final int modeWidth;
    private final int modeHeight;
    private Map map;
    private int squareI;
    private int squareJ;
    private int leftX;
    private int upY;
    private double lastX;
    private double lastY;


    static {
        tiles = new HashMap<>();
        units = new HashMap<>();
        buildings = new HashMap<>();

        blockPixel = 20;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        screenWidth = Math.ceil(screenBounds.getWidth());
        screenHeight = Math.ceil(screenBounds.getHeight());

        blockWidth = ((int) Math.ceil(screenWidth / blockPixel)) + 3;
        blockHeight = ((int) Math.ceil(screenHeight / blockPixel)) + 3;
    }

    public ShowMap(Map map) {
        this.map = map;
        squareI = 0;
        squareJ = 0;
        leftX = 0;
        lastY = 0;
        modeWidth = map.getWidth() - blockWidth;
        modeHeight = map.getLength() - blockHeight;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();

        Scene scene = new Scene(pane, screenWidth, screenHeight);
        stage.setScene(scene);

        keys(stage, scene, pane);

        map = new Map("testMap", 100 ,100);
//        drawMap(pane);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawMap(pane);
            }
        };
        timer.start();

        stage.setFullScreen(true);
        stage.show();
    }

    public void keys(Stage stage, Scene scene, Pane pane) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
//                stage.setFullScreen(!stage.isFullScreen());
            }
        });

        scene.setOnMouseClicked(event -> {
            lastX = event.getX();
            lastY = event.getY();
        });

        scene.setOnMouseDragged(event -> {
            double deltaX = event.getX() - lastX;
            double deltaY = event.getY() - lastY;

            leftX = (int) (Math.floor(deltaX));
            upY = (int) (Math.floor(deltaY));

            while (leftX > blockPixel) {
                squareI++;
                squareI %= modeWidth;
                leftX -= blockPixel;
            }
            while (leftX < -1 * blockPixel) {
                squareI--;
                if (squareI < 0) squareI = 0;
                leftX += blockPixel;
            }

            while (upY > blockPixel) {
                squareJ++;
                squareJ %= modeHeight;
                upY -= blockPixel;
            }
            while (upY < -1 * blockPixel) {
                squareJ--;
                if (squareJ < 0) squareJ = 0;
                upY -= blockPixel;
            }

//            drawMap(pane);

            lastX = event.getX();
            lastY = event.getY();
        });
    }

    public void drawMap(Pane pane) {
        Square[][] squares = map.getSquares();

        int k = leftX, l = upY;
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
            l = upY;
        }
    }

    public static void loadImages() throws FileNotFoundException {
        //tiles :
        Image image = new Image(new FileInputStream("src/main/resources/Images/tiles/default.jpg"));
        Image image1 = new Image(new FileInputStream("src/main/resources/Images/tiles/cliff.jpg"));
        tiles.put(Land.DEFAULT, image);
        tiles.put(Land.CLIFF, image1);
//        for (Land land : Land.values()) {
//            Image image = new Image(new FileInputStream("src/main/resourcesImages/tiles/" + land.name() + ".jpg"));
//            tiles.put(land, image); // check for .name()  or getName  //TODO
//        }
//        //units :
//        for (String unit : Unit.getAllUnits()) {
//            Image image = new Image(new FileInputStream("src/main/resourcesImages/units/" + unit + "."));
//            tiles.put(unit, image);
//        }
//        //buildings :
//        for (String building : Building.getBuildings()) {
//            Image image = new Image(new FileInputStream("src/main/resourcesImages/units/" + building + "."));
//            tiles.put(building, image);
//        }
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
