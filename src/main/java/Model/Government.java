package Model;

import Model.Resource.Resource;

public class Government {
    private User owner;
    private int popularity;
    private int workerRate;
    private int food;
    private int tax;
    private int faith;
    private int fear;
    private double money;

    public Government(User owner, double money) {
        this.owner = owner;
        this.money = money;
        this.food = 0;
        this.popularity = 0;
        this.tax = 0;
        this.fear = 0;
        this.faith = 0;
    }

    public User getOwner() {
        return owner;
    }

    public int getPopularity() {
        popularity = 0;
        popularity += food*4;
        popularity += fear;
        popularity += faith;
        //tax factor
        if (tax <= 0) popularity += (tax*(-2) + 1);
        else if (tax <= 4) popularity += (tax*(-2)) ;
        else popularity += (((tax-5)*(-4)) - 12);

        return popularity;
    }

    public void Influence() {

    }

    private void influenceOfFear() {
        // influence in troops damage and workers rate
    }

    private void influenceOfFood() {
        // remove food
    }

    private void influenceOfTax() {
        // add money
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
}
