package Model;

import Model.Buildings.Building;
import Model.Buildings.Stockpile;
import Model.Units.Unit;
import Model.Resources.Resource;

import java.util.ArrayList;

public class Government {
    private final User owner;
    private int popularity;
    private int workerRate;//TODO
    private int population;//TODO
    private int freeWorker;//TODO
    private int food;
    private int foodType;
    private int foodCount;
    private int tax;
    private int faith;
    private int fear;
    private double money;
    private ArrayList<Resource> resources;
    private ArrayList<Resource> foods;
    private ArrayList<Resource> weapons;
    private ArrayList<Stockpile> stockpiles;
    private ArrayList<Stockpile> armoury;
    private ArrayList<Stockpile> granary;
    private ArrayList<Unit> units;
    private ArrayList<Building> buildings;
    private ArrayList<TradeRequest> requestsAskedFromMe;
    private ArrayList<TradeRequest> tradeHistory;
    private ArrayList<TradeRequest> requestNotifications;

    {
        this.food = 0;
        this.popularity = 0;
        this.tax = 0;
        this.fear = 0;
        this.faith = 0;
        this.population = 0;
        this.freeWorker = 0;
        Resource.newAllResources();
        resources = Resource.getResources();
        foods = Resource.getFoods();
        weapons = Resource.getWeapons();
        stockpiles = new ArrayList<>();
        armoury = new ArrayList<>();
        granary = new ArrayList<>();
        units = new ArrayList<>();
        buildings = new ArrayList<>();
    }

    public Government(User owner, double money) {
        this.owner = owner;
        this.money = money;
        requestsAskedFromMe = new ArrayList<>();
        tradeHistory = new ArrayList<>();
        requestNotifications = new ArrayList<>();
    }

    public User getOwner() {
        return owner;
    }

    public int getPopularity() {
        setFoodFactors();
        popularity = 0;
        popularity += food * 4;
        popularity += foodType - 1;
        popularity += fear;
        popularity += faith;
        //tax factor
        if (tax <= 0) popularity += (tax * (-2) + 1);
        else if (tax <= 4) popularity += (tax * (-2));
        else popularity += (((tax - 5) * (-4)) - 12);

        return popularity;
    }

    public boolean addToStockpile(Resource resource, int number) {
        if (resources.contains(resource))
            return Stockpile.addResource(stockpiles, resource, number);
        else if (foods.contains(resource))
            return Stockpile.addResource(granary, resource, number);
        else
            return Stockpile.addResource(armoury, resource, number);
    }

    public int freeStockpileSpace(Resource resource) {
        if (resources.contains(resource))
            return Stockpile.freeSpaceForResource(stockpiles, resource);
        else if (foods.contains(resource))
            return Stockpile.freeSpaceForResource(granary, resource);
        else
            return Stockpile.freeSpaceForResource(armoury, resource);
    }

    public int getResourceInStockpiles(Resource resource) {
        if (resources.contains(resource))
            return Stockpile.getResourceCount(stockpiles, resource);
        else if (foods.contains(resource))
            return Stockpile.getResourceCount(granary, resource);
        else
            return Stockpile.getResourceCount(armoury, resource);
    }

    public boolean removeFromStockpile(Resource resource, int number) {
        if (resources.contains(resource))
            return Stockpile.removeResource(stockpiles, resource, number);
        else if (foods.contains(resource))
            return Stockpile.removeResource(granary, resource, number);
        else
            return Stockpile.removeResource(armoury, resource, number);
    }

    public void addAndRemoveFromGovernment() {
        // call in next turn //TODO
        setFoodFactors();
        if (this.population * ((this.food * 0.5) + 1) >= this.foodCount) {
            removeFood(this.population * ((this.food * 0.5) + 1));
        } else {
            this.food--;
            while (this.food >= -2) {
                if (this.population * ((this.food * 0.5) + 1) >= this.foodCount) {
                    removeFood(this.population * ((this.food * 0.5) + 1));
                    break;
                }
                this.food--;
            }
        }
        doTaxes();

        //add worker //TODO
    }

    private void setFoodFactors() {
        foodCount = 0;
        foodType = 0;
        for (Resource resource : foods) {
            if (resource.getCount() > 0) {
                foodCount += resource.getCount();
                foodType++;
            }
        }
    }

