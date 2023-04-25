package Model.Units;

import Model.Government;
import Model.Resources.Resource;
import Model.Units.Enums.State;

public class Siege extends Unit{
    private int engineerNeed;
    private Resource whatTheyThrow;
    private int howManyLeft;

    public Siege(Government owner, String name, int speed, int hitPoint, int damage, int attackRange,
                 int cost, int engineerNeed, Resource whatTheyThrow, int howManyLeft) {
        super(owner, name, speed, hitPoint, damage, attackRange, cost);
        this.engineerNeed = engineerNeed;
        this.whatTheyThrow = whatTheyThrow;
        this.howManyLeft = howManyLeft;
    }

    public int getEngineerNeed() {
        return engineerNeed;
    }
}
