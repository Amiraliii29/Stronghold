package Controller;

import Model.User;
import View.Enums.Messages.TradeMenuMessages;

import java.util.HashMap;

public class TradeMenuController {
    private static HashMap<User,String> requests;
     //ideal trade request string: <resource under trade> <amount> <want to give or recieve> "<message written>";
    private static User currentUser;

    static {
        requests = new HashMap<>();
    }


    public static HashMap<User, String> getRequests() {
        return requests;
    }

    public static TradeMenuMessages showTradesAvailable(){
        return null;
    } 

    public static TradeMenuMessages showTradeHistory(){
        return null;
    }

    public static String getMessageBodyByRequest(){
        return null;
        //ToDo
    }

    public static void sendTradeRequest(String resourceName , String  amount , String price , String message  , String governmentName){
        if(resourceName == null || amount == null || price == null
                || price == null || message == null || governmentName == null)
            return;
    }

    public static void acceptTradeByRequest(String request){

    }

    public static void rejectTradeByRequest(String request){

    }

   
}
