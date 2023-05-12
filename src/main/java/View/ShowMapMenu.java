package View;

import Controller.ShowMapMenuController;
import Controller.Orders;
import Model.DataBase;
import View.Enums.Commands.ShowMapMenuCommands;

import java.util.regex.Matcher;

public class ShowMapMenu {
    public static void run(){
        String input;
        Matcher matcher;
        Input_Output.outPut("Show Map Menu:");

        while (true){
            input = Input_Output.getInput();
            if((matcher = ShowMapMenuCommands.getMatcher(input , ShowMapMenuCommands.SHOW_MAP)) != null)
                showMap(matcher);
            else if((matcher = ShowMapMenuCommands.getMatcher(input , ShowMapMenuCommands.MOVE_MAP)) != null)
                moveMap(matcher);
            else if((matcher = ShowMapMenuCommands.getMatcher(input , ShowMapMenuCommands.SHOW_DETAILS)) != null)
                showDetails(matcher);
            else if(ShowMapMenuCommands.getMatcher(input , ShowMapMenuCommands.EXIT) != null) {
                Input_Output.outPut("out of Show Map Menu");
                break;
            } else Input_Output.outPut("Invalid command!");

        }
    }
    private static void showMap(Matcher matcher){
        String options = matcher.group("options");

        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);

        String[][] toPrint = ShowMapMenuController.showMapController(x , y);
        int xInt = 1;
        int yInt = 1;

        if( x != null && y != null && ShowMapMenuCommands.getMatcher(x , ShowMapMenuCommands.VALID_NUMBER) != null &&
        ShowMapMenuCommands.getMatcher(y  , ShowMapMenuCommands.VALID_NUMBER) != null) {
            xInt = Integer.parseInt(x);
            yInt = Integer.parseInt(y);
        }
        else{
            System.out.println("show map error: please enter number in x and y option");
            return;
        }

        if(ShowMapMenuController.errorInShowMapFlag == 0) {
            for (int i = -10; i < 10; i++) {
                if (xInt + i > 0 && xInt + i <= DataBase.getSelectedMap().getLength())
                    System.out.printf("(%3d)", xInt + i);
            }
            System.out.println();
        }


        for (int i = 0 ; i < 20 ; i++){
            int enterFlag = 0;

            for (int j = 0 ; j < 20 ; j++){
                if(toPrint[i][j] == null)
                    continue;
                enterFlag = 1;
                System.out.print(toPrint[i][j]);
            }
            if(ShowMapMenuController.errorInShowMapFlag == 0) {
                if (enterFlag == 1)
                    System.out.println("(" + (yInt + i - 10) + ")");
            }
        }
    }
    private static void moveMap(Matcher matcher){
        String direction = matcher.group("direction");
        String direction2 = matcher.group("direction2");
        String amount = matcher.group("amount");

        String[][] toPrint = ShowMapMenuController.moveMapController(direction , direction2 , amount);

        int xInt = ShowMapMenuController.xLocationOnMap;
        int yInt = ShowMapMenuController.yLocationOnMap;

        if(ShowMapMenuController.errorInShowMapFlag == 0) {
            for (int i = -10; i < 10; i++) {
                if (xInt + i > 0 && xInt + i <= DataBase.getSelectedMap().getLength())
                    System.out.printf("(%3d)", xInt + i);
            }
            System.out.println();
        }


        for (int i = 0 ; i < 20 ; i++){
            int enterFlag = 0;

            for (int j = 0 ; j < 20 ; j++){
                if(toPrint[i][j] == null)
                    continue;
                enterFlag = 1;
                System.out.print(toPrint[i][j]);
            }
            if(ShowMapMenuController.errorInShowMapFlag == 0) {
                if (enterFlag == 1)
                    System.out.println("(" + (yInt + i - 10) + ")");
            }
        }
    }

    private static void showDetails(Matcher matcher){
        String x = Orders.findFlagOption("-x" , matcher.group("options"));
        String y = Orders.findFlagOption("-y" , matcher.group("options"));

        Input_Output.outPut(ShowMapMenuController.showDetailsController(y , x));
    }
}
