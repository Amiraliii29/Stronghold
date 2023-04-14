package Model.Citizen;

import Model.User;

public abstract class Troop extends Character {

    private int cost;
    private int armor;
    private int range;
    //range=0 --> meele
    private boolean canClimbLadder;
    private boolean canDigMoat;
    private boolean isMounted;
    private boolean isRanged;
    private boolean canUseFlame;
    //note: new beshe
    private int[] coordinations;
    private String stance;


    public Troop(String name, User owningPlayer) {
    }


    public int getFinalDamage() {
        //regarding the fear factor
    }


    public int getArmor() {
        return armor;
    }

    public int getCost() {
        return cost;
    }

    public boolean canClimbLadder() {
        return canClimbLadder;
    }

    public boolean canDigMoat() {
        return canDigMoat;
    }

    public boolean isMounted() {
        return isMounted;
    }

    public boolean isRanged() {
        return isRanged;
    }

    public boolean canUseFlame() {
        return canUseFlame;
    }

    public int[] getCoordinations() {
        return coordinations;
    }

    public void setCoordinations(int x, int y) {
        coordinations[0] = x;
        coordinations[1] = y;
    }

    public void setStance(String stance) {
        this.stance = stance;
    }

    public String getStance() {
        return stance;
    }
}