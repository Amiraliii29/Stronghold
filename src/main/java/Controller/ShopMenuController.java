package Controller;

import Model.DataBase;
import Model.Resource;
import Model.User;
import View.Enums.Messages.ShopMenuMessages;
import View.ShopMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class ShopMenuController {

    private static User user;
    private static ArrayList<Resource> items;

    private static HashMap<Resource, Integer> itemsBuyingPrice;
    private static HashMap<Resource, Integer> itemsSellingPrice;

    static {
        items = new ArrayList<Resource>();
        itemsBuyingPrice = new HashMap<>();
        itemsSellingPrice = new HashMap<>();
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


    public static ShopMenuMessages buyItemByNameController(String name, int amount) {
        Resource itemToBuy = null;
        for (Resource item : items) {
            if(item.getName().equals(name)) {
                itemToBuy = item;
                break;
            }
        }

        if(itemToBuy == null)
            return ShopMenuMessages.INVALID_ITEM_NAME;
        else if(amount <= 0)
            return ShopMenuMessages.INVALID_AMOUNT;
        else if(DataBase.getCurrentGovernment().getMoney() < itemToBuy.getBuyPrice() * amount)
            return ShopMenuMessages.NOT_ENOUGH_BALANCE;
        else if(DataBase.getCurrentGovernment().freeStockpileSpace(itemToBuy) < amount)
            return ShopMenuMessages.NOT_ENOUGH_FREE_SPACE_IN_WARE_HOUSE;
        else{
            while (true) {
                String confirmation = ShopMenu.confirmSellOrBuy ("buy" , name , amount);

                if(confirmation.toUpperCase().equals("YES")){
                    DataBase.getCurrentGovernment().changeMoney(-1 * amount * itemToBuy.getBuyPrice());
                    DataBase.getCurrentGovernment().addToStockpile(itemToBuy , amount);
                    return ShopMenuMessages.BUY_ITEM_SUCCESS;
                }
                else if(confirmation.toUpperCase().equals("NO")){
                    return ShopMenuMessages.BUY_ITEM_NOT_CONFIRMED;
                }
            }
        }

    }

    public static ShopMenuMessages sellItemByNameController(String name, int amount) {
            Resource itemToSell = null;
        for (Resource item : items) {
            if(item.getName().equals(name)) {
                itemToSell = item;
                break;
            }
        }

        if(itemToSell == null)
            return ShopMenuMessages.INVALID_ITEM_NAME;
        else if(amount <= 0)
            return ShopMenuMessages.INVALID_AMOUNT;
        else if(DataBase.getCurrentGovernment().getResourceInStockpiles(itemToSell) < amount)
            return ShopMenuMessages.NOT_ENOUGH_ITEM_IN_STOCKPILE;
        else{
            while (true) {
                String confirmation = ShopMenu.confirmSellOrBuy("sell", name, amount);

                if (confirmation.toUpperCase().equals("YES")) {
                    DataBase.getCurrentGovernment().removeFromStockpile(itemToSell , amount);
                    DataBase.getCurrentGovernment().changeMoney(itemToSell.getSellPrice() * amount);
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
