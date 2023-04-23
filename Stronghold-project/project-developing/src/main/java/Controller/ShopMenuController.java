package Controller;

import Model.User;

import java.util.ArrayList;
import java.util.HashMap;


public class ShopMenuController {

    private static User user;
    private static ArrayList<Resource> items;

    private static HashMap<Resource, Integer> itemsBuyingPrice;
    private static HashMap<Resource, Integer> itemsSellingPrice;

    static {
        items = new ArrayList<Resource>();
        itemsBuyingPrice = new HashMap<>();
        itemsSellingPrice = new HashMap<>();
        fillShopItems();
    }

    private static void fillShopItems() {

    }

    public static void setNewPrices() {

    }
    
    public static String showItems(){
        String toReturn = "";
        int itemsCounter = 1;
        for (Resource item : items) {
            toReturn += itemsCounter + ". " + item.getName() + " = " + item.getBuyingPrice()  + " (gold)\n";
        }
        return toReturn;
    }


    public static ShopMenuMessages buyItemByName(String name, int amount) {
    }

    public static ShopMenuMessages sellItemByName(String name, int amount) {

    }

    public static void setTargetUser(User user) {
        ShopMenuController.user = user;
    }
}
