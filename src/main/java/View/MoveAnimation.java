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

        this.setCycleDuration(Duration.millis(8000.0 / unit.getSpeed()));
        if (squares == null) this.setCycleCount(0);
        else this.setCycleCount(squares.size());
    }

    @Override
    protected void interpolate(double v) {
        if (squares == null) return;
        if (squares.size() != 0) {
            for (Unit moveUnit : units)
                moveUnit.setCoordinate(squares.get(0).getX(), squares.get(0).getY());

            if (squares.get(0).getBuilding() != null && squares.get(0).getBuilding().getName().equals("Trap")
                    && !squares.get(0).getBuilding().getOwner().equals(DataBase.getCurrentGovernment())) {
                squares.get(0).setBuilding(null);
                squares.get(0).removeUnit(units.get(0));
                DataBase.removeUnit(units.get(0));
                units.remove(units.get(0));
            }

            squares.remove(0);

            DataBase.getGame().drawMap();
        }
    }
}
