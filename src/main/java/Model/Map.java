package Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Model.Buildings.Building;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

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

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public Square getSquareFromMap(int x, int y) {
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


    public  boolean canConstructBuildingInPlace(Building building, int x, int y){
        boolean check=false;
        
        for (int i = x; i < x+building.getLength(); i++) {
            for (int j = y; j <y+building.getWidth(); j++) {
                

                for (String validLand : building.getLands()) {

                    if(squares[i][j].getLand().equals(validLand))
                        check=true;
                    if(squares[i][j].getBuilding()!=null)
                        return false;
                }

                if(!check) return false;
                else check=false;
            }
        }
        return true;
    }

    public boolean isCoordinationValid (int x, int y){
        if(x>width || x<0 || y<0 || y>width)
            return false;
        return true;
    }

    public void constructBuilding(Building building, int x , int y){

        for (int i = x; i < x+building.getLength(); i++) {
            for (int j = y; j <y+building.getWidth(); j++) {
                
                squares[i][j].setBuilding(building);
            }
        }
    }

    public static void saveMap(Square[][] squares, String fileName) {
        // filename only without address only name
        // will save it in resources/Map/<fileName>
        try {
            String fileAddress = "src/main/resources/Map/" + fileName + ".json";
            FileWriter file = new FileWriter(fileAddress);
            Gson gson = new Gson();
            gson.toJson(map, file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMap(String fileName) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<Map>(){}.getType();
            String fileAddress = "src/main/resources/Map/" + fileName + ".json";
            DataBase.setSelectedMap(gson.fromJson(new FileReader(fileAddress), type));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
