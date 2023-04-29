package Model;

import Model.Buildings.Building;
import Model.Resources.Resource;

import Model.Units.Troop;

import java.util.ArrayList;
import java.util.HashMap;

public class Square {
    private ArrayList<Troop> troops;
    private Building building;
    private String land;
    private Resource resource;
    private int x;
    private int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        troops = new ArrayList<>();
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void addTroop(Troop troop) {
        this.troops.add(troop);
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLand() {
        return land;
    }
    public HashMap<Troop , Integer> getTroopsTypeAndCount(){
        HashMap<Troop , Integer> troopsTypeAndCount = new HashMap<>();
        for (Troop troop : troops) {
            if(!troopsTypeAndCount.keySet().contains(troop))
                troopsTypeAndCount.put(troop , 1);
            else{
                int tmp =  troopsTypeAndCount.get(troop);
                troopsTypeAndCount.remove(troop);
                troopsTypeAndCount.put(troop , tmp + 1);
            }
        }
        return troopsTypeAndCount;
    }
}
