package Model;

import Main.Client;
import Main.Request;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ChatRoom {
    private int ID;
    private ArrayList<Client> clientsInRoom = new ArrayList<>();
    private BlockingDeque<Request> roomChats = new LinkedBlockingDeque<>();
    private static ArrayList<ChatRoom> allRooms = new ArrayList<>();
    private static int availableID = 100;

    public ChatRoom(ArrayList<Client> clientsInRoom ) {
        this.clientsInRoom = clientsInRoom;
        this.ID = availableID;
        allRooms.add(this);
        availableID++;
    }
    public static ChatRoom getRoomByID(int ID){
        for (ChatRoom allRoom : allRooms) {
            return allRoom;
        }
        return null;
    }
    public void addToChats(Request request){
        roomChats.add(request);
    }

    public ArrayList<Client> getClientsInRoom() {
        return clientsInRoom;
    }

    public int getID() {
        return ID;
    }
}
