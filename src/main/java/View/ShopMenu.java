package View;

import Controller.ShopMenuController;
import View.Enums.Commands.ShopMenuCommands;
import View.Enums.Messages.ShopMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {

    public static void run(Scanner scanner){
        String input;
        Matcher matcher;
        while (true) {
            input = scanner.nextLine();
            if (ShopMenuCommands.getMatcher(input, ShopMenuCommands.SHOW_PRICE_LIST) != null)
                showItems();
            else if((matcher = ShopMenuCommands.getMatcher(input , ShopMenuCommands.BUY_ITEM)) != null)
                buyItemByName(matcher , scanner);
            else if((matcher = ShopMenuCommands.getMatcher(input , ShopMenuCommands.SELL_ITEM)) != null)
                sellItemByName(matcher , scanner);
        }
    };


    private static void showItems(){
        String toPrint = ShopMenuController.showItemsController();
        System.out.print(toPrint);
    }

    private static void buyItemByName(Matcher matcher , Scanner scanner){
        String itemName = matcher.group("name");
        int amount = Integer.parseInt(matcher.group("amount"));
        ShopMenuMessages message = ShopMenuController.buyItemByNameController(itemName , amount , scanner);

        switch (message){
            case INVALID_ITEM_NAME:
                System.out.println("buy item error: invalid item name");
                break;
            case NOT_ENOUGH_BALANCE:
                System.out.println("buy item error: not enough gold :|");
                break;
            case NOT_ENOUGH_FREE_SPACE_IN_WARE_HOUSE:
                System.out.println("buy item error: not enough free space in wareHouse you bought just " + amount + " of " + itemName);
                break;
            case INVALID_AMOUNT:
                System.out.println("buy item error: invalid amount");
            case BUY_ITEM_NOT_CONFIRMED:
                System.out.println("buy item Canceled successfully");
                break;
            case BUY_ITEM_SUCCESS:
                System.out.println("item bought successfully");
                break;
        }
    }

    private static void sellItemByName(Matcher matcher , Scanner scanner){
        String itemName = matcher.group("name");
        int amount = Integer.parseInt(matcher.group("amount"));
        ShopMenuMessages message = ShopMenuController.sellItemByNameController(itemName , amount , scanner);

        switch (message){
            case INVALID_ITEM_NAME:
                System.out.println("sell item error: invalid item name");
                break;
            case NOT_ENOUGH_ITEM_IN_STOCKPILE:
                System.out.println("sell item error: not enough item in stockPile you sold just " + amount + " of " + itemName);
                break;
            case INVALID_AMOUNT:
                System.out.println("sell item error: invalid amount");
                break;
            case SELL_ITEM_NOT_CONFIRMED:
                System.out.println("sell item canceled successfully");
                break;
            case SELL_ITEM_SUCCESS:
                System.out.println("item sold successfully");
                break;
        }
    }
    public static String confirmSellOrBuy(Scanner scanner , String operationType , String name , int amount){
        System.out.println("Do you confirm to " + operationType + " " + name + " for the amount: " + amount + " (please enter YES or NO)");
        String confirm;
        confirm = scanner.nextLine();
        return confirm;
    }




}