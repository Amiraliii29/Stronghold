package Controller;

import java.util.TimerTask;

public class PenaltyTimer extends TimerTask {

    private static int processDuration;
    private static long startTime;
    
    @Override
    public void run() {
        
        if(System.currentTimeMillis()-startTime>=processDuration)
            cancel();

         SignUpMenuController.decreasePenalty();
    }

    public static void setProcessDuration(int duration){
        processDuration=1000*duration;
    }

    public static void setProcessStartingTime(){
        startTime=System.currentTimeMillis();
    }
}
