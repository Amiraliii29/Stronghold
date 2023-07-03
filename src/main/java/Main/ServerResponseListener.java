package Main;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

import Controller.GameMenuController;
import Model.*;
import View.Game;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import Controller.GameRoomDatabase;
import View.GameRoom;
import View.Lobby;
import View.SignUpMenu;
import View.Controller.ChatController;
import javafx.application.Platform;

import org.json.simple.JSONArray;

public class ServerResponseListener extends Thread {

    private DataInputStream dataInputStream;
    private boolean isResponseReceived;
    private boolean specific;
    Client client;

    public ServerResponseListener(DataInputStream dataInputStream, Client client) {
        this.dataInputStream = dataInputStream;
        this.client = client;
        this.setDaemon(true);
        specific = false;
        try {
            String token = dataInputStream.readUTF();
            Request.setUserToken(token);

//            UnitPrototype.fillUnitsName(dataInputStream.readUTF());
//
//            String json = dataInputStream.readUTF();
//            BuildingPrototype.fillBuildingsName(json, dataInputStream.readUTF());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String response;
        while (true) {
            try {
                response = dataInputStream.readUTF();
                if (!handleResponse(response) && !specific)
                    client.setRecentResponse(response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean handleResponse(String response) throws IOException {

        if (!response.startsWith("AUTO")) {
            client.setRecentResponse(response);
            setResponseReceived(true);
            return false;
        }

        response = response.replace("AUTO", "");
        Request request = Request.fromJson(response);
        if(request==null) return true;

        if (request.normalRequest.equals(NormalRequest.RECEIVE_GLOBAL_MESSAGE)) {
            Client.client.globalChats.add(request);
            Request saveRequest = new Request(NormalRequest.SAVE_PUBLIC_CHAT);
            saveRequest.argument.put("string" , JSONArray.toJSONString(Client.client.globalChats));
            Client.client.sendRequestToServer(request , false);
        }
         else if (request.normalRequest.equals(NormalRequest.SEND_PRIVATE_MESSAGE)) {
            Client.client.privateChats.add(request);
        }
         else if (request.normalRequest.equals(NormalRequest.SEND_ROOM_MESSAGE)) {
            Client.client.roomChats.add(request);
        } 
         else if (request.normalRequest.equals(NormalRequest.ADD_ROOM_TO_CLIENT)) {
            int ID = Integer.parseInt(request.argument.get("ID"));
            Client.client.myRoomsID.add(ID);
            ChatController.enteredChatRoomID = ID;
        }
        else if(request.normalRequest.equals(NormalRequest.UPDATE_YOUR_DATA)){
            String users=request.argument.get("Users");
            User.setUsersFromJson(users);
        } 
        else if(request.normalRequest.equals(NormalRequest.DELETE_PUBLIC_MESSAGE)){
            Request messageToDelete = Client.client.
                    getPublicMessageByText(request.argument.get("message"));
            Client.client.globalChats.remove(messageToDelete);
        }
        else if(request.normalRequest.equals(NormalRequest.DELETE_PRIVATE_MESSAGE)){
            Request messageToDelete = Client.client.
                    getPrivateMessageByText(request.argument.get("message"));
            Client.client.privateChats.remove(messageToDelete);
        }
        else if(request.normalRequest.equals(NormalRequest.DELETE_ROOM_MESSAGE)){
            Request messageToDelete = Client.client.
                    getRoomMessageByText(request.argument.get("message"));
            Client.client.roomChats.remove(messageToDelete);
        }
        else if(request.normalRequest.equals(NormalRequest.EDIT_GLOBAL_MESSAGE)){
            ArrayList<Request> chats = new ArrayList<>(Client.client.globalChats);
            Request messageToEdit = Client.client.
                    getPublicMessageByText(request.argument.get("message"));
            int index = chats.indexOf(messageToEdit);
            messageToEdit.argument.put("message" , request.argument.get("newMessage"));
            chats.set(index , messageToEdit);
            Client.client.globalChats = new ArrayList<>(chats);
        }
        else if(request.normalRequest.equals(NormalRequest.EDIT_PRIVATE_MESSAGE)){
            ArrayList<Request> chats = new ArrayList<>(Client.client.privateChats);
            Request messageToEdit = Client.client.
                    getPrivateMessageByText(request.argument.get("message"));
            int index = chats.indexOf(messageToEdit);
            messageToEdit.argument.put("message" , request.argument.get("newMessage"));
            chats.set(index , messageToEdit);
            Client.client.privateChats = new ArrayList<Request>(chats);
        }
        else if(request.normalRequest.equals(NormalRequest.EDIT_ROOM_MESSAGE)){
            ArrayList<Request> chats = new ArrayList<>(Client.client.roomChats);
            Request messageToEdit = Client.client.
                    getRoomMessageByText(request.argument.get("message"));
            int index = chats.indexOf(messageToEdit);
            messageToEdit.argument.put("message" , request.argument.get("newMessage"));
            chats.set(index , messageToEdit);
            Client.client.roomChats = new ArrayList<Request>(chats);
        }
        else if(request.normalRequest.equals(NormalRequest.SEEN_PUBLIC_MESSAGE)){
            Request chat =
                    Client.client.getPublicMessageByText(request.argument.get("message"));
            int index = Client.client.globalChats.indexOf(chat);
            chat.argument.put("seen" , "YES");
            Client.client.globalChats.set(index , chat);
        }
        else if(request.normalRequest.equals(NormalRequest.SEEN_PRIVATE_MESSAGE)){
            Request chat =
                    Client.client.getPrivateMessageByText(request.argument.get("message"));
            int index = Client.client.privateChats.indexOf(chat);
            chat.argument.put("seen" , "YES");
            Client.client.privateChats.set(index , chat);
        }
        else if(request.normalRequest.equals(NormalRequest.SEEN_ROOM_MESSAGE)){
            Request chat =
                    Client.client.getRoomMessageByText(request.argument.get("message"));
            int index = Client.client.roomChats.indexOf(chat);
            chat.argument.put("seen" , "YES");
            Client.client.roomChats.set(index , chat);
        }
        else if(request.normalRequest.equals(NormalRequest.DESTROY_GAMEROOM)){
            Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GameRoom.CurrentRoom.exitRoom();
            }
            });  
        }
        else if(request.normalRequest.equals(NormalRequest.TRANSFER_GAMEROOMS_DATA)){
            String DatabasesInJson=request.argument.get("GameRooms");
            GameRoomDatabase.setDatabasesFromJson(DatabasesInJson);
        }
        else if (request.normalRequest.equals(NormalRequest.START_GAME)) {
            startGame();
        }
        //TODO: FILL AUTO RESPONSES
        return true;
    }

    private void startGame() throws IOException {
        DataInputStream dataInputStream = Client.client.getDataInputStream();


        int jsonLength = dataInputStream.readInt();
        if (jsonLength == 0) return;

        byte[] jsonBytes = new byte[jsonLength];

        dataInputStream.readFully(jsonBytes);


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.readValue(jsonBytes, String.class);
        Gson gson = new Gson();
        Map map = gson.fromJson(json, Map.class);
        Client.client.serverResponseListener.changeSpecific();

        DataBase.setSelectedMap(map);

        Square[][] squares = map.getSquares();
        for (int i = 0; i < map.getWidth() + 1; i++) {
            for (int j = 0; j < map.getLength() + 1; j++) {
                squares[i][j].newUnits();
            }
        }
        Resource.load();
        Game.loadImages();

        Government government1 = new Government(1000);
        government1.setOwner(User.getCurrentUser());
        DataBase.setMyGovernment(government1);
        GameMenuController.setCurrentGovernment();

        GameMenuController.setMap(DataBase.getSelectedMap());
        Game game = new Game();
        DataBase.setGame(game);
        GameMenuController.setGame(game);
        try {
            game.start(SignUpMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setResponseReceived(boolean state) {
        isResponseReceived = state;
    }

    public boolean isResponseReceived() {
        return isResponseReceived;
    }

    public void changeSpecific() {
        specific = !specific;
    }
}
