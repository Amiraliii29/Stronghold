package Controller;

import Main.Result;
import Main.ResultEnums;
import Main.UserDataBase;
import Model.*;
import Model.Buildings.Building;
import Model.Buildings.Defence;
import Model.Buildings.Generator;
import Model.Buildings.TownBuilding;
import Model.Units.*;

import java.io.IOException;
import java.util.ArrayList;

public class GameMenuController {
    private UserDataBase userDataBase;
    private Map map;
    private Square[][] squares;
    private ArrayList<ArrayList<Square>> allWays;
    private ArrayList<Square> path;


    public GameMenuController(UserDataBase userDataBase) {
        this.userDataBase = userDataBase;
        map = userDataBase.getDataBase().getSelectedMap();
        squares = map.getSquares();
    }


    public ArrayList<Square> moveUnit(Unit unit, int x, int y) {
        if (squares[x][y].getBuilding() != null) {
            if (squares[x][y].getBuilding() instanceof Defence &&
                    squares[x][y].getUnits().size() >= ((Defence) squares[x][y].getBuilding()).getCapacity())
                return null;

            if (!(squares[x][y].getBuilding() instanceof Defence))
                return null;
        }

        allWays = new ArrayList<>();
        path = new ArrayList<>();

        int startX = unit.getXCoordinate();
        int startY = unit.getYCoordinate();
        boolean up = map.getSquareFromMap(startX, startY).getBuilding() instanceof Defence;
        if (unit instanceof Siege || unit.getName().equals("Knight") || unit.getName().equals("HorseArcher"))
            up = false;

        if (move(unit, startX, startY, x, y, Unit.getMaxDistance(), up)) {
            int size = 1000;
            for (ArrayList<Square> array : allWays) {
                if (array.size() < size) {
                    path = array;
                    size = array.size();
                }
            }

            return path;
        } else
            return null;
    }

    private boolean move(Unit unit, int x, int y, int xFin, int yFin, int speed, boolean up) {
        if (!map.isCoordinationValid(x, y))
            return false;

        if (!squares[x][y].canPass())
            return false;

        if (up) {

            if (squares[x][y].getBuilding() != null && squares[x][y].getBuilding() instanceof Defence) {
                if (squares[x][y].getBuilding().getName().equals("Stair"))
                    up = false;
            } else if (squares[x][y].getBuilding() == null) {
                LadderMan ladderMan = LadderMan.createLadderMan(userDataBase.getGovernment(), -1, -1);
                if (squares[x][y].getUnits().contains(ladderMan) && unit instanceof Troop && ((Troop) unit).isClimbLadder())
                    up = false;
                else if (!unit.getName().equals("Assassin")) return false;
            } else return false;

        } else if (!up) {

            if (squares[x][y].getBuilding() != null) {
                if (squares[x][y].getBuilding() instanceof Defence ) {
                    if (squares[x][y].getBuilding().getName().equals("Stair"))
                        up = true;
                    else if (!unit.getName().equals("Assassin") && !squares[x][y].getBuilding().getCanPass()) return false;
                }

                else if (!squares[x][y].getBuilding().getCanPass()) return false;

                else if (squares[x][y].getBuilding().getName().equals("DrawBridge")
                        || squares[x][y].getBuilding().getName().equals("SmallStoneGate")
                        || squares[x][y].getBuilding().getName().equals("BigStoneGate"))
                    up = up;//nothing

                else if ((unit instanceof Siege || unit.getName().equals("Knight") || unit.getName().equals("HorseArcher")))
                    return false;

                else return false;

            } else {
                LadderMan ladderMan = LadderMan.createLadderMan(userDataBase.getGovernment(), -1, -1);
                if (squares[x][y].getUnits().contains(ladderMan) && unit instanceof Troop && ((Troop) unit).isClimbLadder())
                    up = true;
            }

        }



        if (speed >= 0 && x == xFin && y == yFin) {
            allWays.add(new ArrayList<>(path));
            return true;
        }

        if (speed == 0) return false;

        if (x < map.getWidth() - 1) {
            path.add(squares[x + 1][y]);
            move(unit, x + 1, y, xFin, yFin, speed - 1, up);
            path.remove(path.lastIndexOf(squares[x + 1][y]));
        }
        if (x > 0) {
            path.add(squares[x - 1][y]);
            move(unit, x - 1, y, xFin, yFin, speed - 1, up);
            path.remove(path.lastIndexOf(squares[x - 1][y]));
        }
        if (y < map.getLength() - 1) {
            path.add(squares[x][y + 1]);
            move(unit, x, y + 1, xFin, yFin, speed - 1, up);
            path.remove(path.lastIndexOf(squares[x][y + 1]));
        }
        if (y > 0) {
            path.add(squares[x][y - 1]);
            move(unit, x, y - 1, xFin, yFin, speed - 1, up);
            path.remove(path.lastIndexOf(squares[x][y - 1]));
        }

        return allWays.size() != 0;
    }


