package presentation.scenes.introView;

import application.App;
import application.View;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class IntroViewController {

    private App app;

    private FadeTransition fade1;
    private FadeTransition fade2;
    private FadeTransition fade3;
    private FadeTransition fade4;

    @FXML
    StackPane backgroundStackPane;  // parentContainer
    @FXML
    BorderPane introView;   // borderPaneRoot

    @FXML
    Label introHeaderLabel;

    @FXML
    Button stressPathButton;
    @FXML
    Button meditationPathButton;
    @FXML
    Button motivationPathButton;

    public IntroViewController(App app) {

        this.app = app;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("IntroView.fxml"));
        loader.setController(this);

        try {
            backgroundStackPane = loader.load();
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der .fxml-Datei");
            throw new RuntimeException(e);
        }
    }

    // Automatischer Aufruf vom FXML-Loader
    public void initialize() {

        stressPathButton.setOnAction(
                actionEvent -> app.switchView(View.STRESS_INTRO)
        );

        meditationPathButton.setOnAction(
                actionEvent -> app.switchView(View.MEDITATION_INTRO)
        );

        motivationPathButton.setOnAction(
                actionEvent -> app.switchView(View.MOTIVATION_INTRO)
        );

        fade1 = new FadeTransition();
        fade1.setNode(introHeaderLabel);
        fade1.setDuration(Duration.seconds(1));
        fade1.setFromValue(0);
        fade1.setToValue(1);

        // Erstellt Fades für Buttons
        fade2 = createFadeTransition(stressPathButton);
        fade3 = createFadeTransition(meditationPathButton);
        fade4 = createFadeTransition(motivationPathButton);

        // Elemente werden nacheinander eingefadet
        SequentialTransition multiFades = new SequentialTransition(fade1, fade2, fade3, fade4);
        multiFades.play();
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
        fadeTransition.setDuration(Duration.seconds(0.3));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        return fadeTransition;
    }

    public Pane getParentContainer() {
        return backgroundStackPane;
    }

}
