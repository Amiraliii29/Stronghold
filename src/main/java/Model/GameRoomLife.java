package Model;

import java.io.IOException;
import java.util.Timer;

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
    private WaitTimer WaitTimer;
    private Timer timer;

    public GameRoomLife(GameRoomDatabase associatedGameRoom){
        this.associatedGameRoom=associatedGameRoom;
        this.setDaemon(true);
        WaitTimer=new WaitTimer(10, this);
    }

    @Override
    public void run(){
        startProcess();
    }

    private void startProcess(){
        timer = new Timer("Timer");
        timer.schedule(WaitTimer, 600000);
    }

    public void resetTimer(){
        WaitTimer.cancel();
        WaitTimer=new WaitTimer(10, this);
        startProcess();
    }

    public void destruction(){
        GameRoomDatabase.getAllRoomDatabases().remove(associatedGameRoom);
        sendDestructionNotifToMembers();
        Client.updateGameRoomsForClients();
        return;
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
