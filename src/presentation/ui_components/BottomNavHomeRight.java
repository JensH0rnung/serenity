package presentation.ui_components;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Erstellt die Navigation mit einem Home und einem "Weiter"-Button
 */
public class BottomNavHomeRight extends AnchorPane {

    Button homeButton;
    Button rightArrowButton;

    public BottomNavHomeRight() {
        homeButton = new Button("Home");
        AnchorPane.setLeftAnchor(homeButton, 15.0);
        AnchorPane.setBottomAnchor(homeButton, 15.0);

        rightArrowButton = new Button("RightArrow");
        AnchorPane.setRightAnchor(rightArrowButton, 15.0);
        AnchorPane.setBottomAnchor(rightArrowButton, 15.0);

        getChildren().addAll(homeButton, rightArrowButton);

        homeButton.getStyleClass().add("icon-button");
        rightArrowButton.getStyleClass().add("icon-button");

        homeButton.setId("homeButton");
        rightArrowButton.setId("rightArrowButton");

        homeButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        rightArrowButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        // Animation für rightArrowButton
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(7);
        translate.setDuration(Duration.seconds(1));
        translate.setCycleCount(Animation.INDEFINITE);
        translate.setAutoReverse(true);
        translate.setNode(rightArrowButton);
        translate.play();
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public Button getRightArrowButton() {
        return rightArrowButton;
    }
}
