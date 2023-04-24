package View;

import Controller.Orders;
import Controller.TradeMenuController;
import View.Enums.Commands.TradeMenuCommands;
import View.Enums.Messages.TradeMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public static void run(Scanner scanner) {
        String input;
        Matcher matcher;

        while (true){
            input = Input_Output.getInput();

            if((matcher = TradeMenuCommands.getMatcher(input , TradeMenuCommands.TRADE_REQUEST)) != null)
                sendTradeRequest(matcher);
            else if(TradeMenuCommands.getMatcher(input , TradeMenuCommands.TRADE_LIST) != null)
                tradeList();
            else if((matcher = TradeMenuCommands.getMatcher(input , TradeMenuCommands.ACCEPT_TRADE)) != null)
                acceptTrade(matcher);
        }
    }

    private static void sendTradeRequest(Matcher matcher) {
        // enter user you want to trade with -u flag
        String options = matcher.group("options");
        String resourceName = Orders.findFlagOption("-t" , options);
        String amount = Orders.findFlagOption("-a" , options);
        String  price = Orders.findFlagOption("-p" , options);
        String message = Orders.findFlagOption("-m" , options);
        String governmentNameThatHasBeenAsked = Orders.findFlagOption("-u" , options);

        TradeMenuMessages menuMessages = TradeMenuController.sendTradeRequestController(resourceName , amount , price ,
                message , governmentNameThatHasBeenAsked);

        switch (menuMessages){
            case NOT_ENOUGH_OPTIONS:
                System.out.println("send trade request error: please enter all required options");
                break;
            case INVALID_RESOURCE_TYPE:
                System.out.println("send trade request error: invalid resource type");
                break;
            case INVALID_AMOUNT:
                System.out.println("send trade request error: invalid amount");
                break;
            case INVALID_PRICE:
                System.out.println("send trade request error: invalid price");
                break;
            case SEND_REQUEST_SUCCESS:
                System.out.println("trade request sent successfully");
                break;
        }

    }
    private static void acceptTrade(Matcher matcher){
        String options = matcher.group("options");
        String id = Orders.findFlagOption("-i" , options);
        String message = Orders.findFlagOption("-m" , options);

        TradeMenuMessages menuMessage = TradeMenuController.acceptTradeByRequest(id , message);

        switch (menuMessage){
            case INVALID_REQUEST_ID:
                System.out.println("accept request error: invalid request id");
                break;
            case NOT_ENOUGH_RESOURCE_IN_STOCKPILE:
                System.out.println("accept request error: you don't have enough amount of this resource");
                break;
            case NOT_ENOUGH_FREE_SPACE:
                System.out.println("accept request error: player that requested doesn't have enough free space");
                break;
            case NOT_ENOUGH_MONEY:
                System.out.println("accept request error: player that requested doesn't have enough money");
                break;
            case ACCEPT_TRADE_SUCCESS:
                System.out.println("trade request accepted successfully");
                break;
        }

    }

    private static void tradeList() {
        String toPrint = TradeMenuController.tradeListController();
        System.out.print(toPrint);
    }

    private static void tradeHistory() {
    }

    private static void showMessage() {

    }
}
