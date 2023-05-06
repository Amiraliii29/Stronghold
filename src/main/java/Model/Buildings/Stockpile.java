package Model.Buildings;

import Controller.GameMenuController;
import Model.Government;
import Model.Resources.Resource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Stockpile extends Building {
    private static ArrayList<Stockpile> stockpiles;
    private static ArrayList<String> stockpilesName;
    private HashMap<Resource, Integer> resources;
    private ArrayList<String> resourcesStored;
    private final int capacity;//how many resource in one slot of stockpile

    static {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Stockpile>>() {}.getType();
            stockpiles = gson.fromJson(new FileReader("src/main/resources/Buildings/Stockpiles.json"), type);

        } catch (IOException e) {
            e.printStackTrace();
        }
        stockpilesName = new ArrayList<>();
        for (Stockpile stockpile: stockpiles) {
            stockpilesName.add(stockpile.name);
            GameMenuController.addToGameBuildings(stockpile);
        }
    }

    private Stockpile(Government owner, String name, int width, int length, int xCoordinateLeft, int yCoordinateUp, ArrayList<String> lands,
                     int hp, Resource resource, int numberOfResource, int cost, boolean canPass, ArrayList<String> resourcesStored, int capacity) {
        super(owner, name, width, length, xCoordinateLeft, yCoordinateUp, lands, hp, resource, numberOfResource, cost, canPass);
        this.resourcesStored = resourcesStored;
        this.capacity = capacity;
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

    public static ArrayList<String> getStockpilesName() {
        return stockpilesName;
    }

    public ArrayList<String> getResourcesStored() {
        return resourcesStored;
    }

    public static Stockpile createStockpile(Government owner, int xCoordinateLeft, int yCoordinateUp, String stockpileName) {
        for (Stockpile stockpile : stockpiles) {
            if (stockpile.name.equals(stockpileName)) {
                Stockpile newStockpile = new Stockpile(owner, stockpile.name, stockpile.width, stockpile.length, xCoordinateLeft,
                        yCoordinateUp, stockpile.lands, stockpile.hp, stockpile.resource, stockpile.numberOfResource, stockpile.cost,
                        stockpile.canPass, stockpile.resourcesStored, stockpile.capacity);
                if (stockpileName.equals("Granary")) owner.addGranary(newStockpile);
                else if (stockpileName.equals("Armoury")) owner.addArmoury(newStockpile);
                else owner.addStockpiles(newStockpile);

                //add to squares//TODO
                return newStockpile;
            }
        }
        return null;
    }
}
