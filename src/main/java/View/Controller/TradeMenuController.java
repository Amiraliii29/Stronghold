package View.Controller;

import Controller.GameMenuController;
import Controller.Orders;
import Model.*;
import View.Enums.Messages.TradeMenuMessages;
import View.Game;
import View.ShopMenu;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class TradeMenuController {
    private static final ArrayList<TradeRequest> allRequests = new ArrayList<>();
    private static ArrayList<Government> governmentsInGameOtherThanCurrentUser;
    private static Government governmentToTrade;
    private static Resource selectedItem;
    private static int itemAmount = 0;
    public Label selectedItemAmount;
    public TextField Message;
    public Label selectedItemName ;

    private static TradeRequest selectedTradeRequest;
    private static Button selectedRequestsRejectButton;
    private static Button getSelectedRequestsAcceptButton;
    public static AnchorPane tradeMenuHistoryPane = new AnchorPane();
    public static AnchorPane tradeNewRequestPane = new AnchorPane();
    public Label allGovernmentsName;
    public TextField governmentName;


    public static TradeMenuMessages acceptTradeByRequest() {

        TradeRequest tradeRequest = selectedTradeRequest;

        if (DataBase.getCurrentGovernment().getResourceInStockpiles(tradeRequest.getResource())
                < tradeRequest.getAmount())
            return TradeMenuMessages.NOT_ENOUGH_RESOURCE_IN_STOCKPILE;
        else if (tradeRequest.getGovernmentThatRequested().freeStockpileSpace(tradeRequest.getResource())
                < tradeRequest.getAmount())
            return TradeMenuMessages.NOT_ENOUGH_FREE_SPACE;

        else {
            //change stockpile and money
            DataBase.getCurrentGovernment().removeFromStockpile(tradeRequest.getResource(), tradeRequest.getAmount());
            tradeRequest.getGovernmentThatRequested().addToStockpile(tradeRequest.getResource() , tradeRequest.getAmount());
            //add to trade history and delete from tradeList
            DataBase.getCurrentGovernment().addToTradeHistory(tradeRequest);
            selectedTradeRequest.setAccepted(1);
            allRequests.remove(tradeRequest);

            tradeRequest.getGovernmentThatRequested().addToRequestNotification(tradeRequest);


            return TradeMenuMessages.ACCEPT_TRADE_SUCCESS;

        }

    }


    public void openNewRequestPage(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(ShopMenu.shopPane);
        Game.mainPane.getChildren().remove(TradeMenuController.tradeMenuHistoryPane);
        Game.mainPane.getChildren().remove(TradeMenuController.tradeNewRequestPane);
        Game.mainPane.getChildren().remove(ShopMenuController.tradePane);

        tradeNewRequestPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/TradeNewRequest.fxml").toExternalForm()));


        governmentsInGameOtherThanCurrentUser = new ArrayList<>();
        for (Government government : DataBase.getGame().getGovernmentsInGame()) {
            if(!government.equals(DataBase.getCurrentGovernment()))
                governmentsInGameOtherThanCurrentUser.add(government);
        }

        tradeNewRequestPane.setLayoutX(Game.leftX);
        tradeNewRequestPane.setLayoutY(0);
        tradeNewRequestPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Game.mainPane.getChildren().remove(ShopMenuController.tradePane);
        Game.mainPane.getChildren().add(tradeNewRequestPane);
        //setGovernmentsName in menu
        String allGovernmentsNameString = "";
        for (Government government : governmentsInGameOtherThanCurrentUser) {
            allGovernmentsNameString += government.getOwner().getUsername() + "  |";
        }
        allGovernmentsName = new Label();
        allGovernmentsName.setLayoutX(31);
        allGovernmentsName.setLayoutY(122);
        allGovernmentsName.setText(allGovernmentsNameString);
        tradeNewRequestPane.getChildren().add(allGovernmentsName);

    }

    public void openPreviousRequestsPage(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(ShopMenu.shopPane);
        Game.mainPane.getChildren().remove(TradeMenuController.tradeMenuHistoryPane);
        Game.mainPane.getChildren().remove(TradeMenuController.tradeNewRequestPane);
        Game.mainPane.getChildren().remove(ShopMenuController.tradePane);

        tradeMenuHistoryPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/TradeHistory.fxml").toExternalForm()));
        tradeMenuHistoryPane.setLayoutX(Game.leftX);
        tradeMenuHistoryPane.setLayoutY(0);
        Game.mainPane.getChildren().remove(ShopMenuController.tradePane);
        Game.mainPane.getChildren().add(tradeMenuHistoryPane);
    }


    public void selectApples(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Apples");
        selectedItemName.setText("Apples");
    }

    public void selectWheat(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Wheat");
        selectedItemName.setText("Wheat");
    }

    public void selectAle(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Ale");
        selectedItemName.setText("Ale");
    }

    public void selectHops(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Hops");
        selectedItemName.setText("Hops");
    }

    public void selectFlour(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Flour");
        selectedItemName.setText("Flour");
    }

    public void selectBread(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Bread");
        selectedItemName.setText("Bread");
    }

    public void selectCheese(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Cheese");
        selectedItemName.setText("Cheese");
    }

    public void selectMeat(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Meat");
        selectedItemName.setText("Meat");
    }

    public void selectSword(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Sword");
        selectedItemName.setText("Sword");
    }

    public void selectSpear(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Spear");
        selectedItemName.setText("Spear");
    }

    public void selectPike(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Pike");
        selectedItemName.setText("Pike");
    }

    public void selectMace(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Mace");
        selectedItemName.setText("Mace");
    }

    public void selectBow(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Bow");
        selectedItemName.setText("Bow");
    }

    public void selectCrossBow(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("CrossBow");
        selectedItemName.setText("CrossBow");
    }

    public void selectMetalArmor(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("MetalArmour");
        selectedItemName.setText("MetalArmour");
    }

    public void selectIron(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Iron");
        selectedItemName.setText("Iron");
    }

    public void selectStone(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Stone");
        selectedItemName.setText("Stone");
    }

    public void selectWood(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Wood");
        selectedItemName.setText("Wood");
    }

    public void selectPitch(MouseEvent mouseEvent) {
        selectedItem = Resource.getResourceByName("Pitch");
        selectedItemName.setText("Pitch");
    }
    public void selectLeatherArmor(MouseEvent mouseEvent){
        selectedItem = Resource.getResourceByName("LeatherArmour");
        selectedItemName.setText("LeatherArmour");
    }

    public static TradeMenuMessages sendTradeRequestController( int priceInt , String message) {
        if (selectedItem == null)
            return TradeMenuMessages.NOT_ENOUGH_OPTIONS;
        if(governmentToTrade== null)
            return TradeMenuMessages.INVALID_GOVERNMENT_NAME;
        if (message == null)
            return TradeMenuMessages.ENTER_MESSAGE;
        else {
            TradeRequest tradeRequest = new TradeRequest(selectedItem, itemAmount , priceInt
                    , message , governmentToTrade , GameMenuController.getGame().requestAndDonatesCounter , false);
            GameMenuController.getGame().requestAndDonatesCounter++;
            //add to history
            governmentToTrade.addToRequestsAskedFromMe(tradeRequest);
            DataBase.getCurrentGovernment().addToRequestsIAsked(tradeRequest);

            tradeRequest.getGovernmentThatRequested().addToTradeHistory(tradeRequest);
            tradeRequest.getGovernmentThatHasBeenAsked().addToRequestNotification(tradeRequest);
            allRequests.add(tradeRequest);
            return TradeMenuMessages.SEND_REQUEST_SUCCESS;
        }
    }
    public void request(MouseEvent mouseEvent) {
        String messageString = Message.getText();
        TradeMenuMessages menuMessages = sendTradeRequestController( 0  , messageString);
        selectedItem = null;
        Message.setText("");
        itemAmount = 0;
        selectedItemName.setText("");
        selectedItemAmount.setText("0");

        switch (menuMessages){
            case NOT_ENOUGH_OPTIONS -> {
                GameGraphicController.popUpAlert("Request Error" , ""
                , "Please first select the item you want to trade" , Orders.redNotifErrorColor);
            }
            case INVALID_GOVERNMENT_NAME -> {
                GameGraphicController.popUpAlert("Request Error" , "" ,
                        "Please first select the government you want to trade with" , Orders.redNotifErrorColor);
            }
            case SEND_REQUEST_SUCCESS -> {
                GameGraphicController.popUpAlert("Request Success" , "" ,
                        "Trade request has been sent successfully" , Orders.greenNotifSuccesColor);
            }
            case ENTER_MESSAGE -> {
                GameGraphicController.popUpAlert("Request Error" , "" ,
                        "Message Field is empty" , Orders.redNotifErrorColor);
            }
        }
    }

    public static TradeMenuMessages donateController(String message) {
        if (selectedItem == null)
            return TradeMenuMessages.NOT_ENOUGH_OPTIONS;
        else if(governmentToTrade == null)
            return TradeMenuMessages.INVALID_GOVERNMENT_NAME;
        else if(itemAmount <= 0)
            return TradeMenuMessages.INVALID_AMOUNT;
        else if (DataBase.getCurrentGovernment().getResourceInStockpiles(selectedItem) < itemAmount)
            return TradeMenuMessages.NOT_ENOUGH_RESOURCE_IN_STOCKPILE;
        else if(message == null)
            return TradeMenuMessages.ENTER_MESSAGE;
        else {
            TradeRequest tradeRequest = new TradeRequest(selectedItem, itemAmount , 0, message ,
                    governmentToTrade , GameMenuController.getGame().requestAndDonatesCounter , true);
            GameMenuController.getGame().requestAndDonatesCounter++;

            // remove from my stockpile and add to theirs
            DataBase.getCurrentGovernment().removeFromStockpile(selectedItem, itemAmount);
            governmentToTrade.addToStockpile(selectedItem ,  itemAmount);
            System.out.println(governmentToTrade.getOwner().getUsername() + selectedItem.getName());
            // add to trade history
            DataBase.getCurrentGovernment().addToRequestsIAsked(tradeRequest);
            governmentToTrade.addToRequestsAskedFromMe(tradeRequest);
            DataBase.getCurrentGovernment().addToTradeHistory(tradeRequest);
            governmentToTrade.addToTradeHistory(tradeRequest);
            governmentToTrade.addToRequestNotification(tradeRequest);

            return TradeMenuMessages.DONATE_SUCCESS;
        }
    }

    public void donate(MouseEvent mouseEvent) {
        String messageString = Message.getText();
        TradeMenuMessages menuMessages = donateController(messageString);

        Message.setText("");
        itemAmount = 0;
        selectedItemName.setText("");
        selectedItemAmount.setText("0");

        switch (menuMessages){
            case NOT_ENOUGH_OPTIONS -> {
                GameGraphicController.popUpAlert("Donate Error" , "" ,
                        "Please first select the item you want to donate" ,Orders.redNotifErrorColor );
            }
            case INVALID_GOVERNMENT_NAME -> {
                GameGraphicController.popUpAlert("Donate Error" , "" ,
                        "Please first select the government you want to donate" , Orders.redNotifErrorColor);
            }
            case INVALID_AMOUNT -> {
                GameGraphicController.popUpAlert("Donate Error" , ""
                , "Invalid amount" , Orders.redNotifErrorColor);
            }
            case NOT_ENOUGH_RESOURCE_IN_STOCKPILE -> {
                GameGraphicController.popUpAlert("Donate Error" , "" ,
                        "You don't have enough resource in stockpile to donate" , Orders.redNotifErrorColor);
            }
            case DONATE_SUCCESS-> {
                GameGraphicController.popUpAlert("Donate Success" , "" , "you donated " + selectedItem.getName()
                        + " successfully :]" , Orders.greenNotifSuccesColor);
            }
            case ENTER_MESSAGE -> {
                GameGraphicController.popUpAlert("Donate Error" , ""
                , "Message Field is empty" , Orders.redNotifErrorColor);
            }

        }
        selectedItem = null;
    }

    public void decreaseAmount(MouseEvent mouseEvent) {
        if(itemAmount > 0) {
            itemAmount--;
            selectedItemAmount.setText(Integer.toString(Integer.parseInt(selectedItemAmount.getText()) - 1));
        }

    }

    public void increaseAmount(MouseEvent mouseEvent) {
        itemAmount++;
        selectedItemAmount.setText(Integer.toString(Integer.parseInt(selectedItemAmount.getText()) + 1));
    }

    public void selectGovernment(MouseEvent mouseEvent) {
        String governmentNameString = governmentName.getText();

        Government selectedGovernment = null;

        for (Government government : governmentsInGameOtherThanCurrentUser) {
            if(government.getOwner().getUsername().equals(governmentNameString))
                selectedGovernment = government;
        }

        if(selectedGovernment == null){
            GameGraphicController.popUpAlert("Error" , "",
                    "Invalid Government Name" , Orders.redNotifErrorColor);
            governmentName.setText("");
        }
        else{
            governmentToTrade = selectedGovernment;
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(tradeNewRequestPane);
        Game.mainPane.getChildren().remove(tradeMenuHistoryPane);

        ShopMenuController.tradePane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/TradeMenu.fxml").toExternalForm()));
        ShopMenuController.tradePane.setLayoutX(Game.leftX);
        ShopMenuController.tradePane.setLayoutY(0);
        Game.mainPane.getChildren().add(ShopMenuController.tradePane);
    }

    public void showRequestsIHaveSent(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(tradeMenuHistoryPane);

        tradeMenuHistoryPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/TradeHistory.fxml").toExternalForm()));
        tradeMenuHistoryPane.setLayoutX(Game.leftX);
        tradeMenuHistoryPane.setLayoutY(0);

        Game.mainPane.getChildren().add(tradeMenuHistoryPane);

        for (int i = 0; i < DataBase.getCurrentGovernment().getRequestsIAsked().size() ; i++) {
            TradeRequest tradeRequest = DataBase.getCurrentGovernment().getRequestsIAsked().get(i);
            String text = "";
            Label label = new Label();
            label.setFont(new Font("American Typewriter" , 13));
            label.setLayoutX(2);
            label.setLayoutY(140 + (25 * i));

            if(tradeRequest.isDonate())
                text += "Donate -> ";
            else
                text += "Request -> ";

            text +=  "ID = "  + tradeRequest.getId();
            text += " | Target Government =  " + tradeRequest.getGovernmentThatHasBeenAsked().getOwner().getUsername();
            text += " | Item = " + tradeRequest.getResource().getName();
            text += " | Amount = " + tradeRequest.getAmount();
            text += " | Message = " + tradeRequest.getMessage();

            if(tradeRequest.isDonate()){
                label.setTextFill(Color.BLUE);
            }
            else if(!tradeRequest.isSeenByTargetUser()){
                text += " | State = not seen";
            }
            else if(tradeRequest.isAccepted() == 1) {
                text += " | State = ++Accepted";
                label.setTextFill(Color.GREEN);
            }
            else if(tradeRequest.isAccepted() == 0) {
                text += " | State = --rejected";
                label.setTextFill(Color.RED);
            }

            label.setText(text);
            tradeMenuHistoryPane.getChildren().add(label);
        }
    }

    public void showRequestsIHaveRecieved(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(tradeMenuHistoryPane);

        tradeMenuHistoryPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/TradeHistory.fxml").toExternalForm()));
        tradeMenuHistoryPane.setLayoutX(Game.leftX);
        tradeMenuHistoryPane.setLayoutY(0);

        Game.mainPane.getChildren().add(tradeMenuHistoryPane);

        for (int i = 0; i < DataBase.getCurrentGovernment().getRequestsAskedFromMe().size() ; i++) {
            TradeRequest tradeRequest = DataBase.getCurrentGovernment().getRequestsAskedFromMe().get(i);
            tradeRequest.setSeenByTargetUser(true);
            String text = "";
            Label label = new Label();
            label.setFont(new Font("American Typewriter" , 13));
            label.setLayoutX(2);
            label.setLayoutY(140 + (25 * i));

            if(tradeRequest.isDonate())
                text += "Donate -> ";
            else
                text += "Request -> ";

            text +=  "ID = "  + tradeRequest.getId();
            if(tradeRequest.isDonate())
                text += " | Government that has donated =  " + tradeRequest.getGovernmentThatRequested().getOwner().getUsername();
            else
                text += " | Government that has Asked =  " + tradeRequest.getGovernmentThatRequested().getOwner().getUsername();
            text += " | Item = " + tradeRequest.getResource().getName();
            text += " | Amount = " + tradeRequest.getAmount();
            text += " | Message = " + tradeRequest.getMessage();

            if(!tradeRequest.isDonate() && tradeRequest.isAccepted() == 2){
                Button accept = new Button("accept");
                accept.setFont(new Font("American Typewriter" , 10));
                accept.setLayoutX(880);
                accept.setLayoutY(140 + (25 * i));

                Button reject = new Button("reject");
                reject.setFont(new Font("American Typewriter" , 10));
                reject.setLayoutX(830);
                reject.setLayoutY(140 + (25 * i));
                accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        selectedTradeRequest = tradeRequest;
                        selectedRequestsRejectButton = reject;
                        getSelectedRequestsAcceptButton = accept;
                        acceptRequest();
                    }
                });

                reject.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        selectedTradeRequest = tradeRequest;
                        selectedRequestsRejectButton = reject;
                        getSelectedRequestsAcceptButton = accept;
                        rejectRequest();
                    }
                });

                tradeMenuHistoryPane.getChildren().addAll(accept , reject);
            }

            label.setText(text);
            tradeMenuHistoryPane.getChildren().add(label);
        }
    }

    private void rejectRequest() {
        selectedTradeRequest.setAccepted(0);
        tradeMenuHistoryPane.getChildren().removeAll(selectedRequestsRejectButton ,
                getSelectedRequestsAcceptButton);
    }

    private void acceptRequest() {
        TradeMenuMessages menuMessages = acceptTradeByRequest();

        switch (menuMessages){
            case NOT_ENOUGH_RESOURCE_IN_STOCKPILE -> {
                GameGraphicController.popUpAlert("Accept Request Error"  , ""
                , "Not enough of " + selectedItem.getName() + " in wareHouse" , Orders.redNotifErrorColor);
            }
            case NOT_ENOUGH_FREE_SPACE -> {
                GameGraphicController.popUpAlert("Accept Request Error" , "" ,
                        "Not enough free space in the " + selectedTradeRequest.getGovernmentThatRequested().getOwner().getUsername()
                                + "'s wareHouse" , Orders.redNotifErrorColor);
            }
            case ACCEPT_TRADE_SUCCESS -> {
                tradeMenuHistoryPane.getChildren().removeAll(selectedRequestsRejectButton ,
                        getSelectedRequestsAcceptButton);
                GameGraphicController.popUpAlert("Success" , "" ,
                        "Request accepted successfully" , Orders.greenNotifSuccesColor);
            }
        }

    }

    public void closeTradeMenu(MouseEvent mouseEvent) {
        Game.mainPane.getChildren().remove(ShopMenuController.tradePane);
    }
}
