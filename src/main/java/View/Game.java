package View;

import Controller.CustomizeMapController;
import Model.*;
import Model.Buildings.Building;
import Model.Buildings.Defence;
import Model.Buildings.Generator;
import Model.Buildings.*;
import Model.Units.Unit;
import View.Controller.BuildingInfo;
import View.Controller.GameGraphicController;
import View.Controller.GetCoordinate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Game extends Application{
    private static final HashMap<Land, Image> tiles;
    private static final HashMap<String, Image> units;
    private static final HashMap<String, Image> buildings;
    private static final HashMap<Trees, Image> trees;
    private static final HashMap<String, Image> resources;

    private static final double screenWidth;
    public static final double screenHeight;
    private static int blockPixel;
    public static final int leftX;
    private static int blockWidth;
    private static int blockHeight;
    private static final Rectangle blackRec;
    private static final Rectangle selectSq;

    private static Stage stage;
    public static Pane mainPane; // this pane contains all other panes such as pane
    private static Pane pane;
    public static AnchorPane bottomPane;
    public static AnchorPane customizePane;
    private static Pane squareInfo;
    private static Pane selectedSquareInfo;
    private static Pane errorPane;
    public static String defenceBuildingToCreateName = null;
    public static String generatorBuildingToCreateName = null;
    public static String barrackBuildingToCreateName = null;
    public static String townBuildingToCreateName = null;
    public static String stockPileBuildingToCreateName = null;

    public static Trees tree;
    public static Land land;
    private static AnchorPane detail;
    private static int selectedX;
    private static int selectedY;
    private static ArrayList<Government> governmentsInGame = new ArrayList<>();

    private Scene scene;
    public Map map;
    private Square[][] squares;
    private int squareI;
    private int squareJ;
    private int blockX;
    private int blockY;
    public static Building building;
    private Timeline hoverTimeline;
    private Timeline errorTimeline;
    private double mouseX;
    private double mouseY;
    private int keepOwnerGovernmentsCounter = 0;


    static {
        tiles = new HashMap<>();
        units = new HashMap<>();
        buildings = new HashMap<>();
        trees = new HashMap<>();
        resources = new HashMap<>();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        screenWidth = 1115;
        screenHeight = Math.ceil(screenBounds.getHeight()) - 50;
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
        building = null;
        customizePane = null;
        tree = null;
        land = null;
        DataBase.setSelectedUnit(null);
    }
    public static void addToGovernmentsInGame(Government government){
        governmentsInGame.add(government);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainPane = new Pane();
        pane = new Pane();
        mainPane.getChildren().add(blackRec);
        mainPane.getChildren().add(pane);

        Rectangle leftRec = new Rectangle(0, 0, Color.BLACK);
        leftRec.setWidth(leftX);
        leftRec.setHeight(screenHeight + 40);
        mainPane.getChildren().add(leftRec);

        Rectangle rightRec = new Rectangle(0, 0, Color.BLACK);
        rightRec.setX(leftX + screenWidth);
        rightRec.setWidth(leftX);
        rightRec.setHeight(screenHeight + 40);
        mainPane.getChildren().add(rightRec);

        Game.stage = stage;

        this.scene = new Scene(mainPane, screenWidth, screenHeight);
        stage.setScene(scene);

        blockPixel = 30;
        blockWidth = ((int) Math.ceil(screenWidth / blockPixel));
        blockHeight = ((int) Math.ceil(screenHeight / blockPixel));

        setTimelines();

        drawMap();
        drawBottom();
        keys();

        stage.setFullScreen(true);
        stage.show();
        placeGovernmentsKeep();
    }

    private void placeGovernmentsKeep() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Place Governments Keep Respectively");
        alert.showAndWait();

    }

    public static void setXY(int x, int y) {
        selectedX = x;
        selectedY = y;
    }

    private boolean isCordInMap(double x, double y) {
        return x > leftX && x < screenWidth + leftX && y < screenHeight;
    }

    private void clear() {
        mainPane.getChildren().remove(customizePane);
        customizePane = null;
        tree = null;
        land = null;
        building = null;
        selectSq.setVisible(false);
        DataBase.setSelectedUnit(null);
        bottomPane.getChildren().remove(detail);
        mainPane.getChildren().remove(selectedSquareInfo);
    }

    private void setTimelines() throws IOException {
        squareInfo = FXMLLoader.load(
                new URL(Objects.requireNonNull(Game.class.getResource("/fxml/SquareInfo.fxml")).toExternalForm()));
        squareInfo.setLayoutX(leftX + screenWidth + 50);
        squareInfo.setLayoutY(50);

        Label landLabel = new Label();
        landLabel.setLayoutY(25);
        landLabel.setAlignment(Pos.CENTER);
        landLabel.setPrefHeight(25);
        landLabel.setPrefWidth(100);
        squareInfo.getChildren().add(landLabel);

        Label treeLabel = new Label();
        treeLabel.setLayoutY(75);
        treeLabel.setAlignment(Pos.CENTER);
        treeLabel.setPrefHeight(25);
        treeLabel.setPrefWidth(100);
        squareInfo.getChildren().add(treeLabel);

        Label buildingLabel = new Label();
        buildingLabel.setLayoutY(125);
        buildingLabel.setAlignment(Pos.CENTER);
        buildingLabel.setPrefHeight(25);
        buildingLabel.setPrefWidth(100);
        squareInfo.getChildren().add(buildingLabel);

        hoverTimeline = new Timeline(new KeyFrame(Duration.seconds(3), actionEvent -> {
            int nowX = (int) (Math.floor((mouseX - leftX) / blockPixel));
            int nowY = (int) (Math.floor(mouseY / blockPixel));

            try {
                if (isCordInMap(mouseX, mouseY))
                    drawSquareInfo(squares[nowX + squareI][squareJ + nowY], landLabel, treeLabel, buildingLabel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        hoverTimeline.setCycleCount(-1);
        hoverTimeline.play();

        errorTimeline = new Timeline(new KeyFrame(Duration.seconds(3), actionEvent -> {
            mainPane.getChildren().remove(errorPane);
        }));
        errorTimeline.setCycleCount(1);
    }

    public void keys() {
        double minX = leftX;
        double maxX = leftX + blockWidth * blockPixel;
        Robot robot = new Robot();

        scene.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            double x = event.getX();
            double y = event.getY();

            if (customizePane == null && (x < minX || x > maxX)) {
                robot.mouseMove(Math.min(Math.max(minX, x), maxX), y);
            }
        });
        if(keepOwnerGovernmentsCounter < governmentsInGame.size()){
            scene.setOnMouseClicked(event -> {
                double endX = event.getX();
                double endY = event.getY();
                int nowX = (int) (Math.floor((endX - leftX) / blockPixel));
                int nowY = (int) (Math.floor(endY / blockPixel));

                building = Defence.createDefence(governmentsInGame.get(keepOwnerGovernmentsCounter) , squareI +  nowX, squareJ + nowY, "Keep");
                squares[squareI + nowX][squareJ + nowY].setBuilding(building);
                keepOwnerGovernmentsCounter++;
                drawMap();
            });
        }

        scene.setOnMousePressed(event -> {
            double startX = event.getX();
            double startY = event.getY();
            blockX = (int) (Math.floor((startX - leftX) / blockPixel));
            blockY = (int) (Math.floor(startY / blockPixel));

            if (event.getButton() == MouseButton.MIDDLE) {
                clear();
            } else if (event.getButton() == MouseButton.PRIMARY) {
                if (customizePane != null) {
                    if (tree != null)
                        CustomizeMapController.putTree(tree, squareI + blockX, squareJ + blockY);
                    else if (land != null)
                        CustomizeMapController.changeLand(land, squareI + blockX, squareJ + blockY);

                    drawMap();
                } else if (DataBase.getSelectedUnit() != null) {

                    move(squareI + blockX, squareJ + blockY);
                    try {
                        showSelectedSquares(blockX, blockY, DataBase.getSelectedUnit());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else if (squares[squareI + blockX][squareJ + blockY].getBuilding() != null) {
                    //TODO : Show bound for building !
                    DataBase.setSelectedBuilding(squares[squareI + blockX][squareJ + blockY].getBuilding());
                    try {
                        showBuildingDetail(squares[squareI + blockX][squareJ + blockY].getBuilding());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (event.getButton() == MouseButton.SECONDARY && customizePane == null) {
                selectSq.setX(leftX + blockX * blockPixel);
                selectSq.setY(blockY * blockPixel);
                selectSq.setWidth(blockPixel);
                selectSq.setHeight(blockPixel);
                selectSq.setVisible(true);
            }

        });

        scene.setOnMouseDragged(event -> {
            double endX = event.getX();
            double endY = event.getY();

            if (customizePane == null && (endX < minX || endX > maxX)) {
                robot.mouseMove(Math.min(Math.max(minX, endX), maxX), endY);
            }
            mainPane.getChildren().remove(squareInfo);

            boolean draw = false;
            int nowX = (int) (Math.floor((endX - leftX) / blockPixel));
            int nowY = (int) (Math.floor(endY / blockPixel));


            if (event.getButton() == MouseButton.PRIMARY && customizePane == null) {
                if (building != null) {

                } else {
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
                }
            } else if (event.getButton() == MouseButton.SECONDARY && customizePane == null) {
                selectSq.setWidth(Math.abs((nowX - blockX) * blockPixel));
                selectSq.setHeight(Math.abs((nowY - blockY) * blockPixel));
                selectSq.setX(leftX + Math.min(blockX, nowX) * blockPixel);
                selectSq.setY(Math.min(blockY, nowY) * blockPixel);
            }
        });

        scene.setOnMouseReleased(event -> {
            double endX = event.getX();
            double endY = event.getY();
            int nowX = (int) (Math.floor((endX - leftX) / blockPixel));
            int nowY = (int) (Math.floor(endY / blockPixel));

            if (event.getButton() == MouseButton.PRIMARY && customizePane == null) {
                if (defenceBuildingToCreateName != null) {
                    building = Defence.createDefence(DataBase.getCurrentGovernment() ,squareI +  nowX , squareJ +  nowY , defenceBuildingToCreateName);
                    squares[squareI + nowX][squareJ + nowY].setBuilding(building);
                    defenceBuildingToCreateName = null;
                    drawMap();
                }
                if(barrackBuildingToCreateName != null){
                    building = Barrack.createBarrack(DataBase.getCurrentGovernment() ,squareI +  nowX , squareJ +  nowY , barrackBuildingToCreateName);
                    squares[squareI + nowX][squareJ + nowY].setBuilding(building);
                    barrackBuildingToCreateName = null;
                    drawMap();
                }
                if(generatorBuildingToCreateName != null){
                    building = Generator.createGenerator(DataBase.getCurrentGovernment() ,squareI +  nowX , squareJ +  nowY , generatorBuildingToCreateName);
                    squares[squareI + nowX][squareJ + nowY].setBuilding(building);
                    generatorBuildingToCreateName = null;
                    drawMap();
                }
                if(townBuildingToCreateName != null){
                    building = TownBuilding.createTownBuilding(DataBase.getCurrentGovernment() ,squareI +  nowX , squareJ +  nowY , townBuildingToCreateName);
                    squares[squareI + nowX][squareJ + nowY].setBuilding(building);
                    townBuildingToCreateName = null;
                    drawMap();
                }
                if(stockPileBuildingToCreateName != null){
                    building = Stockpile.createStockpile(DataBase.getCurrentGovernment() ,squareI +  nowX , squareJ +  nowY , stockPileBuildingToCreateName);
                    squares[squareI + nowX][squareJ + nowY].setBuilding(building);
                    stockPileBuildingToCreateName = null;
                    drawMap();
                }
                building = null;
            } else if (event.getButton() == MouseButton.SECONDARY && customizePane == null) {
                ArrayList<Unit> selectedUnit = new ArrayList<>();
                for (int i = Math.min(blockX, nowX); i <= Math.max(blockX, nowX); i++) {
                    for (int j = Math.min(blockY, nowY); j <= Math.max(blockY, nowY); j++) {
                        Square thisSquare = squares[squareI + i][squareJ + j];
                        for (Unit unit : thisSquare.getUnits())
                            if (DataBase.getCurrentGovernment().equals(unit.getOwner())) selectedUnit.add(unit);
                    }
                }

                if (selectedUnit.size() != 0)
                    DataBase.setSelectedUnit(selectedUnit);

                try {
                    showSelectedSquares(nowX, nowY, selectedUnit);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        scene.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();

            mainPane.getChildren().remove(squareInfo);

            hoverTimeline.playFromStart();
        });

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
            } else if (event.getCode() == KeyCode.C) {
                if (customizePane == null) {
                    try {
                        building = null;
                        drawLeft();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    mainPane.getChildren().remove(customizePane);
                    customizePane = null;
                    tree = null;
                    land = null;
                    building = null;
                }
            } else if (event.getCode() == KeyCode.M) {
                if (DataBase.getSelectedUnit() != null) moveGetCoordinate();
            } else if (event.getCode() == KeyCode.A) {
                if (DataBase.getSelectedUnit() != null) attackGetCoordinate();
            }
        });
    }

    public void drawMap() {
        squares = map.getSquares();
        pane.getChildren().clear();

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

            pane.getChildren().add(buildingImage);
        }

        pane.getChildren().add(selectSq);
        selectSq.setVisible(false);
    }

    private void drawBottom() throws IOException {
        bottomPane = FXMLLoader.load(
                new URL(Objects.requireNonNull(Game.class.getResource("/fxml/BottomMenu.fxml")).toExternalForm()));
        bottomPane.setLayoutX(leftX);
        bottomPane.setLayoutY(screenHeight - 60);

        GameGraphicController.setPopularityGoldPopulation();

        mainPane.getChildren().add(bottomPane);
    }

    private void showSelectedSquares(int finalBlockX, int finalBlockY, ArrayList<Unit> selectedUnit) throws IOException {
        bottomPane.getChildren().remove(detail);
        detail = FXMLLoader.load(
                new URL(Objects.requireNonNull(Game.class.getResource("/fxml/ShowSelectedSquares.fxml")).toExternalForm()));
        detail.setLayoutX(115);
        detail.setLayoutY(30);

        bottomPane.getChildren().add(detail);

        HashMap<String, Integer> resourceGenerate = new HashMap<>();
        for (int i = Math.min(blockX, finalBlockX); i <= Math.max(blockX, finalBlockX); i++) {
            for (int j = Math.min(blockY, finalBlockY); j <= Math.max(blockY, finalBlockY); j++) {
                if (squares[squareI + i][squareJ + j].getBuilding() instanceof Generator build &&
                        build.getXCoordinateLeft() == squareI + i && build.getYCoordinateUp() == squareJ + j) {
                    if (resourceGenerate.containsKey(build.getResourceGenerate().getName()))
                        resourceGenerate.put(build.getResourceGenerate().getName(), resourceGenerate.get(build.getResourceGenerate().getName()) + build.getGeneratingRate());
                    else
                        resourceGenerate.put(build.getResourceGenerate().getName(), build.getGeneratingRate());
                }
            }
        }

        mainPane.getChildren().remove(selectedSquareInfo);
        selectedSquareInfo = new Pane();
        selectedSquareInfo.setLayoutX(leftX + screenWidth + 50);
        selectedSquareInfo.setLayoutY(50);
        selectedSquareInfo.setPrefWidth(100);
        selectedSquareInfo.setPrefHeight(500);
        int y = 0;
        for (java.util.Map.Entry<String, Integer> entry : resourceGenerate.entrySet()) {
            if (y == 200) break;

            ImageView resImage = new ImageView(resources.get(entry.getKey()));
            resImage.setLayoutX(10);
            resImage.setLayoutY(y);
            resImage.setFitHeight(30);
            resImage.setFitWidth(30);

            Label cntLabel = new Label(entry.getValue().toString());
            cntLabel.setLayoutX(50);
            cntLabel.setLayoutY(y);
            cntLabel.setAlignment(Pos.CENTER);
            cntLabel.setPrefHeight(30);
            cntLabel.setPrefWidth(30);
            cntLabel.setTextFill(Color.WHITE);
            cntLabel.setFont(new Font(20));

            selectedSquareInfo.getChildren().add(resImage);
            selectedSquareInfo.getChildren().add(cntLabel);

            y += 40;
        }

        initializeDetailsTextFields();

        HashMap<String, Integer> unitCnt = new HashMap<>();
        for (Unit unit : selectedUnit) {
            if (unitCnt.containsKey(unit.getName()))
                unitCnt.put(unit.getName(), unitCnt.get(unit.getName()) + 1);
            else
                unitCnt.put(unit.getName(), 1);
        }

        BuildingInfo.imagesOrder = new ArrayList<>();
        int check = 0;
        int x = 178;
        for (java.util.Map.Entry<String, Integer> set : unitCnt.entrySet()) {
            if (check == 8) break;

            ImageView unitImage2 = new ImageView(units.get(set.getKey()));
            unitImage2.setLayoutX(x);
            unitImage2.setLayoutY(32);
            unitImage2.setFitWidth(50);
            unitImage2.setFitHeight(56);

            BuildingInfo.getTextFields().get(check).setText(set.getValue().toString());

            detail.getChildren().add(unitImage2);

            BuildingInfo.imagesOrder.add(set.getKey());

            x += 50;
            check++;
        }

        for (int i = check; i < 8; i++)
            BuildingInfo.getTextFields().get(i).setVisible(false);

        mainPane.getChildren().add(selectedSquareInfo);
    }

    private void showBuildingDetail(Building building) throws IOException {
        if (building.getName().equals("MercenaryPost")) {
            detail = FXMLLoader.load(
                    new URL(Objects.requireNonNull(Game.class.getResource("/fxml/MercenaryPost.fxml")).toExternalForm()));

        } else if (building.getName().equals("Barrack")) {
            detail = FXMLLoader.load(
                    new URL(Objects.requireNonNull(Game.class.getResource("/fxml/Barrack.fxml")).toExternalForm()));
        } else if (building.getName().equals("EngineerGuild")) {
            detail = FXMLLoader.load(
                    new URL(Objects.requireNonNull(Game.class.getResource("/fxml/EngineerGuild.fxml")).toExternalForm()));
        } else if (building.getName().equals("DrawBridge")
                || building.getName().equals("SmallStoneGate")
                || building.getName().equals("BigStoneGate")) {

            detail = FXMLLoader.load(
                    new URL(Objects.requireNonNull(Game.class.getResource("/fxml/Gate.fxml")).toExternalForm()));
        } else if (building instanceof Defence) {
            detail = FXMLLoader.load(
                    new URL(Objects.requireNonNull(Game.class.getResource("/fxml/Repair.fxml")).toExternalForm()));
        } else if (building.getName().equals("BlackSmith")) {
            detail = FXMLLoader.load(
                    new URL(Objects.requireNonNull(Game.class.getResource("/fxml/BlackSmith.fxml")).toExternalForm()));
        } else if (building.getName().equals("Fletcher")) {
            detail = FXMLLoader.load(
                    new URL(Objects.requireNonNull(Game.class.getResource("/fxml/Fletcher.fxml")).toExternalForm()));
        } else if (building.getName().equals("PoleTurner")) {
            detail = FXMLLoader.load(
                    new URL(Objects.requireNonNull(Game.class.getResource("/fxml/PoleTurner.fxml")).toExternalForm()));
        } else if (building.getName().equals("DairyFarm")) {
            detail = FXMLLoader.load(
                    new URL(Objects.requireNonNull(Game.class.getResource("/fxml/DairyFarm.fxml")).toExternalForm()));
        } else if(building.getName().equals("Shop")){
            System.out.println("shop clicked");
            ShopMenu.openShopMenu(new Stage());
        }else return;

        detail.setLayoutX(115);
        detail.setLayoutY(30);

        bottomPane.getChildren().add(detail);
    }

    private void drawLeft() throws IOException {
        customizePane = FXMLLoader.load(
                new URL(Objects.requireNonNull(Game.class.getResource("/fxml/CustomizeMap.fxml")).toExternalForm()));
        customizePane.setLayoutX(0);
        customizePane.setLayoutY(0);
        mainPane.getChildren().add(customizePane);
    }

    private void drawSquareInfo(Square square, Label landLabel, Label treeLabel, Label buildingLabel) throws IOException {
        landLabel.setText(Land.getName(square.getLand()));
        landLabel.setTextFill(Color.WHITE);

        treeLabel.setText(Trees.getName(square.getTree()));
        treeLabel.setTextFill(Color.WHITE);

        if (square.getBuilding() != null) buildingLabel.setText(square.getBuilding().getName());
        else buildingLabel.setText("--");
        buildingLabel.setTextFill(Color.WHITE);

        HashMap<Unit,Integer> unitCount = new HashMap<>();
        for (Unit unit : square.getUnits()) {
            if (!DataBase.getCurrentGovernment().equals(unit.getOwner())) continue;
            if (unitCount.containsKey(unit))
                unitCount.put(unit, unitCount.get(unit) + 1);
            else
                unitCount.put(unit, 1);
        }

        int y = 175;
        for (java.util.Map.Entry<Unit, Integer> set : unitCount.entrySet()) {
            ImageView unitImage = new ImageView(units.get(set.getKey().getName()));
            unitImage.setLayoutX(10);
            unitImage.setLayoutY(y);
            unitImage.setFitWidth(40);
            unitImage.setFitHeight(40);

            Label unitCnt = new Label(set.getValue().toString());
            unitCnt.setLayoutX(60);
            unitCnt.setLayoutY(y);
            unitCnt.setAlignment(Pos.CENTER);
            unitCnt.setPrefHeight(40);
            unitCnt.setPrefWidth(40);
            unitCnt.setTextFill(Color.WHITE);

            squareInfo.getChildren().add(unitImage);
            squareInfo.getChildren().add(unitCnt);

            y += 50;
        }

        if (!mainPane.getChildren().contains(squareInfo)) mainPane.getChildren().add(squareInfo);
    }

    public void showErrorText(String errorText) {
        mainPane.getChildren().remove(errorPane);

        errorPane = new Pane();
        errorPane.setLayoutY(screenHeight + 30);
        errorPane.setPrefHeight(30);
        errorPane.setPrefWidth(leftX);

        Label error = new Label(errorText);
        error.setTextFill(Color.RED);
        error.setFont(new Font(10));
        error.setLayoutX(0);
        error.setLayoutY(0);

        errorPane.getChildren().add(error);
        mainPane.getChildren().add(errorPane);

        errorTimeline.playFromStart();
    }

    private void initializeDetailsTextFields(){
        for (int i = 0; i < 8; i++) {
            TextField textField= new TextField();
            textField.setLayoutY(93);
            textField.setPrefWidth(36);
            textField.setPrefHeight(18);
            textField.setLayoutX(185+52*i);
            detail.getChildren().add(textField);
            switch (i + 1) {
                case 1 -> BuildingInfo.one = textField;
                case 2 -> BuildingInfo.two = textField;
                case 3 -> BuildingInfo.three = textField;
                case 4 -> BuildingInfo.four = textField;
                case 5 -> BuildingInfo.five = textField;
                case 6 -> BuildingInfo.six = textField;
                case 7 -> BuildingInfo.seven = textField;
                default -> BuildingInfo.eight = textField;
            }
        }
    }

    public void move (int finalX, int finalY) {
        HashMap<String, Integer> unitNameAndCount = new HashMap<>();

        for (int i = 0; i < 8; i++) {
            if (!BuildingInfo.getTextFields().get(i).isVisible()) break;
            if (BuildingInfo.getTextFields().get(i).getText().matches("^\\d*$")) {
                if (BuildingInfo.getTextFields().get(i).getText().matches("^\\d+$"))
                    unitNameAndCount.put(BuildingInfo.imagesOrder.get(i), Integer.parseInt(BuildingInfo.getTextFields().get(i).getText()));
                else
                    unitNameAndCount.put(BuildingInfo.imagesOrder.get(i), -1);

                continue;
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Use Only 0-9");
            return;
        }


        ArrayList<Unit> allUnits = new ArrayList<>();
        ArrayList<MoveAnimation> moveAnimations = new ArrayList<>();

        for (java.util.Map.Entry<String, Integer> set : unitNameAndCount.entrySet()) {
            ArrayList<Square> squaresChecked = new ArrayList<>();

            for (Unit unit : DataBase.getSelectedUnit()) {
                if (!unit.getName().equals(set.getKey()) || squaresChecked.contains(unit.getSquare())) continue;

                squaresChecked.add(unit.getSquare());
                ArrayList<Unit> selectedUnit = new ArrayList<>();

                for (Unit squareUnit : unit.getSquare().getUnits()) {
                    if (set.getValue() == 0) break;
                    if (!squareUnit.getName().equals(set.getKey())) continue;

                    selectedUnit.add(squareUnit);
                    set.setValue(set.getValue() - 1);
                }

                if (selectedUnit.size() != 0)  {
                    allUnits.addAll(selectedUnit);
                    moveAnimations.add(new MoveAnimation(selectedUnit, finalX, finalY));
                }
            }
        }

        DataBase.setSelectedUnit(allUnits);

        for (MoveAnimation animation : moveAnimations)
            animation.play();
    }

    private void moveGetCoordinate() {
        new GetCoordinate();
        if (selectedX != -1 && selectedY != -1) {

        }
    }

    private void attackGetCoordinate() {
        new GetCoordinate();
        if (selectedX != -1 && selectedY != -1) {

        }
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
        //resources :
        for (Resource resource : Resource.getAllResources()) {
            Image image = new Image(new FileInputStream("src/main/resources/Images/resources/" + resource.getName() + ".png"));
            resources.put(resource.getName(), image);
        }
    }
}