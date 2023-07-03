package Model;

import java.util.TimerTask;

import Controller.SignUpMenuController;

public class WaitTimer extends TimerTask{

    private  int processDuration;
    private  long startTime;
    private GameRoomLife GameRoomLife;

    public WaitTimer(int duration,GameRoomLife GameRoomLife){
        this.processDuration=duration;
        this.GameRoomLife=GameRoomLife;
        resetProcessStartingTime();
    }
    
    @Override
    public void run() {
        GameRoomLife.destruction();
    }

    public  void setProcessDuration(int duration){
        processDuration=1000*duration;
    }

    public  void resetProcessStartingTime(){
        startTime=System.currentTimeMillis();
    }
}

