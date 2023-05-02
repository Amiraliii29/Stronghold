import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;


import Model.Buildings.Generator;
import View.Input_Output;
import org.json.simple.parser.ParseException;
import java.util.Iterator;
import Model.Buildings.Generator;
import View.SignUpMenu;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, NoSuchAlgorithmException {
        HashMap<String,Integer> hm = new HashMap<>();
        
        hm.put("Cricket", 5);
        hm.put("Football", 8);
        hm.put("Tennis", 11);
        
        Iterator keySetIterator = hm.keySet().iterator();
        
        while (keySetIterator.hasNext())
        {
            String key = keySetIterator.next().toString();
            
            System.out.println("Key : "+key+"   Value : "+hm.get(key));
        }
    }
}