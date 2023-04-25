package Model.Units;

import Model.Government;

public class Tunneler extends Unit{
    private boolean busy;

    public Tunneler(Government owner) {
        super(owner, "Tunneler", 3, 60, 0, 0, 20);
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isBusy() {
        return busy;
    }
}
