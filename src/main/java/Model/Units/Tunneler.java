package Model.Units;
import Model.DataBase;
import Model.Government;

public class Tunneler extends Unit{
    private boolean busy;

    private Tunneler(Government owner) {
        super(owner, "Tunneler", 3, 60, 0, 0, 20);
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isBusy() {
        return busy;
    }

    public static Tunneler createTunneler(Government owner, int xCoordinate, int yCoordinate) {
        Tunneler newTunneler = new Tunneler(owner);
        newTunneler.xCoordinate = xCoordinate;
        newTunneler.yCoordinate = yCoordinate;
        if (xCoordinate >= 0 && yCoordinate >= 0)
            DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).addUnit(newTunneler);
        return newTunneler;
    }
}
