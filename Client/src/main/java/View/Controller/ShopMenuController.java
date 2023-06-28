package View.Controller;

import Controller.GameMenuController;
import Controller.Orders;
import Model.DataBase;
import Model.Resource;
import Model.User;
import View.Enums.Messages.ShopMenuMessages;
import View.Enums.Messages.TradeMenuMessages;
import View.Game;
import View.ShopMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class ShopMenuController {

    private static User user;
    private static ArrayList<Resource> items;

    private static HashMap<Resource, Integer> itemsBuyingPrice;
    private static HashMap<Resource, Integer> itemsSellingPrice;
    private static Resource selectedItem;
    public static AnchorPane tradePane = new AnchorPane();


    static {
        items = new ArrayList<Resource>();
        itemsBuyingPrice = new HashMap<>();
        itemsSellingPrice = new HashMap<>();
    }

    public Label meatAmount = new Label();
    public Label cheeseAmount = new Label();
    public Label appleAmount = new Label();
    public Label hopsAmount = new Label();
    public Label aleAmount = new Label();
    public Label wheatAmount = new Label();
    public Label flourAmount = new Label();
    public  Label breadAmount = new Label();
    public Label swordAmount  = new Label();
    public Label spearAmount = new Label();
    public Label pikeAmount = new Label();
    public Label bowAmount = new Label();
    public Label maceAmount = new Label();
    public  Label crossBowAmount = new Label();
    public  Label metalArmorAmount = new Label();
    public  Label leatherArmorAmount = new Label();
    public  Label pitchAmount = new Label();
    public  Label ironAmount = new Label();
    public  Label woodAmount = new Label();
    public  Label stoneAmount = new Label();
    public Label selectedItemName;

    public void setItemsAmount(){
        
        meatAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Meat"))));
        cheeseAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Cheese"))));
        appleAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Apples"))));
        hopsAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Hops"))));
        aleAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Ale"))));
        wheatAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Wheat"))));
        flourAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Flour"))));
        breadAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Bread"))));
        swordAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Sword"))));
        spearAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Spear"))));
        pikeAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Pike"))));
        bowAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Bow"))));
        maceAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Mace"))));
        crossBowAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("CrossBow"))));
        ironAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Iron"))));
        woodAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Wood"))));
        stoneAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Stone"))));
        metalArmorAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("MetalArmour"))));
        leatherArmorAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("LeatherArmour"))));
        pitchAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Pitch"))));

    }

    public static void addItem(Resource resource) {
        items.add(resource);
    }




    public static ShopMenuMessages buyItemByNameController(String name) {
        if(name == null)
            return ShopMenuMessages.NO_ITEM_SELECTED;
        Resource itemToBuy = null;
        for (Resource item : items) {
            if(item.getName().equals(name)) {
                itemToBuy = item;
                break;
            }
        }
        int amountInt = 1;
        if(DataBase.getCurrentGovernment().getMoney() < itemToBuy.getBuyPrice() * amountInt)
            return ShopMenuMessages.NOT_ENOUGH_BALANCE;
        else if(DataBase.getCurrentGovernment().freeStockpileSpace(itemToBuy) < amountInt)
            return ShopMenuMessages.NOT_ENOUGH_FREE_SPACE_IN_WARE_HOUSE;
        else{
            DataBase.getCurrentGovernment().changeMoney(-1 * amountInt * itemToBuy.getBuyPrice());
            DataBase.getCurrentGovernment().addToStockpile(itemToBuy , amountInt);
            return ShopMenuMessages.BUY_ITEM_SUCCESS;
        }

    }

    public static ShopMenuMessages sellItemByNameController(String name) {
        if(name == null)
            return ShopMenuMessages.NO_ITEM_SELECTED;
        Resource itemToSell = null;
        for (Resource item : items) {
            if(item.getName().equals(name)) {
                itemToSell = item;
                break;
            }
        }

        int amountInt = 1;

        if(DataBase.getCurrentGovernment().getResourceInStockpiles(itemToSell) < amountInt)
            return ShopMenuMessages.NOT_ENOUGH_ITEM_IN_STOCKPILE;
        else{
            DataBase.getCurrentGovernment().removeFromStockpile(itemToSell , amountInt);
            DataBase.getCurrentGovernment().changeMoney(itemToSell.getSellPrice() * amountInt);
            return ShopMenuMessages.SELL_ITEM_SUCCESS;
        }


    }

    public static void setTargetUser(User user) {
        ShopMenuController.user = user;
    }

    public void buy(MouseEvent mouseEvent) {
        ShopMenuMessages message = buyItemByNameController(selectedItem.getName());
        switch (message){
            case NO_ITEM_SELECTED -> {
                GameGraphicController.popUpAlert("Buy item Error" , "" ,
                        "NO item selected!" , Orders.redNotifErrorColor);
            }
            case NOT_ENOUGH_BALANCE -> {
                GameGraphicController.popUpAlert("Buy item Error" , "" ,
                        "Not enough balance :|" , Orders.redNotifErrorColor);
            }
            case NOT_ENOUGH_FREE_SPACE_IN_WARE_HOUSE -> {
                GameGraphicController.popUpAlert("Buy item Error" , "" ,
                        "Not enough free space in wareHouse :|" , Orders.redNotifErrorColor);
            }
            case BUY_ITEM_SUCCESS -> {
//                GameGraphicController.popUpAlert("Success" , "" ,
//                        "Item bought successfully" , Orders.greenNotifSuccesColor);
            }
        }
        setItemsAmount();
        GameGraphicController.setPopularityGoldPopulation();
    }

    public void sell(MouseEvent mouseEvent) {
        ShopMenuMessages message = sellItemByNameController(selectedItem.getName());

        switch (message){
            case NO_ITEM_SELECTED -> {
                GameGraphicController.popUpAlert("Sell item Error" , "" ,
                        "NO item selected!" , Orders.redNotifErrorColor);
            }
            case NOT_ENOUGH_ITEM_IN_STOCKPILE -> {
                GameGraphicController.popUpAlert("Sell item Error" , "" ,
                        "There is no " + selectedItem.getName() + " in warehouse" , Orders.redNotifErrorColor);
            }
            case SELL_ITEM_SUCCESS -> {
//                GameGraphicController.popUpAlert("Success" , "" ,
//                        "Item Sold Successfully :)" , Orders.greenNotifSuccesColor);
            }
        }
        setItemsAmount();
        GameGraphicController.setPopularityGoldPopulation();
    }
    
        public void selectApples (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Apples");
            selectedItemName.setText("Apples");
        }

        public void selectWheat (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Wheat");
            selectedItemName.setText("Wheat");
        }

        public void selectAle (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Ale");
            selectedItemName.setText("Ale");
        }

        public void selectHops (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Hops");
            selectedItemName.setText("Hops");
        }

        public void selectFlour (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Flour");
            selectedItemName.setText("Flour");
        }

        public void selectBread (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Bread");
            selectedItemName.setText("Bread");
        }

        public void selectCheese (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Cheese");
            selectedItemName.setText("Cheese");
        }

        public void selectMeat (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Meat");
            selectedItemName.setText("Meat");
        }

        public void selectSword (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Sword");
            selectedItemName.setText("Sword");
        }

        public void selectSpear (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Spear");
            selectedItemName.setText("Spear");
        }

        public void selectPike (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Pike");
            selectedItemName.setText("Pike");
        }

        public void selectMace (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Mace");
            selectedItemName.setText("Mace");
        }

        public void selectBow (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Bow");
            selectedItemName.setText("Bow");
        }

        public void selectCrossBow (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("CrossBow");
            selectedItemName.setText("CrossBow");
        }

        public void selectMetalArmor (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("MetalArmour");
            selectedItemName.setText("MetalArmour");
        }

        public void selectIron (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Iron");
            selectedItemName.setText("Iron");
        }

        public void selectStone (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Stone");
            selectedItemName.setText("Stone");
        }

        public void selectWood (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Wood");
            selectedItemName.setText("Wood");
        }

        public void selectPitch (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("Pitch");
            selectedItemName.setText("Pitch");
        }
        public void selectLeatherArmor (MouseEvent mouseEvent){
            setItemsAmount();
            selectedItem = Resource.getResourceByName("LeatherArmour");
            selectedItemName.setText("LeatherArmor");
        }
        public void openTradeMenu() throws IOException {
            Game.mainPane.getChildren().remove(ShopMenu.shopPane);
            Game.mainPane.getChildren().remove(TradeMenuController.tradeMenuHistoryPane);
            Game.mainPane.getChildren().remove(TradeMenuController.tradeNewRequestPane);
            Game.mainPane.getChildren().remove(tradePane);

            tradePane = FXMLLoader.load(
                    new URL(ShopMenu.class.getResource("/fxml/TradeMenu.fxml").toExternalForm()));
            tradePane.setLayoutX(Game.leftX);
            tradePane.setLayoutY(0);

            Game.mainPane.getChildren().remove(ShopMenu.shopPane);
            Game.mainPane.getChildren().add(tradePane);
        }

    public void exitShopMenu(MouseEvent mouseEvent) {
        Game.mainPane.getChildren().remove(ShopMenu.shopPane);
        Game.mainPane.getChildren().remove(TradeMenuController.tradeMenuHistoryPane);
        Game.mainPane.getChildren().remove(TradeMenuController.tradeNewRequestPane);
        Game.mainPane.getChildren().remove(tradePane);
    }
}