package View;

import Controller.CustomizeMapController;
import Controller.GameMenuController;
import Model.*;
import Model.Buildings.Building;
import Model.Units.Unit;
import View.Controller.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
    public static AnchorPane customizePane;
    public static AnchorPane bottomPane;
    private static Tooltip tooltip;
    private static Pane selectedSquareInfo;
    public static String copiedBuildingName;
    private static Pane errorPane;
    public static String buildingToCreateName = null;
    public static Trees tree;
    public static Land land;
    private static AnchorPane detail;
    private static int selectedX;
    private static int selectedY;

    private Scene scene;
    public Map map;
    private Square[][] squares;
    private int squareI;
    private int squareJ;
    private int blockX;
    private int blockY;
    public static BuildingPrototype building;
    private Timeline hoverTimeline;
    private Timeline errorTimeline;
    private double mouseX;
    private double mouseY;
    public int requestAndDonatesCounter = 1;
    private ImageView copiedBuilding;
    private ImageView attackIcon;


    static {
        try {
            loadImages();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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
        tree = null;
        land = null;
        DataBase.setSelectedUnit(null);
    }

    public void customizeMap () {
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
    }

    public static void setXY(int x, int y) {
        selectedX = x;
        selectedY = y;
    }

    public boolean isCordInMap(double x, double y) {
        return x > leftX && x < screenWidth + leftX && y < screenHeight;
    }

    public void clear() {
        tree = null;
        land = null;
        building = null;
        selectSq.setVisible(false);
        DataBase.setSelectedUnit(null);
        bottomPane.getChildren().remove(detail);
        mainPane.getChildren().remove(selectedSquareInfo);
    }

    public void setTimelines() throws IOException {
        tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.seconds(20));
        tooltip.setWrapText(true);
        Tooltip.install(mainPane, tooltip);
        tooltip.hide();

        hoverTimeline = new Timeline(new KeyFrame(Duration.seconds(3), actionEvent -> {
            int nowX = (int) (Math.floor((mouseX - leftX) / blockPixel));
            int nowY = (int) (Math.floor(mouseY / blockPixel));

            try {
                if (isCordInMap(mouseX, mouseY))
                    drawSquareInfo(squares[nowX + squareI][squareJ + nowY], mouseX, mouseY);
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

            if (customizePane == null && (x < minX || x > maxX))
                robot.mouseMove(Math.min(Math.max(minX, x), maxX), y);
        });

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
                } else if (DataBase.getSelectedUnit() != null && DataBase.getSelectedUnit().size() != 0
                        && !map.doesSquareContainEnemyUnits(squareI + blockX, squareJ + blockY, DataBase.getMyGovernment())) {
                    move(squareI + blockX, squareJ + blockY);
                    try {
                        showSelectedSquares(blockX, blockY, DataBase.getSelectedUnit());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else if (DataBase.getSelectedUnit() != null && DataBase.getSelectedUnit().size() != 0
                        && map.doesSquareContainEnemyUnits(squareI + blockX, squareJ + blockY, DataBase.getMyGovernment())) {
                    String targetX=Integer.toString(squareI + blockX);
                    String targetY=Integer.toString(squareJ + blockY);
                    GameMenuController.AttackBySelectedUnits(targetX,targetY);
                }
                else if (squares[squareI + blockX][squareJ + blockY].getBuilding() != null
                        && squares[squareI + blockX][squareJ + blockY].getBuilding().getOwner().equals(DataBase.getMyGovernment())) {

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

            if (customizePane == null && (endX < minX || endX > maxX))
                robot.mouseMove(Math.min(Math.max(minX, endX), maxX), endY);

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

            if (event.getButton() == MouseButton.PRIMARY && buildingToCreateName != null && customizePane == null) {
                building = BuildingPrototype.getBuildingByName(buildingToCreateName);
                BuildingPrototype buildingPrototype = new BuildingPrototype(DataBase.getMyGovernment(), buildingToCreateName, squareI + nowX, squareJ + nowY);
                GameMenuController.constructBuilding(buildingPrototype);
                buildingToCreateName = null;
                drawMap();
                GameGraphicController.setPopularityGoldPopulation();
                building = null;
            } else if (event.getButton() == MouseButton.SECONDARY && customizePane == null) {
                ArrayList<UnitPrototype> selectedUnit = new ArrayList<>();
                for (int i = Math.min(blockX, nowX); i <= Math.max(blockX, nowX); i++) {
                    for (int j = Math.min(blockY, nowY); j <= Math.max(blockY, nowY); j++) {
                        Square thisSquare = squares[squareI + i][squareJ + j];
                        for (UnitPrototype unit : thisSquare.getUnits())
                            if (DataBase.getMyGovernment().equals(unit.getOwner())) selectedUnit.add(unit);
                    }
                }

                if (selectedUnit.size() != 0) {
                    DataBase.setSelectedUnit(selectedUnit);

                    try {
                        showSelectedSquares(nowX, nowY, selectedUnit);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        scene.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();

            tooltip.hide();

            hoverTimeline.playFromStart();
        });

        scene.setOnKeyPressed(event -> {
            final KeyCombination keyCombinationShiftC = new KeyCodeCombination(
                    KeyCode.C,KeyCombination.CONTROL_ANY);

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
            }else if(event.getCode() == KeyCode.E){
                clear();
            } else if (event.getCode() == KeyCode.M) {
                if (DataBase.getSelectedUnit() != null) moveGetCoordinate();
            } else if (event.getCode() == KeyCode.A) {
                if (DataBase.getSelectedUnit() != null) attackGetCoordinate();
            } else if (keyCombinationShiftC.match(event)){
                showCopiedBuildingImage();
            } else if(event.getCode() == KeyCode.S){
                try {
                    ShopMenu.openShopMenu();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else if(event.getCode() == KeyCode.T){
                Game.mainPane.getChildren().remove(ShopMenu.shopPane);
                Game.mainPane.getChildren().remove(TradeMenuController.tradeMenuHistoryPane);
                Game.mainPane.getChildren().remove(TradeMenuController.tradeNewRequestPane);
                Game.mainPane.getChildren().remove(ShopMenuController.tradePane);

                try {
                    ShopMenuController.tradePane = FXMLLoader.load(
                            new URL(ShopMenu.class.getResource("/fxml/TradeMenu.fxml").toExternalForm()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ShopMenuController.tradePane.setLayoutX(Game.leftX);
                ShopMenuController.tradePane.setLayoutY(0);

                Game.mainPane.getChildren().remove(ShopMenu.shopPane);
                Game.mainPane.getChildren().add(ShopMenuController.tradePane);
            }
            else if(event.getCode() == KeyCode.G){
                try {
                    mainPane.getChildren().removeAll(ChatController.chatMenuPane , ChatController.globalChatPane
                    , TradeMenuController.tradeMenuHistoryPane , TradeMenuController.tradeMenuHistoryPane ,
                            ShopMenu.shopPane , ShopMenuController.tradePane);
                    ChatController.chatMenuPane = FXMLLoader.load(new URL
                            (SignUpMenu.class.getResource("/fxml/ChatMenu.fxml").toExternalForm()));
                    ChatController.chatMenuPane.setLayoutX(leftX);
                    ChatController.chatMenuPane.setLayoutY(0);
                    mainPane.getChildren().add(ChatController.chatMenuPane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }



    public void drawMap() {
        squares = map.getSquares();
        pane.getChildren().clear();

        ArrayList<BuildingPrototype> buildingsInMap = new ArrayList<>();
        ArrayList<ImageView> unitImageView = new ArrayList<>();

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

                    for (UnitPrototype unit : squares[i][j].getUnits()) {
                        ImageView unitImage = new ImageView(units.get(unit.getName()));
                        unitImage.setLayoutX(k);
                        unitImage.setLayoutY(l);
                        unitImage.setFitHeight(blockPixel);
                        unitImage.setFitWidth(blockPixel);
                        unitImageView.add(unitImage);
                    }

                    if (squares[i][j].getBuilding() != null && !buildingsInMap.contains(squares[i][j].getBuilding()))
                        buildingsInMap.add(squares[i][j].getBuilding());
                }

                l += blockPixel;
            }
            k += blockPixel;
            l = 0;
        }

        for (BuildingPrototype building : buildingsInMap) {
            ImageView buildingImage = new ImageView(buildings.get(building.getName()));

            buildingImage.setLayoutX((building.getX() - squareI) * blockPixel + leftX);
            buildingImage.setLayoutY((building.getY() - squareJ) * blockPixel);
            buildingImage.setFitWidth(building.getWidth() * blockPixel);
            buildingImage.setFitHeight(building.getLength() * blockPixel);

            pane.getChildren().add(buildingImage);
        }

        for (ImageView imageView : unitImageView)
            pane.getChildren().add(imageView);

        pane.getChildren().add(selectSq);
        selectSq.setVisible(false);
    }

    public void drawBottom() throws IOException {
        bottomPane = FXMLLoader.load(
                new URL(Objects.requireNonNull(Game.class.getResource("/fxml/BottomMenu.fxml")).toExternalForm()));
        bottomPane.setLayoutX(leftX);
        bottomPane.setLayoutY(screenHeight - 60);

        GameGraphicController.setPopularityGoldPopulation();

        mainPane.getChildren().add(bottomPane);
    }

    public void showSelectedSquares(int finalBlockX, int finalBlockY, ArrayList<UnitPrototype> selectedUnit) throws IOException {
        bottomPane.getChildren().remove(detail);
        detail = FXMLLoader.load(
                new URL(Objects.requireNonNull(Game.class.getResource("/fxml/ShowSelectedSquares.fxml")).toExternalForm()));
        detail.setLayoutX(115);
        detail.setLayoutY(30);

        bottomPane.getChildren().add(detail);

        mainPane.getChildren().remove(selectedSquareInfo);
        selectedSquareInfo = new Pane();
        selectedSquareInfo.setLayoutX(leftX + screenWidth + 50);
        selectedSquareInfo.setLayoutY(50);
        selectedSquareInfo.setPrefWidth(100);
        selectedSquareInfo.setPrefHeight(500);
        int y = 0;

        initializeDetailsTextFields();

        HashMap<String, Integer> unitCnt = new HashMap<>();
        for (UnitPrototype unit : selectedUnit) {
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

    public void showBuildingDetail(BuildingPrototype building) throws IOException {
        DataBase.setSelectedUnit(null);
        bottomPane.getChildren().remove(detail);

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
        } else if (BuildingPrototype.Defences.contains(building.name)) {
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
            ShopMenu.openShopMenu();
        }else return;

        if (BuildingPrototype.Defences.contains(building.name)) {
            Label hp = new Label(String.valueOf(building.getHP()));
            hp.setLayoutX(39);
            hp.setLayoutY(41);
            hp.setPrefWidth(35);
            hp.setPrefHeight(37);
            hp.setFont(new Font(15));
            hp.setAlignment(Pos.CENTER);

            Label maxHp = new Label(String.valueOf(building.getMaxHP()));
            maxHp.setLayoutX(105);
            maxHp.setLayoutY(41);
            maxHp.setPrefWidth(35);
            maxHp.setPrefHeight(37);
            maxHp.setFont(new Font(15));
            maxHp.setAlignment(Pos.CENTER);

            detail.getChildren().add(hp);
            detail.getChildren().add(maxHp);
        }
        if(!building.getName().equals("Shop")) {
            detail.setLayoutX(115);
            detail.setLayoutY(30);
            bottomPane.getChildren().add(detail);
        }
    }

    public void drawSquareInfo(Square square, double x, double y) throws IOException {
        String text = "land : " + Land.getName(square.getLand()) + "\n";
        if (square.getTree() != null) text += "tree : " + Trees.getName(square.getTree()) + "\n";
        if (square.getBuilding() != null) text += "building : " + square.getBuilding().getName() + "\n";

        HashMap<UnitPrototype,Integer> unitCount = new HashMap<>();
        for (UnitPrototype unit : square.getUnits()) {
            if (!DataBase.getMyGovernment().equals(unit.getOwner())) continue;
            if (unitCount.containsKey(unit))
                unitCount.put(unit, unitCount.get(unit) + 1);
            else
                unitCount.put(unit, 1);
        }

        for (java.util.Map.Entry<UnitPrototype, Integer> set : unitCount.entrySet())
            text += set.getKey().toString() + " count : " + set.getValue() + "\n";

        tooltip.setText(text);
        tooltip.show(mainPane, x, y);
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

    private void drawLeft() throws IOException {
        customizePane = FXMLLoader.load(
                new URL(Objects.requireNonNull(Game.class.getResource("/fxml/CustomizeMap.fxml")).toExternalForm()));
        customizePane.setLayoutX(0);
        customizePane.setLayoutY(0);
        mainPane.getChildren().add(customizePane);
    }

    public void endGame() {

    }



    public void initializeDetailsTextFields(){
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

    public boolean move (int finalX, int finalY) {
        ArrayList<ArrayList<UnitPrototype>> allUnits = GameMenuController.separateUnits();
        if (allUnits == null) return false;

        ArrayList<UnitPrototype> unitForDataBase = new ArrayList<>();
        ArrayList<MoveAnimation> moveAnimations = new ArrayList<>();
        boolean check = false;

        for (ArrayList<UnitPrototype> selectedUnit : allUnits) {
            MoveAnimation moveAnimation = new MoveAnimation(selectedUnit, finalX, finalY);
            moveAnimations.add(moveAnimation);
            unitForDataBase.addAll(selectedUnit);

            if (moveAnimation.getSquares() != null && moveAnimation.getSquares().size() != 0)
                check = true;
        }

        DataBase.setSelectedUnit(unitForDataBase);

        for (MoveAnimation animation : moveAnimations)
            animation.play();

        return check;
    }

    public void moveGetCoordinate() {
        GetCoordinate getCoordinate = new GetCoordinate();
        try {
            getCoordinate.start(getCoordinate.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (selectedX != -1 && selectedY != -1) {
            move(selectedX, selectedY);
        }
    }

    public void attackGetCoordinate() {
        GetCoordinate getCoordinate = new GetCoordinate();
        try {
            getCoordinate.start(getCoordinate.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (selectedX != -1 && selectedY != -1) {
            GameMenuController.AttackBySelectedUnits(String.valueOf(selectedX), String.valueOf(selectedY));
        }
    }

    public void addCopiedBuildingListener(){
        copiedBuilding.setOnDragDetected(event -> {
            building = BuildingPrototype.getBuildingByName(copiedBuildingName);
            buildingToCreateName = copiedBuildingName;
        });
    }

    public void showCopiedBuildingImage(){
        mainPane.getChildren().remove(copiedBuilding);
        copiedBuildingName = DataBase.getSelectedBuilding().getName();
        Image buildingImage = buildings.get(DataBase.getSelectedBuilding().getName());
        instanciateCopiedBuilding(buildingImage);
    }

    public void instanciateCopiedBuilding(Image buildingImage){
        copiedBuilding=new ImageView(buildingImage);
        copiedBuilding.setLayoutX(screenWidth+leftX);
        copiedBuilding.setLayoutY(screenHeight-100);
        copiedBuilding.setFitWidth(100);
        copiedBuilding.setFitHeight(100);
        setTimelineForImageView(8, copiedBuilding, "Building Copied");
        addCopiedBuildingListener();
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
        for (String building : BuildingPrototype.buildingsName) {
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

    public void setAttackIcon(){
        Image attackImage=null;
        try {
            attackImage = new Image(new FileInputStream("src/main/resources/images/Icon/attack.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        attackIcon=new ImageView(attackImage);
        attackIcon.setLayoutX(screenWidth/2);
        attackIcon.setFitWidth(100);
        attackIcon.setFitHeight(100);
        setTimelineForImageView(5, attackIcon,"Attack Started");
    }

    public void setTimelineForImageView(int time,ImageView imageView,String errorText){
        showErrorText(errorText);
        mainPane.getChildren().add(imageView);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000*time),
                actionEvent -> {
                    mainPane.getChildren().remove(imageView);
                }));
        timeline.setDelay(Duration.millis(0));
        timeline.play();
    }
}