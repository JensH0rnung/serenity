package presentation.scenes.meditationPath.meditationPlayerView;

import application.App;
import application.View;
import business_logic.services.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import presentation.scenes.meditationPath.meditationSelectionVIew.MeditationSelectionController;
import presentation.ui_components.BottomNavLeftHomeRight;

import java.io.IOException;

public class MeditationPlayerController {

    private BorderPane root;
    private App app;
    private Player player;
    private MeditationSelectionController selectionController;

    @FXML
    Label soundHeaderLabel, playTimeLabel;

    @FXML
    ImageView soundCover;

    @FXML
    BottomNavLeftHomeRight bottomNavLeftHomeRight;
    @FXML
    Button leftArrowButton, homeButton, rightArrowButton;

    @FXML
    Button playButton, repeatButton, muteButton;
    @FXML
    MenuButton sleepTimer;

    @FXML
    Slider volumeSlider;

    public MeditationPlayerController(App app, Player player, MeditationSelectionController selectionController) {
        this.app = app;
        this.player = player;
        this.selectionController = selectionController;

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

        // Setzen der Icons
        ImageView sleepIcon = new ImageView(new Image("assets/icons/sleepIcon30.png"));
        ImageView sleepActive = new ImageView(new Image("assets/icons/sleepActive30.png"));
        ImageView playIcon = new ImageView(new Image("assets/icons/playIcon60.png"));
        ImageView pauseIcon = new ImageView(new Image("assets/icons/pauseIcon60.png"));
        ImageView repeatIcon = new ImageView(new Image("assets/icons/repeatIcon30.png"));
        ImageView repeatActive = new ImageView(new Image("assets/icons/repeatActive30.png"));
        ImageView muteIcon = new ImageView(new Image("assets/icons/muteIcon25.png"));
        ImageView muteActive = new ImageView(new Image("assets/icons/muteActive25.png"));

        sleepTimer.setGraphic(sleepIcon);
        playButton.setGraphic(playIcon);
        repeatButton.setGraphic(repeatIcon);
        muteButton.setGraphic(muteIcon);

        sleepTimer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        playButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        muteButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        repeatButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        // UI-Elemente
        soundHeaderLabel.textProperty().bind(selectionController.soundNameLabelProperty());
        soundCover.imageProperty().bind(selectionController.selectedImageProperty());

        player.currentTimeProperty().addListener(
                (currentTimeObservable, oldTime, newTime) -> {
                    int currentTime = newTime.intValue();;

                    Platform.runLater(
                            () -> playTimeLabel.setText(formatTime(currentTime))
                    );
                }
        );

        // PlayerControls
        playButton.setOnAction(
                actionEvent -> {
                    if(player.isPlaying()) {
                        player.pause();
                        playButton.setGraphic(playIcon);
                    }
                    else {
                        player.play();
                        playButton.setGraphic(pauseIcon);
                    }
                }
        );

        repeatButton.setOnAction(
                actionEvent -> player.toggleRepeat()
        );

        muteButton.setOnAction(
                actionEvent -> player.toggleMute()
        );

        volumeSlider.setValue(player.getCurrentVolume());
        volumeSlider.valueProperty().bindBidirectional(player.currentVolumeProperty());

        volumeSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    float volume;
                    volume = newValue.floatValue();
                    player.setVolume(volume);
                }
        );

        // Aktualisieren der Icons
        player.onRepeatProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if(newVal) {
                        repeatButton.setGraphic(repeatActive);
                    } else {
                        repeatButton.setGraphic(repeatIcon);
                    }
                }
        );

        player.mutedProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if(newVal) {
                        muteButton.setGraphic(muteActive);
                    } else {
                        muteButton.setGraphic(muteIcon);
                    }
                }
        );

        player.playStateProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if(newVal) {
                        playButton.setGraphic(pauseIcon);
                    }
                    else {
                        playButton.setGraphic(playIcon);
                    }
                }
        );

        // Zurücksetzen des PlayIcons
        player.actMediaProperty().addListener(
                (observableValue, oldSound, newSound) -> playButton.setGraphic(playIcon)
        );

        // Nav
        leftArrowButton = bottomNavLeftHomeRight.getLeftArrowButton();
        homeButton = bottomNavLeftHomeRight.getHomeButton();
        rightArrowButton = bottomNavLeftHomeRight.getRightArrowButton();

        leftArrowButton.setOnAction(
//                actionevent -> app.leftSlideTo(View.MEDITATION_SELECTION)
                actionevent -> {
                    app.fadeTo(View.MEDITATION_SELECTION);
                    player.reset();
                }
        );

        homeButton.setOnAction(
                actionevent -> {
                    app.fadeTo(View.INTRO);
                    player.reset();
                }
        );

        rightArrowButton.setOnAction(
                actionEvent -> app.rightSlideTo(View.MEDITATION_END)
        );
    }

    /**
     * Formatiert die Zeitangaben
     *
     * @param seconds - Zeit in Sekunden
     * @return - formatierte Zeitangabe
     */
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public Pane getRoot() {
        return root;
    }
}
