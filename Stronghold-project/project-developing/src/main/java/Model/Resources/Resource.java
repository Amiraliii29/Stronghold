package Model.Resources;

import java.util.ArrayList;
import java.util.Objects;

public class Resource {
    private String name;
    private int price;
    private double count;
    private String storage;//where we store it
    private static ArrayList<Resource> resources;
    private static ArrayList<Resource> foods;
    private static ArrayList<Resource> weapons;

    static {
        resources = new ArrayList<>();
        foods = new ArrayList<>();
        weapons = new ArrayList<>();
    }

    public Resource(String name, int price, String storage) {
        this.name = name;
        this.price = price;
        this.storage = storage;
        this.count = 0.0;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return price;
    }

    public int getBuyPrice() {
        return (price*1);
    }

    public double getCount() {
        return count;
    }

    public String getStorage() {
        return storage;
    }

    public void changeCount(double count) {
        this.count += count;
    }

    public boolean isThereEnough(double number) {
        return this.count > number;
    }

    public static void addToResources(Resource resource) {
        resources.add(resource);
        if (resource.getName().equals("Bread")
                || resource.getName().equals("Meat")
                || resource.getName().equals("Apples")
                || resource.getName().equals("Cheese")) {
            foods.add(resource);
        } else if (resource.getName().equals("Sword")
                || resource.getName().equals("Spear")
                || resource.getName().equals("Pike")
                || resource.getName().equals("Bow")
                || resource.getName().equals("CrossBow")
                || resource.getName().equals("Mace")
                || resource.getName().equals("MetalArmor")
                || resource.getName().equals("LeatherArmor")) {
            weapons.add(resource);
        }
    }

    public static ArrayList<Resource> getAllResources() {
        resources = new ArrayList<>();

        //food
        ResourceEnum.addToResources(ResourceEnum.Bread);
        ResourceEnum.addToResources(ResourceEnum.Meat);
        ResourceEnum.addToResources(ResourceEnum.Apples);
        ResourceEnum.addToResources(ResourceEnum.Cheese);
        //weapon
        ResourceEnum.addToResources(ResourceEnum.Bow);
        ResourceEnum.addToResources(ResourceEnum.CrossBow);
        ResourceEnum.addToResources(ResourceEnum.Sword);
        ResourceEnum.addToResources(ResourceEnum.Spear);
        ResourceEnum.addToResources(ResourceEnum.Mace);
        ResourceEnum.addToResources(ResourceEnum.Pike);
        ResourceEnum.addToResources(ResourceEnum.MetalArmor);
        ResourceEnum.addToResources(ResourceEnum.LeatherArmor);
        //other
        ResourceEnum.addToResources(ResourceEnum.Wood);
        ResourceEnum.addToResources(ResourceEnum.Stone);
        ResourceEnum.addToResources(ResourceEnum.Iron);
        ResourceEnum.addToResources(ResourceEnum.Pitch);
        ResourceEnum.addToResources(ResourceEnum.Ale);
        ResourceEnum.addToResources(ResourceEnum.Flour);
        ResourceEnum.addToResources(ResourceEnum.Hops);

        return resources;
    }

    public static ArrayList<Resource> getFoods() {
        return foods;
    }

    public static ArrayList<Resource> getWeapons() {
        return weapons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return price == resource.price && Double.compare(resource.count, count) == 0 && Objects.equals(name, resource.name) && Objects.equals(storage, resource.storage);
    }
}
