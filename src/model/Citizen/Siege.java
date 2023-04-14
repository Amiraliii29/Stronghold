package Citizen;

import java.util.HashMap;

public class Siege extends Troop {
    private int engineersNeeded;
    private int engineersInControl;
    private boolean canMove;

    private HashMap <String ,Integer> ammo;
    
    public Siege(String name,User owningPlayer);

    public int getEngineersNeeded(){
        return engineersNeeded;
    }

    public void changeEngineersInControl(int shift);

    public int getEngineersInControl(){
        return engineersInControl;
    }

    public void setEngineersNeeded(int engineersNeeded){
        this.engineersNeeded=engineersNeeded;
    }


    public static Siege makeSiegeByType(String type);
    
    public static void setSiegeStatsByType(Siege siege);
    

}