    private void removeFood(double numberForRemove) {
        for (int i = 0; i < numberForRemove; i++) {
            if (foods.get((i % 4)).getCount() >= 1) {
                foods.get((i % 4)).changeCount(-1);
            } else numberForRemove++; // need to be checked !!

            if (i + 1 > numberForRemove) {
                for (Resource food : foods) {
                    if (food.getCount() > (numberForRemove - i - 1)) {
                        food.changeCount(numberForRemove - i - 1);
                        break;
                    }
                }
            }
        }
    }

    private void doTaxes() {
        setTax(this.tax);
        double moneyPerPerson = getMoneyEachPersonPay(this.tax);
        this.money += (moneyPerPerson * population);
    }

    private double getMoneyEachPersonPay(int tax) {
        double moneyPerPerson = 0;
        if (tax < 0) moneyPerPerson += (((tax + 3) * 0.2) - 1);
        else if (tax == 0) moneyPerPerson = 0;
        else moneyPerPerson += (tax * 0.2) + 0.4;
        return moneyPerPerson;
    }

    public void setFood(int food) {
        setFoodFactors();
        if (this.population * ((food * 0.5) + 1) >= this.foodCount) this.food = food;
        else {
            food--;
            while (food >= -2) {
                if (this.population * ((food * 0.5) + 1) >= this.foodCount) {
                    this.food = food;
                    return;
                }
                food--;
            }
        }
    }

    public void setTax(int tax) {
        double moneyPerPerson = getMoneyEachPersonPay(tax);
        while (population * moneyPerPerson > money) {
            tax--;
            moneyPerPerson = getMoneyEachPersonPay(tax);
        }
        this.tax = tax;
    }

    public void changeMoney(double money) {
        this.money += money;
    }

    public int getWorkerRate() {
        return workerRate;
    }

    public int getFood() {
        return food;
    }

    public int getTax() {
        return tax;
    }

    public int getFaith() {
        return faith;
    }

    public int getFear() {
        return fear;
    }

    public double getMoney() {
        return money;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public int getPopulation() {
        return population;
    }

    public int getFreeWorker() {
        return freeWorker;
    }

    public int getFoodType() {
        setFoodFactors();
        return foodType;
    }

    public int getFoodCount() {
        setFoodFactors();
        return foodCount;
    }

    public ArrayList<Resource> getFoods() {
        return foods;
    }

    public ArrayList<Resource> getWeapons() {
        return weapons;
    }

    public ArrayList<Stockpile> getStockpiles() {
        return stockpiles;
    }

    public void addStockpiles(Stockpile stockpile) {
        this.stockpiles.add(stockpile);
    }

    public ArrayList<Stockpile> getArmoury() {
        return armoury;
    }

    public void addArmoury(Stockpile armoury) {
        this.armoury.add(armoury);
    }

    public ArrayList<Stockpile> getGranary() {
        return granary;
    }

    public void addGranary(Stockpile granary) {
        this.granary.add(granary);
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void addUnits(Unit unit) {
        this.units.add(unit);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addBuildings(Building building) {
        this.buildings.add(building);
    }

    public void setFear(int fear) {
        this.fear = fear;
    }

    public void setFaith(int faith) {
        this.faith = faith;
    }
    public void addToRequestsAskedFromMe(TradeRequest tradeRequest){
        requestsAskedFromMe.add(tradeRequest);
    }

    public ArrayList<TradeRequest> getRequestsAskedFromMe() {
        return requestsAskedFromMe;
    }
    public TradeRequest getRequestById(int id){
        for (TradeRequest tradeRequest : requestsAskedFromMe) {
            if(tradeRequest.getId() == id)
                return tradeRequest;
        }
        return null;
    }

    public ArrayList<TradeRequest> getTradeHistory() {
        return tradeHistory;
    }
    public void addToTradeHistory(TradeRequest tradeRequest){
        tradeHistory.add(tradeRequest);
    }
    public void removeFromRequestsAskedFromMe(TradeRequest tradeRequest){
        requestsAskedFromMe.remove(tradeRequest);
    }
    public void addToRequestNotification(TradeRequest tradeRequest){
        requestNotifications.add(tradeRequest);
    }

    public ArrayList<TradeRequest> getRequestNotifications() {
        return requestNotifications;
    }
    public void removeAllRequestNotification(){
        requestNotifications.clear();
    }
}
