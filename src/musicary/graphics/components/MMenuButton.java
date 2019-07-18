package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;

public class MMenuButton extends Label {

    public MMenuButton(String text, String picturePath){
        setMouseEventProperties();
        setAppearance(text);
        setGraphic(new ImageView(new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                ".." + File.separator + ".." + File.separator + "res" + File.separator +  "components" + File.separator +
                        "images" + File.separator +
                picturePath), 24.0, 24.0, true, ImageView.SMOOTH_DEFAULT)));
    }

    private void setMouseEventProperties(){
        this.setOnMousePressed(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                MMenuButton.this.setBackground(new Background(new BackgroundFill(Color.web("#e60046"),
                        new CornerRadii(0.0), new Insets(0.0))));
                MMenuButton.this.setBorder(new Border(new BorderStroke(Color.web("#e60046"), Color.web("#e60046"),
                        Color.web("#e60046"), Color.web("#fff"), BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                        BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(0.0), BorderStroke.MEDIUM,
                        new Insets(0.0))));
            }
        });

        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MMenuButton.this.setBackground(new Background(new BackgroundFill(Color.web("#ff004e"),
                        new CornerRadii(0.0), new Insets(0.0))));
                MMenuButton.this.setBorder(new Border(new BorderStroke(Color.web("#ff004e"), Color.web("#ff004e"),
                        Color.web("#ff004e"), Color.web("#fff"), BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                        BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(0.0), BorderStroke.MEDIUM,
                        new Insets(0.0))));
            }
        });

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MMenuButton.this.setBorder(new Border(new BorderStroke(Color.web("#ff004e"), Color.web("#ff004e"),
                        Color.web("#ff004e"), Color.web("#fff"), BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                        BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(0.0), BorderStroke.MEDIUM,
                        new Insets(0.0))));
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MMenuButton.this.setBorder(new Border(new BorderStroke(Color.web("#ff004e"), Color.web("#ff004e"),
                        Color.web("#ff004e"), Color.web("#ff004e"), BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                        BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(0.0), BorderStroke.MEDIUM,
                        new Insets(0.0))));
            }
        });
    }

    private void setAppearance(String text){
        this.setPrefSize(170, 50);
        this.setAlignment(Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.web("#ff004e"),
                new CornerRadii(0.0), new Insets(0.0))));
        this.setTextFill(Color.web("#f5f5f5"));
        this.setFont(Font.font("System", FontWeight.THIN, 15.0));
        this.setText(text);
    }

}
