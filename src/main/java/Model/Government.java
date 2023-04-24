package Model;

import Model.Buildings.Building;
import Model.Buildings.Stockpile;
import Model.Buildings.Trap;
import Model.Resources.Resource;
import View.TradeMenu;

import java.util.ArrayList;

public class Government {
    private User owner;
    private int popularity;
    private int workerRate;
    private int population;
    private int freeWorker;
    private int food;
    private int foodType;
    private int foodCount;
    private int tax;
    private int faith;
    private int fear;
    private double money;
    private ArrayList<Resource> resources;
    private static ArrayList<Resource> foods;
    private static ArrayList<Resource> weapons;
    private ArrayList<Stockpile> stockpiles;
    private ArrayList<Troop> troops;
    private ArrayList<Building> buildings;
    private ArrayList<TradeRequest> requestsAskedFromMe;
    private ArrayList<TradeRequest> tradeHistory;

    public Government(User owner, double money) {
        this.owner = owner;
        this.money = money;
        this.food = 0;
        this.popularity = 0;
        this.tax = 0;
        this.fear = 0;
        this.faith = 0;
        this.population = 0;
        this.freeWorker = 0;
        resources = Resource.getAllResources();
        foods = Resource.getFoods();
        weapons = Resource.getWeapons();
        requestsAskedFromMe = new ArrayList<>();
        tradeHistory = new ArrayList<>();
    }

    public User getOwner() {
        return owner;
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
        return Stockpile.addResource(stockpiles, resource, number);
    }

    public int freeStockpileSpace(Resource resource) {
        return Stockpile.freeSpaceForResource(stockpiles, resource);
    }

    public int getResourceInStockpiles(Resource resource) {
        return Stockpile.getResourceCount(stockpiles, resource);
    }

    public boolean removeFromStockpile(Resource resource, int number) {
        return Stockpile.removeResource(stockpiles, resource, number);
    }

    public void addAndRemoveFromGovernment() {
        // call in next turn
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

    private double getMoneyEachPersonPay(int tax) {
        double moneyPerPerson = 0;
        if (tax < 0) moneyPerPerson += (((tax + 3) * 0.2) - 1);
        else if (tax == 0) moneyPerPerson = 0;
        else moneyPerPerson += (tax * 0.2) + 0.4;
        return moneyPerPerson;
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

    public static ArrayList<Resource> getFoods() {
        return foods;
    }

    public static ArrayList<Resource> getWeapons() {
        return weapons;
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
}
