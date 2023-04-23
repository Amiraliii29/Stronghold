package Controller;

import Model.DataBase;
import Model.Government;
import Model.Resources.Resource;
import Model.TradeRequest;
import Model.User;
import View.Enums.Messages.TradeMenuMessages;

import java.util.ArrayList;
import java.util.HashMap;

public class TradeMenuController {
    private static final ArrayList<TradeRequest> allRequests = new ArrayList<>();
     //ideal trade request string: <resource under trade> <amount> <want to give or recieve> "<message written>";


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

    public static TradeMenuMessages sendTradeRequestController(String resourceName , String  amount , String price
            , String message  , String governmentName){
        if(resourceName == null || amount == null || price == null
                || price == null || message == null || governmentName == null)
            return TradeMenuMessages.NOT_ENOUGH_OPTIONS;

        Resource resourceToTrade = null;

        for (Resource allResource : Resource.getAllResources()) {
            if(allResource.getName().equals(resourceName)) {
                resourceToTrade = allResource;
                break;
            }
        }
        int amountInt = Integer.parseInt(amount);
        int priceInt = Integer.parseInt(price);

        if(resourceToTrade == null)
            return TradeMenuMessages.INVALID_RESOURCE_TYPE;
        else if(amountInt <= 0)
            return TradeMenuMessages.INVALID_AMOUNT;
        else if(priceInt < 0)
            return TradeMenuMessages.INVALID_PRICE;
        else{
            Government governmentAskedFrom = DataBase.getGovernmentByUserName(governmentName);
            TradeRequest tradeRequest = new TradeRequest(resourceToTrade , amountInt , priceInt
                    , message , governmentAskedFrom , allRequests.size() + 1);
            governmentAskedFrom.addToRequestsAskedFromMe(tradeRequest);
            allRequests.add(tradeRequest);
            return TradeMenuMessages.SEND_REQUEST_SUCCESS;
        }
    }

    public static void acceptTradeByRequest(String request){

    }

    public static void rejectTradeByRequest(String request){

    }

   
}
