package presentation.scenes.meditationPath.meditationSoundSelectionVIew;

import application.App;
import application.View;
import business_logic.services.SoundManager;
import business_logic.services.SoundPlayer;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import presentation.ui_components.BottomNavLeftHome;

import java.io.IOException;

public class MeditationSelectionController {

    private BorderPane root;
    private App app;

    private SoundPlayer soundPlayer;
    private SoundManager soundManager;
    private Media mediaToPlay;
    private SimpleStringProperty soundNameLabel;
    private SimpleObjectProperty<Image> selectedImage;
    // ggf. anpassen, da MediaPlayer evtl. andere Wert für Volume verwendet
    private FloatProperty currentVolume = new SimpleFloatProperty(0.5F);  // Standardlautstärke

    Image img1, img2, img3, img4, img5, img6;

    @FXML
    ImageView imgView1, imgView2, imgView3, imgView4, imgView5, imgView6;

    @FXML
    BottomNavLeftHome bottomNavLeftHome;
    Button homeButton;
    Button leftArrowButton;

    public MeditationSelectionController(App app, SoundPlayer soundPlayer, SoundManager soundManager) {
        this.app = app;
        this.soundPlayer = soundPlayer;
        this.soundManager = soundManager;
        soundNameLabel = new SimpleStringProperty("Label");
        selectedImage = new SimpleObjectProperty<>();

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
                actionevent -> app.fadeTo(View.INTRO)
        );

        leftArrowButton.setOnAction(
                actionEvent -> app.leftSlideTo(View.STRESS_INTRO)
        );

        // Bilder für die Tiles setzen
        img1 = new Image("assets/soundSelectionImgs/waves.png");
        img2 = new Image("assets/soundSelectionImgs/forest.png");
        img3 = new Image("assets/soundSelectionImgs/piano.png");
        img4 = new Image("assets/soundSelectionImgs/raindrops.png");
        img5 = new Image("assets/soundSelectionImgs/singing_bowl.png");
        img6 = new Image("assets/soundSelectionImgs/frequency.png");
        imgView1.setImage(img1);
        imgView2.setImage(img2);
        imgView3.setImage(img3);
        imgView4.setImage(img4);
        imgView5.setImage(img5);
        imgView6.setImage(img6);

        imgView1.setOnMouseClicked(
                actionEvent -> {
                    // setzt Player zurück, wenn man vom SelectionView kommt
                    soundPlayer.reset();
                    String soundName = "Meeresrauschen.mp3";

                    mediaToPlay = soundManager.getSound(soundName);
                    app.fadeTo(View.MEDITATION_PLAYER);
                    soundPlayer.actSoundProperty().set(mediaToPlay);

                    // schneidet .mp3 am Ende weg
                    String slicedSoundName = soundName.substring(0, soundName.length() - 4);
                    soundNameLabelProperty().set(slicedSoundName);

                    // speichert geklicktes Bild in Property
                    selectedImage.set(img1);
                }
        );

        setSound(imgView2, "Waldklänge.mp3");
        setSound(imgView3, "Piano.mp3");
        setSound(imgView4, "Regen.mp3");
        setSound(imgView5, "Klangschale.mp3");
        setSound(imgView6, "Delta-Wellen.mp3");
    }

    /**
     * Hilfsmethode um nicht 6x den gleichen EventHandler zu implementieren
     *
     * @param imgView - Image, das gedrückt wurde
     * @param soundName - Sound, der je nach Bild gesetzt werden soll
     */
    public void setSound(ImageView imgView, String soundName) {
        imgView.setOnMouseClicked(
                actionEvent -> {
                    // setzt Player zurück, wenn man vom SelectionView kommt
                    soundPlayer.reset();

                    mediaToPlay = soundManager.getSound(soundName);
                    app.fadeTo(View.MEDITATION_PLAYER);
                    soundPlayer.actSoundProperty().set(mediaToPlay);

                    // schneidet .mp3 am Ende weg
                    String slicedSoundName = soundName.substring(0, soundName.length() - 4);
                    soundNameLabelProperty().set(slicedSoundName);

                    // speichert geklicktes Bild in Property
                    selectedImage.set(imgView.getImage());
                }
        );
    }

    public SimpleStringProperty soundNameLabelProperty() {
        return soundNameLabel;
    }

    public SimpleObjectProperty<Image> selectedImageProperty() {
        return selectedImage;
    }

    public Pane getRoot() {
        return root;
    }
}
