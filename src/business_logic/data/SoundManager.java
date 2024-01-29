package business_logic.data;

import javafx.scene.media.Media;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Lädt alle Sounds aus übergebenem Verzeichnis und speichert diese in einer HashMap
 */
public class SoundManager {
    private static final String soundDir = "src/assets/meditationSounds";
    private Map<String, Media> sounds;

    public SoundManager() {
        sounds = new HashMap<>();

        loadSounds(soundDir);
    }

    private void loadSounds(String directory) {
        File soundFolder = new File(directory);
        File[] files = soundFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".mp3")) {
                    sounds.put(file.getName(), new Media(file.toURI().toString()));
                }
            }
        }
    }

    public Media getSound(String soundName) {
        return sounds.get(soundName);
    }
}