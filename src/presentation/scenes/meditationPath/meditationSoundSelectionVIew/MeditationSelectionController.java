package presentation.scenes.meditationPath.meditationSoundSelectionVIew;

import application.App;
import application.View;
import business_logic.data.SoundManager;
import business_logic.services.SoundPlayer;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import presentation.ui_components.BottomNavLeftHome;

import java.io.IOException;

public class MeditationSelectionController {

    private StackPane root;
    private App app;

    private SoundPlayer soundPlayer;
    private SoundManager soundManager;
    private Media mediaToPlay;
    // hier sinnvoll oder woanders hin?
    private SimpleStringProperty soundNameLabel;

    @FXML
    ImageView img1, img2, img3, img4, img5, img6;

    @FXML
    BottomNavLeftHome bottomNavLeftHome;
    Button homeButton;
    Button leftArrowButton;

    public MeditationSelectionController(App app, SoundPlayer soundPlayer, SoundManager soundManager) {
        this.app = app;
        this.soundPlayer = soundPlayer;
        this.soundManager = soundManager;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MeditationSelection.fxml"));
        loader.setController(this);

        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der .fxml-Datei");
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        homeButton = bottomNavLeftHome.getHomeButton();
        leftArrowButton = bottomNavLeftHome.getLeftArrowButton();

        homeButton.setOnAction(
                actionevent -> app.switchView(View.INTRO)
        );

        leftArrowButton.setOnAction(
                actionEvent -> app.switchView(View.STRESS_INTRO)
        );

        img1.setOnMouseClicked(
                actionEvent -> {
                    mediaToPlay = soundManager.getSound("Meeresrauschen.mp3");
                    app.switchView(View.MEDITATION_PLAYER);
                    soundPlayer.actSoundProperty().set(mediaToPlay);
                }
        );

        setSound(img2, "Waldklänge.mp3");
        setSound(img3, "ruhiges Piano.mp3");  // geht das mit Space? Ansonsten im View umbenennen
        setSound(img4, "Regen.mp3");
        setSound(img5, "Klangschale.mp3");
        setSound(img6, "Delta-Wellen.mp3");
    }

    /**
     * Hilfsmethode um nicht 6x den gleichen EventHandler zu implementieren
     *
     * @param img - Image, das gedrückt wurde
     * @param soundName - Sound, der je nach Bild gesetzt werden soll
     */
    public void setSound(ImageView img, String soundName) {
        img.setOnMouseClicked(
                actionEvent -> {
                    mediaToPlay = soundManager.getSound(soundName);
                    app.switchView(View.MEDITATION_PLAYER);
                    soundPlayer.actSoundProperty().set(mediaToPlay);
                }
        );
    }

    public Pane getRoot() {
        return root;
    }
}
