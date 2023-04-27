package Model.Units;

import Model.Government;

public class LadderMan extends Unit{
    private LadderMan(Government owner) {
        super(owner, "LadderMan", 3, 50, 0, 0, 20);
    }

    public static LadderMan createLadderMan(Government owner, int xCoordinate, int yCoordinate) {
        LadderMan newLadderMan = new LadderMan(owner);
        newLadderMan.xCoordinate = xCoordinate;
        newLadderMan.yCoordinate = yCoordinate;
        return newLadderMan;
    }
}
