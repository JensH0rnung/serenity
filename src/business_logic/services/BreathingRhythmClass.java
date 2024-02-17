package business_logic.services;

import business_logic.data.BreathingRhythm;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Sinnvolle Klasse oder Implementierung lieber woanders?
 */
public class BreathingRhythmClass {

    // Abhängig hiervon werden Zeiten der Animation gesetzt
    private SimpleObjectProperty<BreathingRhythm> breathingRhythm = new SimpleObjectProperty<>();

    public SimpleObjectProperty<BreathingRhythm> breathingRhythmProperty() {
        return breathingRhythm;
    }

    public void setBreathingRhythm(BreathingRhythm breathingRhythm) {
        this.breathingRhythm.set(breathingRhythm);
    }
}
