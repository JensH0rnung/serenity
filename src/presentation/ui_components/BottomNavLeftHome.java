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
public class BottomNavLeftHome extends AnchorPane {

    Button homeButton;
    Button leftArrowButton;

    public BottomNavLeftHome() {

        leftArrowButton = new Button("LeftArrow");
        AnchorPane.setLeftAnchor(leftArrowButton, 15.0);
        AnchorPane.setBottomAnchor(leftArrowButton, 15.0);


        homeButton = new Button("Home");
        AnchorPane.setLeftAnchor(homeButton, 169.0);
        AnchorPane.setRightAnchor(homeButton, 169.0);
        AnchorPane.setBottomAnchor(homeButton, 15.0);

        getChildren().addAll(leftArrowButton, homeButton);

        leftArrowButton.getStyleClass().add("icon-button");
        homeButton.getStyleClass().add("icon-button");

        leftArrowButton.setId("leftArrowButton");
        homeButton.setId("homeButton");

        leftArrowButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        homeButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public Button getLeftArrowButton() {
        return leftArrowButton;
    }
}
