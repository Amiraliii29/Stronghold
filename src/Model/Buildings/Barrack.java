package Model.Buildings;

import Model.Resource;
import Model.Troop;

import java.util.ArrayList;
import java.util.HashMap;

public class Barrack extends Building {
    private ArrayList<Troop> troops;
    private HashMap<Troop, Integer> troopsCreated;

    public Barrack(String name, int hp, Resource resource, int numberOfResource, int cost) {
        super(name, hp, resource, numberOfResource, cost);
        troops = new ArrayList<>();
        troopsCreated = new HashMap<>();
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void addTroop(Troop troop) {
        this.troops.add(troop);
    }

    public void setTroops(ArrayList<Troop> troops) {
        this.troops = troops;
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
