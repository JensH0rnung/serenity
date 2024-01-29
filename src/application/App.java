package application;
import business_logic.data.SoundManager;
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
        primaryViews = new HashMap<>();

        /*
         Übergabe dieser Klasse an Controller, damit switchView aufgerufen werden kann
         Sinnvoll oder andere Implementierung?
         */

        IntroViewController introViewController = new IntroViewController(this);
        introView = introViewController.getParentContainer();
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

        MeditationPlayerViewController meditationPlayerViewController = new MeditationPlayerViewController(this, soundPlayer, soundManager);
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
     * Wechselt zwischen Views
     *
     * @param viewName - View, der angezeigt werden soll
     */
    public void switchView(View viewName) {

        switch(viewName) {
            case INTRO:
                startFadeAnimation(scene, (Pane) scene.getRoot(), introView);
                break;
            case STRESS_INTRO:
                startRightSlideAnimation(scene, (Pane) scene.getRoot(), stressIntroView);
                break;

            default:
                System.out.println("default ViewSwitch - ohne Animation");
                Scene currentScene = primaryStage.getScene();

                Pane nextView = primaryViews.get(viewName);
                if (nextView != null) {
                    currentScene.setRoot(nextView);
                }
                root = nextView;
        }
    }

    public void startFadeAnimation(Scene scene, Pane currentView, Pane toView) {
        // Hilfs-StackPane erzeugen
        StackPane helpPane = new StackPane();
        helpPane.setId("helpPane"); // für BackgroundColor

        // Anim-Vorbereitung
        helpPane.getChildren().addAll(currentView, toView);
        scene.setRoot(helpPane);

        // Anim-Definitionen
        FadeTransition fadeOut = new FadeTransition();
        fadeOut.setNode(currentView);
        fadeOut.setDuration(Duration.seconds(0.25));
        fadeOut.setInterpolator(Interpolator.EASE_OUT);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setNode(toView);
        fadeIn.setDuration(Duration.seconds(0.5));
        fadeIn.setInterpolator(Interpolator.EASE_OUT);
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
     * Animation, die vom navRightArrow aufgerufen wird
     *
     * @param scene
     * @param currentView - aktuell gezeigter View
     * @param toView - View, der angezeigt werden soll
     */
    public void startRightSlideAnimation(Scene scene, Pane currentView, Pane toView) {

        StackPane helpPane = new StackPane();
        helpPane.setId("helpPane");

        helpPane.getChildren().addAll(currentView, toView);
        currentView.setTranslateX(0);
        toView.setTranslateX(scene.getWidth());
        scene.setRoot(helpPane);

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
        });

        parallelTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
