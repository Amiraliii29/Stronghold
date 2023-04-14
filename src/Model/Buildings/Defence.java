package Model.Buildings;

import Model.Resource;

public class Defence extends Building {
    private int defenceRange;
    private int attackRange;
    private int capacity;

    public Defence(String name, int hp, Resource resource, int numberOfResource, int cost) {
        super(name, hp, resource, numberOfResource, cost);
    }

    public void setDefenceRange(int defenceRange) {
        this.defenceRange = defenceRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDefenceRange() {
        return defenceRange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getCapacity() {
        return capacity;
    }
}
