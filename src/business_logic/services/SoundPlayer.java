package business_logic.services;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Steuert die Wiedergabe der MeditationsSounds
 * Funktionalitäten:
 *  - Play
 *  - Pause
 *  - Repeat
 *  - Sleep-Timer
 */
public class SoundPlayer {

    private MediaPlayer mediaPlayer;
    private SimpleObjectProperty<Media> actSound;
    private boolean onRepeat;

    // Default-Werte
    public SoundPlayer() {
        actSound = new SimpleObjectProperty<>();
        onRepeat = false;
    }

    // Wiedergabe starten oder fortsetzen
    public void play() {
        if (mediaPlayer == null && getActSound() != null) {
            mediaPlayer = new MediaPlayer(getActSound());
        }

        if(mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    // setzt den MediaPlayer zurück
    public void reset() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(javafx.util.Duration.ZERO);
        }
    }

    // Müsste soweit funktionieren, testen mit kurzem Sound
    public void toggleRepeat() {
        if (mediaPlayer != null) {
            if (!onRepeat) {
                onRepeat = true;
                mediaPlayer.setOnEndOfMedia(
                        () -> mediaPlayer.seek(Duration.seconds(0))
                );
                // Test
                System.out.println("Repeat on");
            } else {
                onRepeat = false;
                mediaPlayer.setOnEndOfMedia(
                        () -> mediaPlayer.stop()
                );
                // Test
                System.out.println("Repeat off");
            }
        }
    }

    // Gibt an, ob Player gerade spielt
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public Media getActSound() {
        return actSound.get();
    }

    public SimpleObjectProperty<Media> actSoundProperty() {
        return actSound;
    }

    public void setActSound(Media actSound) {
        this.actSound.set(actSound);
    }
}