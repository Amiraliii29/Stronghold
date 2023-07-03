package Model.Units;
import Model.Buildings.Building;
import Model.Buildings.Defence;
import Model.DataBase;
import Model.Government;
import Model.Square;
import Model.UnitPrototype;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Unit {
    protected static ArrayList<String> allUnits;
    protected static final int maxDistance;

    protected transient Government owner;
    protected String name;
    protected int xCoordinate;
    protected int yCoordinate;
    protected int speed;
    protected int hitPoint;
    protected int damage;
    protected int attackRange;
    protected StateUnits stateUnits;
    protected int cost;

    static {
        allUnits = new ArrayList<>();
        maxDistance = 5;
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
    }


    public void setCoordinate(int xCoordinate, int yCoordinate) {
        owner.getDataBase().getSelectedMap().getSquareFromMap(this.xCoordinate, this.yCoordinate).removeUnit(this);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        owner.getDataBase().getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).addUnit(this);
    }

    public void setOwner(Government owner) {
        this.owner = owner;
    }

    public void setState(StateUnits stateUnits) {
        this.stateUnits = stateUnits;
    }

    public void setHitPoint(int hp) {
        hitPoint = hp;
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

    public Square getSquare() {
        return owner.getDataBase().getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate);
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
        Building building = owner.getDataBase().getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).getBuilding();
        if (building instanceof Defence && this.attackRange > 5) {
            return this.attackRange + ((Defence) building).getRange();
        }
        return this.attackRange;
    }

    public StateUnits getState() {
        return stateUnits;
    }

    public int getCost() {
        return cost;
    }

    public int getAggressionRange(){
        if (stateUnits.equals(StateUnits.Aggressive))
            return (int) (Math.floor(attackRange+speed));
        else if (stateUnits.equals(StateUnits.Defensive))
            return (int) (Math.floor(attackRange));
        else
            return (int) (Math.floor(attackRange+speed*0.5));
    }



    public static ArrayList<String> getAllUnits() {
        return allUnits;
    }

    public static int getMaxDistance() {
        return maxDistance;
    }

    public static void load() {
        return;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Unit)
            return Objects.equals(owner, ((Unit) o).owner) && Objects.equals(name, ((Unit) o).name);
        else if (o instanceof UnitPrototype)
            return Objects.equals(owner, ((UnitPrototype) o).owner) && Objects.equals(name, ((UnitPrototype) o).name);
        else return false;
    }
}
