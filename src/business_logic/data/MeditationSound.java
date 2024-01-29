package business_logic.data;

import javafx.scene.media.Media;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Erstellt Sounds zum Meditieren aus mp3-Datei
 */
public class MeditationSound {

    private HashMap<String, Media> sounds;

    public MeditationSound(String soundDir) {
        File folder = new File(soundDir);
        File[] files = folder.listFiles();
        System.out.println("FolderName - " + folder);
        System.out.println("Files im Folder - " + files);

        if(files != null) {
            for (File file : files) {
                System.out.println("DateiName - " + file.getName());
                if (file.isFile() && file.getName().endsWith(".mp3")) {
                    sounds.put(file.getName(), new Media(file.toURI().toString()));
                }
            }
        }
        else {
            System.out.println("Verzeichnis enthält keine Dateien");
        }
    }

    /**
     * Getter für bestimmten Sound
     *
     * @param soundName - Name des Sounds
     * @return - Sound, als Media
     */
    public Media getSound(String soundName) {
        return sounds.get(soundName);
    }
}
