package Model;

import Model.Units.Troop;
import java.util.ArrayList;
import java.util.Objects;

public class Government {
    private final ArrayList<TradeRequest> requestsAskedFromMe;
    private final ArrayList<TradeRequest> requestsIAsked;
    private final ArrayList<TradeRequest> tradeHistory;
    private final ArrayList<TradeRequest> requestNotifications;
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

    {
        requestsAskedFromMe = new ArrayList<>();
        tradeHistory = new ArrayList<>();
        requestNotifications = new ArrayList<>();
        requestsIAsked = new ArrayList<>();
    }

    public  Government(double money) {
        this.money = money;
        this.food = 0;
        this.popularity = 0;
        this.tax = 0;
        this.fear = 0;
        this.faith = 0;
        this.population = 32;
        this.maxPopulation = 40;
        this.freeWorker = 20;
    }



    public void setLord(int x, int y) {
        this.lord = Troop.createTroop(this, "Lord", x, y);
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setFreeWorker(int freeWorker) {
        this.freeWorker = freeWorker;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public void setFaith(int faith) {
        this.faith = faith;
    }

    public void setFear(int fear) {
        this.fear = fear;
    }

    public void setMoney(double money) {
        this.money = money;
    }




    public User getOwner() {
        return owner;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public int getPopulation() {
        return population;
    }

    public int getFreeWorker() {
        return freeWorker;
    }

    public int getFood() {
        return food;
    }

    public int getFoodType() {
        return foodType;
    }

    public int getFoodCount() {
        return foodCount;
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

    public Troop getLord() {
        return lord;
    }


    public void addToRequestsAskedFromMe(TradeRequest tradeRequest) {
        requestsAskedFromMe.add(tradeRequest);
    }

    public void addToTradeHistory(TradeRequest tradeRequest) {
        tradeHistory.add(tradeRequest);
    }

    public void addToRequestNotification(TradeRequest tradeRequest) {
        requestNotifications.add(tradeRequest);
    }

    public void addToRequestsIAsked(TradeRequest tradeRequest){
        requestsIAsked.add(tradeRequest);
    }

    public ArrayList<TradeRequest> getRequestsAskedFromMe() {
        return requestsAskedFromMe;
    }

    public ArrayList<TradeRequest> getTradeHistory() {
        return tradeHistory;
    }

    public ArrayList<TradeRequest> getRequestNotifications() {
        return requestNotifications;
    }

    public ArrayList<TradeRequest> getRequestsIAsked() {
        return requestsIAsked;
    }

    public TradeRequest getRequestById(int id) {
        for (TradeRequest tradeRequest : requestsAskedFromMe) {
            if (tradeRequest.getId() == id)
                return tradeRequest;
        }
        return null;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Government that = (Government) o;
        return Objects.equals(owner, that.owner);
    }
}
