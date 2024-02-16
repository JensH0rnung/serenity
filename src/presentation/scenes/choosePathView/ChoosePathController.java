package presentation.scenes.choosePathView;

import application.AnimatedViews;
import application.App;
import application.View;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class ChoosePathController implements AnimatedViews {

    private BorderPane root;
    private App app;

    private FadeTransition fade1;
    private FadeTransition fade2;
    private FadeTransition fade3;
    private FadeTransition fade4;
    private SequentialTransition multiFades;

    @FXML
    Label introHeaderLabel;

    @FXML
    Button stressPathButton;
    @FXML
    Button meditationPathButton;
    @FXML
    Button motivationPathButton;

    public ChoosePathController(App app) {

        this.app = app;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChoosePathView.fxml"));
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
                actionEvent -> app.fadeTo(View.STRESS_INTRO)
        );

        meditationPathButton.setOnAction(
                actionEvent -> app.fadeTo(View.MEDITATION_INTRO)
        );

        motivationPathButton.setOnAction(
                actionEvent -> app.fadeTo(View.MOTIVATION_INTRO)
        );

        fade1 = new FadeTransition();
        fade1.setNode(introHeaderLabel);
        fade1.setDuration(Duration.seconds(1));  // bewusst länger
        fade1.setFromValue(0);
        fade1.setToValue(1);

        // Erstellt Fades für Buttons
        fade2 = createFadeTransition(stressPathButton);
        fade3 = createFadeTransition(meditationPathButton);
        fade4 = createFadeTransition(motivationPathButton);

        // Elemente werden nacheinander eingefadet
        multiFades = new SequentialTransition(fade1, fade2, fade3, fade4);
    }

    /**
     * Erstellt Fade-Animations für einzelne Buttons
     *
     * @param button - Button, für den Animation erstellt werden soll
     * @return - konfigurierte Animation
     */
    private FadeTransition createFadeTransition(Button button) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(button);
        fadeTransition.setDuration(Duration.seconds(0.3));  // bewusst kürzer
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        return fadeTransition;
    }

    @Override
    public void startAnimations() {
        multiFades.play();
        showAllElements();
    }

    @Override
    public void hideAllElements() {
        introHeaderLabel.setVisible(false);
        stressPathButton.setVisible(false);
        meditationPathButton.setVisible(false);
        motivationPathButton.setVisible(false);
    }

    @Override
    public void showAllElements() {
        introHeaderLabel.setVisible(true);
        stressPathButton.setVisible(true);
        meditationPathButton.setVisible(true);
        motivationPathButton.setVisible(true);
    }

    public Pane getRoot() {
        return root;
    }
}
