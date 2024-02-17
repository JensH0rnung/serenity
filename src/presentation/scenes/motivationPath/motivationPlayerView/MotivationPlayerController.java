package presentation.scenes.motivationPath.motivationPlayerView;

import application.App;
import application.View;
import business_logic.services.Player;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import presentation.scenes.motivationPath.motivationSelection.MotivationSelectionController;
import presentation.ui_components.BottomNavLeftHomeRight;

import java.io.IOException;

public class MotivationPlayerController {

    private BorderPane root;
    private App app;

    private Player player;
    private MotivationSelectionController selectionController;

    @FXML
    Label headerLabel;

    @FXML
    MediaView mediaView;

    @FXML
    BottomNavLeftHomeRight bottomNavLeftHomeRight;
    @FXML
    Button leftArrowButton, homeButton, rightArrowButton;

    @FXML
    Button startVideoButton;

    public MotivationPlayerController(App app, Player player, MotivationSelectionController selectionController) {
        this.app = app;
        this.player = player;
        this.selectionController = selectionController;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MotivationPlayerView.fxml"));
        loader.setController(this);

        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der .fxml-Datei");
            throw new RuntimeException(e);
        }
    }

    public void initialize() {

        player.actMediaProperty().addListener(
                (observableValue, oldMedia, newMedia) -> {
                    mediaView.setMediaPlayer(player.loadNewVideoPlayer(newMedia));
                }
        );

        headerLabel.textProperty().bind(selectionController.videoNameLabelProperty());

        startVideoButton.setOnAction(
                actionEvent -> {
                    player.play();
                    startVideoButton.setDisable(true);
                    startVideoButton.setVisible(false);
                }
        );

        // Nav
        leftArrowButton = bottomNavLeftHomeRight.getLeftArrowButton();
        homeButton = bottomNavLeftHomeRight.getHomeButton();
        rightArrowButton = bottomNavLeftHomeRight.getRightArrowButton();

        leftArrowButton.setOnAction(
//                actionevent -> app.leftSlideTo(View.MEDITATION_SELECTION)
                actionevent -> {
                    app.fadeTo(View.MOTIVATION_SELECTION);
                    resetPlayer();
                }
        );

        homeButton.setOnAction(
                actionevent -> {
                    app.fadeTo(View.CHOOSE_PATH);
                    resetPlayer();
                }
        );

        rightArrowButton.setOnAction(
                actionEvent -> {
                    app.rightSlideTo(View.MOTIVATION_END);
                    resetPlayer();
                }
        );
    }

    private void resetPlayer() {
        PauseTransition resetAfterDelay = new PauseTransition(Duration.seconds(0.5));
        resetAfterDelay.setOnFinished(
                event -> {
                    player.reset();
                    startVideoButton.setDisable(false);
                    startVideoButton.setVisible(true);
                }
        );
        resetAfterDelay.play();
    }

    public Pane getRoot() {
        return root;
    }
}
