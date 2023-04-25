package Model;

import java.util.ArrayList;

public class Map {
    private String name;
    private Square[][] squares;
    private int width;
    private int length;

    public Map(String name, int width, int length) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.squares = new Square[width][length];
        //create squares
    }

    public Square[][] getSquares() {
        return squares;
    }
    public Square getSquareFromMap(int x , int y){
        return squares[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }
}
