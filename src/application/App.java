package application;
import business_logic.services.FileManager;
import business_logic.services.BreathingRhythmClass;
import business_logic.services.Player;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import presentation.scenes.choosePathView.ChoosePathController;
import presentation.scenes.introView.IntroViewController;
import presentation.scenes.meditationPath.meditationEndView.MeditationEndController;
import presentation.scenes.meditationPath.meditationIntroView.MeditationIntroController;
import presentation.scenes.meditationPath.meditationPlayerView.MeditationPlayerController;
import presentation.scenes.meditationPath.meditationSelectionVIew.MeditationSelectionController;
import presentation.scenes.motivationPath.motivationEndView.MotivationEndController;
import presentation.scenes.motivationPath.motivationIntroView.MotivationIntroController;
import presentation.scenes.motivationPath.motivationPlayerView.MotivationPlayerController;
import presentation.scenes.motivationPath.motivationSelection.MotivationSelectionController;
import presentation.scenes.stressPath.stressBreathingView.StressBreathingController;
import presentation.scenes.stressPath.stressEndView.StressEndController;
import presentation.scenes.stressPath.stressIntroView.StressIntroController;
import presentation.scenes.stressPath.stressSelectionView.StressSelectionController;
import javafx.scene.layout.StackPane;

import java.util.HashMap;

public class App extends Application {

    private Scene scene;
    private Pane root;
    private View defaultView;
    private AnimatedViews nextController;
    private HashMap<View, Pane> views;
    private HashMap<View, AnimatedViews> viewControllers;

    BreathingRhythmClass breathingRhythm;
    Player player;
    FileManager fileManager;

    private Pane introView;
    private Pane choosePathView;

    private Pane stressIntroView;
    private Pane stressSelectionView;
    private Pane stressBreathingView;
    private Pane stressEndView;

    private Pane meditationIntroView;
    private Pane meditationSelectSoundView;
    private Pane meditationPlayerView;
    private Pane meditationEndView;

    private Pane motivationIntroView;
    private Pane motivationSelectView;
    private Pane motivationPlayerView;
    private Pane motivationEndView;

