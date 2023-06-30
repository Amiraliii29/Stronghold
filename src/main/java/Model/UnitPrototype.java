package Model;

import Model.Units.StateUnits;

import java.util.ArrayList;
import java.util.Objects;

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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitPrototype that = (UnitPrototype) o;
        return x == that.x && y == that.y && owner.equals(that.owner) && name.equals(that.name);
    }
}
