import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

import Model.Buildings.Building;
import Model.Buildings.Generator;
import Model.Resource;
import Model.Units.Unit;
import View.Input_Output;
import org.json.simple.parser.ParseException;

import Controller.Orders;
import Model.Buildings.Generator;
import View.SignUpMenu;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, NoSuchAlgorithmException {
        Building.readBuildingsFromFile();
        Unit.readUnitsFromFile();
        Resource.readResourcesFromFile();
        SignUpMenu.run();
    }
}