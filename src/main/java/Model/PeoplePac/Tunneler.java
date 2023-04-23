package Model.PeoplePac;

import Model.Government;
import Model.Resources.Resource;

import java.util.ArrayList;

public class Tunneler extends Unit{
    private boolean busy;

    public Tunneler(Government owner) {
        super(owner, name, speed, hitPoint, damage, attackRange, resources, state, cost);
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isBusy() {
        return busy;
    }
}
