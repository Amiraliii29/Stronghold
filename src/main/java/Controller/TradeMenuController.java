package Controller;

import Model.DataBase;
import Model.Government;
import Model.Resource;
import Model.TradeRequest;
import View.Enums.Messages.TradeMenuMessages;
import com.sun.security.auth.UnixNumericUserPrincipal;

import java.util.ArrayList;

public class TradeMenuController {
    private static final ArrayList<TradeRequest> allRequests = new ArrayList<>();

    public static String tradeListController() {
        String toReturn = "";
        int counter = 1;
        for (TradeRequest tradeRequest : DataBase.getCurrentGovernment().getRequestsAskedFromMe()) {
            toReturn += counter + ". " + tradeRequest.getResource().getName() + ", amount: " + tradeRequest.getAmount()
                    + " , price: " + tradeRequest.getPrice() + " , government that requested: " +
                    tradeRequest.getGovernmentThatRequested().getOwner().getUsername() + " , message: " + tradeRequest.getMessage() +
                    " , id: " + tradeRequest.getId() + "\n";
            counter++;

        }
        return toReturn;
    }

    public static String showTradeHistory() {
        String toReturn = "";
        int counter = 1;

        for (TradeRequest tradeRequest : DataBase.getCurrentGovernment().getTradeHistory()) {
            toReturn += counter + ". " + tradeRequest.getResource().getName() + ", amount: " + tradeRequest.getAmount()
                    + " , price: " + tradeRequest.getPrice() + " , government that requested: " +
                    tradeRequest.getGovernmentThatRequested().getOwner().getUsername() +
                    " , government that has been asked: " + tradeRequest.getGovernmentThatHasBeenAsked().getOwner().getUsername()
                    + " , request message: " + tradeRequest.getMessage() +
                    " , id: " + tradeRequest.getId() + "\n";
            counter++;

        }
        return toReturn;
    }

    public static TradeMenuMessages sendTradeRequestController(String resourceName, String amount, String price
            , String message, String governmentName) {
        if (resourceName == null || amount == null || price == null
                || price == null || message == null || governmentName == null)
            return TradeMenuMessages.NOT_ENOUGH_OPTIONS;

        Resource resourceToTrade = null;

        for (Resource allResource : Resource.getAllResources()) {
            if (allResource.getName().equals(resourceName)) {
                resourceToTrade = allResource;
                break;
            }
        }
        int amountInt = Integer.parseInt(amount);
        int priceInt = Integer.parseInt(price);

        if (resourceToTrade == null)
            return TradeMenuMessages.INVALID_RESOURCE_TYPE;
        else if (amountInt <= 0)
            return TradeMenuMessages.INVALID_AMOUNT;
        else if (priceInt < 0)
            return TradeMenuMessages.INVALID_PRICE;
        Government governmentAskedFrom = DataBase.getGovernmentByUserName(governmentName);
        if(governmentAskedFrom == null)
            return TradeMenuMessages.INVALID_GOVERNMENT_NAME;
        else {
            TradeRequest tradeRequest = new TradeRequest(resourceToTrade, amountInt, priceInt
                    , message, governmentAskedFrom, allRequests.size() + 1);
            governmentAskedFrom.addToRequestsAskedFromMe(tradeRequest);
            tradeRequest.getGovernmentThatRequested().addToTradeHistory(tradeRequest);
            tradeRequest.getGovernmentThatHasBeenAsked().addToRequestNotification(tradeRequest);
            allRequests.add(tradeRequest);
            return TradeMenuMessages.SEND_REQUEST_SUCCESS;
        }
    }

    public static TradeMenuMessages acceptTradeByRequest(String id, String acceptanceMessage) {
        int idInt = Integer.parseInt(id);
        TradeRequest tradeRequest = DataBase.getCurrentGovernment().getRequestById(idInt);
        if (tradeRequest == null)
            return TradeMenuMessages.INVALID_REQUEST_ID;
        else if (DataBase.getCurrentGovernment().getResourceInStockpiles(tradeRequest.getResource())
                < tradeRequest.getAmount())
            return TradeMenuMessages.NOT_ENOUGH_RESOURCE_IN_STOCKPILE;
        else if (tradeRequest.getGovernmentThatRequested().freeStockpileSpace(tradeRequest.getResource())
                < tradeRequest.getAmount())
            return TradeMenuMessages.NOT_ENOUGH_FREE_SPACE;
        else if (tradeRequest.getGovernmentThatRequested().getMoney() < tradeRequest.getPrice() * tradeRequest.getAmount())
            return TradeMenuMessages.NOT_ENOUGH_MONEY;
        else {
            //change stockpile and money
            DataBase.getCurrentGovernment().removeFromStockpile(tradeRequest.getResource(), tradeRequest.getAmount());
            tradeRequest.getGovernmentThatRequested().changeMoney(tradeRequest.getPrice() * tradeRequest.getAmount());
            //add to trade history and delete from tradeList
            DataBase.getCurrentGovernment().addToTradeHistory(tradeRequest);
            DataBase.getCurrentGovernment().removeFromRequestsAskedFromMe(tradeRequest);
            allRequests.remove(tradeRequest);

            tradeRequest.getGovernmentThatRequested().addToRequestNotification(tradeRequest);

            tradeRequest.setAcceptanceMessage(acceptanceMessage);

            return TradeMenuMessages.ACCEPT_TRADE_SUCCESS;

        }

    }

