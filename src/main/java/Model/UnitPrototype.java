package Model;

import Model.Units.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UnitPrototype {
    public static ArrayList<String> unitsName;
    public Government owner;
    public String name;
    public int x;
    public int y;
    public int maxHP;
    public int HP;
    public int speed;
    public int attackRange;
    public int damage;
    public int cost;
    public StateUnits stateUnits;



    public UnitPrototype (Government owner, String name, int x, int y) {
        this.owner = owner;
        this.name = name;
        this.x = x;
        this.y = y;
    }


    public Unit createUnit() {
        Unit unit;
        if (Siege.getSiegesName().contains(name))
            unit = Siege.createSiege(owner, name, x, y);
        else if (name.equals("Engineer"))
            unit = Engineer.createEngineer(owner, x, y);
        else if (name.equals("LadderMan"))
            unit = LadderMan.createLadderMan(owner, x, y);
        else if (name.equals("Tunneler"))
            unit = Tunneler.createTunneler(owner, x, y);
        else
            unit = Troop.createTroop(owner, name, x, y);

        unit.setState(stateUnits);
        unit.setHitPoint(HP);

        return unit;
    }




    public static String toJson(ArrayList<UnitPrototype> units) {
        Gson gson = new Gson();
        return gson.toJson(units, ArrayList.class);
    }

    public static ArrayList<UnitPrototype> fromJson(String json) {
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<Siege>>() {}.getType();
        return gson.fromJson(json, type);
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitPrototype that = (UnitPrototype) o;
        return x == that.x && y == that.y && owner.equals(that.owner) && name.equals(that.name);
    }
}
