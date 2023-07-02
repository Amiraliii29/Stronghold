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

public class Defence extends Building {
    private static ArrayList<Defence> defences;
    private static final ArrayList<String> defencesName;
    private final int range;
    private final int capacity;

    static {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Defence>>() {}.getType();
            defences = gson.fromJson(new FileReader("src/main/resources/Buildings/Defences.json"), type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        defencesName = new ArrayList<>();
        for (Defence defence : defences) {
            defencesName.add(defence.name);
            buildingsNames.add(defence.name);
            buildings.add(defence);
        }
    }

    private Defence(Government owner, String name, int width, int length, int xCoordinateLeft, int yCoordinateUp, ArrayList<String> lands,
                    int hp, Resource resource, int numberOfResource, int cost, boolean canPass, int range, int capacity) {
        super(owner, name, width, length, xCoordinateLeft, yCoordinateUp, lands, hp, resource, numberOfResource, cost, canPass);
        this.range = range;
        this.capacity = capacity;
    }

    public int getRange() {
        return range;
    }

    public int getCapacity() {
        return capacity;
    }

    public static ArrayList<String> getDefencesName() {
        return defencesName;
    }

    public static Defence createDefence(Government owner, int xCoordinateLeft, int yCoordinateUp, String defenceName) {
        for (Defence defence : defences) {
            if (defence.name.equals(defenceName)) {
                Defence newDefence = new Defence(owner, defence.name, defence.width, defence.length, xCoordinateLeft,
                        yCoordinateUp, defence.lands, defence.hp, defence.resource, defence.numberOfResource, defence.cost,
                        defence.canPass, defence.range, defence.capacity);
                if (owner != null) {
                    if (owner.gameMenuController.constructBuilding(newDefence)) return null;
                    owner.addBuildings(newDefence);
                }
                return newDefence;
            }
        }
        return null;
    }

    public static void load() {
        return;
    }
}
