import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import Model.Buildings.Building;
import Model.Resource;
import Model.Units.Unit;
import org.json.simple.parser.ParseException;

import View.SignUpMenu;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, NoSuchAlgorithmException {
        Building.readBuildingsFromFile();
        Unit.readUnitsFromFile();
        Resource.readResourcesFromFile();
        SignUpMenu.run();
    }
}