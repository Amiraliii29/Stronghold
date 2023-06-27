package View.Controller;

import Model.DataBase;
import Model.Resource;
import Model.User;
import View.Enums.Messages.ShopMenuMessages;
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

    public static Label meatAmount = new Label();
    public static Label cheeseAmount = new Label();
    public static Label appleAmount = new Label();
    public static Label hopsAmount = new Label();
    public static Label aleAmount = new Label();
    public static Label wheatAmount = new Label();
    public static Label flourAmount = new Label();
    public static Label breadAmount = new Label();
    public static Label swordAmount  = new Label();
    public static Label spearAmount = new Label();
    public static Label pikeAmount = new Label();
    public static Label bowAmount = new Label();
    public static Label maceAmount = new Label();
    public static Label crossBowAmount = new Label();
    public static Label metalArmorAmount = new Label();
    public static Label leatherArmorAmount = new Label();
    public static Label pitchAmount = new Label();
    public static Label ironAmount = new Label();
    public static Label woodAmount = new Label();
    public static Label stoneAmount = new Label();
    public Label selectedItemName;

    public static void setItemsAmount(){
        
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
        metalArmorAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("MetalArmor"))));
        leatherArmorAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("LeatherArmor"))));
        pitchAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Pitch"))));
        ironAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Iron"))));
        woodAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Wood"))));
        stoneAmount.setText(Integer.toString(DataBase.getCurrentGovernment().getResourceInStockpiles(Resource.getResourceByName("Stone"))));

    }

    public static void addItem(Resource resource) {
        items.add(resource);
    }

    public static void setNewPrices() {

    }

    public static String showItemsController(){
        String toReturn = "";
        int itemsCounter = 1;
        for (Resource item : items) {
            toReturn += itemsCounter + ". " + item.getName() + " = " + item.getBuyPrice()  + " (gold)\n";
            itemsCounter++;
        }
        return toReturn;
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
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("buy item error");
        switch (message){
            case NO_ITEM_SELECTED -> {
                error.setContentText("NO item selected!");
                error.showAndWait();
            }
            case NOT_ENOUGH_BALANCE -> {
                error.setContentText("Not enough balance :|");
                error.showAndWait();
            }
            case NOT_ENOUGH_FREE_SPACE_IN_WARE_HOUSE -> {
                error.setContentText("Not enough free space in wareHouse :|");
                error.showAndWait();
            }
            case BUY_ITEM_SUCCESS -> {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText("Success");
                success.setContentText("Item bought successfully");
                success.showAndWait();
            }
        }
    }

    public void sell(MouseEvent mouseEvent) {
        ShopMenuMessages message = sellItemByNameController(selectedItem.getName());
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Sell item error");

        switch (message){
            case NO_ITEM_SELECTED -> {
                error.setContentText("NO item selected!");
                error.showAndWait();
            }
            case NOT_ENOUGH_ITEM_IN_STOCKPILE -> {
                error.setContentText("There is no " + selectedItem.getName() + " in warehouse");
                error.showAndWait();
            }
            case SELL_ITEM_SUCCESS -> {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText("Success");
                success.setContentText("Item Sold Successfully :)");
                success.showAndWait();
            }
        }
    }
    
        public void selectApples (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Apples");
            selectedItemName.setText("Apples");
        }

        public void selectWheat (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Wheat");
            selectedItemName.setText("Wheat");
        }

        public void selectAle (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Ale");
            selectedItemName.setText("Ale");
        }

        public void selectHops (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Hops");
            selectedItemName.setText("Hops");
        }

        public void selectFlour (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Flour");
            selectedItemName.setText("Flour");
        }

        public void selectBread (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Bread");
            selectedItemName.setText("Bread");
        }

        public void selectCheese (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Cheese");
            selectedItemName.setText("Cheese");
        }

        public void selectMeat (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Meat");
            selectedItemName.setText("Meat");
        }

        public void selectSword (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Sword");
            selectedItemName.setText("Sword");
        }

        public void selectSpear (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Spear");
            selectedItemName.setText("Spear");
        }

        public void selectPike (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Pike");
            selectedItemName.setText("Pike");
        }

        public void selectMace (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Mace");
            selectedItemName.setText("Mace");
        }

        public void selectBow (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Bow");
            selectedItemName.setText("Bow");
        }

        public void selectCrossBow (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("CrossBow");
            selectedItemName.setText("CrossBow");
        }

        public void selectMetalArmor (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("MetalArmor");
            selectedItemName.setText("MetalArmour");
        }

        public void selectIron (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Iron");
            selectedItemName.setText("Iron");
        }

        public void selectStone (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Stone");
            selectedItemName.setText("Stone");
        }

        public void selectWood (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Wood");
            selectedItemName.setText("Wood");
        }

        public void selectPitch (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("Pitch");
            selectedItemName.setText("Pitch");
        }
        public void selectLeatherArmor (MouseEvent mouseEvent){
            selectedItem = Resource.getResourceByName("LeatherArmor");
            selectedItemName.setText("LeatherArmor");
        }
        public void openTradeMenu() throws IOException {
            tradePane = FXMLLoader.load(
                    new URL(ShopMenu.class.getResource("/fxml/TradeMenu.fxml").toExternalForm()));
            tradePane.setLayoutX(Game.leftX);
            tradePane.setLayoutY(0);

            Game.mainPane.getChildren().remove(ShopMenu.shopPane);
            Game.mainPane.getChildren().add(tradePane);
        }

    public void exitShopMenu(MouseEvent mouseEvent) {
        Game.mainPane.getChildren().remove(ShopMenu.shopPane);
    }
}