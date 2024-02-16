package presentation.scenes.stressPath.stressEndView;

import application.AnimatedViews;
import application.App;
import application.View;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class StressEndController implements AnimatedViews {

    private BorderPane root;
    private App app;
    private FadeTransition fade1;
    private FadeTransition fade2;
    private FadeTransition fade3;
    private FadeTransition fade4;
    private SequentialTransition fades;

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

        fade1 = createFadeTransition(endViewHeaderLabel);
        fade2 = createFadeTransition(stressPathButton);
        fade3 = createFadeTransition(meditationPathButton);
        fade4 = createFadeTransition(motivationPathButton);
        fades = new SequentialTransition(fade1, fade2, fade3, fade4);
    }

    private FadeTransition createFadeTransition(Node node) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(node);
        fadeTransition.setDuration(Duration.seconds(0.3));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        return fadeTransition;
    }

    public Pane getRoot() {
        return root;
    }

    @Override
    public void startAnimations() {
        fades.play();
        showAllElements();
    }

    @Override
    public void hideAllElements() {
        endViewHeaderLabel.setVisible(false);
        stressPathButton.setVisible(false);
        meditationPathButton.setVisible(false);
        motivationPathButton.setVisible(false);
    }

    @Override
    public void showAllElements() {
        endViewHeaderLabel.setVisible(true);
        stressPathButton.setVisible(true);
        meditationPathButton.setVisible(true);
        motivationPathButton.setVisible(true);
    }
}
