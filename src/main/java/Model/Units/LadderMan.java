package Model.Units;

import Model.DataBase;
import Model.Government;

public class LadderMan extends Unit{
    private LadderMan(Government owner) {
        super(owner, "LadderMan", 3, 50, 0, 0, 20);
    }

    public static LadderMan createLadderMan(Government owner, int xCoordinate, int yCoordinate) {
        LadderMan newLadderMan = new LadderMan(owner);
        newLadderMan.xCoordinate = xCoordinate;
        newLadderMan.yCoordinate = yCoordinate;
        if (xCoordinate >= 0 && yCoordinate >= 0)
            DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).addUnit(newLadderMan);
        return newLadderMan;
    }
}
