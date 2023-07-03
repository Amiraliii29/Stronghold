package Model.Buildings;

import Controller.GameMenuController;
import Model.Government;
import Model.Resource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Stockpile extends Building {
    private static final ArrayList<String> stockpilesName;
    private static ArrayList<Stockpile> stockpiles;
    private HashMap<String, Integer> resources;
    private final ArrayList<String> resourcesStored;
    private final int capacity;//how many resource in one slot of stockpile

    static {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Stockpile>>() {
            }.getType();
            stockpiles = gson.fromJson(new FileReader("src/main/resources/Buildings/Stockpiles.json"), type);

        } catch (IOException e) {
            e.printStackTrace();
        }
        stockpilesName = new ArrayList<>();
        for (Stockpile stockpile : stockpiles) {
            stockpilesName.add(stockpile.name);
            buildingsNames.add(stockpile.name);
            buildings.add(stockpile);
        }
    }

    private Stockpile(Government owner, String name, int width, int length, int xCoordinateLeft, int yCoordinateUp, ArrayList<String> lands,
                      int hp, Resource resource, int numberOfResource, int cost, boolean canPass, ArrayList<String> resourcesStored, int capacity) {
        super(owner, name, width, length, xCoordinateLeft, yCoordinateUp, lands, hp, resource, numberOfResource, cost, canPass);
        this.resourcesStored = resourcesStored;
        this.capacity = capacity;
        resources = new HashMap<>();
    }


    public int getCapacity() {
        return capacity;
    }

    public HashMap<String, Integer> getResources() {
        return resources;
    }

    public static ArrayList<String> getStockpilesName() {
        return stockpilesName;
    }

    public ArrayList<String> getResourcesStored() {
        return resourcesStored;
    }


    public static void addResource(ArrayList<Stockpile> stockpiles, Resource resource, int number) {
        for (Stockpile stockpile : stockpiles) {
            for (Map.Entry<String, Integer> set : stockpile.resources.entrySet()) {
                if (set.getKey().equals(resource.getName()) && set.getValue() < stockpile.capacity) {
                    if (number >= (stockpile.capacity - set.getValue())) {
                        stockpile.addToHashMap(resource.getName(), (stockpile.capacity - set.getValue()));
                        number -= (stockpile.capacity - set.getValue());
                    } else {
                        stockpile.addToHashMap(resource.getName(), number);
                        return;
                    }
                }
            }

            while (stockpile.getFreeSlot() < 4 && number > 0) {
                if (number >= stockpile.getCapacity()) {
                    stockpile.addToHashMap(resource.getName(), stockpile.capacity);
                    number -= stockpile.getCapacity();
                } else {
                    stockpile.addToHashMap(resource.getName(), number);
                    number -= number;
                }
            }
        }
    }

    public static int getResourceCount(ArrayList<Stockpile> stockpiles, Resource resource) {
        int count = 0;
        for (Stockpile stockpile : stockpiles) {
            for (Map.Entry<String, Integer> set : stockpile.getResources().entrySet()) {
                if (set.getKey().equals(resource.getName()))
                    count += set.getValue();
            }
        }
        return count;
    }

    public static int freeSpaceForResource(ArrayList<Stockpile> stockpiles, Resource resource) {
        int freeSpace = 0;
        for (Stockpile stockpile : stockpiles) {
            for (Map.Entry<String, Integer> set : stockpile.getResources().entrySet()) {
                if (set.getKey().equals(resource.getName()) && set.getValue() < stockpile.capacity)
                    freeSpace += stockpile.capacity - set.getValue();
            }
            for (int i = 0; i < (4 - stockpile.getFreeSlot()); i++)
                freeSpace += stockpile.capacity;
        }
        return freeSpace;
    }

    public static boolean removeResource(ArrayList<Stockpile> stockpiles, Resource resource, int number) {
        for (Stockpile stockpile : stockpiles) {
            for (Map.Entry<String, Integer> set : stockpile.resources.entrySet()) {
                if (set.getKey().equals(resource.getName()) && number > 0) {
                    if (set.getValue() >= number) {
                        stockpile.removeFromHashMap(resource.getName(), number);
                        return true;
                    } else {
                        stockpile.removeFromHashMap(resource.getName(), set.getValue());
                        number -= set.getValue();
                    }
                }
            }
            stockpile.resources.entrySet().removeIf(entry -> entry.getValue() == 0);
        }
        return number == 0;
    }



    private void addToHashMap(String resource, int number) {
        if (resources.containsKey(resource))
            resources.put(resource, resources.get(resource) + number);
        else
            resources.put(resource, number);
    }

    private void removeFromHashMap(String resource, int number) {
        resources.put(resource, resources.get(resource) - number);
    }

    private int getFreeSlot() {
        if (resources.keySet().size() == 4) return 4;
        else {
            int slot = resources.keySet().size();
            for (Map.Entry<String, Integer> set : resources.entrySet()) {
                int val = set.getValue();
                while (val >= capacity) {
                    slot++;
                    val -= capacity;
                }
            }
            return slot;
        }
    }



    public static Stockpile createStockpile(Government owner, int xCoordinateLeft, int yCoordinateUp, String stockpileName) {
        for (Stockpile stockpile : stockpiles) {
            if (stockpile.name.equals(stockpileName)) {
                Stockpile newStockpile = new Stockpile(owner, stockpile.name, stockpile.width, stockpile.length, xCoordinateLeft,
                        yCoordinateUp, stockpile.lands, stockpile.hp, stockpile.resource, stockpile.numberOfResource, stockpile.cost,
                        stockpile.canPass, stockpile.resourcesStored, stockpile.capacity);
                if (!GameMenuController.constructBuilding(newStockpile)) return null;
                if (owner != null) {
                    if (stockpileName.equals("Granary")) owner.addGranary(newStockpile);
                    else if (stockpileName.equals("Armoury")) owner.addArmoury(newStockpile);
                    else owner.addStockpiles(newStockpile);
                }
                newStockpile.resources = new HashMap<>();
                return newStockpile;
            }
        }
        return null;
    }

    public static void load() {
        return;
    }
}
