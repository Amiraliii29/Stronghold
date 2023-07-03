package Model;

import Model.Buildings.*;
import com.google.gson.Gson;

import java.util.ArrayList;

public class BuildingPrototype {
    public static ArrayList<String> buildingsName;
    public static ArrayList<String> Defences;
    public Government owner;
    public String name;
    public int x;
    public int y;
    public int width;
    public int length;
    public int maxHP;
    public int HP;
    public boolean canPass;


    static {
        buildingsName = new ArrayList<>();
    }


    public BuildingPrototype(Government owner, String name, int x, int y) {
        this.owner = owner;
        this.name = name;
        this.x = x;
        this.y = y;
    }



    public Building createBuilding() {
        Building building;
        if (Defence.getDefencesName().contains(name))
            building = Defence.createDefence(owner, x, y, name);
        else if (Barrack.getBarracksName().contains(name))
            building = Barrack.createBarrack(owner, x, y, name);
        else if (TownBuilding.getTownBuildingsName().contains(name))
            building = TownBuilding.createTownBuilding(owner, x, y, name);
        else if (Generator.getGeneratorsName().contains(name))
            building = Generator.createGenerator(owner, x, y, name);
        else
            building = Stockpile.createStockpile(owner, x, y, name);

        building.setHp(HP);
        building.setCanPass(canPass);

        return building;
    }







    public String toJson() {
        return new Gson().toJson(this);
    }

    public static BuildingPrototype fromJson(String json) {
        return new Gson().fromJson(json, BuildingPrototype.class);
    }

}
