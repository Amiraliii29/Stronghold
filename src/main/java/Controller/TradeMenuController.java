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


    public static String tradeListController(){
        String toReturn = "";
        int counter = 1;
        for (TradeRequest tradeRequest : DataBase.getCurrentGovernment().getRequestsAskedFromMe()) {
            toReturn += counter + ". " + tradeRequest.getResource().getName() + ", amount: " + tradeRequest.getAmount()
                    + " , price: " + tradeRequest.getPrice() + " , goverment that requested: " +
                    tradeRequest.getGovernmentThatRequested() + " , message: " + tradeRequest.getMessage() +
                    " , id: " + tradeRequest.getId() + "\n";

        }
        return toReturn;
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

    public static TradeMenuMessages acceptTradeByRequest(String id , String acceptanceMessage){
        int idInt = Integer.parseInt(id);
        TradeRequest tradeRequest = DataBase.getCurrentGovernment().getRequestById(idInt);
        if(tradeRequest == null)
            return TradeMenuMessages.INVALID_REQUEST_ID;
        else if(DataBase.getCurrentGovernment().getResourceInStockpiles(tradeRequest.getResource())
                < tradeRequest.getAmount())
            return TradeMenuMessages.NOT_ENOUGH_RESOURCE_IN_STOCKPILE;
        else if(tradeRequest.getGovernmentThatRequested().freeStockpileSpace(tradeRequest.getResource())
                < tradeRequest.getAmount())
            return TradeMenuMessages.NOT_ENOUGH_FREE_SPACE;
        else if(tradeRequest.getGovernmentThatRequested().getMoney() < tradeRequest.getPrice() * tradeRequest.getAmount())
            return TradeMenuMessages.NOT_ENOUGH_MONEY;
        else{
            //change stockpile and money
            DataBase.getCurrentGovernment().removeFromStockpile(tradeRequest.getResource() , tradeRequest.getAmount());
            tradeRequest.getGovernmentThatRequested().changeMoney(tradeRequest.getPrice() * tradeRequest.getAmount());
            //add to trade history and delete from tradeList
            DataBase.getCurrentGovernment().addToTradeHistory(tradeRequest);
            tradeRequest.getGovernmentThatRequested().addToTradeHistory(tradeRequest);
            DataBase.getCurrentGovernment().removeFromRequestsAskedFromMe(tradeRequest);
            allRequests.remove(tradeRequest);

            return TradeMenuMessages.ACCEPT_TRADE_SUCCESS;

        }

    }

    public static void rejectTradeByRequest(String request){

    }

   
}
