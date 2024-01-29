package presentation.scenes.meditationPath.meditationSoundSelectionVIew;

import application.App;
import application.View;
import business_logic.services.SoundPlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import presentation.ui_components.BottomNavLeftHome;

import java.io.IOException;

public class MeditationSelectionController {

    private StackPane root;
    private App app;

    private SoundPlayer soundPlayer;

    @FXML
    ImageView img1;

    @FXML
    BottomNavLeftHome bottomNavLeftHome;
    Button homeButton;
    Button leftArrowButton;

    public MeditationSelectionController(App app) {
        this.app = app;

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
                    System.out.println("Klick img1");
                    soundPlayer.loadSound("");
                    /*
                     Property für Player anpassen
                        Bild & Sound
                     */
                }
        );
    }

    public Pane getRoot() {
        return root;
    }
}
