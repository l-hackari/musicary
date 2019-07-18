package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MAlertButton extends Button {
    public MAlertButton(){
        setAppearance();
        setMouseEventProperties();
    }

    private void setMouseEventProperties(){
        this.setOnMousePressed(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {

            }
        });

        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
    }

    private void setAppearance(){
        this.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.web("#ff004e"),
                new CornerRadii(0.0), new Insets(0.0))));
        this.setTextFill(javafx.scene.paint.Color.web("#fff"));
        this.setFont(Font.font("System", FontWeight.BOLD, 15.0));
    }
}
