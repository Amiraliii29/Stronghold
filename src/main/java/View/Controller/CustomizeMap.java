package View.Controller;

import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Model.DataBase;
import Model.Land;
import Model.Map;
import Model.Trees;
import View.Game;
import View.LoginMenu;
import View.SignUpMenu;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CustomizeMap {

    public TextField name;

    public void bigLake(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.BIG_LAKE;
    }

    public void cliff(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.CLIFF;
    }

    public void defaultTile(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.DEFAULT;
    }

    public void ditch(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.DITCH;
    }

    public void flatRock(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.FLAT_ROCK;
    }

    public void grass(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.GRASS;
    }

    public void gravel(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.GRAVEL;
    }

    public void densityMeadow(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.FULL_MEADOW;
    }

    public void iron(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.IRON;
    }

    public void lowDepthWater(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.LOW_DEPTH_WATER;
    }

    public void meadow(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.MEADOW;
    }

    public void oil(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.OIL;
    }

    public void plain(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.PLAIN;
    }

    public void river(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.RIVER;
    }

    public void rock(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.ROCK;
    }

    public void sand(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.SAND;
    }

    public void sea(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.SEA;
    }

    public void smallLake(MouseEvent ignoredMouseEvent) {
        Game.tree = null;
        Game.land = Land.SMALL_LAKE;
    }

    public void cherry(MouseEvent ignoredMouseEvent) {
        Game.land = null;
        Game.tree = Trees.CHERRY_PALM;
    }

    public void coconut(MouseEvent ignoredMouseEvent) {
        Game.land = null;
        Game.tree = Trees.COCONUT_TREE;
    }

    public void date(MouseEvent ignoredMouseEvent) {
        Game.land = null;
        Game.tree = Trees.DATE_PALM;
    }

    public void olive(MouseEvent ignoredMouseEvent) {
        Game.land = null;
        Game.tree = Trees.OLIVE_PALM;
    }

    public void back(MouseEvent ignoredMouseEvent) throws Exception {
        Game.customizePane = null;
        new LoginMenu().start(SignUpMenu.stage);
    }

    public void save(MouseEvent ignoredMouseEvent) {
        if (name == null || name.getText() == null || name.getText().equals("")) return;
        else {
            Request request = new Request(NormalRequest.CHECK_MAP_NAME);
            request.addToArguments("name", name.getText());

            Client.client.sendRequestToServer(request, true);
            String response = Client.client.getRecentResponse();

            if (response.equals("true")) {
                Request request1 = new Request(NormalRequest.SAVE_MAP);
                request1.addToArguments("name", name.getText());
                Client.client.sendRequestToServer(request, false);

                ObjectMapper objectMapper = new ObjectMapper();
                Object json = DataBase.getSelectedMap();

                try {
                    byte[] jsonBytes = objectMapper.writeValueAsBytes(json);

                    Client.client.getDataOutputStream().writeInt(jsonBytes.length);
                    Client.client.getDataOutputStream().write(jsonBytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}