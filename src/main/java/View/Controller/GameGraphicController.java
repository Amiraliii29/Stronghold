package View.Controller;

import Model.Buildings.Building;
import Model.Buildings.TownBuilding;
import Model.DataBase;
import View.Game;
import View.Main;
import View.ShopMenu;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


// this class has been created by amirali for bottom menu controller
public class GameGraphicController {
    public static Label popularityInBottomMenu = new Label();
    public static Label goldsInBottomMenu = new Label();
    public static Label populationInBottomMenu = new Label();
    private static AnchorPane popularityFactorsPane;
    public static ImageView foodImage = new ImageView();
    public static ImageView taxImage = new ImageView();
    public static ImageView fearImage = new ImageView();
    public static ImageView crowdingImage = new ImageView();
    public static ImageView religionImage = new ImageView();
    public  static ImageView aleImage = new ImageView();
    public static ImageView totalImage = new ImageView();
    public static Label foodNumber = new Label();
    public static Label taxNumber = new Label();
    public static Label crowdingNumber = new Label();
    public static Label fearNumber = new Label();
    public static Label religionNumber = new Label();
    public  Label aleNumber = new Label();
    public static Label totalNumber = new Label();


    public static void setPopularityGoldPopulation(){
        int popularityInt = DataBase.getCurrentGovernment().getPopularity();
        popularityInBottomMenu.setText(popularityInt + "/100");
        popularityInBottomMenu.setLayoutX(913);
        popularityInBottomMenu.setLayoutY(78);
        if(popularityInt > 75)
            popularityInBottomMenu.setTextFill(Color.GREEN);
        else if(popularityInt < 25)
            popularityInBottomMenu.setTextFill(Color.RED);

        if(!Game.bottomPane.getChildren().contains(popularityInBottomMenu)){
            Game.bottomPane.getChildren().add(popularityInBottomMenu);
        }

        popularityInBottomMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    popularityFactorsPane = FXMLLoader.load(
                            new URL(Main.class.getResource("/fxml/PopularityFactorsMenu.fxml").toExternalForm()));
                    popularityFactorsPane.setLayoutX(Game.leftX +  512);
                    popularityFactorsPane.setLayoutY(Game.screenHeight - 285);
                    Game.mainPane.getChildren().add(popularityFactorsPane);
                    fillPopularityFactorsMenuLabelsAndImages();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //show gold
        goldsInBottomMenu.setText((int)DataBase.getCurrentGovernment().getMoney() + "");
        goldsInBottomMenu.setLayoutX(894);
        goldsInBottomMenu.setLayoutY(96);
        if(!Game.bottomPane.getChildren().contains(goldsInBottomMenu))
            Game.bottomPane.getChildren().add(goldsInBottomMenu);

        //show population
        populationInBottomMenu.setText(DataBase.getCurrentGovernment().getPopulation() + "/128" );
        populationInBottomMenu.setLayoutX(886);
        populationInBottomMenu.setLayoutY(112);
        if(!Game.bottomPane.getChildren().contains(populationInBottomMenu))
            Game.bottomPane.getChildren().add(populationInBottomMenu);



    }

