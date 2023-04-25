package Model.Buildings;

import Model.Resources.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Stockpile extends Building {
    // when user build an stockpile add that to government in Government !! //TODO
    private HashMap<Resource, Integer> resources;
    private final int capacity;//how many resource in one slot of stockpile

    public Stockpile(String name, int hp, Resource resource, int numberOfResource, int cost , boolean canPass) {
        super(name, hp, resource, numberOfResource, cost , canPass);
        this.capacity = 40;
        resources = new HashMap<>();
    }

    public HashMap<Resource, Integer> getResources() {
        return resources;
    }

    public static int getResourceCount(ArrayList<Stockpile> stockpiles, Resource resource) {
        int count = 0;
        for (Stockpile stockpile : stockpiles) {
            for (Map.Entry<Resource, Integer> set : stockpile.getResources().entrySet()) {
                if (set.getKey().equals(resource)) count += set.getValue();
            }
        }
        return count;
    }

    public static int freeSpaceForResource(ArrayList<Stockpile> stockpiles, Resource resource) {
        int freeSpace = 0;
        for (Stockpile stockpile : stockpiles) {
            for (Map.Entry<Resource, Integer> set : stockpile.getResources().entrySet()) {
                if (set.getKey().equals(resource)) freeSpace += stockpile.capacity - set.getValue();
            }
            for (int i = 0; i < (4 - stockpile.getResources().keySet().size()); i++) {
                freeSpace += stockpile.capacity;
            }
        }
        return freeSpace;
    }

    public static boolean addResource(ArrayList<Stockpile> stockpiles, Resource resource, int number) {
        for (Stockpile stockpile : stockpiles) {
            for (Map.Entry<Resource, Integer> set : stockpile.getResources().entrySet()) {
                if (set.getKey().equals(resource)) {
                    if (set.getValue() < stockpile.capacity && number >= (stockpile.capacity - set.getValue())) {
                        stockpile.addToHashMap(resource, (stockpile.capacity - set.getValue()));
                        number -= (stockpile.capacity - set.getValue());
                    }
                }
            }
            while (stockpile.getResources().keySet().size() < 4 && number > 0) {
                if (number >= stockpile.getCapacity()) {
                    stockpile.addToHashMap(resource, stockpile.getCapacity());
                    number -= stockpile.getCapacity();
                } else {
                    stockpile.addToHashMap(resource, number);
                    number -= number;
                }
            }
        }
        if (number == 0) return true;
        return false;
    }

    public static boolean removeResource(ArrayList<Stockpile> stockpiles, Resource resource, int number) {
        for (Stockpile stockpile : stockpiles) {
            for (Map.Entry<Resource, Integer> set : stockpile.getResources().entrySet()) {
                if (set.getKey().equals(resource) && number > 0) {
                    if (set.getValue() >= number) {
                        stockpile.removeFromHashMap(resource, number);
                        number -= number;
                    } else {
                        stockpile.removeFromHashMap(resource, set.getValue());
                        number -= set.getValue();
                    }
                }
            }
        }
        if (number == 0) return true;
        return false;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addToHashMap(Resource resource, int number) {
        resources.put(resource, resources.get(resource)+number);
    }

    public void removeFromHashMap(Resource resource, int number) {
        if (resources.get(resource) == number) {
            resources.remove(resource, number);
        } else {
            resources.put(resource, resources.get(resource)-number);
        }
    }
}
