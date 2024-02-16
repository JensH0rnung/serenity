package presentation.scenes.meditationPath.meditationSelectionVIew;

import application.AnimatedViews;
import application.App;
import application.View;
import business_logic.services.FileManager;
import business_logic.services.Player;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.util.Duration;
import presentation.ui_components.BottomNavLeftHome;

import java.io.IOException;

public class MeditationSelectionController implements AnimatedViews {

    private BorderPane root;
    private App app;

    private Player player;
    private FileManager fileManager;
    private Media mediaToPlay;
    private SimpleStringProperty soundNameLabel;
    private SimpleObjectProperty<Image> selectedImage;

    private FadeTransition fade1;
    private FadeTransition fade2;
    private FadeTransition fade3;
    private SequentialTransition fades;

    Image img1, img2, img3, img4, img5, img6;

    @FXML
    Label headerLabel;
    @FXML
    ScrollPane gridScrollPane;

    @FXML
    ImageView imgView1, imgView2, imgView3, imgView4, imgView5, imgView6;

    @FXML
    BottomNavLeftHome bottomNavLeftHome;
    Button homeButton;
    Button leftArrowButton;

    public MeditationSelectionController(App app, Player player, FileManager fileManager) {
        this.app = app;
        this.player = player;
        this.fileManager = fileManager;
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
                actionEvent -> app.leftSlideTo(View.MEDITATION_INTRO)
        );

        fade1 = createFadeTransition(headerLabel);
        fade2 = createFadeTransition(gridScrollPane);
        fade3 = createFadeTransition(bottomNavLeftHome);
        fades = new SequentialTransition(fade1, fade2, fade3);

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

        // EventHandler um Media für Player festzulegen
        imgView1.setOnMouseClicked(
                actionEvent -> {
                    // setzt Player zurück, wenn man vom SelectionView kommt
                    player.reset();
                    String soundName = "Meeresrauschen.mp3";

                    mediaToPlay = fileManager.getSound(soundName);
                    app.fadeTo(View.MEDITATION_PLAYER);
                    player.actMediaProperty().set(mediaToPlay);

                    // schneidet .mp3 am Ende weg
                    String slicedSoundName = soundName.substring(0, soundName.length() - 4);
                    soundNameLabelProperty().set(slicedSoundName);

                    // speichert geklicktes Bild in Property
                    selectedImage.set(img1);
                }
        );

        // übersichtlicher mit Methodenaufruf
        setSound(imgView2, "Waldklänge.mp3");
        setSound(imgView3, "Piano.mp3");
        setSound(imgView4, "Regen.mp3");
        setSound(imgView5, "Klangschale.mp3");
        setSound(imgView6, "Delta-Wellen.mp3");
    }

    private FadeTransition createFadeTransition(Node node) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(node);
        fadeTransition.setDuration(Duration.seconds(0.5));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        return fadeTransition;
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
                    player.reset();

                    mediaToPlay = fileManager.getSound(soundName);
                    app.fadeTo(View.MEDITATION_PLAYER);
                    player.actMediaProperty().set(mediaToPlay);

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

    @Override
    public void startAnimations() {
        fades.play();
        showAllElements();
    }

    @Override
    public void hideAllElements() {
        headerLabel.setVisible(false);
        gridScrollPane.setVisible(false);
        bottomNavLeftHome.setVisible(false);
    }

    @Override
    public void showAllElements() {
        headerLabel.setVisible(true);
        gridScrollPane.setVisible(true);
        bottomNavLeftHome.setVisible(true);
    }
}
