package View.Controller;

import Model.DataBase;
import View.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class GetCoordinate extends Application {
    public Stage stage;
    public TextField xCoordinate;
    public TextField yCoordinate;

    public GetCoordinate(){
        stage = new Stage();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);

        URL url = GetCoordinate.class.getResource("/fxml/GetCoordinate.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        stage.show();
    }

    public void select(MouseEvent mouseEvent) {
        if (xCoordinate == null || yCoordinate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Field");
            alert.setHeaderText("Empty Field!");
            alert.setContentText("Fill Both Field");
            return;
        }

        if (xCoordinate.getText().matches("^\\d+$") && yCoordinate.getText().matches("^\\d+$")) {
            int x = Integer.parseInt(xCoordinate.getText()) - 1;
            int y = Integer.parseInt(yCoordinate.getText()) - 1;

            if (x >= 0 && x < DataBase.getSelectedMap().getWidth() && y >= 0 && y < DataBase.getSelectedMap().getLength()) {
                Game.setXY(x, y);
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Out Of Bound");
                alert.setHeaderText("Use Coordinate That Are In Map");
                alert.setContentText("X Between 1 - " + DataBase.getSelectedMap().getWidth() + " & Y Between 1 - " + DataBase.getSelectedMap().getLength());
            }
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wrong Format");
        alert.setHeaderText("Use Only Number");
    }

    public void back(MouseEvent mouseEvent) {
        Game.setXY(-1, -1);
        stage.close();
    }
}
