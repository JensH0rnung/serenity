package business_logic.data;

/**
 * Listet die Atemrhythmen mit jeweiligen Zeitangaben und StyleKlasse auf
 */
public enum BreathingRhythm {

    FIRST(3, 6, "firstRhythmColor"),
    SECOND(5, 10, "secondRhythmColor"),
    THIRD(8, 16, "thirdRhythmColor");

    private int breathInDuration;
    private int breathOutDuration;
    private String color;   // entspricht CSS-Klasse

    BreathingRhythm(int breathInDuration, int breathOutDuration, String color) {
        this.breathInDuration = breathInDuration;
        this.breathOutDuration = breathOutDuration;
        this.color = color;
    }

    public int getBreathInDuration() {
        return breathInDuration;
    }

    public int getBreathOutDuration() {
        return breathOutDuration;
    }

    public String getColor() { return color; }
}
