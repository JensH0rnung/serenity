package presentation.scenes.introView;

import application.AnimatedViews;
import application.App;
import application.View;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.IOException;

public class IntroViewController implements AnimatedViews {

    private StackPane root;
    private App app;

    private FadeTransition fade1;
    private FadeTransition fade2;
    private FadeTransition fade3;
    private ParallelTransition multiFades;

    @FXML
    Label serenityLabel, introThreeWordsLabel;
    @FXML
    Line introLabelDivider;

    public IntroViewController(App app) {

        this.app = app;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("IntroView.fxml"));
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

        // Automatischer View-Wechsel nach 3.5s
        // Test
//        PauseTransition pause = new PauseTransition(Duration.seconds(3.5));
//        pause.setOnFinished(
//                event -> app.fadeTo(View.CHOOSE_PATH)
//        );
//        pause.play();

        fade1 = createFadeTransition(serenityLabel);
        fade2 = createFadeTransition(introThreeWordsLabel);
        fade3 = createFadeTransition(introLabelDivider);

        multiFades = new ParallelTransition(fade1, fade2, fade3);
    }

    /**
     * Erstellt Fade-Animations für einzelne Nodes
     *
     * @param node - Node, für die Animation erstellt werden soll
     * @return - konfigurierte Animation
     */
    private FadeTransition createFadeTransition(Node node) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(node);
        fadeTransition.setDuration(Duration.seconds(1));
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
        serenityLabel.setVisible(false);
        introThreeWordsLabel.setVisible(false);
        introLabelDivider.setVisible(false);
    }

    @Override
    public void showAllElements() {
        serenityLabel.setVisible(true);
        introThreeWordsLabel.setVisible(true);
        introLabelDivider.setVisible(true);
    }

    public Pane getRoot() {
        return root;
    }
}
