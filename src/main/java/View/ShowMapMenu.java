package View;

import Controller.ShowMapMenuController;
import Controller.Orders;
import View.Enums.Commands.ShowMapMenuCommands;

import java.util.regex.Matcher;

public class ShowMapMenu {
    public static void run(){
        String input;
        input = Input_Output.getInput();
        Matcher matcher;

        while (true){
            if((matcher = ShowMapMenuCommands.getMatcher(input , ShowMapMenuCommands.SHOW_MAP)) != null)
                showMap(matcher);
            else if((matcher = ShowMapMenuCommands.getMatcher(input , ShowMapMenuCommands.MOVE_MAP)) != null)
                moveMap(matcher);
            else if((matcher = ShowMapMenuCommands.getMatcher(input , ShowMapMenuCommands.SHOW_DETAILS)) != null)
                showMap(matcher);
            else if(ShowMapMenuCommands.getMatcher(input , ShowMapMenuCommands.EXIT) != null)
                exit();
        }
    }
    private static void showMap(Matcher matcher){
        String options = matcher.group("options");

        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);



        String[][] toPrint = ShowMapMenuController.showMapController(x , y);

        for (int i = 0 ; i < 20 ; i++){
            for (int j = 0 ; j < 20 ; j++){
                if(toPrint == null)
                    break;
                System.out.println(toPrint[i][j]);
            }
        }
    }
    private static void moveMap(Matcher matcher){
        String direction = matcher.group("direction");
        String direction2 = matcher.group("direction2");
        String amount = matcher.group("amount");

        String[][] toPrint = ShowMapMenuController.moveMapController(direction , direction2 , amount);

        for (int i = 0 ; i < 20 ; i++){
            for (int j = 0 ; j < 20 ; j++){
                if(toPrint == null)
                    break;
                System.out.println(toPrint[i][j]);
            }
        }
    }
    private static void showDetails(Matcher matcher){
        String x = Orders.findFlagOption("-x" , matcher.group("options"));
        String y = Orders.findFlagOption("-y" , matcher.group("options"));

        String toPrint = ShowMapMenuController.showDetailsController(x , y);

        System.out.print(toPrint);
    }
    private static void exit(){
        Input_Output.outPut("returned back to main menu");
        GameMenu.run();
    }
}
