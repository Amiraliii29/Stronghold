package View;

import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Model.DataBase;
import Model.Map;
import Model.Square;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInputStream;
import java.net.URL;
import java.util.Arrays;

public class CustomizeMapEntry extends Application {
    static Stage stage;
    static ChoiceBox<String> choiceBox;
    static ChoiceBox<String> choiceBox2;


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        AnchorPane pane = FXMLLoader.load(
                new URL(CustomizeMapEntry.class.getResource("/fxml/CustomizeMapEntry.fxml").toExternalForm()));

        Scene scene = new Scene(pane);
        stage.setScene(scene);

        ObservableList<String> options = FXCollections.observableArrayList(
        );
        Client.client.sendRequestToServer(new Request(NormalRequest.MAP_NAME),true);
        String response = Client.client.getRecentResponse();
        String[] names =  response.split(",");

        options.addAll(Arrays.asList(names));

        choiceBox = new ChoiceBox<>(options);
        pane.getChildren().add(choiceBox);
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.setLayoutX(71);
        choiceBox.setLayoutY(112);
        choiceBox.setPrefWidth(150);


        ObservableList<String> options2 = FXCollections.observableArrayList(
                "150 * 150", "200 * 200", "400 * 400"
        );

        choiceBox2 = new ChoiceBox<>(options2);
        pane.getChildren().add(choiceBox2);
        choiceBox2.getSelectionModel().selectFirst();
        choiceBox2.setLayoutX(71);
        choiceBox2.setLayoutY(266);
        choiceBox2.setPrefWidth(150);

        stage.show();
    }

    public void open(MouseEvent mouseEvent) throws Exception {
        String choice = choiceBox.getValue();
        Request request = new Request(NormalRequest.GET_MAP);
        request.addToArguments("name", choice);

        Client.client.serverResponseListener.changeSpecific();
        Client.client.sendRequestToServer(request, false);

        DataInputStream dataInputStream = Client.client.dataInputStream;

        System.out.println("before all!");


        int jsonLength = dataInputStream.readInt();
        System.out.println("get int : " + jsonLength);
        byte[] jsonBytes = new byte[jsonLength];

        dataInputStream.readFully(jsonBytes);

        System.out.println("bytes read!");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.readValue(jsonBytes, String.class);
        System.out.println("into string");
        Gson gson = new Gson();
        Map map = gson.fromJson(json, Map.class);
        System.out.println("done");
        Client.client.serverResponseListener.changeSpecific();

        DataBase.setSelectedMap(map);

        Square[][] squares = map.getSquares();
        for (int i = 0; i < map.getWidth() + 1; i++) {
            for (int j = 0; j < map.getLength() + 1; j++) {
                squares[i][j].newUnits();
            }
        }

        Game game = new Game();
        game.customizeMap();
        game.start(stage);
    }

    public void create(MouseEvent mouseEvent) throws Exception {
        String choice = choiceBox2.getValue();
        int size = 0;
        switch (choice) {
            case "150 * 150" -> size = 150;
            case "200 * 200" -> size = 200;
            case "400 * 400" -> size = 400;
        }

        Map map = new Map("untitled", size, size);
        DataBase.setSelectedMap(map);

        Game game = new Game();
        game.customizeMap();
        game.start(stage);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new LoginMenu().start(stage);
    }
}
