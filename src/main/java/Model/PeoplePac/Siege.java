package Model.PeoplePac;

import Model.Government;
import Model.Resources.Resource;

import java.util.ArrayList;

public class Siege extends Unit{
    private int engineerNeed;
    private ArrayList<Engineer> engineers;

    //resource they throw //TODO

    {
        engineers = new ArrayList<>();
    }

    public Siege(Government owner, String name, int speed, int hitPoint, int damage,
                 int attackRange, State state, int cost, int engineerNeed) {
        super(owner, name, speed, hitPoint, damage, attackRange, state, cost);
        this.engineerNeed = engineerNeed;
    }

    public int getEngineerNeed() {
        return engineerNeed;
    }

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }

    public void addEngineers(Engineer engineer) {
        this.engineers.add(engineer);
    }
}
