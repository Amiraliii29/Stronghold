package View;

import Controller.GameMenuController;
import Model.DataBase;
import Model.Square;
import Model.UnitPrototype;
import Model.Units.Unit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;

public class MoveAnimation {
    private Timeline timeline;
    private int squareI;
    private int squareJ;
    private UnitPrototype unit;
    private ArrayList<UnitPrototype> units;
    private ArrayList<Square> squares;



    public MoveAnimation(ArrayList<UnitPrototype> units, int squareI, int squareJ) {
        this.squareI = squareI;
        this.squareJ = squareJ;
        this.units = units;
        unit = units.get(0);

        squares = GameMenuController.moveUnit(unit, squareI, squareJ);

        timeline = new Timeline(new KeyFrame(Duration.millis(2000.0 / unit.getSpeed()), actionEvent -> {
            move();
        }));

        if (squares == null || squares.size() == 0) timeline.setCycleCount(0);
        else timeline.setCycleCount(squares.size());
    }


    protected void move() {
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


    public void play() {
        timeline.play();
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }
}
