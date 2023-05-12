package Model.Buildings;

import Controller.GameMenuController;
import Model.Government;
import Model.Resource;
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
    private static ArrayList<String> barracksName;
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
        barracksName = new ArrayList<>();
        for (Barrack barrack : barracks) {
            barracksName.add(barrack.name);
            GameMenuController.addToGameBuildings(barrack);
        }
    }

    private Barrack(Government owner, String name, int width, int length, int xCoordinateLeft, int yCoordinateUp, ArrayList<String> lands,
                   int hp, Resource resource, int numberOfResource, int cost, boolean canPass, ArrayList<String> troops) {
        super(owner, name, width, length, xCoordinateLeft, yCoordinateUp, lands, hp, resource, numberOfResource, cost, canPass);
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


    public boolean canBuildTroopByName(String targetTroopName){
        for (String troopName : troops) {
            if(troopName.equals(targetTroopName))
                return true;
        }
        return false;
    }

    public void addTroopsCreated(Troop troop, int number) {

    }

    public static ArrayList<String> getBarracksName() {
        return barracksName;
    }

    public static Barrack createBarrack(Government owner, int xCoordinateLeft, int yCoordinateUp, String barrackName) {
        for (Barrack barrack : barracks) {
            if (barrack.name.equals(barrackName)) {
                Barrack newBarrack = new Barrack(owner, barrack.name, barrack.width, barrack.length, xCoordinateLeft,
                        yCoordinateUp, barrack.lands, barrack.hp, barrack.resource, barrack.numberOfResource, barrack.cost,
                        barrack.canPass, barrack.troops);
                if (owner != null) owner.addBuildings(newBarrack);
                return newBarrack;
            }
        }
        return null;
    }
}
