package Model.Buildings;

import Model.Government;
import Model.Resources.Resource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Defence extends Building {
    private static ArrayList<Defence> defences;
    private int range;
    private int capacity;

    static {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Defence>>(){}.getType();
            defences = gson.fromJson(new FileReader("src/main/resources/Buildings/Defences.json"), type);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static Defence createDefence(Government owner, int xCoordinateLeft, int yCoordinateUp, String defenceName) {
        for (Defence defence : defences) {
            if (defence.getName().equals(defenceName)) {

            }
        }
    }
}
