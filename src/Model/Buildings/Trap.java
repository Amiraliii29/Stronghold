package Model.Buildings;

import Model.Resource;

public class Trap extends Building{
    private int damage;
    private boolean used;

    public Trap(String name, int hp, Resource resource, int numberOfResource, int cost, int damage) {
        super(name, hp, resource, numberOfResource, cost);
        this.damage = damage;
        used = false;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
