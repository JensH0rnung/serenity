package presentation.scenes.motivationPath.motivationIntroView;

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

public class MotivationIntroController implements AnimatedViews {

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

        fade1 = createFadeTransition(motivationIntroHeaderLabel);
        fade2 = createFadeTransition(firstTextLabel);
        fade3 = createFadeTransition(secondTextLabel);
        fade4 = createFadeTransition(thirdTextLabel);
        fade5 = createFadeTransition(bottomNavHomeRight);
        fades = new SequentialTransition(fade1, fade2, fade3, fade4, fade5);
    }

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
        motivationIntroHeaderLabel.setVisible(false);
        firstTextLabel.setVisible(false);
        secondTextLabel.setVisible(false);
        thirdTextLabel.setVisible(false);
        bottomNavHomeRight.setVisible(false);
    }

    @Override
    public void showAllElements() {
        motivationIntroHeaderLabel.setVisible(true);
        firstTextLabel.setVisible(true);
        secondTextLabel.setVisible(true);
        thirdTextLabel.setVisible(true);
        bottomNavHomeRight.setVisible(true);
    }
}
