package Model;

import java.util.ArrayList;

public class Resource {
    private String name;
    private int price;
    private int usingRate;
    private static ArrayList<Resource> weapons;
    private static ArrayList<Resource> foods;

    static {
        weapons = new ArrayList<>();
        foods = new ArrayList<>();
    }

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

    public static ArrayList<Resource> getWeapons() {
        return weapons;
    }

    public static void addWeapon(Resource weapon) {
        weapons.add(weapon);
    }

    public static ArrayList<Resource> getFoods() {
        return foods;
    }

    public static void addFood(Resource food) {
        foods.add(food);
    }
}
