package presentation.scenes.stressPath.stressSelectionView;

import application.AnimatedViews;
import application.App;
import application.View;
import business_logic.data.BreathingRhythm;
import business_logic.services.BreathingRhythmClass;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import presentation.ui_components.BottomNavLeftHome;

import java.io.IOException;

public class StressSelectionController implements AnimatedViews {

    private BorderPane root;
    private App app;
    private BreathingRhythmClass breathingRhythm;

    private FadeTransition fade1;
    private FadeTransition fade2;
    private FadeTransition fade3;
    private SequentialTransition fades;

    @FXML
    Label stressSelectionHeaderLabel;
    @FXML
    VBox rhythmElements;

    @FXML
    BottomNavLeftHome bottomNavLeftHome;
    Button homeButton;
    Button leftArrowButton;

    @FXML
    Button chooseFirstRhythm, chooseSecondRhythm, chooseThirdRhythm;

    public StressSelectionController(App app, BreathingRhythmClass breathingRhythm) {
        this.app = app;
        this.breathingRhythm = breathingRhythm;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("StressSelection.fxml"));
        loader.setController(this);

        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der .fxml-Datei");
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        homeButton = bottomNavLeftHome.getHomeButton();
        leftArrowButton = bottomNavLeftHome.getLeftArrowButton();

        chooseFirstRhythm.setOnAction(
                actionEvent -> {
                    breathingRhythm.setBreathingRhythm(BreathingRhythm.FIRST);
                    app.fadeTo(View.STRESS_BREATHING);
                }
        );

        chooseSecondRhythm.setOnAction(
                actionEvent -> {
                    breathingRhythm.setBreathingRhythm(BreathingRhythm.SECOND);
                    app.fadeTo(View.STRESS_BREATHING);
                }
        );

        chooseThirdRhythm.setOnAction(
                actionEvent -> {
                    breathingRhythm.setBreathingRhythm(BreathingRhythm.THIRD);
                    app.fadeTo(View.STRESS_BREATHING);
                }
        );

        homeButton.setOnAction(
                actionevent -> app.fadeTo(View.CHOOSE_PATH)
        );

        leftArrowButton.setOnAction(
                actionEvent -> app.leftSlideTo(View.STRESS_INTRO)
        );

        fade1 = createFadeTransition(stressSelectionHeaderLabel);
        fade2 = createFadeTransition(rhythmElements);
        fade3 = createFadeTransition(bottomNavLeftHome);
        fades = new SequentialTransition(fade1, fade2, fade3);
    }

    /**
     * Hilfsmethode um Fades zu erstellen
     *
     * @param node - Element, für das Fade erstellt werden soll
     * @return - Fade Transition
     */
    private FadeTransition createFadeTransition(Node node) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(node);
        fadeTransition.setDuration(Duration.seconds(0.5));
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
        stressSelectionHeaderLabel.setVisible(false);
        rhythmElements.setVisible(false);
        bottomNavLeftHome.setVisible(false);
    }

    @Override
    public void showAllElements() {
        stressSelectionHeaderLabel.setVisible(true);
        rhythmElements.setVisible(true);
        bottomNavLeftHome.setVisible(true);
    }
}