    /**
     * Initialisieren der Views
     */
    @Override
    public void init() {

        player = new Player();
        fileManager = new FileManager();

        // Ermöglicht Zugriff auf DurationProperty der CircleAnimation
        breathingRhythm = new BreathingRhythmClass();

        defaultView = View.INTRO;

        // Zum speichern aller Views
        views = new HashMap<>();
        // Zum speichern aller Controller
        viewControllers = new HashMap<>();

        /*
         Übergabe dieser Klasse an Controller, damit switchView aufgerufen werden kann
         Sinnvoll oder andere Implementierung?
         */

        IntroViewController introViewController = new IntroViewController();
        introView = introViewController.getRoot();
        views.put(View.INTRO, introView);
        viewControllers.put(View.INTRO, introViewController);

        ChoosePathController choosePathController = new ChoosePathController(this);
        choosePathView = choosePathController.getRoot();
        views.put(View.CHOOSE_PATH, choosePathView);
        viewControllers.put(View.CHOOSE_PATH, choosePathController);

        // StressPath
        StressIntroController stressIntroController = new StressIntroController(this);
        stressIntroView = stressIntroController.getRoot();
        views.put(View.STRESS_INTRO, stressIntroView);
        viewControllers.put(View.STRESS_INTRO, stressIntroController);

        StressSelectionController stressSelectionController = new StressSelectionController(this, breathingRhythm);
        stressSelectionView = stressSelectionController.getRoot();
        views.put(View.STRESS_SELECTION, stressSelectionView);
        viewControllers.put(View.STRESS_SELECTION, stressSelectionController);

        StressBreathingController stressBreathingController = new StressBreathingController(this, breathingRhythm);
        stressBreathingView = stressBreathingController.getRoot();
        views.put(View.STRESS_BREATHING, stressBreathingView);

        StressEndController stressEndController = new StressEndController(this);
        stressEndView = stressEndController.getRoot();
        views.put(View.STRESS_END, stressEndView);
        viewControllers.put(View.STRESS_END, stressEndController);

        // MeditationPath
        MeditationIntroController meditationIntroController = new MeditationIntroController(this);
        meditationIntroView = meditationIntroController.getRoot();
        views.put(View.MEDITATION_INTRO, meditationIntroView);
        viewControllers.put(View.MEDITATION_INTRO, meditationIntroController);

        MeditationSelectionController meditationSelectionController = new MeditationSelectionController(this, player, fileManager);
        meditationSelectSoundView = meditationSelectionController.getRoot();
        views.put(View.MEDITATION_SELECTION, meditationSelectSoundView);
        viewControllers.put(View.MEDITATION_SELECTION, meditationSelectionController);

        MeditationPlayerController meditationPlayerController = new MeditationPlayerController(this, player, meditationSelectionController);
        meditationPlayerView = meditationPlayerController.getRoot();
        views.put(View.MEDITATION_PLAYER, meditationPlayerView);

        MeditationEndController meditationEndController = new MeditationEndController(this);
        meditationEndView = meditationEndController.getRoot();
        views.put(View.MEDITATION_END, meditationEndView);
        viewControllers.put(View.MEDITATION_END, meditationEndController);

        // MotivationPath
        MotivationIntroController motivationIntroController = new MotivationIntroController(this);
        motivationIntroView = motivationIntroController.getRoot();
        views.put(View.MOTIVATION_INTRO, motivationIntroView);
        viewControllers.put(View.MOTIVATION_INTRO, motivationIntroController);

        MotivationSelectionController motivationSelectionController = new MotivationSelectionController(this, player, fileManager);
        motivationSelectView = motivationSelectionController.getRoot();
        views.put(View.MOTIVATION_SELECTION, motivationSelectView);
        viewControllers.put(View.MOTIVATION_SELECTION, motivationSelectionController);

        MotivationPlayerController motivationPlayerController = new MotivationPlayerController(this, player, motivationSelectionController);
        motivationPlayerView = motivationPlayerController.getRoot();
        views.put(View.MOTIVATION_PLAYER, motivationPlayerView);

        MotivationEndController motivationEndController = new MotivationEndController(this);
        motivationEndView = motivationEndController.getRoot();
        views.put(View.MOTIVATION_END, motivationEndView);
        viewControllers.put(View.MOTIVATION_END, motivationEndController);
    }

    @Override
    public void start(Stage primaryStage) {

        root = views.get(defaultView);

        // Displaygröße iPhone 13 / 14
        scene = new Scene(root, 390, 844);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Serenity");
//        fadeTo(defaultView);
        primaryStage.show();
    }

    /**
     * Startet FadeAnimation zum nächsten View
     *
     * @param nextView - View der angezeigt werden soll
     */
    public void fadeTo(View nextView) {
        Pane currentPane = (Pane) scene.getRoot();
        Pane nextPane = views.get(nextView);

        nextController = viewControllers.get(nextView);
        // nur bei animierten Views
        if(nextController != null) {
            nextController.hideAllElements();
        }

        startFadeAnimation(scene, currentPane, nextPane);
    }

    /**
     * Startet RightSlideAnimation zum nächsten View
     *
     * @param nextView - View der angezeigt werden soll
     */
    public void rightSlideTo(View nextView) {
        Pane currentPane = (Pane) scene.getRoot();
        Pane nextPane = views.get(nextView);

        System.out.println("nextView - " + nextView);
        nextController = viewControllers.get(nextView);
        System.out.println("nextController - "+ nextController);
        nextController.hideAllElements();

        startRightSlideAnimation(scene, currentPane, nextPane);
    }

    /**
     * Startet LeftSlideAnimation zum nächsten View
     *
     * @param nextView - View der angezeigt werden soll
     */
    public void leftSlideTo(View nextView) {
        Pane currentPane = (Pane) scene.getRoot();
        Pane nextPane = views.get(nextView);

        nextController = viewControllers.get(nextView);
        nextController.hideAllElements();

        startLeftSlideAnimation(scene, currentPane, nextPane);
    }

    /**
     * Definition und Ausführung der FadeTransition
     *
     * @param scene
     * @param currentView - View, der verlassen wird
     * @param toView - View, der eingefadet wird
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

                    if(nextController != null) {
                        nextController.startAnimations();
                    }
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
            nextController.startAnimations();
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
            nextController.startAnimations();
        });

        parallelTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
