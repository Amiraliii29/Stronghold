package View;

import Controller.Orders;
import Controller.TradeMenuController;
import View.Enums.Commands.GameMenuCommands;
import View.Enums.Commands.TradeMenuCommands;
import View.Enums.Messages.TradeMenuMessages;

import java.io.CharArrayReader;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public static void run() {
        String input;
        Matcher matcher;

        System.out.println("TRADE MENU:");

        showNotifications();

        while (true){
            input = Input_Output.getInput();

            if((matcher = TradeMenuCommands.getMatcher(input , TradeMenuCommands.TRADE_REQUEST)) != null)
                sendTradeRequest(matcher);
            else if(TradeMenuCommands.getMatcher(input , TradeMenuCommands.TRADE_LIST) != null)
                tradeList();
            else if((matcher = TradeMenuCommands.getMatcher(input , TradeMenuCommands.ACCEPT_TRADE)) != null)
                acceptTrade(matcher);
            else if(TradeMenuCommands.getMatcher(input , TradeMenuCommands.TRADE_HISTORY) != null )
                tradeHistory();
            else if((matcher = TradeMenuCommands.getMatcher(input , TradeMenuCommands.TRADE_REJECT)) != null)
                rejectTrade(matcher);
            else if((matcher = TradeMenuCommands.getMatcher(input , TradeMenuCommands.DONATE)) != null)
                donate(matcher);
            else if(TradeMenuCommands.getMatcher(input , TradeMenuCommands.EXIT) != null) {
                System.out.println("returned back to game menu");
                return;
            }
            else
                System.out.println("invalid command");
        }
    }

    private static void donate(Matcher matcher) {
        String options = matcher.group("options");
        String resourceName = Orders.findFlagOption("-t" , options);
        String amount = Orders.findFlagOption("-a" , options);
        String message = Orders.findFlagOption("-m" , options);
        String governmentNameThatHasBeenDonated = Orders.findFlagOption("-u" , options);

        if(Orders.isOrderJunky(options , false , "-t" , "-a" , "-m" , "-u")){
            Input_Output.outPut("unmatching inputs for this function!");
            return;
        }

        TradeMenuMessages controllerMessage = TradeMenuController.donateController(resourceName , amount , message ,
                governmentNameThatHasBeenDonated);

        switch (controllerMessage){
            case NOT_ENOUGH_OPTIONS -> Input_Output.outPut("donate error: please all required options");
            case INVALID_RESOURCE_TYPE -> Input_Output.outPut("donate error: invalid resource type");
            case INVALID_AMOUNT -> Input_Output.outPut("donate error: invalid amount");
            case NOT_ENOUGH_RESOURCE_IN_STOCKPILE -> Input_Output.outPut("donate error: not enough resource in stockpile");
            case INVALID_GOVERNMENT_NAME -> Input_Output.outPut("donate error: invalid government name");
            case DONATE_SUCCESS -> Input_Output.outPut("donate success");
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

        if(Orders.isOrderJunky(options , false , "-t" , "-a" , "-p" , "-m" , "-u")){
            Input_Output.outPut("unmatching inputs for this function!");
            return;
        }

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
            case INVALID_GOVERNMENT_NAME:
                System.out.println("send trade request error: invalid government name");
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

        if(Orders.isOrderJunky(options , false , "-i" , "-m")){
            Input_Output.outPut("unmatching inputs for this function!");
            return;
        }

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
        String toPrint;
        toPrint = TradeMenuController.showTradeHistory();

        System.out.print(toPrint);
    }

    private static void showNotifications(){
        String toPrint = TradeMenuController.showNotificationsController();

        System.out.print(toPrint);
    }
    private static void rejectTrade(Matcher matcher){
        String id = matcher.group("id");
        TradeMenuMessages message = TradeMenuController.rejectTradeByRequest(id);

        switch (message){
            case INVALID_REQUEST_ID:
                System.out.println("reject request error: invalid request id");
                break;
            case TRADE_REQUEST_REJECTED_SUCCESSFULLY:
                System.out.println("trade request rejected successfully");
                break;
        }
    }
}
