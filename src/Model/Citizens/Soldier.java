package Model.Citizens;

import Model.User;

import java.util.HashMap;

public class Soldier extends Troop {
    private HashMap <String , Integer> weaponsNeeded;

    public Soldier(String name, User owningPlayer){
    }

    private void setNeededWeapons(){
        //according to troop name
    }

    public HashMap<String , Integer> getNeededWeapons(){
        //weapons are captured in name
        return weaponsNeeded;
    }

    private static void setSoldiersStatsByType(Soldier soldier) {

    }

    public static Soldier makeSoldierByName(String name) {

    }
  
    
}


