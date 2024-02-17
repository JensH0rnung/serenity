package business_logic.services;

import javafx.beans.property.*;
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
public class Player {

    private MediaPlayer mediaPlayer;
    private SimpleObjectProperty<Media> actMedia;
    private SimpleIntegerProperty currentTime;
    private SimpleBooleanProperty onRepeat;
    private SimpleBooleanProperty muted;
    private SimpleBooleanProperty playState;
    private SimpleFloatProperty currentVolume;

    private Thread countTimeThread;

    public Player() {
        actMedia = new SimpleObjectProperty<>();
        currentTime = new SimpleIntegerProperty();
        onRepeat = new SimpleBooleanProperty();
        muted = new SimpleBooleanProperty();
        playState = new SimpleBooleanProperty();
        currentVolume = new SimpleFloatProperty(0.5F);  // Standardlautstärke
    }

    /**
     * Erstellt neuen VideoPlayer und spielt Video ab
     *
     * @return - neuen MediaPlayer mit Video, das gespielt werden soll
     */
    public MediaPlayer loadNewVideoPlayer(Media media) {
        actMedia.set(media);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer(getActMedia());
        }

        return mediaPlayer;
    }

    // Wiedergabe starten oder fortsetzen
    public void play() {

        // neue Datei -> neuer Player
        if(mediaPlayer != null  && !mediaPlayer.getMedia().equals(getActMedia())) {
            mediaPlayer = new javafx.scene.media.MediaPlayer(getActMedia());
            mediaPlayer.setVolume(getCurrentVolume());
            mediaPlayer.play();
        }

        // Wiedergabe fortsetzen
        if(mediaPlayer != null) {
            mediaPlayer.play();
            countTime();
        }

        // starte erste Wiedergabe
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer(getActMedia());
            mediaPlayer.setVolume(currentVolume.get());
            mediaPlayer.play();
            countTime();
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            countTimeThread.interrupt();
        }
    }

    /**
     * setzt den MediaPlayer und UI-Elemente zurück
     */
    public void reset() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
            onRepeat.set(false);
            muted.set(false);
            playState.set(false);
            currentTimeProperty().setValue(0);
        }
    }

    /**
     * Zählt die Zeit während der Wiedergabe pro Sekunde um 1 hoch
     */
    public void countTime() {
        countTimeThread = new Thread(() -> {

            // erstes hochzählen nach einer Sekunde, muss verspätet gestartet werden
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            // zählt pro Sekunde um 1 hoch
            while (isPlaying() && !Thread.currentThread().isInterrupted()) {
                currentTime.set(currentTime.get() + 1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // wenn interrupt nicht funktioniert, da sleep, einf nochmal interrupten
                    Thread.currentThread().interrupt();
                }
            }

        });
        countTimeThread.setDaemon(true);
        countTimeThread.start();
    }

    /**
     * Ändert den Repeat-Status
     */
    public void toggleRepeat() {
        if (mediaPlayer != null) {
            if (!onRepeat.get()) {
                onRepeat.set(true);
                mediaPlayer.setOnEndOfMedia(
                        () -> mediaPlayer.seek(Duration.seconds(0))
                );
            } else {
                onRepeat.set(false);
                mediaPlayer.setOnEndOfMedia(
                        () -> mediaPlayer.stop()
                );
            }
        }
    }

    /**
     * Ändert den Mute-Status
     */
    public void toggleMute() {
        if (mediaPlayer != null) {
            if (!muted.get()) {
                mediaPlayer.setMute(true);
                muted.set(true);
            } else {
                mediaPlayer.setMute(false);
                muted.set(false);
            }
        }
    }

    /**
     * Setzt Lautstärke des Players
     * @param volume - zwischen 0 & 1
     */
    public void setVolume(Float volume) {
        if(mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == javafx.scene.media.MediaPlayer.Status.PLAYING;
    }

    public Media getActMedia() {
        return actMedia.get();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public SimpleObjectProperty<Media> actMediaProperty() {
        return actMedia;
    }

    public int getCurrentTime() {
        return currentTime.get();
    }

    public SimpleIntegerProperty currentTimeProperty() {
        return currentTime;
    }

    public SimpleBooleanProperty onRepeatProperty() {
        return onRepeat;
    }

    public SimpleBooleanProperty mutedProperty() {
        return muted;
    }

    public boolean isPlayState() {
        return playState.get();
    }

    public SimpleBooleanProperty playStateProperty() {
        return playState;
    }

    public void setPlayState(boolean playState) {
        this.playState.set(playState);
    }

    public float getCurrentVolume() {
        return currentVolume.get();
    }

    public SimpleFloatProperty currentVolumeProperty() {
        return currentVolume;
    }

    public void setCurrentVolume(float currentVolume) {
        this.currentVolume.set(currentVolume);
    }
}