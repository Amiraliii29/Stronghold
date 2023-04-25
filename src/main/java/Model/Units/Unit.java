package Model.Units;
import Model.Government;
import Model.Units.Enums.State;

public abstract class Unit {
    private Government owner;
    private String name;
    private int xCoordinate;
    private int yCoordinate;
    private int speed;
    private int hitPoint;
    private int damage;
    private int attackRange;
    private State state;
    private int cost;

    public Unit(String name, int speed, int hitPoint, int damage, int attackRange, int cost) {
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

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getDamage() {
        //look from fear //TODO
        return damage;
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
}
