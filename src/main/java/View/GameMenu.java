package View;

import Controller.CustomizeMapController;
import Controller.GameMenuController;
import Controller.Orders;
import Model.DataBase;
import Model.Government;
import View.Enums.Commands.GameMenuCommands;
import View.Enums.Messages.CustomizeMapMessages;
import View.Enums.Messages.GameMenuMessages;

import java.util.regex.Matcher;

public class GameMenu {
    private static int keepCnt;
    public static void run() {
        keepCnt = 0;
        String input;
        Matcher matcher;
        Input_Output.outPut("please put each player's keep");
        while (keepCnt < DataBase.getGovernments().size()) {
            input = Input_Output.getInput();
            input = input.trim();

            if (GameMenuCommands.getMatcher(input, GameMenuCommands.ENTER_SHOW_MAP_MENU) != null) {
                Input_Output.outPut("entered show map menu successfully");
                ShowMapMenu.run();
            }
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.DROP_BUILDING_FOR_CUSTOMIZE)) != null)
                dropBuildingTest(matcher);
            else Input_Output.outPut("invalid command");
        }
        DataBase.setCurrentGovernment(GameMenuController.getCurrentGovernment());
        Input_Output.outPut("game started!");
        while (true) {
            input = Input_Output.getInput();
            input = input.trim();

            if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_UNIT)) != null)
                selectUnit(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.MOVE_UNIT)) != null)
                moveUnit(matcher);
            else if (((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.PATRON_UNIT)) != null))
                patrol(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SET_STATE)) != null)
                setUnitMode(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.ATTACK_GROUND)) != null)
                attack(matcher);
            else if (((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.ATTACK_AIR)) != null))
                attackAir(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.DIG_TUNNEL)) != null)
                digTunnel(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.POUR_OIL)) != null)
                pourOil(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.BUILD_SIEGE)) != null)
                buildEquipment(matcher);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.DISBAND) != null)
                disbandUnit();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.DROP_BUILDING) != null)
                dropBuilding(matcher);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_BUILDING) != null)
                selectBuilding(matcher);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.CREATE_UNIT) != null)
                createUnit(matcher);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.REPAIR_BUILDING) != null)
                repair();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_TURNS_PASSED) != null)
                showTurnsPassed();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_CURRENT_GOVERNMENT) != null)
                showCurrentPlayer();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.ENTER_SHOW_MAP_MENU) != null) {
                Input_Output.outPut("entered show map menu successfully");
                ShowMapMenu.run();
            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SET_TAX_RATE)) != null)
                setTaxRate(matcher);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_TAX_RATE) != null)
                showTaxRate();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_POPULARITY_FACTORS) != null)
                showPopularityFactors();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_POPULARITY) != null)
                showPopularity();
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_FOOD_LIST) != null)
                showFoodList();
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SET_FOOD_RATE)) != null)
                setFoodRate(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SET_FEAR_RATE)) != null)
                setFearRate(matcher);
            else if (GameMenuCommands.getMatcher(input, GameMenuCommands.NEXT_TURN) != null)
                nextTurn();
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.DROP_BUILDING_FOR_CUSTOMIZE)) != null)
                dropBuildingTest(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.DROP_UNIT)) != null)
                dropUnit(matcher);
            else Input_Output.outPut("invalid command");
        }
    }

    private static void showCurrentPlayer() {
        Input_Output.outPut("current player:" + GameMenuController.getCurrentGovernmentUsername());
    }

    private static void showTurnsPassed() {
        Input_Output.outPut("turns passed: " + GameMenuController.getTurnsPassed());
    }

    private static void setFearRate(Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        GameMenuMessages message = GameMenuController.setFearRateController(rateNumber);
        switch (message) {
            case INVALID_FEAR_RATE:
                Input_Output.outPut("set fear error: invalid rate number");
                break;
            case SET_FEAR_RATE_SUCCESS:
                Input_Output.outPut("fear rate set successfully");
                break;
        }
    }

    private static void setFoodRate(Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        GameMenuMessages message = GameMenuController.setFoodRateController(rateNumber);

        switch (message) {
            case SET_FOOD_RATE_SUCCESS:
                Input_Output.outPut("food rate set successfully");
                break;
            case INVALID_FOOD_RATE:
                System.out.println("set food rate error: invalid food rate");
                break;
        }
    }

    private static void showFoodList() {
        String toPrint = GameMenuController.showFoodListController();

        Input_Output.outPut(toPrint);
    }

    private static void showPopularity() {
        Government government = DataBase.getCurrentGovernment();
        government.updatePopularity();

        Input_Output.outPut("popularity: " + government.getPopularity());
    }

    private static void showPopularityFactors() {
        Government myGovernment = DataBase.getCurrentGovernment();
        String toPrint = "";
        toPrint += "tax rate: " + myGovernment.getTax() + "\n";
        toPrint += "food rate: " + myGovernment.getFood() + "\n";
        toPrint += "food types: " + myGovernment.getFoodType() + "\n";
        toPrint += "fear rate: " + myGovernment.getFear() + "\n";
        toPrint += "faith amount: " + myGovernment.getFaith();

        Input_Output.outPut(toPrint);
    }

    private static void showTaxRate() {
        int taxRate = DataBase.getCurrentGovernment().getTax();

        Input_Output.outPut("you tax rate: " + taxRate);
    }

    private static void setTaxRate(Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));

        GameMenuMessages message = GameMenuController.setTaxRateController(rateNumber);

        switch (message) {
            case INVALID_TAX_RATE:
                Input_Output.outPut("invalid tax amount");
                break;
            case SET_TAX_SUCCESS:
                Input_Output.outPut("tax rate set successfully");
                break;
        }
    }

    private static void nextTurn() {
        GameMenuMessages message = GameMenuController.nextTurnController();
        switch (message) {
            case END ->
                    Input_Output.outPut("Game ended!\n" + "player " + DataBase.getGovernments().get(0).getOwner().getUsername() + " Won");
            case SUCCESS ->
                    Input_Output.outPut("now is " + DataBase.getCurrentGovernment().getOwner().getUsername() + " Turn");
        }
    }

    private static void userLogout() {
    }

    private static void dropBuilding(Matcher matcher) {
        String buildingComponents = matcher.group("buildingComponents");
        String x = Orders.findFlagOption("-x", buildingComponents);
        String y = Orders.findFlagOption("-y", buildingComponents);
        String buildingName = Orders.findFlagOption("-type", buildingComponents);

        if (x == null || y == null || buildingName == null) {
            Input_Output.outPut("error: empty necessary fields!");
            return;
        }

        GameMenuMessages result = GameMenuController.dropBuildingController(x, y, buildingName);
        switch (result) {
            case WRONG_FORMAT_COORDINATE -> Input_Output.outPut("error: coordination format is invalid!");
            case INVALID_COORDINATE -> Input_Output.outPut("error: coordination is out of maps' bounds!");
            case DROPBUILDING_INVALID_BUILDINGNAME ->
                    Input_Output.outPut("error: there is no building with such name!");
            case DROPBUILDING_INVALID_PLACE ->
                    Input_Output.outPut("error: can't build there! either incompatible or already occupied land!");
            case INSUFFICIENT_GOLD -> Input_Output.outPut("error: you don't have enough gold for this operation!");
            case INSUFFICIENT_RESOURCES ->
                    Input_Output.outPut("error: you don't have enough resources for this building!");
            default -> Input_Output.outPut("the building was succesfully built!");
        }
    }

    private static void selectBuilding(Matcher matcher) {
        String buildingComponents = matcher.group("buildingComponents");
        String x = Orders.findFlagOption("-x", buildingComponents);
        String y = Orders.findFlagOption("-y", buildingComponents);

        if (x == null || y == null) {
            Input_Output.outPut("error: empty necessary fields!");
            return;
        }

        GameMenuMessages result = GameMenuController.selectBuildingController(x, y);
        switch (result) {
            case WRONG_FORMAT_COORDINATE -> Input_Output.outPut("error: coordination format is invalid!");
            case INVALID_COORDINATE -> Input_Output.outPut("error: coordination is out of maps' bounds!");
            case SELECTBUILDING_EMPTY_SQUARE ->
                    Input_Output.outPut("error: the selected square doesn't have a building!");
            case SELECTBUILDING_UNOWNED_BUILDING ->
                    Input_Output.outPut("error: the building you want to select is not yours!");
            default -> Input_Output.outPut("succesfully selected the building!");
        }


    }

    private static void createUnit(Matcher matcher) {
        String inputComponents = matcher.group("unitComponents");
        String type = Orders.findFlagOption("-t", inputComponents);
        String count = Orders.findFlagOption("-c", inputComponents);

        if (type == null || count == null) {
            Input_Output.outPut("error: empty necessary fields!");
            return;
        }
        GameMenuMessages result = GameMenuController.createUnitController(type, count);
        switch (result) {
            case CREATE_UNIT_WRONG_SELECTED_BUILDING ->
                    Input_Output.outPut("error: the selected building is not a barracks!");
            case CREATEUNIT_WRONG_NUMBERFORMAT -> Input_Output.outPut("error: invalid format or number of units!");
            case CREATEUNIT_UNMATCHING_BARRACK ->
                    Input_Output.outPut("error: the selected barracks can't build such unit!");
            case INSUFFICIENT_GOLD -> Input_Output.outPut("error: you don't have enough gold for this operation!");
            case INSUFFICIENT_RESOURCES ->
                    Input_Output.outPut("error: you don't have enough weapons for the trainings!");
            case CREATEUNIT_INSUFFICIENT_FREEPOP ->
                    Input_Output.outPut("error: you don't have enough population of free workers!");
            default -> Input_Output.outPut("succesfully trained the troops!");
        }

    }

    private static void repair() {
        GameMenuMessages message = GameMenuController.repairController();
        switch (message) {
            case REPAIR_UNREPAIRABLE_SELECTED_BUILDING ->
                    Input_Output.outPut("error: the selected building is not repairable!");
            case EMPTY_INPUT_FIELDS_ERROR -> Input_Output.outPut("error: nothing is selected!");
            case INSUFFICIENT_RESOURCES -> Input_Output.outPut("error: insufficient stone to continue the repairing!");
            default -> Input_Output.outPut("building is now fully repaired!");
        }
    }

    private static void selectUnit(Matcher matcher) {
        String option = matcher.group("option");
        GameMenuMessages message = GameMenuController.selectUnitController(option);
        switch (message) {
            case SUCCESS -> Input_Output.outPut("unit selected");
            case INVALID_COORDINATE -> Input_Output.outPut("invalid coordinate!");
            case INVALID_TROOP_TYPE -> Input_Output.outPut("we don't have a unit with this name");
            case WRONG_FORMAT_COORDINATE -> Input_Output.outPut("coordinate is invalid!");
            case THERE_IS_NO_UNIT -> Input_Output.outPut("you dont have any unit with this name here");
        }
    }

    private static void moveUnit(Matcher matcher) {
        String coordinate = matcher.group("coordinate");
        GameMenuMessages message = GameMenuController.moveUnitController(coordinate);
        switch (message) {
            case WRONG_FORMAT_COORDINATE -> Input_Output.outPut("wrong format for coordinate");
            case INVALID_COORDINATE -> Input_Output.outPut("invalid coordinate");
            case CANT_GO_THERE -> Input_Output.outPut("cant go there");
            case SUCCESS -> Input_Output.outPut("units moved successfully");
        }
    }

    private static void patrol(Matcher matcher) {

    }

    private static void setUnitMode(Matcher matcher) {
        String option = matcher.group("option");
        GameMenuMessages message = GameMenuController.setUnitModeController(option);
        switch (message) {
            case SUCCESS -> Input_Output.outPut("state changed");
            case INVALID_COORDINATE -> Input_Output.outPut("invalid coordinate!");
            case WRONG_FORMAT_COORDINATE -> Input_Output.outPut("coordinate is invalid!");
            case INVALID_STATE -> Input_Output.outPut("invalid state!");
        }
    }

    private static void attackAir(Matcher matcher) {
        String input = matcher.group("coordinates");
        String targetX = Orders.findFlagOption("-x", input);
        String targetY = Orders.findFlagOption("-y", input);

        if (targetX == null || targetY == null) {
            Input_Output.outPut("error: empty coordination fields!");
            return;
        }

        GameMenuMessages result = GameMenuController.rangedAttackController(targetX, targetY);
        switch (result) {
            case SUCCESS ->
                    Input_Output.outPut("the selected units performed a ranged attack on the target units successfully!");
            case CHOSE_UNIT_FIRST -> Input_Output.outPut("chose a unit first");
            case WRONG_FORMAT_COORDINATE -> Input_Output.outPut("error: coordination format is invalid!");
            case INVALID_COORDINATE -> Input_Output.outPut("error: coordination is out of map's bounds!");
            case ATTACK_NO_ENEMY_IN_AREA -> Input_Output.outPut("error: the selected square doesn't have enemy!");
            case RANGEDATTACK_NON_ARCHER_SELECTION -> Input_Output.outPut("error: the selected units are not archers!");
            case RANGEDATTACK_TARGET_NOT_IN_RANGE ->
                    Input_Output.outPut("error: the target square is not in the range of archers!");
            default -> {
            }
        }
    }

    private static void attack(Matcher matcher) {
        String targetX = matcher.group("x");
        String targetY = matcher.group("y");

        GameMenuMessages result = GameMenuController.attackController(targetX, targetY);
        switch (result) {
            case SUCCESS ->
                    Input_Output.outPut("the selected units performed a ranged attack on the target units successfully!");
            case WRONG_FORMAT_COORDINATE -> Input_Output.outPut("error: coordination format is invalid!");
            case CHOSE_UNIT_FIRST -> Input_Output.outPut("chose a unit first");
            case INVALID_COORDINATE -> Input_Output.outPut("error: coordination is out of map's bounds!");
            case ATTACK_NO_ENEMY_IN_AREA -> Input_Output.outPut("error: there is no enemy there!");
            case NORMALATTACK_TARGET_NOT_IN_RANGE ->
                    Input_Output.outPut("error: the units are not able to reach the target to perform attack!");
            default -> {
            }
        }
    }

    private static void pourOil(Matcher matcher) {
        String direction = matcher.group("direction");
        GameMenuMessages message = GameMenuController.pourOilController(direction);
        switch (message) {

        }
    }

    private static void digTunnel(Matcher matcher) {
        String coordinate = matcher.group("coordinate");
        GameMenuMessages message = GameMenuController.digTunnelController(coordinate);

    }

    private static void buildEquipment(Matcher matcher) {
        String siegeName = matcher.group("siegeName");
        GameMenuMessages message = GameMenuController.buildEquipmentController(siegeName);
        switch (message) {
            case WRONG_NAME -> System.out.println("there is no siege with this name");
            case CHOSE_UNIT_FIRST -> System.out.println("choose Engineer first");
            case UNIT_ISNT_ENGINEER -> System.out.println("unit is not Engineer");
            case NOT_ENOUGH_ENGINEER -> System.out.println("not enough Engineer");
            case NOT_ENOUGH_BALANCE -> System.out.println("you dont have enough money");
            case CANT_BUILD_HERE -> System.out.println("cant build here");
            case SUCCESS -> System.out.println("siege created");
        }
    }

    private static void disbandUnit() {
        GameMenuMessages message = GameMenuController.disbandUnitController();
        switch (message) {
            case CHOSE_UNIT_FIRST -> System.out.println("choose unit first");
            case SUCCESS -> System.out.println("unit disbanded");
        }
    }

    private static void dropUnit(Matcher matcher) {
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x" , options);
        String y = Orders.findFlagOption("-y" , options);
        String type = Orders.findFlagOption("-t" , options);
        String count = Orders.findFlagOption("-c" , options);
        String onwerGovernmentNumber = Orders.findFlagOption("-g" , options);

        CustomizeMapMessages message = CustomizeMapController.dropUnitController(x , y , type , count , onwerGovernmentNumber);

        switch (message){
            case INVALID_GOVERNMENT_NUMBER:
                System.out.println("drop unit error: invalid government number");
                break;
            case NO_OWNER_GOVERNMENT_NUMBER:
                System.out.println("drop unit error: please enter owner government number after " +
                        " -g flag next time");
                break;
            case INVALID_NUMBER:
                System.out.println("drop unit error: invalid number");
                break;
            case INVALID_OPTIONS:
                System.out.println("drop unit error: please enter x and y component");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("drop unit error: x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("drop unit error: y out of bounds");
                break;
            case INVALID_COUNT:
                System.out.println("drop unit error: invalid count");
                break;
            case UNSUITABLE_LAND:
                System.out.println("drop unit error: unsuitable land to drop unit");
                break;
            case DROP_UNIT_SUCCESS:
                System.out.println("unit dropped successfully");
                break;
            case NO_MAP_SELECTED:
                System.out.println("drop unit error: please first select your map");
                break;
        }
    }

    private static void dropBuildingTest(Matcher matcher) {
        String options = matcher.group("options");
        String x = Orders.findFlagOption("-x", options);
        String y = Orders.findFlagOption("-y", options);
        String type = Orders.findFlagOption("-t", options);
        String governmentNumber = Orders.findFlagOption("-g", options);

        CustomizeMapMessages message = CustomizeMapController.dropBuildingController(x, y, type, governmentNumber);

        switch (message) {
            case INVALID_NUMBER -> Input_Output.outPut("drop building error: invalid number");
            case INVALID_OPTIONS -> Input_Output.outPut("drop building error: please enter x and y component");
            case X_OUT_OF_BOUNDS -> Input_Output.outPut("drop building error: x out of bounds");
            case Y_OUT_OF_BOUNDS -> Input_Output.outPut("drop building error: y out of bounds");
            case INVALID_BUILDING_NAME -> Input_Output.outPut("drop building error: invalid building name");
            case DROP_BUILDING_SUCCESS -> Input_Output.outPut("building dropped successfully");
            case UNSUITABLE_LAND -> Input_Output.outPut("drop building error: can't place there my lord");
            case INVALID_GOVERNMENT_NUMBER -> Input_Output.outPut("drop building error: invalid government number");
            case THIS_GOVERNMENT_HAS_KEEP -> Input_Output.outPut("this government has keep");
            case NO_OWNER_GOVERNMENT_NUMBER ->
                    Input_Output.outPut("drop building error: please enter owner government number after -g flag next time");
            case DROPBUILDING_INVALID_PLACE ->
                    Input_Output.outPut("error: can't build there! either incompatible or already occupied land!");
            case NO_MAP_SELECTED -> Input_Output.outPut("drop building error: please first select your map");
        }
    }

    public static void addKeepCnt() {
        keepCnt++;
    }
}