    public boolean constructBuilding(Building building) {
        Government currentGovernment = userDataBase.getGovernment();

        if (!map.canConstructBuildingInPlace(building, building.getXCoordinateLeft(), building.getYCoordinateUp()))
            userDataBase.getDataBase().updateClient(new Result(ResultEnums.CANT_BUILD_HERE));

        else if (building.getCost() > currentGovernment.getMoney())
            userDataBase.getDataBase().updateClient(new Result(ResultEnums.NOT_ENOUGH_MONEY));

        else if (building.getResource() != null &&
                currentGovernment.getResourceInStockpiles(building.getResource()) < building.getNumberOfResource())
            userDataBase.getDataBase().updateClient(new Result(ResultEnums.NOT_ENOUGH_MATERIAL));

        else if (building instanceof Generator && ((Generator) building).getNumberOfWorker() > currentGovernment.getFreeWorker())
            userDataBase.getDataBase().updateClient(new Result(ResultEnums.NOT_ENOUGH_FREE_WORKER));

        else {
            currentGovernment.changeMoney(-building.getCost());
            currentGovernment.removeFromStockpile(building.getResource(), building.getNumberOfResource());

            switch (Building.getBuildingCategoryByName(building.getName())) {
                case "Generator" -> {
                    assert building instanceof Generator;
                    Generator generator = (Generator) building;
                    currentGovernment.changePopulation(generator.getNumberOfWorker());
                    currentGovernment.changeFreeWorkers(-generator.getNumberOfWorker());
                    currentGovernment.addToGenerationRate(generator.getResourceGenerate().getName(), generator.getGeneratingRate());
                    currentGovernment.applyOxEffectOnStoneGeneration();
                }
                case "TownBuilding" -> {
                    assert building instanceof TownBuilding;
                    TownBuilding townBuilding = (TownBuilding) building;
                    currentGovernment.addToMaxPopulation(townBuilding.getCapacity());
                    currentGovernment.updateBuildingPopularity();
                }
            }
            map.constructBuilding(building, building.getXCoordinateLeft(), building.getYCoordinateUp());
            userDataBase.getDataBase().updateClient(new Result(ResultEnums.SUCCESS));
            return true;
        }
        return false;
    }



    public static void disbandUnit() {
        ArrayList<ArrayList<Unit>> allUnits = separateUnits();
        if (allUnits == null) return;

        for (ArrayList<Unit> selectedUnit : allUnits) {
            currentGovernment.changeFreeWorkers(selectedUnit.size());
            squares[selectedUnit.get(0).getXCoordinate()][selectedUnit.get(0).getYCoordinate()].removeAllUnit(selectedUnit.get(0));
        }
    }

    public static void modifyGates(boolean open) {
        DataBase.getSelectedBuilding().changeCanPass(open);
    }

    public static void repair() {
        Building selectedBuilding = DataBase.getSelectedBuilding();
        if (selectedBuilding.getMaximumHp() == selectedBuilding.getHp()) return;

        Resource stone = Resource.getResourceByName("Stone");
        double damage = (selectedBuilding.getMaximumHp() - selectedBuilding.getHp()) / (selectedBuilding.getMaximumHp() * 1.0);
        int stoneNumber = (int) (Math.ceil(damage * selectedBuilding.getNumberOfResource()));

        if (currentGovernment.getResourceInStockpiles(stone) >= stoneNumber) {
            currentGovernment.removeFromStockpile(stone, stoneNumber);
            selectedBuilding.changeHP(selectedBuilding.getHp() - selectedBuilding.getMaximumHp());

            try {
                game.showBuildingDetail(selectedBuilding);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void changeProduct(String product) {
        Building selectedBuilding = DataBase.getSelectedBuilding();
        if (!(selectedBuilding instanceof Generator)) return;

        Resource last = ((Generator) selectedBuilding).getResourceGenerate();
        currentGovernment.addToGenerationRate(product, ((Generator) selectedBuilding).getGeneratingRate());
        currentGovernment.removeFromResourceGenerationRate(last.getName(), ((Generator) selectedBuilding).getGeneratingRate());
    }

    public static void setUnitModeController(String state) {
        StateUnits newState;
        switch (state) {
            case "Standing" -> newState = StateUnits.Stan_Ground;
            case "Defensive" -> newState = StateUnits.Defensive;
            case "Aggressive" -> newState = StateUnits.Aggressive;
            default -> {
                return ;
            }
        }

        for (Unit unit : DataBase.getSelectedUnit())
            unit.setState(newState);
    }
}
