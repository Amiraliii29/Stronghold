package Model;

import Controller.GameMenuController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Controller.Orders;
import Model.Buildings.Building;
import Model.Buildings.Defence;
import Model.Units.Unit;

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



    public void setGovernmentsInMap(int count) {
        this.governmentsInMap = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Government government = new Government(2000);
            governmentsInMap.add(government);
        }
    }



    public boolean canConstructBuildingInPlace(Building building, int x, int y) {
        boolean check = false;
        int width;

        if (building.getName().equals("Keep")) width = building.getWidth()  + 4;
        else width = building.getWidth();

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + building.getLength(); j++) {
                for (String validLand : building.getLands()) {
                    if (Land.getName(squares[i][j].getLand()).equals(validLand))
                        check = true;
                    if (squares[i][j].getBuilding() != null || squares[i][j].getTree() != null)
                        return false;
                }
                if (!check) return false;
                check = false;
            }
        }
        return true;
    }

    public ArrayList<int[]> getSquaresWithinRange(int centerX, int centerY, double range, int answerCaretsianZone) {
        double distance;
        int xModifier, yModifier;
        ArrayList<int[]> answers = new ArrayList<>();
        switch (answerCaretsianZone) {
            case 1 -> {
                xModifier = 1;
                yModifier = 1;
            }
            case 2 -> {
                xModifier = -1;
                yModifier = 1;
            }
            case 3 -> {
                xModifier = -1;
                yModifier = -1;
            }
            default -> {
                xModifier = 1;
                yModifier = -1;
            }
        }

        for (int i = 0; i < Math.floor(range); i++)
            for (int j = 0; j < Math.floor(range); j++) {
                distance = getDistance(0, 0, i, j);
                if (distance <= range) {
                    int[] viableCoord = new int[2];
                    viableCoord[0] = centerX + i * xModifier;
                    viableCoord[1] = centerY + j * yModifier;
                    if(!isCoordinationValid(viableCoord[0], viableCoord[1])) continue;

                    answers.add(viableCoord);
                }
            }
        return answers;
    }

    public static double getDistance(int x1, int y1, int x2, int y2) {
        double distancePwr2 = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
        return Math.sqrt(distancePwr2);
    }

    public static int getCartesianZone(int centerX, int centerY, int targetX, int targetY) {
        if (targetX >= centerX && targetY >= centerY) return 1;
        else if (targetX <= centerX && targetY >= centerY) return 2;
        else if (targetX <= centerX) return 3;
        else return 4;
    }

    public void constructBuilding(Building building, int x, int y) {
        for (int i = x; i < x + building.getWidth(); i++) {
            for (int j = y; j < y + building.getLength(); j++) {
                squares[i][j].setBuilding(building);
            }
        }
    }

    public ArrayList<Unit> getSquareUnfriendlyUnits(Government ownGovernment, int x, int y) {
        Square targetSquare = getSquareFromMap(x, y);
        ArrayList<Unit> enemyUnits = new ArrayList<>();

        for (Unit unit : targetSquare.getUnits())
            if (!DataBase.isUnitFriendly(ownGovernment, unit)) enemyUnits.add(unit);

        return enemyUnits;
    }

    public int getSquareUnfriendlyBelongingsType(Government ownGovernment, int x, int y) {
        //0 for nothing, 1 for troops (prime to buildings, except deffences), 2 for buildings
        Square targetSquare = getSquareFromMap(x, y);
        Building targetBuilding = targetSquare.getBuilding();

        if (doesSquareContainEnemyUnits(x, y, ownGovernment))
            return 1;

        if (targetBuilding != null)
            if (!DataBase.isBuildingFriendly(ownGovernment, targetBuilding))
                return 2;

        return 0;
    }

    public void removeBuildingFromMap(Building building) {
        int width, length, cornerX, cornerY;
        width = building.getWidth();
        length = building.getLength();
        cornerX = building.getXCoordinateLeft();
        cornerY = building.getYCoordinateUp();

        for (int i = cornerX; i < width; i++)
            for (int j = cornerY; j < length; j++)
                getSquareFromMap(i, j).setBuilding(null);
    }

    public int[] getAnEnemyCoordInRange(Unit mainUnit) {
        ArrayList<int[]> landsWithinRange = new ArrayList<>();
        int aggressionRange = mainUnit.getAggressionRange();
        int unitX = mainUnit.getXCoordinate();
        int unitY = mainUnit.getYCoordinate();

        for (int cartesianzone = 1; cartesianzone <= 4; cartesianzone++)
            landsWithinRange.addAll(getSquaresWithinRange(unitX, unitY, aggressionRange, cartesianzone));
        
        for (int[] coord : landsWithinRange)
            if (doesSquareContainEnemyUnits(coord[0], coord[1], mainUnit.getOwner()))
                return coord;
        return null;
    }

    public boolean doesSquareContainEnemyUnits(int x, int y, Government owner) {
        Square targetSquare = squares[x][y];

        for (Unit unit : targetSquare.getUnits())
            if (!DataBase.isUnitFriendly(owner, unit)) return true;

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
            } else {
                DataBase.setSelectedMap(null);
                GameMenuController.setMap(null);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
