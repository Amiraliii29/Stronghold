package Model;

import java.io.IOException;

import com.google.gson.Gson;

import Controller.GameRoomDatabase;
import Main.Client;
import Main.NormalRequest;
import Main.Request;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameRoomLife extends Thread{
    
    private GameRoomDatabase associatedGameRoom;
    private int time=12;

    public GameRoomLife(GameRoomDatabase associatedGameRoom){
        this.associatedGameRoom=associatedGameRoom;
        this.setDaemon(true);
    }

    @Override
    public void run(){
        decreaseTime();
    }

    public void resetTimer(){
        time=10;
    }

    private void decreaseTime(){
        time--;
        if (time==0){
            GameRoomDatabase.getAllRoomDatabases().remove(associatedGameRoom);
            sendDestructionNotifToMembers();
            Client.updateGameRoomsForClients();
            return;
        }
        WaitThread waiter=new WaitThread(1000);
        try {
            waiter.start();
            waiter.join();
            decreaseTime();
            System.out.println(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
    }

    private void sendDestructionNotifToMembers(){
        System.out.println("end");
        Request request=new Request(null,NormalRequest.DESTROY_GAMEROOM);
        Gson gson=new Gson();

        for (Client client : DataBase.getAllClients()) 
            if(associatedGameRoom.getUsersInRoom().contains(client.getUser()))
                try {
                    client.getDataOutputStream().writeUTF("AUTO"+gson.toJson(request));
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }
}
