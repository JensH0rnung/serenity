package presentation.scenes.stressPath.stressBreathingView;

import application.App;
import application.View;
import business_logic.services.BreathingRhythmClass;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import presentation.ui_components.BottomNavLeftHomeRight;

import java.io.IOException;

public class StressBreathingController {

    private StackPane root;
    private App app;
    private BreathingRhythmClass breathingRhythm;

    private ScaleTransition circGrow;
    private ScaleTransition circShrink;

    private int breathInDuration;
    private int breathOutDuration;
    private String color;

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
                ((observableValue, oldV, selectedRhythm) -> {
                    breathInDuration = selectedRhythm.getBreathInDuration();
                    breathOutDuration = selectedRhythm.getBreathOutDuration();
                    color = selectedRhythm.getColor();

                    breathingCircle.getStyleClass().add(color);
                    circGrow.setDuration(Duration.seconds(breathInDuration));
                    circShrink.setDuration(Duration.seconds(breathOutDuration));
                })
        );

        stressBreathingHeaderLabel.setVisible(false);

        // Startet Animation und versteckt Button
        startAnimationButton.setOnMouseClicked(
                actionevent -> {
                    // Test
                    System.out.println("Dauer einatmen - " + circGrow.getDuration());
                    System.out.println("Dauer ausatmen - " + circShrink.getDuration());
                    circGrow.play();
                    Platform.runLater(
                            () -> {
                                stressBreathingHeaderLabel.setVisible(true);
                                startAnimationButton.setDisable(true);
                                startAnimationButton.setOpacity(0);
                            }
                    );
                }
        );

        leftArrowButton = bottomNavLeftHomeRight.getLeftArrowButton();
        homeButton = bottomNavLeftHomeRight.getHomeButton();
        rightArrowButton = bottomNavLeftHomeRight.getRightArrowButton();

        leftArrowButton.setOnAction(
                actionevent -> app.switchView(View.STRESS_SELECTION)
        );

        homeButton.setOnAction(
                actionevent -> app.switchView(View.INTRO)
        );

        rightArrowButton.setOnAction(
                actionEvent -> app.switchView(View.STRESS_END)
        );

//        breathingCircle.radiusProperty().addListener(
//                (observable, oldV, newV) -> {
//                    // Dies verwenden anstatt Scaling setByX / Y?
//                }
//        );

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

    public Pane getRoot() {
        return root;
    }
}
