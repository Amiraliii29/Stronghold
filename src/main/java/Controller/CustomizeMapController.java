package Controller;

import Model.*;

import java.util.ArrayList;
import java.util.Iterator;

public class CustomizeMapController {
    private static ArrayList<Land> lands;

    static {
        lands = new ArrayList<>();
        lands.add(Land.DEFAULT);
        lands.add(Land.MEADOW);
        lands.add(Land.FULL_MEADOW);
        lands.add(Land.GRASS);
        lands.add(Land.GRAVEL);
        lands.add(Land.PLAIN);
        lands.add(Land.SAND);
    }


    public static void putTree(Trees tree, int i, int j) {
        if (i < 0 || j < 0 || i >= DataBase.getSelectedMap().getWidth() || j >= DataBase.getSelectedMap().getLength()) return;

        Square[][] squares = DataBase.getSelectedMap().getSquares();

        if (!lands.contains(squares[i][j].getLand()) || squares[i][j].getTree() != null) return;

        squares[i][j].setTree(tree);
    }

    public static void changeLand(Land land, int i, int j) {
        if (i < 0 || j < 0 || i >= DataBase.getSelectedMap().getWidth() || j >= DataBase.getSelectedMap().getLength()) return;

        Square[][] squares = DataBase.getSelectedMap().getSquares();

        if (squares[i][j].getTree() != null && !lands.contains(land)) return;

        squares[i][j].setLand(land);
    }
}
