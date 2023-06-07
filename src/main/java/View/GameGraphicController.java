package View;

import Model.DataBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;


// this class has been created by amirali for bottom menu controller
public class GameGraphicController {
    public static Label popularity;
    public static Label golds;
    public static Label population;
    private static Stage popularityFactorsStage = new Stage();


    public static void setPopularityGoldPopulation(){
        int popularityInt = DataBase.getCurrentGovernment().getPopularity();
        popularity.setText(Integer.toString(popularityInt));

        if(popularityInt > 75)
            popularity.setTextFill(Color.GREEN);
        else if(popularityInt < 25)
            popularity.setTextFill(Color.RED);

        golds.setText(Integer.toString((int) DataBase.getCurrentGovernment().getMoney()));

        population.setText(DataBase.getCurrentGovernment().getPopulation() + "/128");

    }
    public void openPopularityReport(MouseEvent mouseEvent) {
        try {

            AnchorPane popularityFactorsPane = FXMLLoader.load(
                    new URL(Main.class.getResource("/fxml/PopularityFactorsMenu.fxml").toExternalForm()));
            Scene scene = new Scene(popularityFactorsPane);
            popularityFactorsStage.setScene(scene);
            popularityFactorsStage.show();
        }
        catch (Exception e){}
        throw new RuntimeException("heyo");
    }
}
