package Model;

import Controller.GameMenuController;
import Model.Buildings.Building;
import Model.Units.Unit;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class HashMaps {
    private static HashMap<String, Image> tiles;
    private static HashMap<String, Image> units;
    private static HashMap<String, Image> buildings;


    static {
        tiles = new HashMap<>();
        units = new HashMap<>();
        buildings = new HashMap<>();
    }

    public static void loadImages() throws FileNotFoundException {
        //tiles :
        Image image = new Image(new FileInputStream("src/main/resources/Images/tiles/default.jpg"));
        tiles.put("default", image);
//        for (Land land : Land.values()) {
//            Image image = new Image(new FileInputStream("src/main/resourcesImages/tiles/" + land.name() + "."));
//            tiles.put(land.name(), image); // check for .name()  or getName  //TODO
//        }
//        //units :
//        for (String unit : Unit.getAllUnits()) {
//            Image image = new Image(new FileInputStream("src/main/resourcesImages/units/" + unit + "."));
//            tiles.put(unit, image);
//        }
//        //buildings :
//        for (String building : Building.getBuildings()) {
//            Image image = new Image(new FileInputStream("src/main/resourcesImages/units/" + building + "."));
//            tiles.put(building, image);
//        }
    }

    public static Image getImage(String name) {
        if (tiles.containsKey(name)) return tiles.get(name);
        if (units.containsKey(name)) return units.get(name);
        if (buildings.containsKey(name)) return buildings.get(name);

        return null;
    }

    public static HashMap<String, Image> getTiles() {
        return tiles;
    }

    public static HashMap<String, Image> getUnits() {
        return units;
    }

    public static HashMap<String, Image> getBuildings() {
        return buildings;
    }
}
