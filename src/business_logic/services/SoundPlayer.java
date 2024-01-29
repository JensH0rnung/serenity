package business_logic.services;

import business_logic.data.MeditationSound;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * simpler MP3Player, der versch. Sounds zum Meditieren abspielen soll
 * Verwendet den Minim-Player um Dateien abzuspielen
 */
public class SoundPlayer {

    private MediaPlayer player;
    private MeditationSound meditationSound;
    private static final String soundDir = "./assets/meditationSounds";
    private SimpleObjectProperty actSound;

    public SoundPlayer() {

        actSound = new SimpleObjectProperty<>();
        // lädt alle Sounds aus Directory?
        meditationSound = new MeditationSound(soundDir);

        actSound.addListener(
                ((observableValue, oldMedia, newMedia) -> {
                    player = new MediaPlayer((Media) newMedia);
                })
        );
    }

    public void play() {
        player.play();
    }

    public void pause() {
        player.pause();
    }

    public void loadSound(String soundName) {
        try {

            Media sound = meditationSound.getSound(soundName);
            actSound.set(sound);

        } catch(NullPointerException e) {
            System.out.println("Ungültiger SoundName");
        }
    }

    // vermutlich unnötig
    public Object getActSound() {
        return actSound.get();
    }

    // Aufruf aus PlayerView
    public SimpleObjectProperty actSoundProperty() {
        return actSound;
    }

    // Aufruf aus SelectionView
    public void setActSound(Object actSound) {
        this.actSound.set(actSound);
    }
}
