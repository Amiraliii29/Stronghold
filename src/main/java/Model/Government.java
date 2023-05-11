package Model;

import Model.Buildings.Building;
import Model.Buildings.Generator;
import Model.Buildings.Stockpile;
import Model.Buildings.TownBuilding;
import Model.Units.Troop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Controller.GameMenuController;

public class Government {
    private User owner;
    private int popularity;
    private int maxPopulation;
    private int population;
    private int freeWorker;
    private int food;
    private int foodType;
    private int foodCount;
    private int tax;
    private int faith;
    private int fear;
    private double money;
    private Troop lord;
    private ArrayList<Stockpile> stockpiles;
    private ArrayList<Stockpile> armoury;
    private ArrayList<Stockpile> granary;
    private HashMap<String, Integer> resourceGenerationRate;
    private ArrayList<Building> buildings;
    private ArrayList<TradeRequest> requestsAskedFromMe;
    private ArrayList<TradeRequest> tradeHistory;
    private ArrayList<TradeRequest> requestNotifications;

    public Troop getLord() {
        return lord;
    }

    public void setLord(int x, int y) {
        Troop lord = Troop.createTroop(this, "Lord", x, y);
        this.lord = lord;
    }

    public Government(double money) {
        this.money = money;
        this.food = 0;
        this.popularity = 0;
        this.tax = 0;
        this.fear = 0;
        this.faith = 0;
        this.population = 32;
        this.maxPopulation = 40;
        this.freeWorker = 0;
        stockpiles = new ArrayList<>();
        armoury = new ArrayList<>();
        granary = new ArrayList<>();
        buildings = new ArrayList<>();
        requestsAskedFromMe = new ArrayList<>();
        tradeHistory = new ArrayList<>();
        requestNotifications = new ArrayList<>();
        resourceGenerationRate = new HashMap<String, Integer>();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void updatePopularity() {
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
        updateBuildingPopularity();
    }

    public void updateBuildingPopularity() {
        for (Building building : buildings)
            if (building.getName().equals("Church")) {
                TownBuilding church = (TownBuilding) building;
                faith += church.getPopularityRate();
                return;
            }
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

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addToMaxPopulation(int addValue) {
        maxPopulation += addValue;
    }

    public void addToGenerationRate(String resourceType, int addedGenerationValue) {
        int previousValue = 0;
        if (resourceGenerationRate.containsKey(resourceType))
            previousValue = resourceGenerationRate.get(resourceType);

        resourceGenerationRate.put(resourceType, previousValue + addedGenerationValue);
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

    public void addAndRemoveFromGovernment() {
        setFoodFactors();
        if (this.population * ((this.food * 0.5) + 1) <= this.foodCount) {
            removeFood(this.population * ((this.food * 0.5) + 1));
        } else {
            this.food--;
            while (this.food >= -2) {
                if (this.population * ((this.food * 0.5) + 1) <= this.foodCount) {
                    removeFood(this.population * ((this.food * 0.5) + 1));
                    break;
                }
                this.food--;
            }
        }
        doTaxes();
        updatePopularity();
        changeFreeWorkers(popularity * 3);
    }

    private void setFoodFactors() {
        foodCount = 0;
        ArrayList<String> names = new ArrayList<>();
        for (Stockpile granary : this.granary) {
            for (Map.Entry<String, Integer> set : granary.getResources().entrySet()) {
                foodCount += set.getValue();
                names.add(set.getKey());
            }
        }
        foodType = names.size();
    }

    private void removeFood(double numberForRemove) {
        for (int i = 0; i < numberForRemove; i++) {
            if (!removeFromStockpile(Resource.createResource(Resource.getFoodsName().get(i % 4)), 1)) numberForRemove++;
            //need to be checked//TODO
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

    public boolean addToStockpile(Resource resource, int number) {
        if (Resource.getResourcesName().contains(resource.getName()))
            return Stockpile.addResource(stockpiles, resource, number);
        else if (Resource.getFoodsName().contains(resource.getName()))
            return Stockpile.addResource(granary, resource, number);
        else
            return Stockpile.addResource(armoury, resource, number);
    }

    public int freeStockpileSpace(Resource resource) {
        if (Resource.getResourcesName().contains(resource.getName()))
            return Stockpile.freeSpaceForResource(stockpiles, resource);
        else if (Resource.getFoodsName().contains(resource.getName()))
            return Stockpile.freeSpaceForResource(granary, resource);
        else
            return Stockpile.freeSpaceForResource(armoury, resource);
    }

    public int getResourceInStockpiles(Resource resource) {
        if (Resource.getResourcesName().contains(resource.getName()))
            return Stockpile.getResourceCount(stockpiles, resource);
        else if (Resource.getFoodsName().contains(resource.getName()))
            return Stockpile.getResourceCount(granary, resource);
        else
            return Stockpile.getResourceCount(armoury, resource);
    }

    public boolean removeFromStockpile(Resource resource, int number) {
        if (Resource.getResourcesName().contains(resource.getName()))
            return Stockpile.removeResource(stockpiles, resource, number);
        else if (Resource.getFoodsName().contains(resource.getName()))
            return Stockpile.removeResource(granary, resource, number);
        else
            return Stockpile.removeResource(armoury, resource, number);
    }

    public void addToRequestsAskedFromMe(TradeRequest tradeRequest) {
        requestsAskedFromMe.add(tradeRequest);
    }

    public ArrayList<TradeRequest> getRequestsAskedFromMe() {
        return requestsAskedFromMe;
    }

    public TradeRequest getRequestById(int id) {
        for (TradeRequest tradeRequest : requestsAskedFromMe) {
            if (tradeRequest.getId() == id)
                return tradeRequest;
        }
        return null;
    }

    public ArrayList<TradeRequest> getTradeHistory() {
        return tradeHistory;
    }

    public void addToTradeHistory(TradeRequest tradeRequest) {
        tradeHistory.add(tradeRequest);
    }

    public void removeFromRequestsAskedFromMe(TradeRequest tradeRequest) {
        requestsAskedFromMe.remove(tradeRequest);
    }

    public void addToRequestNotification(TradeRequest tradeRequest) {
        requestNotifications.add(tradeRequest);
    }

    public ArrayList<TradeRequest> getRequestNotifications() {
        return requestNotifications;
    }

    public void removeAllRequestNotification() {
        requestNotifications.clear();
    }

    public void changeFreeWorkers(int addedWorkers) {
        freeWorker += addedWorkers;
        if (freeWorker > 32) freeWorker = 32;
        if (freeWorker < 0) freeWorker = 0;
    }

    public void changePopulation(int addedPopulation) {
        population += addedPopulation;
        if (population > maxPopulation) population = maxPopulation;
    }

    public HashMap<String, Integer> getResourceGenerationRates() {
        return resourceGenerationRate;
    }

    public int getBuildingCountByName(String buildingName) {
        int Number = 0;
        for (Building building : buildings)
            if (building.getName().equals(buildingName))
                Number++;
        return Number;
    }

    public void applyOxEffectOnStoneGeneration() {
        int cowCount = getBuildingCountByName("StoneCow");
        int QuarryNumber = getBuildingCountByName("Quarry");
        if (QuarryNumber * 3 < cowCount) cowCount = QuarryNumber * 3;

        Generator quarry = (Generator) GameMenuController.getBuildingByName("Quarry");
        resourceGenerationRate.put(quarry.getResourceGenerate().getName(), quarry.getGeneratingRate() * cowCount / 3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Government that = (Government) o;
        return Objects.equals(owner, that.owner);
    }

    public int getPopularity() {
        updatePopularity();
        return popularity;
    }
}
