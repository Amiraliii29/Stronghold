package Model.Buildings;

import Model.Resources.Resource;

public class Defence extends Building {
    private int defenceRange;
    private int attackRange;
    private int capacity;

    public Defence(String name, int hp, Resource resource, int numberOfResource, int cost,
                   boolean canPass, int defenceRange, int attackRange, int capacity) {
        super(name, hp, resource, numberOfResource, cost, canPass);
        this.defenceRange = defenceRange;
        this.attackRange = attackRange;
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
