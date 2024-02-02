package application;
import business_logic.services.SoundManager;
import business_logic.services.BreathingRhythmClass;
import business_logic.services.SoundPlayer;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import presentation.scenes.introView.IntroViewController;
import presentation.scenes.meditationPath.meditationEndView.MeditationEndController;
import presentation.scenes.meditationPath.meditationIntroView.MeditationIntroController;
import presentation.scenes.meditationPath.meditationPlayerView.MeditationPlayerViewController;
import presentation.scenes.meditationPath.meditationSoundSelectionVIew.MeditationSelectionController;
import presentation.scenes.motivationPath.motivationEndView.MotivationEndController;
import presentation.scenes.motivationPath.motivationIntroView.MotivationIntroController;
import presentation.scenes.stressPath.stressBreathingView.StressBreathingController;
import presentation.scenes.stressPath.stressEndView.StressEndController;
import presentation.scenes.stressPath.stressIntroView.StressIntroController;
import presentation.scenes.stressPath.stressSelectionView.StressSelectionController;
import javafx.scene.layout.StackPane;

import java.util.HashMap;

public class App extends Application {

    private Scene scene;
    private Stage primaryStage;
    private Pane root;
    private View defaultView;
    private HashMap<View, Pane> primaryViews;

    BreathingRhythmClass breathingRhythm;
    SoundPlayer soundPlayer;
    SoundManager soundManager;

    private Pane introView;

    private Pane stressIntroView;
    private Pane stressSelectionView;
    private Pane stressBreathingView;
    private Pane stressEndView;

    private Pane meditationIntroView;
    private Pane meditationSelectSoundView;
    private Pane meditationPlayerView;
    private Pane meditationEndView;

    private Pane motivationIntroView;
    private Pane motivationSelectExerciseView;
    private Pane motivationExerciseDetailView;
    private Pane motivationEndView;

    /**
     * Initialisieren der Views
     */
    @Override
    public void init() {

        soundPlayer = new SoundPlayer();
        soundManager = new SoundManager();

        // Ermöglicht Zugriff auf DurationProperty der CircleAnimation
        breathingRhythm = new BreathingRhythmClass();

        defaultView = View.MEDITATION_PLAYER;
        System.out.println("defaultView - " + defaultView);
        primaryViews = new HashMap<>();

        /*
         Übergabe dieser Klasse an Controller, damit switchView aufgerufen werden kann
         Sinnvoll oder andere Implementierung?
         */

        IntroViewController introViewController = new IntroViewController(this);
        introView = introViewController.getRoot();
        primaryViews.put(View.INTRO, introView);

        // StressPath
        StressIntroController stressIntroController = new StressIntroController(this);
        stressIntroView = stressIntroController.getRoot();
        primaryViews.put(View.STRESS_INTRO, stressIntroView);

        StressSelectionController stressSelectionController = new StressSelectionController(this, breathingRhythm);
        stressSelectionView = stressSelectionController.getRoot();
        primaryViews.put(View.STRESS_SELECTION, stressSelectionView);

        StressBreathingController stressBreathingController = new StressBreathingController(this, breathingRhythm);
        stressBreathingView = stressBreathingController.getRoot();
        primaryViews.put(View.STRESS_BREATHING, stressBreathingView);

        StressEndController stressEndController = new StressEndController(this);
        stressEndView = stressEndController.getRoot();
        primaryViews.put(View.STRESS_END, stressEndView);

        // MeditationPath
        MeditationIntroController meditationIntroController = new MeditationIntroController(this);
        meditationIntroView = meditationIntroController.getRoot();
        primaryViews.put(View.MEDITATION_INTRO, meditationIntroView);

        MeditationSelectionController meditationSelectionController = new MeditationSelectionController(this, soundPlayer, soundManager);
        meditationSelectSoundView = meditationSelectionController.getRoot();
        primaryViews.put(View.MEDITATION_SELECTION, meditationSelectSoundView);

        MeditationPlayerViewController meditationPlayerViewController = new MeditationPlayerViewController(this, soundPlayer, soundManager, meditationSelectionController);
        meditationPlayerView = meditationPlayerViewController.getRoot();
        primaryViews.put(View.MEDITATION_PLAYER, meditationPlayerView);

        MeditationEndController meditationPathEndController = new MeditationEndController(this);
        meditationEndView = meditationPathEndController.getRoot();
        primaryViews.put(View.MEDITATION_END, meditationEndView);

        // MotivationPath
        MotivationIntroController motivationIntroController = new MotivationIntroController(this);
        motivationIntroView = motivationIntroController.getRoot();
        primaryViews.put(View.MOTIVATION_INTRO, motivationIntroView);

        // MotivationSelect

        // MotivationExercise

        MotivationEndController motivationEndController = new MotivationEndController(this);
        motivationEndView = motivationEndController.getRoot();
        primaryViews.put(View.MOTIVATION_END, motivationEndView);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        root = primaryViews.get(defaultView);

        // Displaygröße iPhone 13 / 14
        scene = new Scene(root, 390, 844);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Serenity");
        primaryStage.show();
    }

