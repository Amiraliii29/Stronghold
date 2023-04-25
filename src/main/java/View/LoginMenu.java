package View;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;

import Controller.LoginMenuController;
import Controller.Orders;
import View.Enums.Commands.LoginMenuCommands;

public class LoginMenu {
    public static void run() throws NoSuchAlgorithmException{
        Input_Output.outPut("MAIN MENU:");
        LoginMenuCommands menuEntry=null;
        String input;        
        while (menuEntry==null) {
            Matcher matcher;
            input=Input_Output.getInput();

            if( (matcher=LoginMenuCommands.getMatcher(input,LoginMenuCommands.LOGOUT)) !=null)
                menuEntry=LoginMenuCommands.LOGOUT;

            else if( (matcher=LoginMenuCommands.getMatcher(input,LoginMenuCommands.ENTER_MAP_MENU)) !=null)
                menuEntry=LoginMenuCommands.ENTER_MAP_MENU;

            else if( (matcher=LoginMenuCommands.getMatcher(input,LoginMenuCommands.ENTER_PROFILE_MENU)) !=null )
                menuEntry=LoginMenuCommands.ENTER_PROFILE_MENU;
            
            else Input_Output.outPut("error: invalid command!");
            
            
        }
        LoginMenuController.prepareMenuByType(menuEntry);
        enterMenuByType(menuEntry);

    }

    private static void enterMenuByType(LoginMenuCommands menuName) throws NoSuchAlgorithmException{
        switch (menuName) {
            case LOGOUT:
                SignUpMenu.run();
                break;
            case ENTER_PROFILE_MENU:
                ProfileMenu.run();
                break;
            default:
                break;
        }
    }
}
