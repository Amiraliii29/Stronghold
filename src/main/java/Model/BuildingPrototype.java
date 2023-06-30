package Model;

import Main.*;
import com.google.gson.Gson;
import java.util.ArrayList;

public class BuildingPrototype {
    public static ArrayList<String> buildingsName;
    public static ArrayList<String> Defences;
    public Government owner;
    public String name;
    public int x;
    public int y;
    public int width;
    public int length;
    public int maxHP;
    public int HP;
    public boolean canPass;


    static {
        buildingsName = new ArrayList<>();
    }


    public BuildingPrototype(Government owner, String name, int x, int y) {
        this.owner = owner;
        this.name = name;
        this.x = x;
        this.y = y;
    }





    public Government getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getHP() {
        return HP;
    }

    public boolean isCanPass() {
        return canPass;
    }

    public static ArrayList<String> getBuildingsName() {
        return buildingsName;
    }

    public static void setBuildingsName(ArrayList<String> buildingsName) {
        BuildingPrototype.buildingsName = buildingsName;
    }




    public static BuildingPrototype getBuildingByName(String name) {
        BuildingPrototype building = new BuildingPrototype(null, name, -1, -1);
        Request request = new Request(GameRequest.CREATE_BUILDING);
        request.argument.put("fake", building.toJson());

        Client.client.sendRequestToServer(request, true);

        Result result = Result.fromJson(Client.client.getRecentResponse());

        return  fromJson(result.arguments.get("fake"));
    }



    public String toJson() {
        return new Gson().toJson(this);
    }

    public static BuildingPrototype fromJson(String json) {
        return new Gson().fromJson(json, BuildingPrototype.class);
    }
}
