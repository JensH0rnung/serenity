package presentation.scenes.meditationPath.meditationPlayerView;

import application.App;
import business_logic.services.SoundPlayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

public class MeditationPlayerViewController {

    private StackPane root;
    private App app;
    private SoundPlayer player;

    public MeditationPlayerViewController(App app, SoundPlayer player) {
        this.app = app;
        this.player = player;

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

        // 3 NavButtons

        player.actSoundProperty().addListener(
                ((observableValue, oldSound, newSound) -> {

                })
        );
    }


    public Pane getRoot() {
        return root;
    }

}
