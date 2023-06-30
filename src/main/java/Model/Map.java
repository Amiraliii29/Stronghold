package Model;

import Controller.GameMenuController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Map {
    // toole mehvar ofogi = width  moalefe ofoghi = x
    // toole mehvar amoudi = length  moalefe amoudi = y
    private final String name;
    private final Square[][] squares;
    private ArrayList<Government> governmentsInMap;
    private final int width;
    private final int length;



    public Map(String name, int width, int length) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.squares = new Square[width + 1][length + 1];
        for (int i = 0; i < width + 1; i++) {
            for (int j = 0; j < length + 1; j++) {
                squares[i][j] = new Square(i, j);
                if (i == width || j >= length) squares[i][j].setLand(Land.CLIFF);
            }
        }
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

    public ArrayList<Government> getGovernmentsInMap() {
        return governmentsInMap;
    }

    public boolean isCoordinationValid(int x, int y) {
        return x < width && x >= 0 && y >= 0 && y < length;
    }







    public void constructBuilding(BuildingPrototype building, int x, int y) {
        for (int i = x; i < x + building.getWidth(); i++) {
            for (int j = y; j < y + building.getLength(); j++) {
                squares[i][j].setBuilding(building);
            }
        }
    }

    public boolean doesSquareContainEnemyUnits(int x, int y, Government owner) {
        Square targetSquare = squares[x][y];

        for (UnitPrototype unit : targetSquare.getUnits())
            if (!owner.equals(unit.getOwner())) return true;

        return false;
    }



    public static void saveMap(Map map, String fileName) {
        // filename only without address only name
        // will save it in resources/Map/<fileName>
        try {
            String fileAddress = "src/main/resources/Map/" + fileName + ".json";
            FileWriter file = new FileWriter(fileAddress);
            file.flush();
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
            Type type = new TypeToken<Map>() {}.getType();
            String fileAddress = "src/main/resources/Map/" + fileName + ".json";
            File f = new File(fileAddress);
            if (f.exists() && !f.isDirectory()) {
                DataBase.setSelectedMap(gson.fromJson(new FileReader(fileAddress), type));
                GameMenuController.setMap(DataBase.getSelectedMap());

                for (int i = 0; i < DataBase.getSelectedMap().getWidth() + 1; i++) {
                    for (int j = 0; j < DataBase.getSelectedMap().getLength() + 1; j++) {
                        DataBase.getSelectedMap().squares[i][j].newUnits();
                    }
                }

            } else {
                DataBase.setSelectedMap(null);
                GameMenuController.setMap(null);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
