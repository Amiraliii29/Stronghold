package Controller;

import Model.Resource;
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

    private static void setNewPrices() {

    }


    public static void buyItemByName(String name, int amount) {

    }

    public static void sellItemByName(String name, int amount) {

    }

    public static void setTargetUser(User user) {
        ShopMenuController.user = user;
    }
}
