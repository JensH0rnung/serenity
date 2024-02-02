package presentation.scenes.motivationPath.motivationIntroView;

import application.App;
import application.View;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import presentation.ui_components.BottomNavHomeRight;

import java.io.IOException;

public class MotivationIntroController {

    private BorderPane root;
    private App app;

    private FadeTransition fade1;
    private FadeTransition fade2;
    private FadeTransition fade3;
    private FadeTransition fade4;

    @FXML
    BottomNavHomeRight bottomNavHomeRight;
    Button homeButton;
    Button rightArrowButton;

    @FXML
    Label motivationIntroHeaderLabel;

    @FXML
    Label firstTextLabel;
    @FXML
    Label secondTextLabel;
    @FXML
    Label thirdTextLabel;

    public MotivationIntroController(App app) {

        this.app = app;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MotivationIntroView.fxml"));
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
                actionevent -> app.fadeTo(View.INTRO)
        );

        rightArrowButton.setOnAction(
                actionEvent -> app.rightSlideTo(View.MOTIVATION_SELECTION)
        );

        motivationIntroHeaderLabel.setVisible(false);
        firstTextLabel.setVisible(false);
        secondTextLabel.setVisible(false);
        thirdTextLabel.setVisible(false);

        fade1 = createFadeTransition(motivationIntroHeaderLabel);
        fade2 = createFadeTransition(firstTextLabel);
        fade3 = createFadeTransition(secondTextLabel);
        fade4 = createFadeTransition(thirdTextLabel);

        startNewFade(fade1, motivationIntroHeaderLabel);

        fade1.setOnFinished(
                event -> startNewFade(fade2, firstTextLabel)
        );

        fade2.setOnFinished(
                event -> startNewFade(fade3, secondTextLabel)
        );

        fade3.setOnFinished(
                event -> startNewFade(fade4, thirdTextLabel)
        );

    }

    private FadeTransition createFadeTransition(Label label) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(label);
        fadeTransition.setDuration(Duration.seconds(2));
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

    public Pane getRoot() {
        return root;
    }
}
