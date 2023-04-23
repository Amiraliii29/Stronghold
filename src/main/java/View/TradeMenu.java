package View;

import Controller.Orders;
import View.Enums.Commands.TradeMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public static void run(Scanner scanner) {
        String input;
        Matcher matcher;

        while (true){
            input = Input_Output.getInput();

            if((matcher = TradeMenuCommands.getMatcher(input , TradeMenuCommands.TRADE_REQUEST)) != null)
                trade(matcher);
        }
    }

    private static void trade(Matcher matcher) {
        // enter user you want to trade with -u flag
        String options = matcher.group("options");
        String ResourceName = Orders.findFlagOption("-t" , options);
        String amount = Orders.findFlagOption("-a" , options);
        String  price = Orders.findFlagOption("-p" , options);
        String message = Orders.findFlagOption("-m" , options);
        String governmentNameThatHasBeenAsked = Orders.findFlagOption("-u" , options);


    }
    private static void acceptTrade(Matcher matcher){
    }

    private static void tradeList() {

    }

    private static void tradeHistory() {
    }

    private static void showMessage() {

    }
}