    public static TradeMenuMessages rejectTradeByRequest(String id) {
        TradeRequest tradeRequest = null;
        int idInt = Integer.parseInt(id);

        tradeRequest = DataBase.getCurrentGovernment().getRequestById(idInt);
        if (tradeRequest == null)
            return TradeMenuMessages.INVALID_REQUEST_ID;
        else {
            DataBase.getCurrentGovernment().removeFromRequestsAskedFromMe(tradeRequest);
            allRequests.remove(tradeRequest);
            return TradeMenuMessages.TRADE_REQUEST_REJECTED_SUCCESSFULLY;
        }
    }

    public static String showNotificationsController() {
        String toReturn = "new trade request notifications:\n";
        int counter = 1;

        for (TradeRequest requestNotification : DataBase.getCurrentGovernment().getRequestNotifications()) {
            if (requestNotification.getGovernmentThatRequested().equals(DataBase.getCurrentGovernment())) {
                toReturn += counter + ". " + requestNotification.getGovernmentThatHasBeenAsked().getOwner().getUsername()
                        + " has accepted your request for " + requestNotification.getResource().getName() +
                        " and has a message for you: " + requestNotification.getAcceptanceMessage() + "\n";
                counter++;
            } else if (requestNotification.getGovernmentThatHasBeenAsked().equals(DataBase.getCurrentGovernment())) {
                toReturn += counter + ". " + requestNotification.getGovernmentThatRequested().getOwner().getUsername() +
                        " has requested you for " + requestNotification.getAmount() + " of " +
                        requestNotification.getResource().getName() + " for price " + requestNotification.getPrice() +
                        " , message: " + requestNotification.getMessage() + "\n";
                counter++;
            }
        }
        return toReturn;
    }

    public static TradeMenuMessages donateController(String resourceName, String amount, String message,
                                                     String governmentName) {
        if (resourceName == null || amount == null || message == null || governmentName == null)
            return TradeMenuMessages.NOT_ENOUGH_OPTIONS;

        Resource resourceToTrade = null;
        Government governmentHasBeenDonated = DataBase.getGovernmentByUserName(governmentName);

        for (Resource allResource : Resource.getAllResources()) {
            if (allResource.getName().equals(resourceName)) {
                resourceToTrade = allResource;
                break;
            }
        }

        int amountInt = Integer.parseInt(amount);

        if (resourceToTrade == null)
            return TradeMenuMessages.INVALID_RESOURCE_TYPE;
        else if (amountInt <= 0)
            return TradeMenuMessages.INVALID_AMOUNT;
        else if (DataBase.getCurrentGovernment().getResourceInStockpiles(resourceToTrade) < amountInt)
            return TradeMenuMessages.NOT_ENOUGH_RESOURCE_IN_STOCKPILE;
        else if(governmentHasBeenDonated == null)
            return TradeMenuMessages.INVALID_GOVERNMENT_NAME;
        else {
            TradeRequest tradeRequest = new TradeRequest(resourceToTrade, amountInt, 0, message,
                    governmentHasBeenDonated, allRequests.size() + 1);

            // remove from my stockpile and add to theirs
            DataBase.getCurrentGovernment().removeFromStockpile(resourceToTrade, amountInt);
            governmentHasBeenDonated.addToStockpile(resourceToTrade, amountInt);
            // add to trade history
            DataBase.getCurrentGovernment().addToTradeHistory(tradeRequest);
            governmentHasBeenDonated.addToTradeHistory(tradeRequest);
            governmentHasBeenDonated.addToRequestNotification(tradeRequest);

            return TradeMenuMessages.DONATE_SUCCESS;
        }
    }
}
