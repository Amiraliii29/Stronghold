package Model.PeoplePac;

import Model.Government;
import Model.Resources.Resource;

import java.util.ArrayList;

public class Troop extends Unit{
    private boolean climbLadder;
    private boolean digMoat;


    public Troop(Government owner, String name, int speed, int hitPoint, int damage, int attackRange,
                 ArrayList<Resource> resources, State state, int cost, boolean climbLadder, boolean digMoat) {
        super(owner, name, speed, hitPoint, damage, attackRange, resources, state, cost);
        this.climbLadder = climbLadder;
        this.digMoat = digMoat;
    }

    public boolean isClimbLadder() {
        return climbLadder;
    }

    public boolean isDigMoat() {
        return digMoat;
    }
}
