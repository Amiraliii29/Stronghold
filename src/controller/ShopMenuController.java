package controller;

import java.util.ArrayList;
import java.util.HashMap;

import model.Resource;
import model.User;

public class ShopMenuController {
    
    private static User targetUser;
    private static ArrayList <Resource> items;

    private static HashMap<String, Integer> itemsBuyingPrice;
    private static HashMap<String, Integer> itemsSellingPrice;

    static{
        items= new ArrayList<Resource>();
        itemsBuyingPrice=new HashMap<String, Integer>();
        itemsSellingPrice= new HashMap<String, Integer>();

        fillShopItems();
    }

    private static void fillShopItems(){

    };

    private static void setNewPrices(){

    };


    public static String showShopInfo(){

    };

    public static void buyItemByName(String name, int amount){

    };

    public static void sellItemByName(String name, int amount){

    };

    public static void setTargetUser(User user){
        ShopMenuController.targetUser=user;
    }


}
