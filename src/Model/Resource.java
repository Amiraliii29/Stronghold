package Model;

public class Resource {
    private String name;
    private int price;
    private int usingRate;

    public Resource(String name, int price, int usingRate) {
        this.name = name;
        this.price = price;
        this.usingRate = usingRate;
    }

    public boolean useResource() {
        //check there is enough resource if true remove that
        return false;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getUsingRate() {
        return usingRate;
    }
}