    /**
     * Startet FadeAnimation zum nächsten View
     *
     * @param nextView - View der angezeigt werden soll
     */
    public void fadeTo(View nextView) {
        Pane currentPane = (Pane) scene.getRoot();
        Pane nextPane = primaryViews.get(nextView);

        startFadeAnimation(scene, currentPane, nextPane);
    }

    /**
     * Startet RightSlideAnimation zum nächsten View
     *
     * @param nextView - View der angezeigt werden soll
     */
    public void rightSlideTo(View nextView) {
        Pane currentPane = (Pane) scene.getRoot();
        Pane nextPane = primaryViews.get(nextView);

        startRightSlideAnimation(scene, currentPane, nextPane);
    }

    /**
     * Startet LeftSlideAnimation zum nächsten View
     *
     * @param nextView - View der angezeigt werden soll
     */
    public void leftSlideTo(View nextView) {
        Pane currentPane = (Pane) scene.getRoot();
        Pane nextPane = primaryViews.get(nextView);

        startLeftSlideAnimation(scene, currentPane, nextPane);
    }

    /**
     * Definition und Ausführung der FadeTransition
     */
    public void startFadeAnimation(Scene scene, Pane currentView, Pane toView) {
        // Hilfs-StackPane erzeugen
        StackPane helpPane = new StackPane();

        // Anim-Vorbereitung
        helpPane.getChildren().addAll(currentView, toView);
        scene.setRoot(helpPane);

        // Anim-Definitionen
        FadeTransition fadeOut = new FadeTransition();
        fadeOut.setNode(currentView);
        fadeOut.setDuration(Duration.seconds(0.25));
        fadeOut.setInterpolator(Interpolator.EASE_BOTH);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setNode(toView);
        fadeIn.setDuration(Duration.seconds(0.5));
        fadeIn.setInterpolator(Interpolator.EASE_BOTH);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        SequentialTransition sequentialTransition = new SequentialTransition(fadeOut, fadeIn);
        sequentialTransition.setOnFinished(
                actionEvent -> {
                    helpPane.getChildren().removeAll(currentView, toView);
                    scene.setRoot(toView);
                }
        );

        sequentialTransition.play();
    }

    /**
     * LeftSlideAnimation, die vom navLeftArrow aufgerufen wird
     *
     * @param scene
     * @param currentView - aktuell gezeigter View
     * @param toView - View, der angezeigt werden soll
     */
    public void startLeftSlideAnimation(Scene scene, Pane currentView, Pane toView) {
        // Hilfs-StackPane erzeugen
        StackPane helpPane = new StackPane();

        // Anim-Vorbereitung
        helpPane.getChildren().addAll(currentView, toView);
        toView.setTranslateX(-scene.getWidth());
        scene.setRoot(helpPane);

        // Anim-Definitionen
        TranslateTransition slideIn = new TranslateTransition();
        slideIn.setNode(toView);
        slideIn.setDuration(Duration.seconds(0.5));
        slideIn.setInterpolator(Interpolator.EASE_OUT);
        slideIn.setToX(0);

        TranslateTransition slideOut = new TranslateTransition();
        slideOut.setNode(currentView);
        slideOut.setDuration(Duration.seconds(0.5));
        slideOut.setInterpolator(Interpolator.EASE_OUT);
        slideOut.setToX(scene.getWidth());

        // SlideIn & SlideOut soll gleichzeitig passieren
        ParallelTransition parallelTransition = new ParallelTransition(slideIn, slideOut);
        parallelTransition.setOnFinished(e -> {
            helpPane.getChildren().removeAll(currentView, toView);
            scene.setRoot(toView);

            currentView.setTranslateX(0);
        });

        parallelTransition.play();
    }

    /**
     * RightSlideAnimation, die vom navRightArrow aufgerufen wird
     *
     * @param scene
     * @param currentView - aktuell gezeigter View
     * @param toView - View, der angezeigt werden soll
     */
    public void startRightSlideAnimation(Scene scene, Pane currentView, Pane toView) {
        // Hilfs-StackPane erzeugen
        StackPane helpPane = new StackPane();

        // Anim-Vorbereitung
        helpPane.getChildren().addAll(currentView, toView);
        toView.setTranslateX(scene.getWidth());
        scene.setRoot(helpPane);

        // Anim-Definitionen
        TranslateTransition slideIn = new TranslateTransition();
        slideIn.setNode(toView);
        slideIn.setDuration(Duration.seconds(0.5));
        slideIn.setInterpolator(Interpolator.EASE_OUT);
        slideIn.setToX(0);

        TranslateTransition slideOut = new TranslateTransition();
        slideOut.setNode(currentView);
        slideOut.setDuration(Duration.seconds(0.5));
        slideOut.setInterpolator(Interpolator.EASE_OUT);
        slideOut.setToX(-scene.getWidth());

        // SlideIn & SlideOut soll gleichzeitig passieren
        ParallelTransition parallelTransition = new ParallelTransition(slideIn, slideOut);
        parallelTransition.setOnFinished(e -> {
            helpPane.getChildren().removeAll(currentView, toView);
            scene.setRoot(toView);

            currentView.setTranslateX(0);
        });

        parallelTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
