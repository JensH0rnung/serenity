package presentation.scenes.introView;

import application.AnimatedViews;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class IntroViewController implements AnimatedViews {

    private StackPane root;

    private FadeTransition fade1;
    private FadeTransition fade2;
    private SequentialTransition multiFades;

    @FXML
    Label serenityLabel, introThreeWordsLabel;

    public IntroViewController() {

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

        fade1 = new FadeTransition();
        fade1.setNode(serenityLabel);
        fade1.setDuration(Duration.seconds(1));  // bewusst länger
        fade1.setFromValue(0);
        fade1.setToValue(1);

        // Erstellt Fades für Buttons
        fade2 = createFadeTransition(introThreeWordsLabel);

        // Elemente werden nacheinander eingefadet
        multiFades = new SequentialTransition(fade1, fade2);
    }

    /**
     * Erstellt Fade-Animations für einzelne Buttons
     *
     * @param label - Label, für den Animation erstellt werden soll
     * @return - konfigurierte Animation
     */
    private FadeTransition createFadeTransition(Label label) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(label);
        fadeTransition.setDuration(Duration.seconds(0.3));  // bewusst kürzer
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
    }

    @Override
    public void showAllElements() {
        serenityLabel.setVisible(true);
        introThreeWordsLabel.setVisible(true);
    }

    public Pane getRoot() {
        return root;
    }
}
