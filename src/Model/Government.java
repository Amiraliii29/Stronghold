package Model;

public class Government {
    private int popularity;
    private int workerRate;
    private int food;
    private int tax;
    private int faith;
    private int fear;

    public int getPopularity() {
        popularity = food + faith + fear + tax;
        return popularity;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getFaith() {
        return faith;
    }

    public void setFaith(int faith) {
        this.faith = faith;
    }

    public int getFear() {
        return fear;
    }

    public void setFear(int fear) {
        this.fear = fear;
    }

    public int getWorkerRate() {
        return workerRate;
    }
}
