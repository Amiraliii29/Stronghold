package Main;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

import Model.BuildingPrototype;
import Model.Map;
import Model.UnitPrototype;
import Model.User;
import View.Controller.ChatController;

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

            UnitPrototype.fillUnitsName(dataInputStream.readUTF());

            String json = dataInputStream.readUTF();
            BuildingPrototype.fillBuildingsName(json, dataInputStream.readUTF());

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
        System.out.println(request.normalRequest+"=======");

        if (request.normalRequest.equals(NormalRequest.RECEIVE_GLOBAL_MESSAGE)) {
            Client.client.globalChats.add(request);
            ChatController.showGlobalChats();
        } else if (request.normalRequest.equals(NormalRequest.SEND_PRIVATE_MESSAGE)) {
            Client.client.privateChats.add(request);
            ChatController.showPrivateChat();
        } else if (request.normalRequest.equals(NormalRequest.SEND_ROOM_MESSAGE)) {
            Client.client.roomChats.add(request);
            ChatController.showRoomChats();
        } else if (request.normalRequest.equals(NormalRequest.ADD_ROOM_TO_CLIENT)) {
            int ID = Integer.parseInt(request.argument.get("ID"));
            Client.client.myRoomsID.add(ID);
        }
        else if(request.normalRequest.equals(NormalRequest.UPDATE_YOUR_DATA)){
            String users=request.argument.get("Users");
            User.setUsersFromJson(users);
        } else if (request.normalRequest.equals(NormalRequest.UPDATE_YOUR_DATA)) {
            User.getUsersFromServer();
        }
        else if(request.normalRequest.equals(NormalRequest.DELETE_PUBLIC_MESSAGE)){
            Request messageToDelete = Client.client.
                    getPublicMessageByText(request.argument.get("message"));
            Client.client.globalChats.remove(messageToDelete);
            ChatController.showGlobalChats();
        }
        else if(request.normalRequest.equals(NormalRequest.DELETE_PRIVATE_MESSAGE)){
            Request messageToDelete = Client.client.
                    getPrivateMessageByText(request.argument.get("message"));
            Client.client.privateChats.remove(messageToDelete);
            ChatController.showPrivateChat();
        }
        else if(request.normalRequest.equals(NormalRequest.DELETE_ROOM_MESSAGE)){
            Request messageToDelete = Client.client.
                    getRoomMessageByText(request.argument.get("message"));
            Client.client.roomChats.remove(messageToDelete);
            ChatController.showRoomChats();
        }
        else if(request.normalRequest.equals(NormalRequest.EDIT_GLOBAL_MESSAGE)){
            ArrayList<Request> chats = new ArrayList<>(Client.client.globalChats);
            Request messageToEdit = Client.client.
                    getPublicMessageByText(request.argument.get("message"));
            int index = chats.indexOf(messageToEdit);
            messageToEdit.argument.put("message" , request.argument.get("newMessage"));
            chats.set(index , messageToEdit);
            Client.client.globalChats = new LinkedBlockingDeque<Request>(chats);
            ChatController.showGlobalChats();
        }
        else if(request.normalRequest.equals(NormalRequest.EDIT_PRIVATE_MESSAGE)){
            ArrayList<Request> chats = new ArrayList<>(Client.client.privateChats);
            Request messageToEdit = Client.client.
                    getPrivateMessageByText(request.argument.get("message"));
            int index = chats.indexOf(messageToEdit);
            messageToEdit.argument.put("message" , request.argument.get("newMessage"));
            chats.set(index , messageToEdit);
            Client.client.privateChats = new LinkedBlockingDeque<Request>(chats);
            ChatController.showPrivateChat();
        }
        else if(request.normalRequest.equals(NormalRequest.EDIT_ROOM_MESSAGE)){
            ArrayList<Request> chats = new ArrayList<>(Client.client.roomChats);
            Request messageToEdit = Client.client.
                    getRoomMessageByText(request.argument.get("message"));
            int index = chats.indexOf(messageToEdit);
            messageToEdit.argument.put("message" , request.argument.get("newMessage"));
            chats.set(index , messageToEdit);
            Client.client.roomChats = new LinkedBlockingDeque<Request>(chats);
            ChatController.showRoomChats();
        }


        //TODO: FILL AUTO RESPONSES
        return true;
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
