package Model.Units;

import Model.Government;
import Model.Resources.Resource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Siege extends Unit {
    private static ArrayList<Siege> sieges;
    private int engineerNeed;
    private Resource whatTheyThrow;
    private int howManyLeft;

    static {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Siege>>() {
            }.getType();
            sieges = gson.fromJson(new FileReader("src/main/resources/Units/Siege.json"), type);

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Siege siege : sieges){
            allUnits.add(siege.name);
        }
    }

    private Siege(Government owner, String name, int speed, int hitPoint, int damage, int attackRange,
                  int cost, int engineerNeed, Resource whatTheyThrow, int howManyLeft) {
        super(owner, name, speed, hitPoint, damage, attackRange, cost);
        this.engineerNeed = engineerNeed;
        this.whatTheyThrow = whatTheyThrow;
        this.howManyLeft = howManyLeft;
    }

    public int getEngineerNeed() {
        return engineerNeed;
    }

    public Resource getWhatTheyThrow() {
        return whatTheyThrow;
    }

    public int getHowManyLeft() {
        return howManyLeft;
    }

    public static Siege createSiege(Government owner, String siegeName, int xCoordinate, int yCoordinate) {
        for (Siege siege : sieges) {
            if (siege.name.equals(siegeName)) {
                Siege newSiege = new Siege(owner, siegeName, siege.speed, siege.hitPoint, siege.damage,
                        siege.attackRange, siege.cost, siege.engineerNeed, siege.whatTheyThrow, siege.howManyLeft);
                newSiege.xCoordinate = xCoordinate;
                newSiege.yCoordinate = yCoordinate;
                return newSiege;
            }
        }
        return null;
    }
}
