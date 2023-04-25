package Model.Units;

import Model.Government;
import Model.Units.Enums.State;

public class Siege extends Unit{
    private int engineerNeed;

    //resource they throw //TODO

    public Siege(Government owner, String name, int speed, int hitPoint, int damage,
                 int attackRange, State state, int cost, int engineerNeed) {
        super(owner, name, speed, hitPoint, damage, attackRange, state, cost);
        this.engineerNeed = engineerNeed;
    }

    public int getEngineerNeed() {
        return engineerNeed;
    }
}
