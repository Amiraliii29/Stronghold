package View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import Model.Buildings.Building;
import Model.Resource;
import Model.Units.Unit;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        Building.readBuildingsFromFile();
        Unit.readUnitsFromFile();
        Resource.readResourcesFromFile();
        SignUpMenu.run();
    }
}