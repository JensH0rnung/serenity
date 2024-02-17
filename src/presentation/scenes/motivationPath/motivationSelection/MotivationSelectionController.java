package presentation.scenes.motivationPath.motivationSelection;

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

public class MotivationSelectionController implements AnimatedViews {

    private BorderPane root;
    private App app;

    private Player player;
    private FileManager fileManager;
    private Media mediaToPlay;
    private SimpleStringProperty videoNameLabel;

    private FadeTransition fade1;
    private FadeTransition fade2;
    private FadeTransition fade3;
    private SequentialTransition fades;

    Image img1, img2, img3, img4;

    @FXML
    Label headerLabel;
    @FXML
    ScrollPane gridScrollPane;

    @FXML
    ImageView imgView1, imgView2, imgView3, imgView4;

    @FXML
    BottomNavLeftHome bottomNavLeftHome;
    Button homeButton;
    Button leftArrowButton;

    public MotivationSelectionController(App app, Player player, FileManager fileManager) {
        this.app = app;
        this.player = player;
        this.fileManager = fileManager;
        videoNameLabel = new SimpleStringProperty("Label");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MotivationSelection.fxml"));
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
                actionevent -> app.fadeTo(View.CHOOSE_PATH)
        );

        leftArrowButton.setOnAction(
                actionEvent -> app.leftSlideTo(View.MOTIVATION_INTRO)
        );

        fade1 = createFadeTransition(headerLabel);
        fade2 = createFadeTransition(gridScrollPane);
        fade3 = createFadeTransition(bottomNavLeftHome);
        fades = new SequentialTransition(fade1, fade2, fade3);

        // Bilder für die Tiles setzen
        img1 = new Image("assets/videoSelectionImgs/hampelmänner_thumbnail.jpeg");
        img2 = new Image("assets/videoSelectionImgs/lächeln_thumbnail.jpeg");
        img3 = new Image("assets/videoSelectionImgs/spazieren_thumbnail.jpeg");
        img4 = new Image("assets/videoSelectionImgs/ziele_thumbnail.jpeg");
        imgView1.setImage(img1);
        imgView2.setImage(img2);
        imgView3.setImage(img3);
        imgView4.setImage(img4);

        // EventHandler um Media für Player festzulegen
        imgView1.setOnMouseClicked(
                actionEvent -> {
                    // setzt Player zurück, wenn man vom SelectionView kommt
                    player.reset();
                    String videoName = "Hampelmänner.mp4";

                    mediaToPlay = fileManager.getVideo(videoName);
                    app.fadeTo(View.MOTIVATION_PLAYER);
                    player.actMediaProperty().set(mediaToPlay);

                    // schneidet .mp4 am Ende weg
                    String slicedVideoName = videoName.substring(0, videoName.length() - 4);
                    videoNameLabelProperty().set(slicedVideoName);
                }
        );

        // übersichtlicher mit Methodenaufruf
        setVideo(imgView2, "Lächeln.mp4");
        setVideo(imgView3, "Spazieren gehen.mp4");
        setVideo(imgView4, "Ziele setzen.mp4");
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
     * @param videoName - Video, das je nach Bild gesetzt werden soll
     */
    public void setVideo(ImageView imgView, String videoName) {
        imgView.setOnMouseClicked(
                actionEvent -> {
                    // setzt Player zurück, wenn man vom SelectionView kommt
                    player.reset();

                    mediaToPlay = fileManager.getVideo(videoName);
                    app.fadeTo(View.MOTIVATION_PLAYER);
                    player.actMediaProperty().set(mediaToPlay);

                    // schneidet .mp4 am Ende weg
                    String slicedVideoName = videoName.substring(0, videoName.length() - 4);
                    videoNameLabelProperty().set(slicedVideoName);
                }
        );
    }

    public SimpleStringProperty videoNameLabelProperty() {
        return videoNameLabel;
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
