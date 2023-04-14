package Model.Citizen;

import Model.User;

public abstract class Character extends Citizen {
    private int hitPoint;
    private int damage;

    public Character(String name, User owningPlayer) {

    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getDamage() {
        return damage;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void takeDamage(int damageTaken) {
        this.hitPoint -= damageTaken;
    }
}