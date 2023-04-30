package Model.Units;
import Model.Government;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Unit {
    protected static ArrayList<String> allUnits;
    protected Government owner;
    protected String name;
    protected int xCoordinate;
    protected int yCoordinate;
    protected int speed;
    protected int hitPoint;
    protected int damage;
    protected int attackRange;
    protected State state;
    protected int cost;

    static {
        allUnits = new ArrayList<>();
        allUnits.add("Engineer");
        allUnits.add("Tunneler");
        allUnits.add("LadderMan");
        Troop.createTroop(null, null, 1, 1);
        Siege.createSiege(null, null, 1, 1);
    }

    public Unit(Government owner, String name, int speed, int hitPoint, int damage, int attackRange, int cost) {
        this.owner = owner;
        this.name = name;
        this.speed = speed;
        this.hitPoint = hitPoint;
        this.damage = damage;
        this.attackRange = attackRange;
        this.cost = cost;
        this.state = State.Stan_Ground;
    }

    public void setCoordinate(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public void setOwner(Government owner) {
        this.owner = owner;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int changeHitPoint(int damage) {
        hitPoint -= damage;
        return hitPoint;
    }

    public Government getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getDamage() {
        double damageFinal = 100 + (owner.getFear() * (-5));
        damageFinal = Math.floor((damage * damageFinal) / 100);
        return ((int) (damageFinal));
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void changeState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public int getCost() {
        return cost;
    }

    public static ArrayList<String> getAllUnits() {
        return allUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(owner, unit.owner) && Objects.equals(name, unit.name);
    }
}
