package Model.Buildings;

import Model.Resources.Resource;
import Model.Units.Troop;

import java.util.ArrayList;
import java.util.HashMap;

public class Barrack extends Building {
    private ArrayList<String> troops;
    private HashMap<Troop, Integer> troopsCreated;

    public Barrack(String name, int hp, Resource resource, int numberOfResource, ArrayList<String> troops, int cost, boolean canPass) {
        super(name, hp, resource, numberOfResource, cost, canPass);
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
}
