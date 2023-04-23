package Controller;

import Model.DataBase;
import Model.Resources.Resource;
import Model.User;
import View.Enums.Messages.ShopMenuMessages;

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
            toReturn += itemsCounter + ". " + item.getName() + " = " + item.getBuyPrice()  + " (gold)\n";
        }
        return toReturn;
    }


    public static ShopMenuMessages buyItemByName(String name, int amount , Scanner scanner) {
        Resource itemToBuy = null;
        for (Resource item : items) {
            if(item.getName().equals(name))
                itemToBuy = item;
        }

        if(itemToBuy == null)
            return ShopMenuMessages.INVALID_ITEM_NAME;
        else if(DataBase.getCurrentGovernment().getMoney() < itemToBuy.getBuyPrice() * amount)
            return ShopMenuMessages.NOT_ENOUGH_BALANCE;
        else if(DataBase.getCurrentGovernment().freeStockpileSpace(itemToBuy) < amount)
            return ShopMenuMessages.NOT_ENOUGH_FREE_SPACE_IN_WARE_HOUSE;
        else if(amount <= 0)
            return ShopMenuMessages.INVALID_AMOUNT;
        else{
            while (true) {
                System.out.println("Do you confirm to buy " + name + " for the amount: " + amount + " (please enter YES or NO)");
                String confirmation;
                confirmation = scanner.nextLine();
                if(confirmation.toUpperCase().equals("YES")){
                    DataBase.getCurrentGovernment().changeMoney(-1 * amount * itemToBuy.getBuyPrice());
                    DataBase.getCurrentGovernment().addToStockpile(itemToBuy , amount);
                    return ShopMenuMessages.BUY_ITEM_SUCCESS;
                }
                else if(confirmation.toUpperCase().equals("NO")){
                    return ShopMenuMessages.BUY_ITEM_NOT_CONFIRMED;
                }
                else{
                    System.out.println("invalid input (please enter YES or NO)");
                }
            }
        }

    }

    public static ShopMenuMessages sellItemByName(String name, int amount , Scanner scanner) {

    }

    public static void setTargetUser(User user) {
        ShopMenuController.user = user;
    }
}
