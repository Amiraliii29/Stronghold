import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;
import View.Input_Output;
import org.json.simple.parser.ParseException;

import Model.Buildings.Generator;
import View.SignUpMenu;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, NoSuchAlgorithmException {
           HashMap <String , Integer> lol= new HashMap<>();
           lol.put("guz", 2);
           lol.put("guz",3);
           System.out.println(lol.size());

    }
}