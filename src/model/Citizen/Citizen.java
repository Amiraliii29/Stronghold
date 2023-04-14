package Citizen;

import model.User;
import model.weapon;
import model.Resource;

public class Citizen{

    private User owningPlayer;
    private int walkingSpeed;
    private String name;

    public void setWalkingSpeed(int walkingSpeed){
        this.walkingSpeed=walkingSpeed;
    }

    public int getWalkingSpeed() {
        return walkingSpeed;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public User getOwningPlayer(){
        return owningPlayer;
    }

    public Citizen( String name ,User owningPlayer) {

    }

    public static Citizen makeCitizen(){

    }

    private void setPeasantDefaultWalkingSpeed(){

    }
}
