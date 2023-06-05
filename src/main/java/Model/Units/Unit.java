package Model.Units;
import Model.Buildings.Building;
import Model.Buildings.Defence;
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
        Troop.load();
        Siege.load();
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
        DataBase.getSelectedMap().getSquareFromMap(this.yCoordinate, this.xCoordinate).removeUnit(this);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        DataBase.getSelectedMap().getSquareFromMap(yCoordinate, xCoordinate).addUnit(this);
    }

    public void setOwner(Government owner) {
        this.owner = owner;
    }

    public void setState(StateUnits stateUnits) {
        this.stateUnits = stateUnits;
    }

    public void changeHitPoint(int damage) {
        hitPoint -= damage;
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
        Building building = DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).getBuilding();
        if (building instanceof Defence && this.attackRange > 5) {
            return this.attackRange + ((Defence) building).getRange();
        }
        return this.attackRange;
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
        if (stateUnits.equals(StateUnits.Aggressive))
            return (int) (Math.floor(attackRange+speed));
        else if (stateUnits.equals(StateUnits.Defensive))
            return (int) (Math.floor(attackRange));
        else
            return (int) (Math.floor(attackRange+speed*0.5));
    }

    public static void load() {
        return;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(owner, unit.owner) && Objects.equals(name, unit.name);
    }
}
