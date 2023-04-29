package Model.Buildings;

import Model.Government;
import Model.Resources.Resource;
import Model.Units.Troop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Barrack extends Building {
    private static ArrayList<Barrack> barracks;
    private ArrayList<String> troops;
    private HashMap<Troop, Integer> troopsCreated;

    static {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Barrack>>(){}.getType();
            barracks = gson.fromJson(new FileReader("src/main/resources/Buildings/Barrack.json"), type);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Barrack(String name, int hp, Resource resource, int numberOfResource,
                    ArrayList<String> troops, int cost, boolean canPass, int width, int length) {
        super(name, hp, resource, numberOfResource, cost, canPass, width, length);
        this.troops = troops;
        troopsCreated = new HashMap<>();
    }

    public ArrayList<String> getTroops() {
        return troops;
    }

    public HashMap<Troop, Integer> getTroopsCreated() {
        return troopsCreated;
    }

    public int getTroopNumber(Troop troop) {
        //TODO
        return 0;
    }

    public void addTroopsCreated(Troop troop, int number) {

    }

    public static Barrack createBarrack(Government owner, int xCoordinateLeft, int yCoordinateUp, String barrackName) {
        for (Barrack barrack : barracks) {
            if (barrack.getName().equals(barrackName)) {
                Barrack newBarrack = new Barrack(barrackName, barrack.hp, barrack.resource,
                        barrack.numberOfResource, barrack.troops, barrack.cost, barrack.canPass, barrack.width, barrack.length);
                newBarrack.setCoordinate(xCoordinateLeft, yCoordinateUp);
                newBarrack.setOwner(owner);
                return barrack;
            }
        }
        return null;
    }
}
