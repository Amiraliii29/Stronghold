package Model.Units;

import Model.Government;

public class Tunneler extends Unit{
    private boolean busy;

    public Tunneler(Government owner) {
        super(owner, name, speed, hitPoint, damage, attackRange, state, cost);
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isBusy() {
        return busy;
    }
}
