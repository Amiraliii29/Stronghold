package Model.Units;

import Model.DataBase;
import Model.Government;
import Model.Resource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Troop extends Unit{
    private static ArrayList<Troop> troops;
    private final boolean climbLadder;
    private final boolean digMoat;
    private boolean needHorse;
    private final ArrayList<Resource> weapons;

    static {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Troop>>(){}.getType();
            troops = gson.fromJson(new FileReader("src/main/resources/Units/Troop.json"), type);

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Troop troop : troops) {
            allUnits.add(troop.name);
        }
    }
    private Troop(Government owner, String name, int speed, int hitPoint, int damage, int attackRange,
                 ArrayList<Resource> weapons, int cost, boolean climbLadder, boolean digMoat) {
        super(owner, name, speed, hitPoint, damage, attackRange, cost);
        this.weapons = weapons;
        this.climbLadder = climbLadder;
        this.digMoat = digMoat;
    }

    public boolean isNeedHorse() {
        return needHorse;
    }

    public void setNeedHorse(boolean needHorse) {
        this.needHorse = needHorse;
    }

    public boolean isClimbLadder() {
        return climbLadder;
    }

    public boolean isDigMoat() {
        return digMoat;
    }

    public ArrayList<Resource> getWeapons() {
        return weapons;
    }

    public static Troop getTroopByName(String troopName){
        for (Troop troop : troops) {
            if(troop.getName().equals(troopName))
                return troop;
        }
        return null;
    }

    public static Troop createTroop(Government owner, String troopName, int xCoordinate, int yCoordinate) {
        for (Troop troop : troops) {
            if (troop.name.equals(troopName)) {
                Troop newTroop = new Troop(owner, troopName, troop.speed, troop.hitPoint, troop.damage, troop.attackRange,
                        troop.weapons, troop.cost, troop.climbLadder, troop.digMoat);
                newTroop.needHorse = troop.needHorse;
                newTroop.xCoordinate = xCoordinate;
                newTroop.yCoordinate = yCoordinate;
                if (xCoordinate >= 0 && yCoordinate >= 0)
                    DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).addUnit(newTroop);
                return newTroop;
            }
        }
        return null;
    }

    public static void load() {
        return;
    }
}
