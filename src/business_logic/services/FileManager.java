package business_logic.services;

import javafx.scene.media.Media;

import java.io.File;
import java.util.HashMap;

/**
 * Lädt alle Dateien aus übergebenem Verzeichnis und speichert diese, je nach Datentyp, in einer HashMap
 */
public class FileManager {
    private static final String soundDir = "src/assets/meditationSounds";
    private static final String videoDir = "src/assets/videos";
    private HashMap<String, Media> sounds;
    private HashMap<String, Media> videos;

    public FileManager() {
        sounds = new HashMap<>();
        videos = new HashMap<>();

        loadFiles(soundDir);
        loadFiles(videoDir);
    }

    /**
     * Lädt alle Sounds aus oben stehendem Directory und fügt diese einer Liste hinzu
     *
     * @param directory - Sound-Ordner
     */
    private void loadFiles(String directory) {
        File folder = new File(directory);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".mp3")) {
                    sounds.put(file.getName(), new Media(file.toURI().toString()));
                } else if (file.isFile() && file.getName().endsWith(".mp4")) {
                    videos.put(file.getName(), new Media(file.toURI().toString()));
                }
            }
        }
    }

    public Media getSound(String soundName) {
        return sounds.get(soundName);
    }

    public Media getVideo(String videoName) {
        return videos.get(videoName);
    }

}