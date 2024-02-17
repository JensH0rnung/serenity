package presentation.scenes.stressPath.stressBreathingView;

import application.AnimatedViews;
import application.App;
import application.View;
import business_logic.services.BreathingRhythmClass;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import presentation.ui_components.BottomNavLeftHomeRight;

import java.io.IOException;

public class StressBreathingController implements AnimatedViews {

    private BorderPane root;
    private App app;
    private BreathingRhythmClass breathingRhythm;

    private ScaleTransition circGrow;
    private ScaleTransition circShrink;

    private int breathInDuration;
    private int breathOutDuration;
    private String newColor;
    private String oldColor;

    @FXML
    Label stressBreathingHeaderLabel;

    @FXML
    Circle breathingCircle;
    @FXML
    Button startAnimationButton;

    @FXML
    BottomNavLeftHomeRight bottomNavLeftHomeRight;
    Button leftArrowButton;
    Button homeButton;
    Button rightArrowButton;

    public StressBreathingController(App app, BreathingRhythmClass breathingRhythm) {

        this.app = app;
        this.breathingRhythm = breathingRhythm;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("StressBreathingView.fxml"));
        loader.setController(this);

        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der .fxml-Datei");
            throw new RuntimeException(e);
        }
    }

    public void initialize() {

        // Durations der Animationen werden abhängig vom Property gesetzt
        breathingRhythm.breathingRhythmProperty().addListener(
                ((observableValue, oldRhythm, selectedRhythm) -> {
                    breathInDuration = selectedRhythm.getBreathInDuration();
                    breathOutDuration = selectedRhythm.getBreathOutDuration();

                    circGrow.setDuration(Duration.seconds(breathInDuration));
                    circShrink.setDuration(Duration.seconds(breathOutDuration));

                    newColor = selectedRhythm.getColor();
                    breathingCircle.getStyleClass().add(newColor);

                    if(oldColor != null) {
                        oldColor = oldRhythm.getColor();
                        breathingCircle.getStyleClass().remove(oldColor);
                    }
                })
        );

        stressBreathingHeaderLabel.setVisible(false);

        // Startet Animation und versteckt Button
        startAnimationButton.setOnMouseClicked(
                actionevent -> {
                    circGrow.play();
                    stressBreathingHeaderLabel.setVisible(true);
                    startAnimationButton.setDisable(true);
                    startAnimationButton.setVisible(false);
                }
        );

        leftArrowButton = bottomNavLeftHomeRight.getLeftArrowButton();
        homeButton = bottomNavLeftHomeRight.getHomeButton();
        rightArrowButton = bottomNavLeftHomeRight.getRightArrowButton();

        leftArrowButton.setOnAction(
                actionevent -> {
                    app.fadeTo(View.STRESS_SELECTION);
                    resetBreathingCircle();
                }
        );

        homeButton.setOnAction(
                actionevent -> app.fadeTo(View.CHOOSE_PATH)
        );

        rightArrowButton.setOnAction(
                actionEvent -> app.rightSlideTo(View.STRESS_END)
        );

        // Animation, die den Kreis wachsen lässt
        circGrow = new ScaleTransition();
        circGrow.setNode(breathingCircle);
        circGrow.setInterpolator(Interpolator.LINEAR);  // Linear, da gleichmäßige Atmung

        // Kreis wächst auf sein 4-Faches
        circGrow.setByX(4);
        circGrow.setByY(4);

        // Animation, die den Kreis schrumpfen lässt
        circShrink = new ScaleTransition();
        circShrink.setNode(breathingCircle);
        circShrink.setInterpolator(Interpolator.LINEAR);

        circShrink.setByX(-4);
        circShrink.setByY(-4);

        circGrow.setOnFinished(
                actionEvent-> {
                    circShrink.play();
                    Platform.runLater(
                            () -> stressBreathingHeaderLabel.setText("Ausatmen ...")
                    );
                }
        );
        circShrink.setOnFinished(
                actionEvent-> {
                    circGrow.play();
                    Platform.runLater(
                            () -> stressBreathingHeaderLabel.setText("Einatmen ...")
                    );
                }
        );
    }

    private void resetBreathingCircle() {

        // wartet mit dem Reset bis Elemente ausgefadet sind
        // 500ms = Dauer des Fade-Outs
        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(
                event -> {
                    circGrow.stop();
                    circShrink.stop();

                    breathingCircle.setScaleX(1);
                    breathingCircle.setScaleY(1);

                    startAnimationButton.setVisible(true);
                    startAnimationButton.setDisable(false);
                }
        );
        pause.play();
    }

    public Pane getRoot() {
        return root;
    }

    @Override
    public void startAnimations() {

    }

    @Override
    public void hideAllElements() {

    }

    @Override
    public void showAllElements() {

    }
}
