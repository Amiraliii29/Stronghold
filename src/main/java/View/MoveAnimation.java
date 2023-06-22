package View;

import Controller.GameMenuController;
import Model.DataBase;
import Model.Square;
import Model.Units.Unit;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MoveAnimation extends Transition {
    private int squareI;
    private int squareJ;
    private Unit unit;
    private ArrayList<Unit> units;
    private ArrayList<Square> squares;



    public MoveAnimation(ArrayList<Unit> units, int squareI, int squareJ) {
        this.squareI = squareI;
        this.squareJ = squareJ;
        this.units = units;
        unit = units.get(0);

        squares = GameMenuController.moveUnit(unit, squareI, squareJ);

        this.setCycleDuration(Duration.millis(2000.0 / unit.getSpeed()));
        if (squares == null) this.setCycleCount(0);
        else this.setCycleCount(squares.size());
    }

    @Override
    protected void interpolate(double v) {
        for (int i = 0; i < squares.size() - 1; i++) {
            if (squares.get(i).getX() == unit.getXCoordinate() && squares.get(i).getY() == unit.getYCoordinate()) {
                //Trap :
                if (squares.get(i + 1).getBuilding() != null && squares.get(i + 1).getBuilding().getName().equals("Trap")
                        && !squares.get(i + 1).getBuilding().getOwner().equals(DataBase.getCurrentGovernment())) {
                    squares.get(i + 1).setBuilding(null);
                    squares.get(i).removeUnit(units.get(0));
                    DataBase.removeUnit(units.get(0));
                    units.remove(units.get(0));
                }

                //Move :
                for (Unit moveUnit : units) {
                    moveUnit.setCoordinate(squares.get(i + 1).getX(), squares.get(i + 1).getY());
                }

                DataBase.getGame().drawMap();
                break;
            }
        }
    }
}
