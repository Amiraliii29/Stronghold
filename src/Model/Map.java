package Model;

import java.util.ArrayList;

public class Map {
    private String name;
    private ArrayList<Square> squares;
    private int x;
    private int y;

    public Map(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.squares = new ArrayList<>();
        //create squares
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }
}
