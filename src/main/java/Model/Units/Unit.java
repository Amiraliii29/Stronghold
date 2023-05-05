package Model.Units;
import Model.DataBase;
import Model.Government;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Unit {
    protected static ArrayList<String> allUnits;
    protected boolean didFight;
    protected Government owner;
    protected String name;
    protected int xCoordinate;
    protected int yCoordinate;
    protected int speed;
    protected int hitPoint;
    protected int damage;
    protected int attackRange;
    protected StateUnits stateUnits;
    protected int cost;
    protected int moveLeft;

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
        this.stateUnits = StateUnits.Stan_Ground;
        this.moveLeft = speed;
        this.didFight=false;
    }

    public void setCoordinate(int xCoordinate, int yCoordinate) {
        DataBase.getSelectedMap().getSquareFromMap(this.xCoordinate, this.yCoordinate).removeUnit(this);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).addUnit(this);
    }

    public void setOwner(Government owner) {
        this.owner = owner;
    }

    public void setState(StateUnits stateUnits) {
        this.stateUnits = stateUnits;
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

    public void changeState(StateUnits stateUnits) {
        this.stateUnits = stateUnits;
    }

    public StateUnits getState() {
        return stateUnits;
    }

    public int getCost() {
        return cost;
    }

    public static ArrayList<String> getAllUnits() {
        return allUnits;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(int moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setDidFight(boolean value){
        this.didFight=value;
    }

    public boolean getDidFight(){
        return didFight;
    }

    public int getAggressionRange(){
        
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(owner, unit.owner) && Objects.equals(name, unit.name);
    }
}
