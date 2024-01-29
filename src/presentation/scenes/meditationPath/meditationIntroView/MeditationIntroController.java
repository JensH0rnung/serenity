package presentation.scenes.meditationPath.meditationIntroView;

import application.App;
import application.View;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import presentation.ui_components.BottomNavHomeRight;

import java.io.IOException;

public class MeditationIntroController {

    private StackPane root;
    private App app;

    private FadeTransition fade1;
    private FadeTransition fade2;
    private FadeTransition fade3;

    // "Erzeugung" des Subviews durch Einbindung in FXML
    @FXML
    BottomNavHomeRight bottomNavHomeRight;
    Button homeButton;
    Button rightArrowButton;

    @FXML
    Label meditationIntroHeaderLabel;

    @FXML
    Label firstTextLabel;
    @FXML
    VBox fourLabelContainer;
    @FXML
    Label thirdTextLabel;

    public MeditationIntroController(App app) {

        this.app = app;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MeditationIntroView.fxml"));
        loader.setController(this);

        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der .fxml-Datei");
            throw new RuntimeException(e);
        }
    }

    public void initialize() {

        homeButton = bottomNavHomeRight.getHomeButton();
        rightArrowButton = bottomNavHomeRight.getRightArrowButton();

        homeButton.setOnAction(
                actionevent -> app.switchView(View.INTRO)
        );

        rightArrowButton.setOnAction(
                actionEvent -> app.switchView(View.MEDITATION_SELECTION)
        );

        Image image = new Image("presentation/scenes/meditationPath/meditationIntroView/sleepTimerIcon.png");
        ImageView sleepIcon = new ImageView(image);
        thirdTextLabel.setGraphic(sleepIcon);
        thirdTextLabel.setContentDisplay(ContentDisplay.RIGHT);

        meditationIntroHeaderLabel.setVisible(false);
        firstTextLabel.setVisible(false);
        fourLabelContainer.setVisible(false);

        fade1 = createFadeTransition(meditationIntroHeaderLabel);
        fade2 = createFadeTransition(firstTextLabel);
        fade3 = createFadeTransition(fourLabelContainer);

        fade1.play();
        meditationIntroHeaderLabel.setVisible(true);

        fade1.setOnFinished(
                event -> startNewFade(fade2, firstTextLabel)
        );

        fade2.setOnFinished(
                event -> startNewFade(fade3, fourLabelContainer)
        );
    }

    private FadeTransition createFadeTransition(Label label) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(label);
        fadeTransition.setDuration(Duration.seconds(1.5));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        return fadeTransition;
    }

    /**
     * Erstellt Fade-Animation für mehrere Labels
     *
     * @param labelContainer - gruppierte Labels
     * @return - eine Animation für mehrere Labels
     */
    private FadeTransition createFadeTransition(VBox labelContainer) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(labelContainer);
        fadeTransition.setDuration(Duration.seconds(2));
        fadeTransition.setInterpolator(Interpolator.EASE_IN);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        return fadeTransition;
    }

    private void startNewFade(FadeTransition fadeToStart, Label labelToShow) {
        new Thread(
                () -> {
                    try {
                        Thread.currentThread().sleep(100);
                    } catch (InterruptedException e) {}
                    fadeToStart.play();
                    labelToShow.setVisible(true);
                }
        ).start();
    }

    /**
     * Startet Fades in neuem Thread
     *
     * @param fadeToStart
     * @param groupToShow - Gruppe von Labels
     */
    private void startNewFade(FadeTransition fadeToStart, VBox groupToShow) {
        new Thread(
                () -> {
                    try {
                        Thread.currentThread().sleep(100);
                    } catch (InterruptedException e) {}
                    fadeToStart.play();
                    groupToShow.setVisible(true);
                }
        ).start();
    }

    public Pane getRoot() {
        return root;
    }
}
