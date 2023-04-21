import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.json.simple.*;
import org.json.simple.parser.ParseException;

import Controller.JsonConverter;
import Controller.Orders;
import Controller.SignUpMenuController;
import Model.DataBase;
import Model.User;
import View.SignUpMenu;

public class Main {
    
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, NoSuchAlgorithmException {
        Scanner scanner= new Scanner(System.in);
       SignUpMenu.run(scanner);
}
}