package Model.Resource;

import java.util.ArrayList;

public class Resource {
    private String name;
    private int price;
    private double count;
    private String storage;//where we store it
    private static ArrayList<Resource> resources;

    static {
        resources = new ArrayList<>();
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

    public int getPrice() {
        return price;
    }

    public double getCount() {
        return count;
    }

    public String getStorage() {
        return storage;
    }

    public void changeCount(int count) {
        this.count += count;
    }

    public boolean isThereEnough(double number) {
        return this.count > number;
    }

    public static void addToResources(Resource resource) {
        resources.add(resource);
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
}
