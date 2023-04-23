package Model.PeoplePac;

import Model.Government;

public class Engineer extends Unit{
    private boolean inSiege;

    public Engineer(Government owner) {
        super(owner, name, speed, hitPoint, damage, attackRange, resources, state, cost);
    }

    public boolean isInSiege() {
        return inSiege;
    }

    public void setInSiege(boolean inSiege) {
        this.inSiege = inSiege;
    }
}
