package presentation.scenes.stressPath.stressIntroView;

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
import presentation.ui_components.BottomNavHomeRight;

import java.io.IOException;

public class StressIntroController implements AnimatedViews {

    private BorderPane root;
    private App app;

    private FadeTransition fade1;
    private FadeTransition fade2;
    private FadeTransition fade3;
    private FadeTransition fade4;
    private FadeTransition fade5;
    private SequentialTransition fades;

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
                actionevent -> app.fadeTo(View.CHOOSE_PATH)
        );

        rightArrowButton.setOnAction(
                actionEvent -> app.rightSlideTo(View.STRESS_SELECTION)
        );

        // Erstellt Fades für jedes Label, bzw. Label-Gruppierungen
        fade1 = createFadeTransition(stressIntroHeaderLabel);
        fade2 = createFadeTransition(firstTextLabel);
        fade3 = createFadeTransition(secondTextLabel);
        fade4 = createFadeTransition(thirdTextLabel);
        fade5 = createFadeTransition(bottomNavHomeRight);

        // Elemente werden nacheinander eingefadet
        fades = new SequentialTransition(fade1, fade2, fade3, fade4, fade5);
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
        stressIntroHeaderLabel.setVisible(false);
        firstTextLabel.setVisible(false);
        secondTextLabel.setVisible(false);
        thirdTextLabel.setVisible(false);
        bottomNavHomeRight.setVisible(false);
    }

    @Override
    public void showAllElements() {
        stressIntroHeaderLabel.setVisible(true);
        firstTextLabel.setVisible(true);
        secondTextLabel.setVisible(true);
        thirdTextLabel.setVisible(true);
        bottomNavHomeRight.setVisible(true);
    }
}
