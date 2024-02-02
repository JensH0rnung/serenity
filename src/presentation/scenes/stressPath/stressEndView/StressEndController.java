package presentation.scenes.stressPath.stressEndView;

import application.App;
import application.View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class StressEndController {

    private BorderPane root;
    private App app;

    @FXML
    Label endViewHeaderLabel;

    @FXML
    Button stressPathButton;
    @FXML
    Button meditationPathButton;
    @FXML
    Button motivationPathButton;

    public StressEndController(App app) {

        this.app = app;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("StressEndView.fxml"));
        loader.setController(this);

        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der .fxml-Datei");
            throw new RuntimeException(e);
        }
    }

    // Automatischer Aufruf vom FXML-Loader
    public void initialize() {

        stressPathButton.setOnAction(
                (actionEvent) -> app.fadeTo(View.STRESS_INTRO)
        );

        meditationPathButton.setOnAction(
                (actionEvent) -> app.fadeTo(View.MEDITATION_INTRO)
        );

        motivationPathButton.setOnAction(
                (actionEvent) -> app.fadeTo(View.MOTIVATION_INTRO)
        );

    }

    public Pane getRoot() {
        return root;
    }

}
