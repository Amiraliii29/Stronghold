//package Controller;
//
//import java.util.ArrayList;
//
//import Model.*;
//import Model.Buildings.Defence;
//import Model.Buildings.*;
//import Model.Units.Troop;
//import Model.Units.*;
//import View.Enums.Messages.GameMenuMessages;
//import javafx.scene.input.MouseEvent;
//
//import java.util.HashMap;
//import java.util.Objects;
//
//public class FORDELETEGameMenuController {
//    private static Government currentGovernment;
//    private static int turnsPassed = 0;
//    private static Building selectedBuilding = null;
//    private static Map currentMap;
//    private static ArrayList<Unit> allUnits;
//    private static ArrayList<ArrayList<Square>> allWays;
//    private static ArrayList<Square> squares;
//    private static HashMap<Square, String> buildSiege;
//
//    static {
//        buildSiege = new HashMap<>();
//        allWays = new ArrayList<>();
//        squares = new ArrayList<>();
//        allUnits = new ArrayList<>();
//    }
//
//    public static void setCurrentGovernment(Government government) {
//        currentGovernment = government;
//    }
//
//    public static GameMenuMessages nextTurnController() {
//        //remove tent and add sieges
//        for (HashMap.Entry<Square, String> entry : buildSiege.entrySet()) {
//            Siege siege = Siege.createSiege(currentGovernment, entry.getValue(), entry.getKey().getX(), entry.getKey().getY());
//            for (int i = 0; i < Objects.requireNonNull(siege).getEngineerNeed(); i++) {
//                entry.getKey().removeUnit(Engineer.createEngineer(currentGovernment, -1, -1));
//            }
//            entry.getKey().getBuilding().changeHP(-10000);
//            DataBase.removeDestroyedBuildings(entry.getKey().getBuilding());
//        }
//        if (checkForEnd())
//            return GameMenuMessages.END;
//
//        //automatic fights
//        ArrayList<Government> governments = DataBase.getGovernments();
//        int index = governments.indexOf(currentGovernment);
////        if (index == governments.size() - 1)
////            DataBase.handleEndOfTurnFights();
//
//        //change government
//        if (index == governments.size() - 1)
//            currentGovernment = governments.get(0);
//        else
//            currentGovernment = governments.get(index + 1);
//
//        DataBase.setCurrentGovernment(currentGovernment);
//        selectedBuilding = null;
//
//        //work with government
//        currentGovernment.addAndRemoveFromGovernment();
//        DataBase.generateResourcesForCurrentGovernment();
//        DataBase.setSelectedUnit(new ArrayList<>());
//
//        allWays = new ArrayList<>();
//        squares = new ArrayList<>();
//        buildSiege = new HashMap<>();
//
//        if (checkForEnd())
//            return GameMenuMessages.END;
//        return GameMenuMessages.SUCCESS;
//    }
//
//    public static boolean checkForEnd() {
//        ArrayList<Government> governments = DataBase.getGovernments();
//        Square[][] allSquares = DataBase.getSelectedMap().getSquares();
//
//        for (int i = 0; i < governments.size(); i++) {
//            if (governments.get(i).getLord().getHitPoint() <= 0) {
//                //destroy every thing for this government
//                for (Square[] allSquare : allSquares) {
//                    for (int k = 0; k < allSquares[0].length; k++) {
//                        if (allSquare[k].getBuilding() != null
//                                && allSquare[k].getBuilding().getOwner().equals(governments.get(i))) {
//                            allSquare[k].getBuilding().changeHP(-100000);
//                            DataBase.removeDestroyedBuildings(allSquare[k].getBuilding());
//                        }
//                        for (int l = 0; l < allSquare[k].getUnits().size(); l++) {
//                            if (allSquare[k].getUnits().get(l).getOwner().equals(governments.get(i))) {
//                                allSquare[k].removeUnit(allSquare[k].getUnits().get(l));
//                                l--;
//                            }
//                        }
//                    }
//                }
//                governments.remove(i);
//                i--;
//            }
//        }
//
//        allUnits = new ArrayList<>();
//
//        for (Square[] allSquare : allSquares) {
//            for (int k = 0; k < allSquares[0].length; k++) {
//                for (Unit unit : allSquare[k].getUnits()) {
//                    allUnits.add(unit);
//                }
//            }
//        }
//        //if both lord die in one turn !!! //TODO
//        if (governments.size() == 1) return true;
//        return false;
//    }
//
//    public static GameMenuMessages selectBuildingController(String x, String y) {
//        if (!Orders.isInputInteger(x) || !Orders.isInputInteger(y))
//            return GameMenuMessages.WRONG_FORMAT_COORDINATE;
//
//        int xInNum = Integer.parseInt(x) - 1;
//        int yInNum = Integer.parseInt(y) - 1;
//
//        if (!currentMap.isCoordinationValid(xInNum, yInNum))
//            return GameMenuMessages.INVALID_COORDINATE;
//
//        Building targetBuilding = currentMap.getSquareFromMap(xInNum, yInNum).getBuilding();
//        if (targetBuilding == null)
//            return GameMenuMessages.SELECTBUILDING_EMPTY_SQUARE;
//
//        if (!targetBuilding.getOwner().getOwner().getUsername().equals(currentGovernment.getOwner().getUsername()))
//            return GameMenuMessages.SELECTBUILDING_UNOWNED_BUILDING;
//
//        selectedBuilding = targetBuilding;
//        return GameMenuMessages.SUCCESS;
//    }
//
//
//
//
//
//
//    public static GameMenuMessages repairController() {
//        if (selectedBuilding == null)
//            return GameMenuMessages.EMPTY_INPUT_FIELDS_ERROR;
//
//        if (!(selectedBuilding instanceof Defence))
//            return GameMenuMessages.REPAIR_UNREPAIRABLE_SELECTED_BUILDING;
//
//        int hpToRecover = selectedBuilding.getMaximumHp() - selectedBuilding.getHp();
//        int stepsToRepair = hpToRecover / 50 + 1;
//        Resource stone = Resource.getResourceByName("Stone");
//
//        for (int i = 0; i < stepsToRepair + 1; i++) {
//            if (currentGovernment.getResourceInStockpiles(stone) < 5)
//                return GameMenuMessages.INSUFFICIENT_RESOURCES;
//
//            currentGovernment.removeFromStockpile(stone, 5);
//
//            if (selectedBuilding.changeHP(-50) > selectedBuilding.getMaximumHp()) {
//                selectedBuilding.changeHP(selectedBuilding.getHp() - selectedBuilding.getMaximumHp());
//                break;
//            }
//        }
//        return GameMenuMessages.SUCCESS;
//    }
//
//    public static GameMenuMessages selectUnitController(String option) {
//        String x = Orders.findFlagOption("-x", option);
//        String y = Orders.findFlagOption("-y", option);
//        String type = Orders.findFlagOption("-type", option);
//
//        assert x != null;
//        if (!x.matches("^\\d+$") || !Objects.requireNonNull(y).matches("^\\d+$"))
//            return GameMenuMessages.WRONG_FORMAT_COORDINATE;
//
//        if (!Unit.getAllUnits().contains(type))
//            return GameMenuMessages.INVALID_TROOP_TYPE;
//
//        int xCoordinate = Integer.parseInt(x) - 1;
//        int yCoordinate = Integer.parseInt(y) - 1;
//
//        if (!currentMap.isCoordinationValid(xCoordinate, yCoordinate))
//            return GameMenuMessages.INVALID_COORDINATE;
//
//        Square square = DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate);
//        ArrayList<Unit> selectedUnit = new ArrayList<>();
//
//        for (Unit unit : square.getUnits()) {
//            if (unit.getName().equals(type) && unit.getOwner().equals(currentGovernment)) {
//                selectedUnit.add(unit);
//            }
//        }
//
//        if (selectedUnit.size() == 0) return GameMenuMessages.THERE_IS_NO_UNIT;
//        DataBase.setSelectedUnit(selectedUnit);
//        return GameMenuMessages.SUCCESS;
//    }
//
//    public static GameMenuMessages moveUnitController(String coordinate) {
//        String x = Orders.findFlagOption("-x", coordinate);
//        String y = Orders.findFlagOption("-y", coordinate);
//
//        assert x != null;
//        if (!x.matches("^\\d+$") || !Objects.requireNonNull(y).matches("^\\d+$"))
//            return GameMenuMessages.WRONG_FORMAT_COORDINATE;
//
//        int xCoordinate = Integer.parseInt(x) - 1;
//        int yCoordinate = Integer.parseInt(y) - 1;
//
//        if (!currentMap.isCoordinationValid(xCoordinate, yCoordinate))
//            return GameMenuMessages.INVALID_COORDINATE;
//
//        if (DataBase.getSelectedUnit().size() == 0) return GameMenuMessages.CHOSE_UNIT_FIRST;
//
//        if (!DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).canPass())
//            return GameMenuMessages.CANT_GO_THERE;
//
//        if (moveUnit(xCoordinate, yCoordinate)) return GameMenuMessages.SUCCESS;
//        else return GameMenuMessages.CANT_GO_THERE;
//    }
//
//    private static boolean moveUnit(int x, int y) {
//        ArrayList<Unit> unit = DataBase.getSelectedUnit();
//        Map map = DataBase.getSelectedMap();
//
//        int speed = 5;
//        int firstX = unit.get(0).getXCoordinate();
//        int firstY = unit.get(0).getYCoordinate();
//        boolean up = false;
//
//        squares = new ArrayList<>();
//        allWays = new ArrayList<>();
//        if (move(unit.get(0), map, firstX, firstY, x, y, speed, up)) {
//            int size = 1000;
//            int index = 0;
//            int i = 0;
//            for (ArrayList<Square> array : allWays) {
//                if (array.size() < size) {
//                    index = i;
//                    size = array.size();
//                }
//                i++;
//            }
//
//            for (Square square : allWays.get(index)) {
//                if (unit.size() != 0 && square.getBuilding().getName().equals("Trap")) {
//                    unit.remove(0);
//                    square.setBuilding(null);
//                }
//            }
//            boolean check = false;
//            for (Unit unitSelected : unit) {
//                unitSelected.setCoordinate(x, y);
//            }
//            return check;
//        } else return false;
//    }
//
//    private static boolean move(Unit unit, Map map, int x, int y, int xFin, int yFin, int speed, boolean up) {
////        //conditions
////        if (currentMap.isCoordinationValid(x, y)) return false;
////
////        if (!map.getSquareFromMap(x, y).canPass()) return false;
////
////        if (map.getSquareFromMap(x, y).getBuilding() != null) {
////            if (unit instanceof Siege || unit.getName().equals("Knight") || unit.getName().equals("HorseArcher")) {
////
//////                if (!map.getSquareFromMap(x, y).getBuilding().getCanPass(up)
//////                        || map.getSquareFromMap(x, y).getBuilding().getName().equals("Stair"))
//////                    return false;
////                return false;
////
////            } else if (!unit.getName().equals("Assassin")) {
////
////                if (!(map.getSquareFromMap(x, y).getBuilding() instanceof Defence
////                        || map.getSquareFromMap(x, y).getBuilding().getCanPass(up)))
////                    return false;
////
////                LadderMan ladderMan = LadderMan.createLadderMan(DataBase.getCurrentGovernment(), -1, -1);
////                if (map.getSquareFromMap(x, y).getBuilding().getName().equals("Stair")) up = !up;
////
////                else if (map.getSquareFromMap(x, y).getUnits().contains(ladderMan)
////                        && unit instanceof Troop && ((Troop) unit).isClimbLadder()) up = !up;
////
////                if (up && !(map.getSquareFromMap(x, y).getBuilding() instanceof Defence)) return false;
////
////                if (!up && (map.getSquareFromMap(x, y).getBuilding() instanceof Defence
////                        && !map.getSquareFromMap(x, y).getBuilding().getCanPass(false)))
////                    return false;
////            }
////        }
////
////        if (speed >= 0 && x == xFin && y == yFin) {
////            if (map.getSquareFromMap(x, y).getBuilding() != null
////                    && map.getSquareFromMap(x, y).getBuilding() instanceof Defence
////                    && map.getSquareFromMap(x, y).getUnits().size() >= ((Defence) map.getSquareFromMap(x, y).getBuilding()).getCapacity())
////                return false;
////
////            if (map.getSquareFromMap(x,y).getBuilding() != null && map.getSquareFromMap(x,y).getBuilding().getCanPass(up))
////                return false;
////
////            allWays.add(squares);
////            return true;
////        }
////
////        if (speed == 0) return false;
////
////        if (x < map.getWidth() - 1) {
////            squares.add(map.getSquareFromMap(x + 1, y));
////            move(unit, map, x + 1, y, xFin, yFin, speed - 1, up);
////            squares.remove(map.getSquareFromMap(x + 1, y));
////        }
////        if (x > 0) {
////            squares.add(map.getSquareFromMap(x - 1, y));
////            move(unit, map, x - 1, y, xFin, yFin, speed - 1, up);
////            squares.remove(map.getSquareFromMap(x - 1, y));
////        }
////        if (y < map.getLength() - 1) {
////            squares.add(map.getSquareFromMap(x, y + 1));
////            move(unit, map, x, y + 1, xFin, yFin, speed - 1, up);
////            squares.remove(map.getSquareFromMap(x, y + 1));
////        }
////        if (y > 0) {
////            squares.add(map.getSquareFromMap(x, y - 1));
////            move(unit, map, x, y - 1, xFin, yFin, speed - 1, up);
////            squares.remove(map.getSquareFromMap(x, y - 1));
////        }
////
////        return allWays.size() != 0;
//        return false;
//    }
//
//
//
//
//
//    public static GameMenuMessages patrolController(String coordinates) {
//        return null;
//    }
//
//    public static GameMenuMessages pourOilController(String direction) {
//        if (DataBase.getSelectedUnit().size() == 0)
//            return GameMenuMessages.CHOSE_UNIT_FIRST;
//        if (!(DataBase.getSelectedUnit().get(0) instanceof Engineer))
//            return GameMenuMessages.UNIT_ISNT_ENGINEER;
//        return null;
//    }
//
//    public static GameMenuMessages digTunnelController(String coordinate) {
//        String x = Orders.findFlagOption("-x", coordinate);
//        String y = Orders.findFlagOption("-y", coordinate);
//
//        assert x != null;
//        if (!x.matches("^\\d+$") || !Objects.requireNonNull(y).matches("^\\d+$"))
//            return GameMenuMessages.WRONG_FORMAT_COORDINATE;
//
//        int xCoordinate = Integer.parseInt(x) - 1;
//        int yCoordinate = Integer.parseInt(y) - 1;
//        if (currentMap.isCoordinationValid(xCoordinate, yCoordinate))
//            return GameMenuMessages.INVALID_COORDINATE;
//
//        if (DataBase.getSelectedUnit().size() == 0) return GameMenuMessages.CHOSE_UNIT_FIRST;
//        if (!(DataBase.getSelectedUnit().get(0) instanceof Tunneler)) return GameMenuMessages.NOT_TUNNELER;
//        if (!DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).canPass())
//            return GameMenuMessages.CANT_GO_THERE;
//
//        if (moveUnit(xCoordinate, yCoordinate)) {
//            int[] coord = DataBase.getEnemyClosestBuilding(xCoordinate, yCoordinate);
//            Building targetBuilding = currentMap.getSquareFromMap(coord[0], coord[1]).getBuilding();
//            if (!targetBuilding.getName().equals("Keep") && !targetBuilding.getName().equals("SquareTower")
//                    && !targetBuilding.getName().equals("CircularTower") && !targetBuilding.getName().equals("BigStoneGate")) {
//                targetBuilding.changeHP(999999);
//                DataBase.removeDestroyedBuildings(targetBuilding);
//            }
//            return GameMenuMessages.SUCCESS;
//        } else return GameMenuMessages.CANT_GO_THERE;
//    }
//
//    public static GameMenuMessages buildEquipmentController(String siegeName) {
//        if (!Siege.getSiegesName().contains(siegeName)) return GameMenuMessages.WRONG_NAME;
//        Siege siege = Siege.createSiege(currentGovernment, siegeName, -1, -1);
//
//        if (DataBase.getSelectedUnit().size() == 0) return GameMenuMessages.CHOSE_UNIT_FIRST;
//        if (!(DataBase.getSelectedUnit().get(0) instanceof Engineer)) return GameMenuMessages.UNIT_ISNT_ENGINEER;
//
//        assert siege != null;
//        if (DataBase.getSelectedUnit().size() < siege.getEngineerNeed()) return GameMenuMessages.NOT_ENOUGH_ENGINEER;
//        if (siege.getCost() > currentGovernment.getMoney()) return GameMenuMessages.NOT_ENOUGH_BALANCE;
//
//        int xCoordinate = DataBase.getSelectedUnit().get(0).getXCoordinate();
//        int yCoordinate = DataBase.getSelectedUnit().get(0).getYCoordinate();
//
//        if (DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).getBuilding() == null)
//            return GameMenuMessages.CANT_BUILD_HERE;
//
//        currentGovernment.changeMoney(siege.getCost());
//        Defence siegeTent = Defence.createDefence(currentGovernment, xCoordinate, yCoordinate, "SiegeTent");
//        buildSiege.put(DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate), siegeName);
//
//        return GameMenuMessages.SUCCESS;
//    }
//
//    public static GameMenuMessages disbandUnitController() {
//        ArrayList<Unit> units = DataBase.getSelectedUnit();
//        int xCoordinate = DataBase.getSelectedUnit().get(0).getXCoordinate();
//        int yCoordinate = DataBase.getSelectedUnit().get(0).getYCoordinate();
//        if (units.size() == 0) return GameMenuMessages.CHOSE_UNIT_FIRST;
//
//        currentGovernment.changeFreeWorkers(units.size());
//        DataBase.getSelectedMap().getSquareFromMap(xCoordinate, yCoordinate).removeAllUnit(units.get(0));
//
//        return GameMenuMessages.SUCCESS;
//    }
//
//    public static void setCurrentMap(Map map) {
//        currentMap = map;
//    }
//
//    public static int getTurnsPassed() {
//        return turnsPassed;
//    }
//
//    public static String getCurrentGovernmentUsername() {
//        return currentGovernment.getOwner().getUsername();
//    }
//
//    public static ArrayList<String> getBuildingValidLandsByName(String buildingName) {
//        for (Building building : Building.getBuildings())
//            if (building.getName().equals(buildingName))
//                return building.getLands();
//        return null;
//    }
//
//    public static ArrayList<Unit> getAllUnits() {
//        return allUnits;
//    }
//
//    public static void addToAllUnits(Unit unit) {
//        allUnits.add(unit);
//    }
//
//    public static GameMenuMessages setTaxRateController(int rateNumber) {
//        if (rateNumber > 8 || rateNumber < -3 || (rateNumber % 1) != 0)
//            return GameMenuMessages.INVALID_TAX_RATE;
//        else {
//            DataBase.getCurrentGovernment().setTax(rateNumber);
//            return GameMenuMessages.SET_TAX_SUCCESS;
//        }
//    }
//
//    public static String showFoodListController() {
//        Government myGovernment = DataBase.getCurrentGovernment();
//        String toReturn = "";
//        Resource apple = Resource.createResource("Apples");
//        Resource meat = Resource.createResource("Meat");
//        Resource cheese = Resource.createResource("Cheese");
//        Resource bread = Resource.createResource("Bread");
//
//        toReturn += "Apples: " + myGovernment.getResourceInStockpiles(apple) + "\n";
//        toReturn += "Meat: " + myGovernment.getResourceInStockpiles(meat) + "\n";
//        toReturn += "Cheese: " + myGovernment.getResourceInStockpiles(cheese) + "\n";
//        toReturn += "Bread: " + myGovernment.getResourceInStockpiles(bread);
//
//        return toReturn;
//    }
//
//    public static GameMenuMessages setFoodRateController(int rateNumber) {
//        if (rateNumber > 2 || rateNumber < -2 || (rateNumber % 1) != 0)
//            return GameMenuMessages.INVALID_FOOD_RATE;
//        else {
//            DataBase.getCurrentGovernment().setFood(rateNumber);
//            return GameMenuMessages.SET_FOOD_RATE_SUCCESS;
//        }
//    }
//
//    public static GameMenuMessages modifyGates(String openState){
//        if(openState==null)
//            return GameMenuMessages.EMPTY_INPUT_FIELDS_ERROR;
//
//        if(selectedBuilding==null)
//            return GameMenuMessages.MODIFYGATES_UNMATCHING_BUILDING;
//
//        if(!selectedBuilding.getName().equals("BigStoneGate")||
//            selectedBuilding.getName().equals("SmallStoneGate"))
//                return GameMenuMessages.MODIFYGATES_UNMATCHING_BUILDING;
//
//        if(openState.equals("open")){
//            selectedBuilding.changeCanPass(true);
//            return GameMenuMessages.SUCCESS;
//        }
//        else if(openState.equals("close")){
//            selectedBuilding.changeCanPass(false);
//            return GameMenuMessages.SUCCESS;
//        }
//        else return GameMenuMessages.INVALID_STATE;
//    }
//
//    public static GameMenuMessages setFearRateController(int rateNumber) {
//        if (rateNumber > 5 || rateNumber < -5)
//            return GameMenuMessages.INVALID_FEAR_RATE;
//        else {
//            DataBase.getCurrentGovernment().setFear(rateNumber);
//            return GameMenuMessages.SET_FEAR_RATE_SUCCESS;
//        }
//    }
//
//    public static Government getCurrentGovernment() {
//        return currentGovernment;
//    }
//
//    public static GameMenuMessages digDitchController(String x, String y) {
//        if(x == null || y == null)
//            return GameMenuMessages.NO_OPTIONS;
//        int xInt = Integer.parseInt(x) - 1;
//        int yInt = Integer.parseInt(y) - 1;
//
//        if(xInt >= DataBase.getSelectedMap().getWidth() || xInt < 0)
//            return GameMenuMessages.INVALID_X;
//        if(yInt >= DataBase.getSelectedMap().getLength() || yInt < 0)
//            return GameMenuMessages.INVALID_Y;
//
//        if(DataBase.getSelectedUnit() == null)
//            return GameMenuMessages.NO_UNIT_SELECTED;
//        else if(! (DataBase.getSelectedUnit().get(0) instanceof Troop selectedTroop)){
//            return GameMenuMessages.CANNOT_DIG_DITCH;
//        }
//        else{
//            if(!selectedTroop.isDigMoat())
//                return GameMenuMessages.CANNOT_DIG_DITCH;
//
//            DataBase.getSelectedMap().getSquareFromMap(xInt , yInt).setLand(Land.DITCH);
//            return GameMenuMessages.DIG_DITCH_SUCCESS;
//        }
//    }
//
//    public static GameMenuMessages fillDitchController(String x, String y) {
//        if(x == null || y == null)
//            return GameMenuMessages.NO_OPTIONS;
//        int xInt = Integer.parseInt(x) - 1;
//        int yInt = Integer.parseInt(y) - 1;
//
//        if(xInt >= DataBase.getSelectedMap().getWidth() || xInt < 0)
//            return GameMenuMessages.INVALID_X;
//        if(yInt >= DataBase.getSelectedMap().getLength() || yInt < 0)
//            return GameMenuMessages.INVALID_Y;
//
//        if(DataBase.getSelectedUnit() == null)
//            return GameMenuMessages.NO_UNIT_SELECTED;
//        else if(! (DataBase.getSelectedUnit().get(0) instanceof Troop selectedTroop)){
//            return GameMenuMessages.CANNOT_DIG_DITCH;
//        }
//        else{
//            if(!selectedTroop.isDigMoat())
//                return GameMenuMessages.CANNOT_DIG_DITCH;
//
//            DataBase.getSelectedMap().getSquareFromMap(xInt , yInt).setLand(Land.DITCH);
//            return GameMenuMessages.FILL_DITCH_SUCCESS;
//        }
//    }
//}
