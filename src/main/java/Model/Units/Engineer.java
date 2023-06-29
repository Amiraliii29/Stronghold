package Model.Units;

import Model.DataBase;
import Model.Government;

public class Engineer extends Unit {
    private transient boolean busy;

    private Engineer(Government owner) {
        super(owner, "Engineer", 2, 50, 0, 0, 15);
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public static Engineer createEngineer(Government owner, int xCoordinate, int yCoordinate) {
        Engineer newEngineer = new Engineer(owner);
        newEngineer.xCoordinate = xCoordinate;
        newEngineer.yCoordinate = yCoordinate;
        newEngineer.busy = false;
        if (xCoordinate >= 0 && yCoordinate >= 0)
            owner.getDataBase().getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).addUnit(newEngineer);
        return newEngineer;
    }
}
