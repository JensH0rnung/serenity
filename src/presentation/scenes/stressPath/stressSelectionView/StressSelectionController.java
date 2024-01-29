package presentation.scenes.stressPath.stressSelectionView;

import application.App;
import application.View;
import business_logic.data.BreathingRhythm;
import business_logic.services.BreathingRhythmClass;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import presentation.ui_components.BottomNavLeftHome;

import java.io.IOException;

public class StressSelectionController {

    private StackPane root;
    private App app;
    private BreathingRhythmClass breathingRhythm;

    private FadeTransition fade1;
    private FadeTransition fade2;

    @FXML
    Label stressSelectionHeaderLabel;
    @FXML
    VBox rhythmElements;

    @FXML
    BottomNavLeftHome bottomNavLeftHome;
    Button homeButton;
    Button leftArrowButton;

    @FXML
    Button chooseFirstRhythm;
    @FXML
    Button chooseSecondRhythm;
    @FXML
    Button chooseThirdRhythm;

    public StressSelectionController(App app, BreathingRhythmClass breathingRhythm) {
        this.app = app;
        this.breathingRhythm = breathingRhythm;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("StressSelectionView.fxml"));
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
                (actionEvent) -> {
                    breathingRhythm.setBreathingRhythm(BreathingRhythm.FIRST);
                    app.switchView(View.STRESS_BREATHING);
                }
        );

        chooseSecondRhythm.setOnAction(
                (actionEvent) -> {
                    breathingRhythm.setBreathingRhythm(BreathingRhythm.SECOND);
                    app.switchView(View.STRESS_BREATHING);
                }
        );

        chooseThirdRhythm.setOnAction(
                (actionEvent) -> {
                    breathingRhythm.setBreathingRhythm(BreathingRhythm.THIRD);
                    app.switchView(View.STRESS_BREATHING);
                }
        );

        homeButton.setOnAction(
                actionevent -> app.switchView(View.INTRO)
        );

        leftArrowButton.setOnAction(
                actionEvent -> app.switchView(View.STRESS_INTRO)
        );

        // Animations
        stressSelectionHeaderLabel.setVisible(false);
        rhythmElements.setVisible(false);

        fade1 = new FadeTransition();
        fade1.setNode(stressSelectionHeaderLabel);
        fade1.setDuration(Duration.seconds(1));
        fade1.setFromValue(0);
        fade1.setToValue(1);

        fade2 = new FadeTransition();
        fade2.setNode(rhythmElements);
        fade2.setDuration(Duration.seconds(1));
        fade2.setFromValue(0);
        fade2.setToValue(1);

        new Thread(
                () -> {
                    try {
                        Thread.currentThread().sleep(100);
                    } catch (InterruptedException e) {}
                    fade1.play();
                    stressSelectionHeaderLabel.setVisible(true);
                }
        ).start();

        fade1.setOnFinished(
                event -> {
                    new Thread(
                            () -> {
                                try {
                                    Thread.currentThread().sleep(100);
                                } catch (InterruptedException e) {}
                                fade2.play();
                                rhythmElements.setVisible(true);
                            }
                    ).start();
                }
        );
    }

    public Pane getRoot() {
        return root;
    }
}
