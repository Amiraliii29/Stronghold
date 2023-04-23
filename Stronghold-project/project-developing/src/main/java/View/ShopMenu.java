package View;

import java.util.Scanner;

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
        }
    };


    private static void showItems(){
        String toPrint = ShopMenuController.showItems();
        System.out.print(toPrint);
    }

    private static void buyItemByName(Matcher matcher , Scanner scanner){
        String itemName = matcher.group("name");
        int amount = Integer.parseInt(matcher.group("amount"));
        ShopMenuMessages message = ShopMenuController.buyItemByName(itemName , amount , scanner );

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

    private static void sellItemByName(String input){

    }




}
