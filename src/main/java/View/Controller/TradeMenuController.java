package View.Controller;

import View.Controller.ShopMenuController;
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
import javafx.stage.Stage;

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
    public static TextField Message;
    public Label selectedItemName;
    public CheckBox User1;
    public CheckBox User2;
    public CheckBox User3;
    public CheckBox User4;
    public CheckBox User5;
    public CheckBox User6;
    public CheckBox User7;

    private static TradeRequest selectedTradeRequest;
    private static Button selectedRequestsRejectButton;
    private static Button getSelectedRequestsAcceptButton;
    public static AnchorPane tradeMenuHistoryPane = new AnchorPane();
    public static AnchorPane tradeNewRequestPane = new AnchorPane();



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
        if(1 <= governmentsInGameOtherThanCurrentUser.size())
            User1.setText(governmentsInGameOtherThanCurrentUser.get(0).getOwner().getUsername());
        else
            User1.setVisible(false);
        if(2 <= governmentsInGameOtherThanCurrentUser.size())
            User2.setText(governmentsInGameOtherThanCurrentUser.get(1).getOwner().getUsername());
        else
            User2.setVisible(false);
        if(3 <= governmentsInGameOtherThanCurrentUser.size())
            User3.setText(governmentsInGameOtherThanCurrentUser.get(2).getOwner().getUsername());
        else
            User3.setVisible(false);
        if(4 <= governmentsInGameOtherThanCurrentUser.size())
            User4.setText(governmentsInGameOtherThanCurrentUser.get(3).getOwner().getUsername());
        else
            User4.setVisible(false);
        if(5 <= governmentsInGameOtherThanCurrentUser.size())
            User5.setText(governmentsInGameOtherThanCurrentUser.get(4).getOwner().getUsername());
        else
            User5.setVisible(false);
        if(6 <= governmentsInGameOtherThanCurrentUser.size())
            User6.setText(governmentsInGameOtherThanCurrentUser.get(5).getOwner().getUsername());
        else
            User6.setVisible(false);
        if(7 <= governmentsInGameOtherThanCurrentUser.size())
            User7.setText(governmentsInGameOtherThanCurrentUser.get(6).getOwner().getUsername());
        else
            User7.setVisible(false);

    }

    public void openPreviousRequestsPage(MouseEvent mouseEvent) throws IOException {
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

    public static TradeMenuMessages sendTradeRequestController( int priceInt) {
        if (selectedItem == null)
            return TradeMenuMessages.NOT_ENOUGH_OPTIONS;
        if(governmentToTrade== null)
            return TradeMenuMessages.INVALID_GOVERNMENT_NAME;
        if(Message.getText() == null)
            return TradeMenuMessages.ENTER_MESSAGE;
        else {
            TradeRequest tradeRequest = new TradeRequest(selectedItem, itemAmount , priceInt
                    , Message.getText() , governmentToTrade , allRequests.size() + 1 , false);
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
        TradeMenuMessages menuMessages = sendTradeRequestController( 0 );
        selectedItem = null;
        Message.setText("");
        itemAmount = 0;
        selectedItemName.setText("");
        selectedItemAmount.setText("0");

        switch (menuMessages){
            case NOT_ENOUGH_OPTIONS -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Send trade request error");
                alert.setContentText("Please first select the item you want to trade");
                alert.showAndWait();
            }
            case INVALID_GOVERNMENT_NAME -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Send trade request error");
                alert.setContentText("Please first select the government you want to trade with");
                alert.showAndWait();
            }
            case SEND_REQUEST_SUCCESS -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success");
                alert.setContentText("Trade request has been sent successfully");
                alert.showAndWait();
            }
            case ENTER_MESSAGE -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Send trade request error");
                alert.setContentText("Enter your message");
                alert.showAndWait();
            }
        }
    }

    public static TradeMenuMessages donateController() {
        if (selectedItem == null)
            return TradeMenuMessages.NOT_ENOUGH_OPTIONS;
        else if(governmentToTrade == null)
            return TradeMenuMessages.INVALID_GOVERNMENT_NAME;
        else if(itemAmount <= 0)
            return TradeMenuMessages.INVALID_AMOUNT;
        else if (DataBase.getCurrentGovernment().getResourceInStockpiles(selectedItem) < itemAmount)
            return TradeMenuMessages.NOT_ENOUGH_RESOURCE_IN_STOCKPILE;
        else if(Message.getText() == null)
            return TradeMenuMessages.ENTER_MESSAGE;
        else {
            TradeRequest tradeRequest = new TradeRequest(selectedItem, itemAmount , 0, Message.getText() ,
                    governmentToTrade , allRequests.size() + 1 , true);

            // remove from my stockpile and add to theirs
            DataBase.getCurrentGovernment().removeFromStockpile(selectedItem, itemAmount);
            governmentToTrade.addToStockpile(selectedItem ,  itemAmount);
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
        TradeMenuMessages menuMessages = donateController();

        selectedItem = null;
        Message.setText("");
        itemAmount = 0;
        selectedItemName.setText("");
        selectedItemAmount.setText("0");

        switch (menuMessages){
            case NOT_ENOUGH_OPTIONS -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Donate error");
                alert.setContentText("Please first select the item you want to donate");
                alert.showAndWait();
            }
            case INVALID_GOVERNMENT_NAME -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Donate error");
                alert.setContentText("Please first select the government you want to donate");
                alert.showAndWait();
            }
            case INVALID_AMOUNT -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Donate error");
                alert.setContentText("Invalid amount");
                alert.showAndWait();
            }
            case NOT_ENOUGH_RESOURCE_IN_STOCKPILE -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Donate error");
                alert.setContentText("You don't have enough resource in stockpile to donate");
                alert.showAndWait();
            }
            case DONATE_SUCCESS-> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success");
                alert.setContentText("you donated " + selectedItem.getName() + " successfully :]");
                alert.showAndWait();
            }
            case ENTER_MESSAGE -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Donate error");
                alert.setContentText("Enter your message");
                alert.showAndWait();
            }

        }
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

        if(User1.isSelected()){
            governmentToTrade = governmentsInGameOtherThanCurrentUser.get(0);
            User2.setSelected(false);
            User3.setSelected(false);
            User4.setSelected(false);
            User5.setSelected(false);
            User6.setSelected(false);
            User7.setSelected(false);
        }
        else if(User2.isSelected() && 2 <= governmentsInGameOtherThanCurrentUser.size()){
            governmentToTrade = governmentsInGameOtherThanCurrentUser.get(1);
            User1.setSelected(false);
            User3.setSelected(false);
            User4.setSelected(false);
            User5.setSelected(false);
            User6.setSelected(false);
            User7.setSelected(false);
        }
        else if(User3.isSelected() && 3 <= governmentsInGameOtherThanCurrentUser.size()){
            governmentToTrade = governmentsInGameOtherThanCurrentUser.get(2);
            User1.setSelected(false);
            User2.setSelected(false);
            User4.setSelected(false);
            User5.setSelected(false);
            User6.setSelected(false);
            User7.setSelected(false);
        }
        else if(User4.isSelected() && 4 <= governmentsInGameOtherThanCurrentUser.size()){
            governmentToTrade = governmentsInGameOtherThanCurrentUser.get(3);
            User1.setSelected(false);
            User3.setSelected(false);
            User2.setSelected(false);
            User5.setSelected(false);
            User6.setSelected(false);
            User7.setSelected(false);
        }
        else if(User5.isSelected() && 5 <= governmentsInGameOtherThanCurrentUser.size()){
            governmentToTrade = governmentsInGameOtherThanCurrentUser.get(4);
            User1.setSelected(false);
            User3.setSelected(false);
            User4.setSelected(false);
            User2.setSelected(false);
            User6.setSelected(false);
            User7.setSelected(false);
        }
        else if(User6.isSelected() && 6 <= governmentsInGameOtherThanCurrentUser.size()){
            governmentToTrade = governmentsInGameOtherThanCurrentUser.get(5);
            User1.setSelected(false);
            User3.setSelected(false);
            User4.setSelected(false);
            User5.setSelected(false);
            User2.setSelected(false);
            User7.setSelected(false);
        }
        else if(User7.isSelected() && 7 <= governmentsInGameOtherThanCurrentUser.size()){
            governmentToTrade = governmentsInGameOtherThanCurrentUser.get(6);
            User1.setSelected(false);
            User3.setSelected(false);
            User4.setSelected(false);
            User5.setSelected(false);
            User6.setSelected(false);
            User2.setSelected(false);
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

    public void showRequestsIhaveSent(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(tradeMenuHistoryPane);

        tradeMenuHistoryPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/TradeHistory.fxml").toExternalForm()));
        tradeMenuHistoryPane.setLayoutX(Game.leftX);
        tradeMenuHistoryPane.setLayoutY(0);
        tradeMenuHistoryPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Game.mainPane.getChildren().add(tradeMenuHistoryPane);

        for (int i = 0; i < DataBase.getCurrentGovernment().getRequestsIAsked().size() ; i++) {
            TradeRequest tradeRequest = DataBase.getCurrentGovernment().getRequestsIAsked().get(i);
            String text = "Request -> ";
            Label label = new Label();
            label.setFont(new Font("American Typewriter" , 13));
            label.setLayoutX(78);
            label.setLayoutY(140 + (25 * i));

            if(tradeRequest.isDonate())
                text += "Donate -> ";

            text +=  "ID = "  + tradeRequest.getId();
            text += " | Target Government =  " + tradeRequest.getGovernmentThatHasBeenAsked();
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

    public void showRequestsIhaveRecieved(MouseEvent mouseEvent) throws IOException {
        Game.mainPane.getChildren().remove(tradeMenuHistoryPane);

        tradeMenuHistoryPane = FXMLLoader.load(
                new URL(ShopMenu.class.getResource("/fxml/TradeHistory.fxml").toExternalForm()));
        tradeMenuHistoryPane.setLayoutX(Game.leftX);
        tradeMenuHistoryPane.setLayoutY(0);

        Game.mainPane.getChildren().add(tradeMenuHistoryPane);

        for (int i = 0; i < DataBase.getCurrentGovernment().getRequestsAskedFromMe().size() ; i++) {
            TradeRequest tradeRequest = DataBase.getCurrentGovernment().getRequestsAskedFromMe().get(i);
            tradeRequest.setSeenByTargetUser(true);
            String text = "Request -> ";
            Label label = new Label();
            label.setFont(new Font("American Typewriter" , 13));
            label.setLayoutX(78);
            label.setLayoutY(140 + (25 * i));

            if(tradeRequest.isDonate())
                text += "Donate -> ";

            text +=  "ID = "  + tradeRequest.getId();
            text += " | Government that has Asked =  " + tradeRequest.getGovernmentThatRequested();
            text += " | Item = " + tradeRequest.getResource().getName();
            text += " | Amount = " + tradeRequest.getAmount();
            text += " | Message = " + tradeRequest.getMessage();

            if(!tradeRequest.isDonate() && tradeRequest.isAccepted() == 2){
                Button accept = new Button("accept");
                accept.setFont(new Font("American Typewriter" , 10));
                accept.setLayoutX(701);
                accept.setLayoutY(140 + (25 * i));

                Button reject = new Button("reject");
                reject.setFont(new Font("American Typewriter" , 10));
                reject.setLayoutX(653);
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("accept request error");
                alert.setContentText("Not enough of " + selectedItem.getName() + " in wareHouse");
                alert.showAndWait();
            }
            case NOT_ENOUGH_FREE_SPACE -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("accept request error");
                alert.setContentText("Not enough free space in the " + selectedTradeRequest.getGovernmentThatRequested().getOwner().getUsername()
                        + "'s wareHouse");
                alert.showAndWait();
            }
            case ACCEPT_TRADE_SUCCESS -> {
                tradeMenuHistoryPane.getChildren().removeAll(selectedRequestsRejectButton ,
                        getSelectedRequestsAcceptButton);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success");
                alert.setContentText("Request accepted successfully");
                alert.showAndWait();
            }
        }

    }

    public void closeTradeMenu(MouseEvent mouseEvent) {
        Game.mainPane.getChildren().remove(ShopMenuController.tradePane);
    }
}
