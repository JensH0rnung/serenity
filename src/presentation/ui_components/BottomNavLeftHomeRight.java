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
public class BottomNavLeftHomeRight extends AnchorPane {

    Button homeButton;
    Button rightArrowButton;
    Button leftArrowButton;

    public BottomNavLeftHomeRight() {
        leftArrowButton = new Button("LeftArrow");
        AnchorPane.setLeftAnchor(leftArrowButton, 15.0);
        AnchorPane.setBottomAnchor(leftArrowButton, 15.0);

        homeButton = new Button("Home");
        AnchorPane.setLeftAnchor(homeButton, 169.0);
        AnchorPane.setRightAnchor(homeButton, 169.0);
        AnchorPane.setBottomAnchor(homeButton, 15.0);

        rightArrowButton = new Button("RightArrow");
        AnchorPane.setRightAnchor(rightArrowButton, 15.0);
        AnchorPane.setBottomAnchor(rightArrowButton, 15.0);

        getChildren().addAll(leftArrowButton, homeButton, rightArrowButton);

        homeButton.getStyleClass().add("icon-button");
        rightArrowButton.getStyleClass().add("icon-button");
        leftArrowButton.getStyleClass().add("icon-button");

        homeButton.setId("homeButton");
        rightArrowButton.setId("rightArrowButton");
        leftArrowButton.setId("leftArrowButton");

        Image leftArrowButtonImage = new Image("presentation/ui_components/leftArrowIcon.png");
        ImageView leftArrowButtonIcon = new ImageView(leftArrowButtonImage);
        leftArrowButton.setGraphic(leftArrowButtonIcon);
        leftArrowButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        Image homeButtonImage = new Image("presentation/ui_components/homeIcon.png");
        ImageView homeButtonIcon = new ImageView(homeButtonImage);
        homeButton.setGraphic(homeButtonIcon);
        homeButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        Image rightArrowButtonImage = new Image("presentation/ui_components/rightArrowIcon.png");
        ImageView rightArrowButtonIcon = new ImageView(rightArrowButtonImage);
        rightArrowButton.setGraphic(rightArrowButtonIcon);
        rightArrowButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        // Animation für rightArrowButton
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(7);
        translate.setDuration(Duration.seconds(1));
        translate.setCycleCount(Animation.INDEFINITE);
        translate.setAutoReverse(true);
//        translate.setNode(rightArrowButton);
        translate.setNode(rightArrowButtonIcon);    // denke, dass sich nur Icon verschiebt is sinnvoller
        translate.play();

    }

    public Button getHomeButton() {
        return homeButton;
    }

    public String getHomeButtonID() {return getHomeButtonID();}

    public Button getRightArrowButton() {
        return rightArrowButton;
    }

    public Button getLeftArrowButton() {
        return leftArrowButton;
    }
}
