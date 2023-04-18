package Model.Buildings;

import Model.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class Stockpile extends Building{
    private HashMap<Resource, Integer> resources;//each can contain 4 different type of resource
    private final int capacity;//how many resource in one slot of stockpile

    public Stockpile(String name, int hp, Resource resource, int numberOfResource, int cost, int capacity) {
        super(name, hp, resource, numberOfResource, cost);
        this.capacity = capacity;
        resources = new HashMap<>();
    }

    public HashMap<Resource, Integer> getResources() {
        return resources;
    }

    public int getResourceCount(Resource resource) {
        //check if resource is in HashMap
        return 0;
    }

    public boolean addResource(Resource resource, int number) {
        //check if resource exist in HashMap --> += number
        //check if HashMap key list has less than 4

        return false;
    }
}
