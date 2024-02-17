package presentation.scenes.meditationPath.meditationIntroView;

import application.AnimatedViews;
import application.App;
import application.View;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import presentation.ui_components.BottomNavHomeRight;

import java.io.IOException;

public class MeditationIntroController implements AnimatedViews {

    private BorderPane root;
    private App app;

    private FadeTransition fade1;
    private FadeTransition fade2;
    private FadeTransition fade3;
    private FadeTransition fade4;
    private SequentialTransition fades;

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
                actionevent -> app.fadeTo(View.CHOOSE_PATH)
        );

        rightArrowButton.setOnAction(
                actionEvent -> app.rightSlideTo(View.MEDITATION_SELECTION)
        );

        Image image = new Image("assets/icons/sleepIcon20.png");
        ImageView sleepIcon = new ImageView(image);
        thirdTextLabel.setGraphic(sleepIcon);
        thirdTextLabel.setContentDisplay(ContentDisplay.RIGHT);

        fade1 = createFadeTransition(meditationIntroHeaderLabel);
        fade2 = createFadeTransition(firstTextLabel);
        fade3 = createFadeTransition(fourLabelContainer);
        fade4 = createFadeTransition(bottomNavHomeRight);
        fades = new SequentialTransition(fade1, fade2, fade3, fade4);
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

    @Override
    public void startAnimations() {
        fades.play();
        showAllElements();
    }

    @Override
    public void hideAllElements() {
        meditationIntroHeaderLabel.setVisible(false);
        firstTextLabel.setVisible(false);
        fourLabelContainer.setVisible(false);
        bottomNavHomeRight.setVisible(false);
    }

    @Override
    public void showAllElements() {
        meditationIntroHeaderLabel.setVisible(true);
        firstTextLabel.setVisible(true);
        fourLabelContainer.setVisible(true);
        bottomNavHomeRight.setVisible(true);
    }

    public Pane getRoot() {
        return root;
    }
}
