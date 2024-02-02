package presentation.scenes.stressPath.stressIntroView;

import application.App;
import application.View;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
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

public class StressIntroController {

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
    Label stressIntroHeaderLabel;

    @FXML
    Label firstTextLabel;
    @FXML
    Label secondTextLabel;
    @FXML
    Label thirdTextLabel;

    public StressIntroController(App app) {

        this.app = app;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("StressIntroView.fxml"));
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
                actionEvent -> app.rightSlideTo(View.STRESS_SELECTION)
        );

        // Erstellt Fades für jedes Label, bzw. Label-Gruppierungen
        fade1 = createFadeTransition(stressIntroHeaderLabel);
        fade2 = createFadeTransition(firstTextLabel);
        fade3 = createFadeTransition(secondTextLabel);
        fade4 = createFadeTransition(thirdTextLabel);

        // Elemente werden nacheinander eingefadet
        SequentialTransition multiFades = new SequentialTransition(fade1, fade2, fade3, fade4);
        multiFades.play();
    }

    /**
     * Erstellt Fade-Animations für einzelne Labels
     *
     * @param label - Label, für das Animation erstellt werden soll
     * @return - konfigurierte Animation
     */
    private FadeTransition createFadeTransition(Label label) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(label);
        fadeTransition.setDuration(Duration.seconds(0.7));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        return fadeTransition;
    }

    public Pane getRoot() {
        return root;
    }
}
