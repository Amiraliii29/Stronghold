package View.Controller;

import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Model.User;
import View.Game;
import View.ShopMenu;
import View.SignUpMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ChatController {

    public TextField messageText;
    public static AnchorPane globalChatPane = new AnchorPane();
    public static AnchorPane chatMenuPane = new AnchorPane();
    public static  AnchorPane privateChatPane = new AnchorPane();
    public static AnchorPane chatRoomMenuPane = new AnchorPane();
    public static AnchorPane chatRoomPane = new AnchorPane();
    private static Label selectedPublicMessageToDeleteOrEdit = null;
    private static Label selectedPrivateMessageToDeleteOrEdit = null;

    private static Label selectedRoomMessageToDeleteOrEdit = null;
    public TextField privateChatContactUserName;
    public TextField user1Room;
    public TextField user2Room;
    public TextField user3Room;
    public TextField user4Room;
    public TextField user5Room;
    public TextField user6Room;
    public TextField user7Room;
    private static int enteredChatRoomID;
    public TextField roomID;

    public void enterPublicChat(MouseEvent mouseEvent) throws IOException {
//        Game.mainPane.getChildren().removeAll(ChatController.chatMenuPane , ChatController.globalChatPane
//                , TradeMenuController.tradeMenuHistoryPane , TradeMenuController.tradeMenuHistoryPane ,
//                ShopMenu.shopPane , ShopMenuController.tradePane);

        globalChatPane = FXMLLoader.load(new URL(SignUpMenu.class.
                getResource("/fxml/PublicChat.fxml").toExternalForm()));
//        globalChatPane.setLayoutX(Game.leftX);
//        globalChatPane.setLayoutY(0);
//
//        Game.mainPane.getChildren().add(globalChatPane);

        Stage stage = SignUpMenu.stage;
        Scene scene = new Scene(ChatController.globalChatPane);
        stage.setScene(scene);
        stage.show();

        showGlobalChats();
    }

    public static void showGlobalChats() throws IOException {
        int messageCounter = 0;
        for (Request globalChat : Client.client.globalChats) {
            Label senderName = new Label();
            senderName.setLayoutX(110);
            senderName.setLayoutY(78 + (60 * messageCounter));
            senderName.setText(globalChat.argument.get("userName"));
            senderName.setFont(new Font("American Typewriter" , 15));

            ImageView avatar = new ImageView();
            avatar.setLayoutX(70);
            avatar.setLayoutY(75 + (60 * messageCounter));
            avatar.setFitWidth(30);
            avatar.setFitHeight(30);
            avatar.setImage(new Image(new FileInputStream
                    ("src/main/resources/Images/avatars/"+globalChat.argument.get("avatar"))));

            Label messageText = new Label();
            messageText.setLayoutX(95);
            messageText.setLayoutY(120 + (60 * messageCounter));
            messageText.setText(globalChat.argument.get("message"));
            messageText.setFont(new Font("American Typewriter" , 13));

            messageText.setOnMouseClicked(event -> {
                if(senderName.getText().equals(User.getCurrentUser().getUsername())){
                    selectedPublicMessageToDeleteOrEdit = messageText;
                }
            });

            globalChatPane.getChildren().addAll(senderName , messageText , avatar);

            if(80 + (60 * messageCounter) >= 400){
                globalChatPane.getChildren().clear();
                globalChatPane = FXMLLoader.load(new URL(SignUpMenu.class.
                        getResource("/fxml/PublicChat.fxml").toExternalForm()));
                messageCounter = 0;
            }
            messageCounter++;
        }
    }

    public void enterPrivateChat(MouseEvent mouseEvent) throws IOException {
//        Game.mainPane.getChildren().removeAll(ChatController.chatMenuPane , ChatController.globalChatPane
//                , TradeMenuController.tradeMenuHistoryPane , TradeMenuController.tradeMenuHistoryPane ,
//                ShopMenu.shopPane , ShopMenuController.tradePane , ChatController.privateChatPane);

        privateChatPane = FXMLLoader.load(new URL(SignUpMenu.class.
                getResource("/fxml/PrivateChat.fxml").toExternalForm()));
//        privateChatPane.setLayoutX(Game.leftX);
//        privateChatPane.setLayoutY(0);
//
//        Game.mainPane.getChildren().add(privateChatPane);

        Stage stage = SignUpMenu.stage;
        Scene scene = new Scene(ChatController.privateChatPane);
        stage.setScene(scene);
        stage.show();
        showPrivateChat();
    }

    public static void showPrivateChat() throws IOException {
        User currentUser = User.getCurrentUser();
        int messageCounter = 0;
        for (Request privateChat : Client.client.privateChats) {
            if(privateChat.argument.get("userName").equals(currentUser.getUsername()) ||
            privateChat.argument.get("receiverUserName").equals(currentUser.getUsername())){
                Label senderName = new Label();
                senderName.setLayoutX(110);
                senderName.setLayoutY(78 + (60 * messageCounter));
                senderName.setText(privateChat.argument.get("userName"));
                senderName.setFont(new Font("American Typewriter" , 15));

                ImageView avatar = new ImageView();
                avatar.setLayoutX(70);
                avatar.setLayoutY(75 + (60 * messageCounter));
                avatar.setFitWidth(30);
                avatar.setFitHeight(30);
                avatar.setImage(new Image(new FileInputStream
                        ("src/main/resources/Images/avatars/"+privateChat.argument.get("avatar"))));

                Label messageText = new Label();
                messageText.setLayoutX(95);
                messageText.setLayoutY(120 + (60 * messageCounter));
                messageText.setText(privateChat.argument.get("message"));
                messageText.setFont(new Font("American Typewriter" , 13));

                messageText.setOnMouseClicked(event -> {
                    if(senderName.getText().equals(User.getCurrentUser().getUsername())){
                        selectedPrivateMessageToDeleteOrEdit = messageText;
                    }
                });

                privateChatPane.getChildren().addAll(senderName , messageText , avatar);

                if(80 + (60 * messageCounter) >= 400){
                    privateChatPane.getChildren().clear();
                    privateChatPane = FXMLLoader.load(new URL(SignUpMenu.class.
                            getResource("/fxml/PublicChat.fxml").toExternalForm()));
                    messageCounter = 0;
                }
                else
                    messageCounter++;
            }
        }
    }
    

    public void sendGlobalMessage(MouseEvent mouseEvent) throws IOException {
        String message = messageText.getText();
        Request request = new Request(NormalRequest.SEND_GLOBAL_MESSAGE);
        request.argument.put("message" , message);
        request.argument.put("userName" , User.getCurrentUser().getUsername());
        request.argument.put("avatar" , User.getCurrentUser().getAvatarFileName());
        Client.client.getDataOutputStream().writeUTF(request.toJson());
        messageText.setText("");
    }

    public void backToChatMenu(MouseEvent mouseEvent) throws IOException {
        ChatController.chatMenuPane = FXMLLoader
                .load(new URL(SignUpMenu.class.getResource("/fxml/ChatMenu.fxml").toExternalForm()));
        Stage stage = SignUpMenu.stage;
        Scene scene = new Scene(ChatController.chatMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    public void sendPrivateMessage(MouseEvent mouseEvent) {
        if(privateChatContactUserName.getText() != null) {
            String message = messageText.getText();
            Request request = new Request(NormalRequest.SEND_PRIVATE_MESSAGE);
            request.argument.put("message", message);
            request.argument.put("userName", User.getCurrentUser().getUsername());
            request.argument.put("avatar", User.getCurrentUser().getAvatarFileName());
            request.argument.put("receiverUserName", privateChatContactUserName.getText());
            Client.client.sendRequestToServer(request, false);
            messageText.setText("");
        }
    }

    public void createRoom(MouseEvent mouseEvent) throws IOException {
        Request request = new Request(NormalRequest.CREATE_ROOM);
        request.argument.put("user0" , User.getCurrentUser().getUsername());
        request.argument.put("user1" , user1Room.getText());
        request.argument.put("user2" , user2Room.getText());
        request.argument.put("user3" , user3Room.getText());
        request.argument.put("user4", user4Room.getText());
        request.argument.put("user5" , user5Room.getText());
        request.argument.put("user6" , user6Room.getText());
        request.argument.put("user7" , user7Room.getText());

        Client.client.sendRequestToServer(request , false);
        enteredChatRoomID = Client.client.myRoomsID.get(Client.client.myRoomsID.size() - 1);

//        Game.mainPane.getChildren().removeAll(ChatController.chatMenuPane , ChatController.globalChatPane
//                , TradeMenuController.tradeMenuHistoryPane , TradeMenuController.tradeMenuHistoryPane ,
//                ShopMenu.shopPane , ShopMenuController.tradePane , ChatController.chatRoomMenuPane);
        chatRoomPane = FXMLLoader.load(new URL(SignUpMenu.class.
                getResource("/fxml/ChatRoom.fxml").toExternalForm()));
//        chatRoomPane.setLayoutX(Game.leftX);
//        chatRoomPane.setLayoutY(0);
//
//        Game.mainPane.getChildren().add(chatRoomPane);

        Stage stage = SignUpMenu.stage;
        Scene scene = new Scene(ChatController. chatRoomPane);
        stage.setScene(scene);
        stage.show();
    }

    public void enterRoom(MouseEvent mouseEvent) throws IOException {
        if(Client.client.myRoomsID.contains(Integer.parseInt(roomID.getText()))){
            enteredChatRoomID = Integer.parseInt(roomID.getText());
//            Game.mainPane.getChildren().removeAll(ChatController.chatMenuPane , ChatController.globalChatPane
//                    , TradeMenuController.tradeMenuHistoryPane , TradeMenuController.tradeMenuHistoryPane ,
//                    ShopMenu.shopPane , ShopMenuController.tradePane , ChatController.chatRoomMenuPane);
            chatRoomPane = FXMLLoader.load(new URL(SignUpMenu.class.
                    getResource("/fxml/ChatRoom.fxml").toExternalForm()));
//            chatRoomPane.setLayoutX(Game.leftX);
//            chatRoomPane.setLayoutY(0);
//            Game.mainPane.getChildren().add(chatRoomPane);
            Stage stage = SignUpMenu.stage;
            Scene scene = new Scene(ChatController. chatRoomPane);
            stage.setScene(scene);
            stage.show();

            showRoomChats();
        }
    }

    public static void showRoomChats() throws IOException {
        int messageCounter = 0;
        for (Request roomChat : Client.client.roomChats) {
            if(Integer.parseInt(roomChat.argument.get("ID")) == enteredChatRoomID){
                Label senderName = new Label();
                senderName.setLayoutX(110);
                senderName.setLayoutY(78 + (60 * messageCounter));
                senderName.setText(roomChat.argument.get("userName"));
                senderName.setFont(new Font("American Typewriter" , 15));

                ImageView avatar = new ImageView();
                avatar.setLayoutX(70);
                avatar.setLayoutY(75 + (60 * messageCounter));
                avatar.setFitWidth(30);
                avatar.setFitHeight(30);
                avatar.setImage(new Image(new FileInputStream
                        ("src/main/resources/Images/avatars/"+roomChat.argument.get("avatar"))));

                Label messageText = new Label();
                messageText.setLayoutX(95);
                messageText.setLayoutY(120 + (60 * messageCounter));
                messageText.setText(roomChat.argument.get("message"));
                messageText.setFont(new Font("American Typewriter" , 13));

                messageText.setOnMouseClicked(event -> {
                    if(senderName.getText().equals(User.getCurrentUser().getUsername())){
                        selectedRoomMessageToDeleteOrEdit = messageText;
                    }
                });

                chatRoomPane.getChildren().addAll(senderName , messageText , avatar);

                if(80 + (60 * messageCounter) >= 400){
                    chatRoomPane.getChildren().clear();
                    chatRoomPane = FXMLLoader.load(new URL(SignUpMenu.class.
                            getResource("/fxml/ChatRoom.fxml").toExternalForm()));
                    messageCounter = 0;
                }
                else
                    messageCounter++;
            }
        }
    }

    public void sendRoomMessage(MouseEvent mouseEvent) {
        Request request = new Request(NormalRequest.SEND_ROOM_MESSAGE);
        request.argument.put("userName" , User.getCurrentUser().getUsername());
        request.argument.put("avatar", User.getCurrentUser().getAvatarFileName());
        request.argument.put("message" , messageText.getText());
        request.argument.put("ID" , Integer.toString(enteredChatRoomID));
        Client.client.sendRequestToServer(request, false);

        Client.client.sendRequestToServer(request  , false);
        messageText.setText("");
    }

    public void enterChatRoomMenu(MouseEvent mouseEvent) throws IOException {
//        Game.mainPane.getChildren().removeAll(ChatController.chatMenuPane , ChatController.globalChatPane
//                , TradeMenuController.tradeMenuHistoryPane , TradeMenuController.tradeMenuHistoryPane ,
//                ShopMenu.shopPane , ShopMenuController.tradePane);
        chatRoomMenuPane = FXMLLoader.load(new URL(SignUpMenu.class.
                getResource("/fxml/CreateRoomMenu.fxml").toExternalForm()));
//        chatRoomMenuPane.setLayoutX(Game.leftX);
//        chatRoomMenuPane.setLayoutY(0);
//
//        Game.mainPane.getChildren().add(chatRoomMenuPane);
        Stage stage = SignUpMenu.stage;
        Scene scene = new Scene(ChatController. chatRoomMenuPane);
        stage.setScene(scene);
        stage.show();
    }

    public void deletePublicMessageForMe(MouseEvent mouseEvent) {
        if(selectedPublicMessageToDeleteOrEdit != null) {
            Request messageToDelete = Client.client.
                    getPublicMessageByText(selectedPublicMessageToDeleteOrEdit.getText());
            Client.client.globalChats.remove(messageToDelete);
        }
    }

    public void deleteRoomMessageForMe(MouseEvent mouseEvent) {
        if(selectedRoomMessageToDeleteOrEdit != null) {
            Request messageToDelete = Client.client.
                    getRoomMessageByText(selectedRoomMessageToDeleteOrEdit.getText());
            Client.client.roomChats.remove(messageToDelete);
        }
    }

    public void deletePrivateMessageForMe(MouseEvent mouseEvent) {
        if(selectedPrivateMessageToDeleteOrEdit != null){
            Request messageToDelete = Client.client.
                    getPrivateMessageByText(selectedPrivateMessageToDeleteOrEdit.getText());
            Client.client.privateChats.remove(messageToDelete);
        }
    }

    public void deletePrivateMessageForEveryone(MouseEvent mouseEvent) {
        if(selectedPrivateMessageToDeleteOrEdit != null){
            Request messageToDelete = Client.client.
                    getPrivateMessageByText(selectedPrivateMessageToDeleteOrEdit.getText());
            messageToDelete.setNormalRequest(NormalRequest.DELETE_PRIVATE_MESSAGE);
            Client.client.sendRequestToServer(messageToDelete , false);
        }
    }

    public void deleteRoomMessageForEveryone(MouseEvent mouseEvent) {
        if(selectedRoomMessageToDeleteOrEdit != null) {
            Request messageToDelete = Client.client.
                    getRoomMessageByText(selectedRoomMessageToDeleteOrEdit.getText());
            messageToDelete.setNormalRequest(NormalRequest.DELETE_ROOM_MESSAGE);
            Client.client.sendRequestToServer(messageToDelete , false);
        }
    }

    public void deletePublicMessageForEveryone(MouseEvent mouseEvent) {
        if(selectedPublicMessageToDeleteOrEdit != null) {
            Request messageToDelete = Client.client.
                    getPublicMessageByText(selectedPublicMessageToDeleteOrEdit.getText());
            messageToDelete.setNormalRequest(NormalRequest.DELETE_PUBLIC_MESSAGE);
            Client.client.sendRequestToServer(messageToDelete , false);
        }
    }

    public void editRoomMessage(MouseEvent mouseEvent) {
        if(selectedRoomMessageToDeleteOrEdit != null && messageText.getText() != null) {
            Request messageToEdit = Client.client.
                    getRoomMessageByText(selectedRoomMessageToDeleteOrEdit.getText());
            messageToEdit.setNormalRequest(NormalRequest.EDIT_ROOM_MESSAGE);
            messageToEdit.argument.put("newMessage" , messageText.getText());
            Client.client.sendRequestToServer(messageToEdit, false);
            messageText.setText("");
        }
    }

    public void editGlobalMessage(MouseEvent mouseEvent) {
        if(selectedPublicMessageToDeleteOrEdit != null && messageText.getText() != null) {
            Request messageToEdit = Client.client.
                    getPublicMessageByText(selectedPublicMessageToDeleteOrEdit.getText());
            messageToEdit.setNormalRequest(NormalRequest.EDIT_GLOBAL_MESSAGE);
            messageToEdit.argument.put("newMessage" , messageText.getText());
            Client.client.sendRequestToServer(messageToEdit, false);
            messageText.setText("");
        }
    }

    public void editPrivateMessage(MouseEvent mouseEvent) {
        if(selectedPrivateMessageToDeleteOrEdit != null && messageText.getText() != null) {
            Request messageToEdit = Client.client.
                    getPrivateMessageByText(selectedPublicMessageToDeleteOrEdit.getText());
            messageToEdit.setNormalRequest(NormalRequest.EDIT_PRIVATE_MESSAGE);
            messageToEdit.argument.put("newMessage" , messageText.getText());
            Client.client.sendRequestToServer(messageToEdit, false);
            messageText.setText("");
        }
    }
}
