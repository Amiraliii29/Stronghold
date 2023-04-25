package Model.Buildings;

import Model.Resources.Resource;

import java.util.ArrayList;

public class Defence extends Building {
    private int range;
    private int capacity;

    public Defence(String name, int hp, Resource resource, int numberOfResource, int cost,
                   boolean canPass, int range, int capacity) {
        super(name, hp, resource, numberOfResource, cost, canPass);
        this.range = range;
        this.capacity = capacity;
    }

    public int getRange() {
        return range;
    }

    public int getCapacity() {
        return capacity;
    }
}
