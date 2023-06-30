package View;

import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Model.DataBase;
import Model.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class CustomizeMapEntry extends Application {
    Stage stage;
    ChoiceBox<String> choiceBox;
    ChoiceBox<String> choiceBox2;


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        AnchorPane pane = FXMLLoader.load(
                new URL(CustomizeMapEntry.class.getResource("/fxml/CustomizeMapEntry.fxml").toExternalForm()));

        Scene scene = new Scene(pane);
        stage.setScene(scene);

        ObservableList<String> options = FXCollections.observableArrayList(
                "Default"
        );
        Client.client.sendRequestToServer(new Request(NormalRequest.MAP_NAME),true);
        String response = Client.client.getRecentResponse();
        String[] names =  response.split(",");

        for (int i = 0; i < names.length; i++)
            options.add(names[i]);

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
        request.addToArguments("Map Name", choice);

        Client.client.sendRequestToServer(request, true);
        Map map = Map.loadMapFromJson(Client.client.getRecentResponse());
        DataBase.setSelectedMap(map);

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
