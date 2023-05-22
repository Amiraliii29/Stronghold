package View;

import Controller.Orders;
import Controller.ShopMenuController;
import View.Enums.Commands.ShopMenuCommands;
import View.Enums.Messages.ShopMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {

    public static void run(){
        String input;
        Matcher matcher;
        Input_Output.outPut("SHOP MENU: ");
        while (true) {
            input = Input_Output.getInput();
            if (ShopMenuCommands.getMatcher(input, ShopMenuCommands.SHOW_PRICE_LIST) != null)
                showItems();
            else if((matcher = ShopMenuCommands.getMatcher(input , ShopMenuCommands.BUY_ITEM)) != null)
                buyItemByName(matcher);
            else if((matcher = ShopMenuCommands.getMatcher(input , ShopMenuCommands.SELL_ITEM)) != null)
                sellItemByName(matcher);
            else if(ShopMenuCommands.getMatcher(input , ShopMenuCommands.EXIT) != null){
                Input_Output.outPut("returned back to game menu");
                return;
            }
            else
                Input_Output.outPut("invalid command");

        }
    };


    private static void showItems(){
        String toPrint = ShopMenuController.showItemsController();
        System.out.print(toPrint);
    }

    private static void buyItemByName(Matcher matcher){
        String options = matcher.group("options");
        String itemName = Orders.findFlagOption("-i" , options);
        String amount = Orders.findFlagOption("-a" , options);

        if(Orders.isOrderJunky(options , false , "-i" , "-a")){
            Input_Output.outPut("unmatching inputs for this function!");
            return;
        }

        ShopMenuMessages message = ShopMenuController.buyItemByNameController(itemName , amount);

        switch (message){
            case INVALID_ITEM_NAME:
                System.out.println("buy item error: invalid item name");
                break;
            case NOT_ENOUGH_BALANCE:
                System.out.println("buy item error: not enough gold :|");
                break;
            case NOT_ENOUGH_FREE_SPACE_IN_WARE_HOUSE:
                System.out.println("buy item error: not enough free space in wareHouse");
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

    private static void sellItemByName(Matcher matcher){
        String options = matcher.group("options");
        String itemName = Orders.findFlagOption("-i" , options);
        String amount = Orders.findFlagOption("-a" , options);

        if(Orders.isOrderJunky(options , false , "-i" , "-a")){
            Input_Output.outPut("unmatching inputs for this function!");
            return;
        }

        ShopMenuMessages message = ShopMenuController.sellItemByNameController(itemName , amount);

        switch (message){
            case INVALID_ITEM_NAME:
                System.out.println("sell item error: invalid item name");
                break;
            case NO_AMOUNT:
                System.out.println("sell item error : please enter amount");
                break;
            case NOT_ENOUGH_ITEM_IN_STOCKPILE:
                System.out.println("sell item error: not enough item in stockPile");
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
    public static String confirmSellOrBuy( String operationType , String name , int amount){
        System.out.println("Do you confirm to " + operationType + " " + name + " for the amount: " + amount + " (please enter YES or NO)");
        String confirm;
        confirm = Input_Output.getInput();

        return confirm;

    }




}
