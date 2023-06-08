package View;

import Model.*;
import Model.Buildings.Building;
import Model.Units.Unit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class Game extends Application{
    private static final HashMap<Land, Image> tiles;
    private static final HashMap<String, Image> units;
    private static final HashMap<String, Image> buildings;
    private static final HashMap<Trees, Image> trees;
    private static int blockPixel;
    private static final double screenWidth;
    public static final double screenHeight;
    public static final int leftX;
    private static int blockWidth;
    private static int blockHeight;
    private static final Rectangle blackRec;
    private static final Rectangle selectSq;
    public static AnchorPane bottomPane;

    private Stage stage;
    private Pane mainPane; // this pane contains all other panes such as pane
    private Pane pane;
    private Scene scene;
    private final Map map;
    private final Square[][] squares;
    private int squareI;
    private int squareJ;
    private int blockX;
    private int blockY;
    private boolean moveMode;

    static {
        tiles = new HashMap<>();
        units = new HashMap<>();
        buildings = new HashMap<>();
        trees = new HashMap<>();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        screenWidth = 1115;
        screenHeight = Math.ceil(screenBounds.getHeight()) - 100;
        leftX = (int) (Math.floor((screenBounds.getWidth() - screenWidth) / 2));

        blackRec = new Rectangle(0, 0, Color.BLACK);
        blackRec.setWidth(screenBounds.getWidth());
        blackRec.setHeight(screenBounds.getHeight() + 50);

        selectSq = new Rectangle();
        selectSq.setFill(null);
        selectSq.setStroke(Color.BLUE);
    }

    public Game() {
        this.map = DataBase.getSelectedMap();
        squares = map.getSquares();
        squareI = 0;
        squareJ = 0;
        moveMode = true;
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainPane = new Pane();
        this.pane = new Pane();
        mainPane.getChildren().add(pane);
        this.stage = stage;

        this.scene = new Scene(mainPane, screenWidth, screenHeight);
        this.stage.setScene(scene);

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
        scene.setOnMousePressed(event -> {
            double startX = event.getX();
            double startY = event.getY();

            blockX = (int) (Math.floor((startX - leftX) / blockPixel));
            blockY = (int) (Math.floor(startY / blockPixel));

            if (!moveMode) {
                selectSq.setVisible(true);
                selectSq.setX(leftX + blockX * blockPixel);
                selectSq.setY(blockY * blockPixel);
                selectSq.setWidth(blockPixel);
                selectSq.setHeight(blockPixel);
            }
        });

        scene.setOnMouseDragged(event -> {
            double endX = event.getX();
            double endY = event.getY();
            boolean draw = false;

            int nowX = (int) (Math.floor((endX - leftX) / blockPixel));
            int nowY = (int) (Math.floor(endY / blockPixel));

            if (moveMode) {
                //on move mode :
                if (nowX > blockX && squareI < map.getWidth() - blockWidth + 1) {
                    squareI++;
                    draw = true;
                } else if (nowX < blockX && squareI > 0) {
                    squareI--;
                    draw = true;
                }

                if (nowY > blockY && squareJ < map.getLength() - blockHeight + 1) {
                    squareJ++;
                    draw = true;
                } else if (nowY < blockY && squareJ > 0) {
                    squareJ--;
                    draw = true;
                }

                if (draw ) {
                    blockX = nowX;
                    blockY = nowY;
                    drawMap();
                }
            } else {
                selectSq.setWidth(Math.abs((nowX - blockX) * blockPixel));
                selectSq.setHeight(Math.abs((nowY - blockY) * blockPixel));
                selectSq.setX(leftX + Math.min(blockX, nowX) * blockPixel);
                selectSq.setY(Math.min(blockY, nowY) * blockPixel);
            }
        });

        pane.setOnMouseReleased(event -> {
            if (!moveMode) {
                double endX = event.getX();
                double endY = event.getY();
                int nowX = (int) (Math.floor((endX - leftX) / blockPixel));
                int nowY = (int) (Math.floor(endY / blockPixel));

                for (int i = Math.min(blockX, nowX); i < Math.max(blockX, nowX); i++) {
                    for (int j = Math.min(blockY, nowY); j < Math.max(blockY, nowY); j++) {
                        Square thisSquare = squares[squareI + i][squareJ + j];
                        //TODO
                    }
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

                    while (blockWidth * blockPixel < screenWidth)
                        blockWidth++;
                    while (blockHeight * blockPixel < screenHeight)
                        blockHeight++;

                    drawMap();
                }
            } else if (event.getCode() == KeyCode.O) {
                if (blockPixel > 25) {
                    blockPixel -= 5;
                    blockWidth = ((int) Math.ceil(screenWidth / blockPixel));
                    blockHeight = ((int) Math.ceil(screenHeight / blockPixel));

                    while (blockWidth * blockPixel < screenWidth)
                        blockWidth++;
                    while (blockHeight * blockPixel < screenHeight)
                        blockHeight++;

                    if (squareI > map.getWidth() - blockWidth) squareI = map.getWidth() - blockWidth;
                    if (squareJ > map.getLength() - blockHeight) squareJ = map.getLength() - blockHeight;

                    drawMap();
                }
            } else if (event.getCode() == KeyCode.S) {
                moveMode = !moveMode;
            }
        });
    }

    private void drawMap() {
        pane.getChildren().clear();
        pane.getChildren().add(blackRec);

        ArrayList<Building> buildingsInMap = new ArrayList<>();

        boolean check;
        int k = leftX, l = 0;
        for (int i = squareI; i < squareI + blockWidth; i++) {
            for (int j = squareJ; j < squareJ + blockHeight; j++) {
                ImageView imageView = new ImageView(tiles.get(squares[i][j].getLand()));
                imageView.setLayoutX(k);
                imageView.setLayoutY(l);
                imageView.setFitHeight(blockPixel);
                imageView.setFitWidth(blockPixel);
                pane.getChildren().add(imageView);

                check = true;

                if (l + blockPixel > screenHeight) {
                    imageView.setFitHeight(screenHeight - l);
                    check = false;
                }
                if (k + blockPixel > screenWidth + leftX) {
                    imageView.setFitWidth(screenWidth + leftX - k);
                    check = false;
                }

                if (check) {
                    if (squares[i][j].getTree() != null) {
                        ImageView treeImage = new ImageView(trees.get(squares[i][j].getTree()));
                        treeImage.setLayoutX(k);
                        treeImage.setLayoutY(l);
                        treeImage.setFitHeight(blockPixel);
                        treeImage.setFitWidth(blockPixel);
                        pane.getChildren().add(treeImage);
                    }

                    for (Unit unit : squares[i][j].getUnits()) {
                        ImageView unitImage = new ImageView(units.get(unit.getName()));
                        unitImage.setLayoutX(k);
                        unitImage.setLayoutY(l);
                        unitImage.setFitHeight(blockPixel);
                        unitImage.setFitWidth(blockPixel);
                        pane.getChildren().add(unitImage);
                    }

                    if (squares[i][j].getBuilding() != null && !buildingsInMap.contains(squares[i][j].getBuilding()))
                        buildingsInMap.add(squares[i][j].getBuilding());
                }

                l += blockPixel;
            }
            k += blockPixel;
            l = 0;
        }

        for (Building building : buildingsInMap) {
            ImageView buildingImage = new ImageView(buildings.get(building.getName()));

            buildingImage.setLayoutX((building.getXCoordinateLeft() - squareI) * blockPixel + leftX);
            buildingImage.setLayoutY((building.getYCoordinateUp() - squareJ) * blockPixel);
            buildingImage.setFitWidth(building.getWidth() * blockPixel);
            buildingImage.setFitHeight(building.getLength() * blockPixel);

            //TODO : buildings in edge

            pane.getChildren().add(buildingImage);
        }

        pane.getChildren().add(selectSq);
        selectSq.setVisible(false);
    }

    private void drawBottom() throws IOException {
        bottomPane = FXMLLoader.load(
                new URL(Game.class.getResource("/fxml/BottomMenu.fxml").toExternalForm()));
        bottomPane.setLayoutX(leftX);
        bottomPane.setLayoutY(screenHeight - 150);
//        todo uncomment when code finished

//        GameGraphicController.setPopularityGoldPopulation();
        mainPane.getChildren().add(bottomPane);
    }

    public static void loadImages() throws FileNotFoundException {
        //tiles :
        for (Land land : Land.values()) {
            Image image = new Image(new FileInputStream("src/main/resources/Images/tiles/" + Land.getName(land) + ".jpg"));
            tiles.put(land, image);
        }
        //units :
        for (String unit : Unit.getAllUnits()) {
            Image image = new Image(new FileInputStream("src/main/resources/Images/units/" + unit + ".png"));
            units.put(unit, image);
        }
        //buildings :
        for (String building : Building.getBuildingsNames()) {
            Image image = new Image(new FileInputStream("src/main/resources/Images/buildings/" + building + ".png"));
            buildings.put(building, image);
        }
        //trees :
        for (Trees tree : Trees.values()) {
            Image image = new Image(new FileInputStream("src/main/resources/Images/trees/" + Trees.getName(tree) + ".png"));
            trees.put(tree, image);
        }
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
