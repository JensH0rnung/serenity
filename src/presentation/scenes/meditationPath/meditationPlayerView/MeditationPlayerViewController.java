package presentation.scenes.meditationPath.meditationPlayerView;

import application.App;
import application.View;
import business_logic.data.SoundManager;
import business_logic.services.SoundPlayer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import presentation.scenes.meditationPath.meditationSoundSelectionVIew.MeditationSelectionController;
import presentation.ui_components.BottomNavLeftHomeRight;

import java.io.IOException;

public class MeditationPlayerViewController {

    private StackPane root;
    private App app;
    private SoundPlayer player;
    private SoundManager soundManager;

    @FXML
    Label soundHeaderLabel, playTimeLabel;

    @FXML
    BottomNavLeftHomeRight bottomNavLeftHomeRight;
    @FXML
    Button leftArrowButton, homeButton, rightArrowButton;

    @FXML
    Button sleepTimer, playButton, repeatButton;

    public MeditationPlayerViewController(App app, SoundPlayer player, SoundManager soundManager) {
        this.app = app;
        this.player = player;
        this.soundManager = soundManager;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MeditationPlayerView.fxml"));
        loader.setController(this);

        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der .fxml-Datei");
            throw new RuntimeException(e);
        }
    }

    public void initialize() {

        player.actSoundProperty().addListener(
                ((observableValue, oldSound, newSound) -> {
                    System.out.println("Listener actSoundProperty");
                })
        );

        /*
         noch überarbeiten
         Wiedergabe starten muss keinen Sound setzen, Sound wird bei Auswahl des Sounds gesetzt
         Daher hier nur play & pause
         */
        playButton.setOnAction(
                actionEvent -> {
                    System.out.println("Play");
                    if(player.isPlaying()) {
                        System.out.println("if - pause");
                        player.pause();
                    }
                    else if (player != null && !player.isPlaying()) {
                        System.out.println("else if - wiedergabe starten");
                        System.out.println("actSound von Player - " + player.getActSound());

                        player.play();
                    }
                    else {
                        System.out.println("else - wiedergabe fortsetzen");
                    }
                }
        );

        repeatButton.setOnAction(
                actionEvent -> player.toggleRepeat()
        );

        leftArrowButton = bottomNavLeftHomeRight.getLeftArrowButton();
        homeButton = bottomNavLeftHomeRight.getHomeButton();
        rightArrowButton = bottomNavLeftHomeRight.getRightArrowButton();

        leftArrowButton.setOnAction(
                actionevent -> app.switchView(View.MEDITATION_SELECTION)
        );

        homeButton.setOnAction(
                actionevent -> app.switchView(View.INTRO)
        );

        rightArrowButton.setOnAction(
                actionEvent -> app.switchView(View.MEDITATION_END)
        );
    }

    public void updateSoundLabel(String soundName) {
        Media sound = soundManager.getSound(soundName);
        if (sound != null) {
            String labelText = soundName.substring(0, soundName.length() - 4);
            Platform.runLater(
                    () -> soundHeaderLabel.setText(labelText)
            );
        }
    }



    public Pane getRoot() {
        return root;
    }

}