    private static void fillPopularityFactorsMenuLabelsAndImages() throws MalformedURLException, FileNotFoundException {
        DataBase.getCurrentGovernment().updateBuildingPopularity();

        int foodNumberInt = (DataBase.getCurrentGovernment().getFood()) * 4;
        int taxNumberInt = 0;
        int tax = DataBase.getCurrentGovernment().getTax();
        if (tax <= 0) taxNumberInt += (tax * (-2) + 1);
        else if (tax <= 4) taxNumberInt += (tax * (-2));
        else taxNumberInt += (((tax - 5) * (-4)) - 12);
        int fearNumberInt = DataBase.getCurrentGovernment().getFear();
        int religionNumberInt = DataBase.getCurrentGovernment().getFaith();

        int totalNumberInt = foodNumberInt + fearNumberInt + religionNumberInt + taxNumberInt;

        foodImage.setLayoutX(200);
        foodImage.setY(25);
        foodNumber.setLayoutX(170);
        foodNumber.setLayoutY(39);

        taxImage.setLayoutX(200);
        taxImage.setLayoutY(63);
        taxNumber.setLayoutX(170);
        taxNumber.setLayoutY(78);

        fearImage.setLayoutX(200);
        fearImage.setLayoutY(135);
        fearNumber.setLayoutX(170);
        fearNumber.setLayoutY(150);

        religionImage.setLayoutX(412);
        religionImage.setLayoutY(25);
        religionNumber.setLayoutX(378);
        religionNumber.setLayoutY(39);

        totalImage.setLayoutX(492);
        totalImage.setLayoutY(175);
        totalNumber.setLayoutX(538);
        totalNumber.setLayoutY(192);

        popularityFactorsPane.getChildren().addAll(foodImage , foodNumber , fearImage , fearNumber , religionImage
        , religionNumber , taxNumber , taxImage , totalNumber , totalImage);

        foodNumber = new Label(fearNumberInt + "");
        if(foodNumberInt > 0)
            foodImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/happyFace.png")));
        else if(foodNumberInt == 0)
            foodImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/pokerFace.png")));
        else
            foodImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/sadFace.png")));

        if(foodNumberInt > 0)
            foodNumber.setTextFill(Color.GREEN);
        else if(foodNumberInt == 0)
            foodNumber.setTextFill(Color.YELLOW);
        else
            foodNumber.setTextFill(Color.RED);


        taxNumber.setText(taxNumberInt + "");

        if(taxNumberInt > 0)
            taxImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/happyFace.png")));
        else if(taxNumberInt == 0)
            taxImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/pokerFace.png")));
        else
            taxImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/sadFace.png")));

        if(taxNumberInt > 0)
            taxNumber.setTextFill(Color.GREEN);
        else if(taxNumberInt == 0)
            taxNumber.setTextFill(Color.YELLOW);
        else
            taxNumber.setTextFill(Color.RED);

        fearNumber.setText(fearNumberInt + "");

        if(fearNumberInt > 0)
            fearImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/happyFace.png")));
        else if(fearNumberInt == 0)
            fearImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/pokerFace.png")));
        else
            fearImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/sadFace.png")));

        if(fearNumberInt > 0)
            fearNumber.setTextFill(Color.GREEN);
        else if(fearNumberInt == 0)
            fearNumber.setTextFill(Color.YELLOW);
        else
            fearNumber.setTextFill(Color.RED);

        religionNumber.setText(religionNumberInt + "");
        if(religionNumberInt > 0)
            religionImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/happyFace.png")));
        else if(religionNumberInt == 0)
            religionImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/pokerFace.png")));
        else
            religionImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/sadFace.png")));

        if(religionNumberInt > 0)
            religionNumber.setTextFill(Color.GREEN);
        else if(religionNumberInt == 0)
            religionNumber.setTextFill(Color.YELLOW);
        else
            religionNumber.setTextFill(Color.GREEN);

        totalNumber.setText(totalNumberInt + "");
        if(totalNumberInt > 0)
            totalImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/happyFace.png")));
        else if(foodNumberInt == 0)
            totalImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/pokerFace.png")));
        else
            totalImage.setImage(new Image(new FileInputStream("src/main/resources/Images/avatars/sadFace.png")));

        if(totalNumberInt > 0)
            totalNumber.setTextFill(Color.GREEN);
        else if(foodNumberInt == 0)
            totalNumber.setTextFill(Color.YELLOW);
        else
            totalNumber.setTextFill(Color.RED);

        // place sliders to change rates
        Slider foodSlider = new Slider(-2 , 2  , 0);
        foodSlider.setLayoutX(290);
        foodSlider.setLayoutY(40);
        foodSlider.setPrefWidth(90);

        foodSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int foodRate = (int) Math.floor(foodSlider.getValue());
                DataBase.getCurrentGovernment().setFood(foodRate);
                setPopularityGoldPopulation();
            }
        });

        Slider taxSlider = new Slider(-3 , 8 , 0);
        taxSlider.setLayoutX(300);
        taxSlider.setLayoutY(80);
        taxSlider.setPrefWidth(90);

        taxSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int taxRate = (int) Math.floor(taxSlider.getValue());
                DataBase.getCurrentGovernment().setTax(taxRate);
                setPopularityGoldPopulation();
            }
        });

        Slider fearSlider = new Slider(-3 , 8 , 0);
        fearSlider.setLayoutX(360);
        fearSlider.setLayoutY(150);
        fearSlider.setPrefWidth(90);

        fearSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int fearRate = (int) Math.floor(fearSlider.getValue());
                DataBase.getCurrentGovernment().setFear(fearRate);
                setPopularityGoldPopulation();
            }
        });


        popularityFactorsPane.getChildren().addAll(foodSlider , taxSlider , fearSlider);
    }

    public void openConstructorBuildingsMenu(MouseEvent ignoredMouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/ConstructorBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);

        GameGraphicController.setPopularityGoldPopulation();
    }

    public void openFoodBuildingsMenu(MouseEvent ignoredMouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/FoodBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);

        GameGraphicController.setPopularityGoldPopulation();
    }

    public void openVillageBuildingsMenu(MouseEvent ignoredMouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/VillageBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);

        GameGraphicController.setPopularityGoldPopulation();
    }

    public void openKitchenBuildingsMenu(MouseEvent ignoredMouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/KitchenBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);

        GameGraphicController.setPopularityGoldPopulation();
    }

    public void openDefenceBuildingsMenu(MouseEvent ignoredMouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/DefenceBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);

        GameGraphicController.setPopularityGoldPopulation();
    }

    public void openWallBuildingsMenu(MouseEvent ignoredMouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/BottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);

        GameGraphicController.setPopularityGoldPopulation();
    }

    public void openTowersBottomMenu(MouseEvent ignoredMouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/towersBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);

        GameGraphicController.setPopularityGoldPopulation();
    }
    public void openDefenciveBuildingsBottomMenu(MouseEvent ignoredMouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/defenciveBuildingsBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);

        GameGraphicController.setPopularityGoldPopulation();
    }

    public void openGatesBottomMenu(MouseEvent ignoredMouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(Game.bottomPane);
        AnchorPane anchorPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/gatesBottomMenu.fxml").toExternalForm()));
        anchorPane.setLayoutX(Game.leftX);
        anchorPane.setLayoutY(Game.screenHeight - 60);
        Game.bottomPane = anchorPane;
        Game.mainPane.getChildren().add(Game.bottomPane);

        GameGraphicController.setPopularityGoldPopulation();
    }
    

    public void stairDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "Stair";
    }

    public void smallWallDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "SmallWall";
    }

    public void wallDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "Wall";
    }

    public void strongWallDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "StrongWall";
    }

    public void barackDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.barrackBuildingToCreateName = "Barrack";
    }

    public void mercenaryPostDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.barrackBuildingToCreateName = "MercenaryPost";
    }

    public void stockPileDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.stockPileBuildingToCreateName = "Stockpile";
    }

    public void woodCutterDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "WoodCutter";
    }

    public void stoneMineDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "Quarry";
    }

    public void cowDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "StoneCow";
    }

    public void ironMineDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "IronMine";
    }

    public void pitchRigDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "PitchRig";
    }

    public void shopDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.townBuildingToCreateName = "Shop";
    }

    public void armouryDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.stockPileBuildingToCreateName = "Armoury";
    }

    public void fletcherDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "Fletcher";
    }

    public void armourerDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "Armourer";
    }

    public void poleTurnerDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "PoleTurner";
    }

    public void blackSmithDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "BlackSmith";
    }

    public void dairyFarmDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "DairyFarm";
    }

    public void millDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "Mill";
    }

    public void bakeryDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "Bakery";
    }

    public void breweryDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "Brewery";
    }

    public void granaryDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.stockPileBuildingToCreateName = "Granary";
    }

    public void innDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "Inn";
    }

    public void cheeseDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "DairyFarm";
    }

    public void appleFarmDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "Orchard";
    }

    public void wheatFarmDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "WheatFarm";
    }

    public void hopsFarmDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "HopsFarm";
    }

    public void homeDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.townBuildingToCreateName = "Home";
    }

    public void churchDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.townBuildingToCreateName = "Church";
    }

    public void cathedralDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.barrackBuildingToCreateName = "Cathedral";
    }

    public void wellDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
    }

    public void lookOutTowerDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "LookOutTower";
    }

    public void perimeterTowerDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "PerimeterTower";
    }

    public void defenciveTowerDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "DefenciveTower";
    }

    public void squareTowerDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "SquareTower";
    }

    public void circularTowerDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "CircularTower";
    }

    public void smallStoneGateDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "SmallStoneGate";
    }

    public void bigStoneGateDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "BigStoneGate";
    }

    public void drawBridgeDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "DrawBridge";
    }

    public void trapDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.defenceBuildingToCreateName = "Trap";
    }

    public void ditchDragDetected(MouseEvent ignoredMouseEvent) {
        //todo
    }

    public void fillDitchDragDetected(MouseEvent ignoredMouseEvent) {
        //todo
    }

    public void stableDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "Stable";
    }

    public void engineerGuildDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.barrackBuildingToCreateName = "EngineerGuild";
    }

    public void oilSmelterDragDetected(MouseEvent ignoredMouseEvent) {
        Game.building = Building.getBuildingByName("Stable");
        Game.generatorBuildingToCreateName = "OilSmelter";
    }

    public void setCopiedBuildingName(String Name){
        
    }

    public void exitPopularityFactorsMenu(MouseEvent mouseEvent) {
        Game.mainPane.getChildren().remove(popularityFactorsPane);
    }
}
