package application;

/**
 * Interface, das von jedem ViewController implementiert wird
 * Funktionen:
 *  - Animationen der Views starten, wenn diese angezeigt werden
 *  - alle UI-Elemente verstecken (vor der Animation) und anzeigen (während der Animation)
 */
public interface AnimatedViews {
    void startAnimations();
    void hideAllElements();
    void showAllElements();
}
