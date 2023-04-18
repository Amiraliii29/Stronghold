package controller;

import model.User;
import view.TradeMenuMessages;

import java.util.HashMap;

public class TradeMenuController {
    private static HashMap<User,String> requset;
     //ideal trade request string: <resource under trade> <amount> <want to give or recieve> "<message written>";
    private static User currentUser;

    static {
        requset = new HashMap<>();
    }


    public static HashMap<User, String> getRequest() {
        return requset;
    }

    public static TradeMenuMessages showTradesAvailbale(){
        return null;
    } 

    public static TradeMenuMessages showTradeHistory(){
        return null;
    }

    public static String getMessageBodyByRequest(){
        return null;
        //ToDo
    }

    public static void sendTradeRequest(User receivingPlayer,int amount ,String tradeType,String message){

    }

    public static void acceptTradeByRequest(String request){

    }

    public static void rejectTradeByrequest(String request){

    }

   
}
