package Controller;

import Model.DataBase;
import Model.Government;
import Model.Resource;
import Model.User;
import View.Enums.Messages.ShopMenuMessages;
import View.ShopMenu;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class ShopMenuController {
    private static User user;
    private static ArrayList<Resource> items;
    private static HashMap<Resource, Integer> itemsBuyingPrice;
    private static HashMap<Resource, Integer> itemsSellingPrice;
    private static Resource selectedItem;


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

    public static void setItemsAmount(){
        DataBase.setCurrentGovernment(new Government(2000));
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

    public static ShopMenuMessages buyItemByNameController(String name, String amount) {
        Resource itemToBuy = null;
        for (Resource item : items) {
            if(item.getName().equals(name)) {
                itemToBuy = item;
                break;
            }
        }
        if(amount == null)
            return ShopMenuMessages.NO_AMOUNT;
        int amountInt = Integer.parseInt(amount);
        if(itemToBuy == null)
            return ShopMenuMessages.INVALID_ITEM_NAME;
        else if(amountInt <= 0)
            return ShopMenuMessages.INVALID_AMOUNT;
        else if(DataBase.getCurrentGovernment().getMoney() < itemToBuy.getBuyPrice() * amountInt)
            return ShopMenuMessages.NOT_ENOUGH_BALANCE;
        else if(DataBase.getCurrentGovernment().freeStockpileSpace(itemToBuy) < amountInt)
            return ShopMenuMessages.NOT_ENOUGH_FREE_SPACE_IN_WARE_HOUSE;
        else{
            while (true) {
                String confirmation = ShopMenu.confirmSellOrBuy ("buy" , name , amountInt);

                if(confirmation.toUpperCase().equals("YES")){
                    DataBase.getCurrentGovernment().changeMoney(-1 * amountInt * itemToBuy.getBuyPrice());
                    DataBase.getCurrentGovernment().addToStockpile(itemToBuy , amountInt);
                    return ShopMenuMessages.BUY_ITEM_SUCCESS;
                }
                else if(confirmation.toUpperCase().equals("NO")){
                    return ShopMenuMessages.BUY_ITEM_NOT_CONFIRMED;
                }
            }
        }

    }

    public static ShopMenuMessages sellItemByNameController(String name, String amount) {
        Resource itemToSell = null;
        for (Resource item : items) {
            if(item.getName().equals(name)) {
                itemToSell = item;
                break;
            }
        }
        if(amount == null)
            return ShopMenuMessages.NO_AMOUNT;
        int amountInt = Integer.parseInt(amount);
        if(itemToSell == null)
            return ShopMenuMessages.INVALID_ITEM_NAME;
        else if(amountInt <= 0)
            return ShopMenuMessages.INVALID_AMOUNT;
        else if(DataBase.getCurrentGovernment().getResourceInStockpiles(itemToSell) < amountInt)
            return ShopMenuMessages.NOT_ENOUGH_ITEM_IN_STOCKPILE;
        else{
            while (true) {
                String confirmation = ShopMenu.confirmSellOrBuy("sell", name, amountInt);

                if (confirmation.toUpperCase().equals("YES")) {
                    DataBase.getCurrentGovernment().removeFromStockpile(itemToSell , amountInt);
                    DataBase.getCurrentGovernment().changeMoney(itemToSell.getSellPrice() * amountInt);
                    return ShopMenuMessages.SELL_ITEM_SUCCESS;
                }
                else if (confirmation.toUpperCase().equals("NO")) {
                    return ShopMenuMessages.SELL_ITEM_NOT_CONFIRMED;
                }
            }

        }


    }

    public static void setTargetUser(User user) {
        ShopMenuController.user = user;
    }
}