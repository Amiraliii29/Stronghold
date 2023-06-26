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

public class TownBuilding extends Building {
    private static ArrayList<TownBuilding> townBuildings;
    private static ArrayList<String> townBuildingsName;
    private transient int capacity;
    private transient int popularityRate;

    static {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<TownBuilding>>() {}.getType();
            townBuildings = gson.fromJson(new FileReader("src/main/resources/Buildings/TownBuilding.json"), type);

        } catch (IOException e) {
            e.printStackTrace();
        }
        townBuildingsName = new ArrayList<>();
        for (TownBuilding townBuilding : townBuildings) {
            townBuildingsName.add(townBuilding.name);
            buildingsNames.add(townBuilding.name);
            buildings.add(townBuilding);
        }
    }

    private TownBuilding(Government owner, String name, int width, int length, int xCoordinateLeft, int yCoordinateUp,
                        ArrayList<String> lands, int hp, Resource resource, int numberOfResource, int cost, boolean canPass,
                        int capacity, int popularityRate) {
        super(owner, name, width, length, xCoordinateLeft, yCoordinateUp, lands, hp, resource, numberOfResource, cost, canPass);
        this.capacity = capacity;
        this.popularityRate = popularityRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPopularityRate() {
        return popularityRate;
    }

    public static ArrayList<String> getTownBuildingsName() {
        return townBuildingsName;
    }



    public static TownBuilding createTownBuilding(Government owner, int xCoordinateLeft, int yCoordinateUp, String townBuildingName) {
        for (TownBuilding townBuilding : townBuildings) {
            if (townBuilding.name.equals(townBuildingName)) {
                TownBuilding newTownBuilding = new TownBuilding(owner, townBuilding.name, townBuilding.width, townBuilding.length, xCoordinateLeft,
                        yCoordinateUp, townBuilding.lands, townBuilding.hp, townBuilding.resource, townBuilding.numberOfResource, townBuilding.cost,
                        townBuilding.canPass, townBuilding.capacity, townBuilding.popularityRate);

                if (!GameMenuController.constructBuilding(newTownBuilding)) return null;
                if (owner != null) owner.addBuildings(newTownBuilding);
                return newTownBuilding;
            }
        }
        return null;
    }

    public static void load() {
        return;
    }
}
