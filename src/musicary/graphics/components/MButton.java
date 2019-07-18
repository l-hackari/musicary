package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MButton extends Button {

    public MButton(){
        setAppearance();
        setMouseEventProperties();
    }

    private void setMouseEventProperties(){
        this.setOnMousePressed(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                MButton.this.setEffect(new DropShadow(2.0, 0.0, 0.0, Color.web("#6b6b6b")));
            }
        });

        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MButton.this.setEffect(new DropShadow(2.0, 0.0, 2.0, Color.web("#545454")));
            }
        });
    }

    private void setAppearance(){
        this.setBackground(new Background(new BackgroundFill(Color.web("#fff"),
                new CornerRadii(0.0), new Insets(0.0))));
        this.setTextFill(Color.web("#ff004e"));
        this.setFont(Font.font("System", FontWeight.BOLD, 15.0));
        this.setEffect(new DropShadow(2.0, 0.0, 2.0, Color.web("#6b6b6b")));
    }
}
