package Model;

import Controller.ShopMenuController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class Resource {
    private static ArrayList<Resource> allResources;
    private String name;
    private int price;
    private double count;
    private String storage;//where we store it
    private static ArrayList<Resource> resources;
    private static ArrayList<Resource> foods;
    private static ArrayList<Resource> weapons;
    private static ArrayList<String> resourcesName;
    private static ArrayList<String> foodsName;
    private static ArrayList<String> weaponsName;

    static {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Resource>>() {
            }.getType();
            resources = gson.fromJson(new FileReader("src/main/resources/Resources/Resource.json"), type);
            foods = gson.fromJson(new FileReader("src/main/resources/Resources/Food.json"), type);
            weapons = gson.fromJson(new FileReader("src/main/resources/Resources/Weapon.json"), type);

        } catch (IOException e) {
            e.printStackTrace();
        }
        allResources = new ArrayList<>();
        resourcesName = new ArrayList<>();
        foodsName = new ArrayList<>();
        weaponsName = new ArrayList<>();
        for (Resource resource : resources) {
            resourcesName.add(resource.getName());
            allResources.add(resource);
            ShopMenuController.addItem(resource);
        }
        for (Resource food : foods) {
            foodsName.add(food.getName());
            allResources.add(food);
            ShopMenuController.addItem(food);
        }
        for (Resource weapon : weapons) {
            weaponsName.add(weapon.getName());
            allResources.add(weapon);
            ShopMenuController.addItem(weapon);
        }
    }

    private Resource(String name, int price, String storage) {
        this.name = name;
        this.price = price;
        this.storage = storage;
        this.count = 0.0;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return ((int) (Math.floor(price / 2.0)));
    }

    public int getBuyPrice() {
        return price;
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

    public static ArrayList<String> getResourcesName() {
        return resourcesName;
    }

    public static ArrayList<String> getFoodsName() {
        return foodsName;
    }

    public static ArrayList<String> getWeaponsName() {
        return weaponsName;
    }

    public static ArrayList<Resource> getAllResources() {
        return allResources;
    }

    public static Resource createResource(String resourceName) {
        for (Resource resource : resources) {
            if (resource.getName().equals(resourceName)) {
                return new Resource(resourceName, resource.price, resource.storage);
            }
        }
        for (Resource food : foods) {
            if (food.getName().equals(resourceName)) {
                return new Resource(resourceName, food.price, food.storage);
            }
        }
        for (Resource weapon : weapons) {
            if (weapon.getName().equals(resourceName)) {
                return new Resource(resourceName, weapon.price, weapon.storage);
            }
        }
        return null;
    }

    public static Resource getResourceByName(String name) {

        for (Resource resource : allResources) {
            if (resource.getName().equals(name))
                return resource;
        }
        return null;
    }

    public static void readResourcesFromFile() {
        return;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return price == resource.price && Objects.equals(name, resource.name) && Objects.equals(storage, resource.storage);
    }
}
