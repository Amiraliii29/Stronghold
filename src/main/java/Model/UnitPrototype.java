package Model;

import Main.Request;
import com.google.gson.Gson;

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



    public Government getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getHP() {
        return HP;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public StateUnits getStateUnits() {
        return stateUnits;
    }

    public static ArrayList<String> getUnitsName() {
        return unitsName;
    }

    public Square getSquare() {
        return DataBase.getSelectedMap().getSquareFromMap(x, y);
    }




    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public void setStateUnits(StateUnits stateUnits) {
        this.stateUnits = stateUnits;
    }

    public static void setUnitsName(ArrayList<String> unitsName) {
        UnitPrototype.unitsName = unitsName;
    }

    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }



    public static void fillUnitsName(String json) {
        Gson gson = new Gson();
        unitsName = gson.fromJson(json, ArrayList.class);
    }

    public static String toJson(ArrayList<UnitPrototype> units) {
        Gson gson = new Gson();
        return gson.toJson(units, ArrayList.class);
    }





    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", speed=" + speed +
                ", hitPoint=" + HP +
                ", damage=" + damage +
                ", attackRange=" + attackRange +
                ", stateUnits=" + stateUnits +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitPrototype that = (UnitPrototype) o;
        return x == that.x && y == that.y && owner.equals(that.owner) && name.equals(that.name);
    }
}
