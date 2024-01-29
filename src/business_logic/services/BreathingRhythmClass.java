package business_logic.services;

import business_logic.data.BreathingRhythm;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Sinnvolle Klasse oder Implementierung lieber woanders?
 */
public class BreathingRhythmClass {

    private SimpleObjectProperty<BreathingRhythm> breathingRhythm = new SimpleObjectProperty<>(); // Abhängig hiervon werden Zeiten der Animation gesetzt

    public BreathingRhythm getBreathingRhythm() {
        return breathingRhythm.get();
    }

    public SimpleObjectProperty<BreathingRhythm> breathingRhythmProperty() {
        return breathingRhythm;
    }

    public void setBreathingRhythm(BreathingRhythm breathingRhythm) {
        this.breathingRhythm.set(breathingRhythm);
    }
}
