package Model.PeoplePac;
import Model.Government;

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

    public Unit(Government owner, String name, int speed, int hitPoint, int damage,
                int attackRange, State state, int cost) {
        this.owner = owner;
        this.name = name;
        this.speed = speed;
        this.hitPoint = hitPoint;
        this.damage = damage;
        this.attackRange = attackRange;
        this.state = state;
        this.cost = cost;
        owner.addUnits(this);
    }

    public void setCoordinate(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
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
