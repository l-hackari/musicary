package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import musicary.controllers.MainController;

import java.io.File;

public class MGenresButton extends BorderPane {

    private ImageView image;
    private MHomeText label;
    private String genre;
    protected MainController controller;

    public MGenresButton(String text, String imagePath, MainController controller) {
        this.controller = controller;
        this.genre = text.toLowerCase();
        label = new MHomeText(text);
        image = new ImageView((new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                ".." + File.separator + ".." + File.separator + "res" + File.separator +  "components" + File.separator +
                "images" + File.separator + imagePath),
                128.0, 128.0, true, ImageView.SMOOTH_DEFAULT)));
        this.setBottom(label);
        this.setTop(image);
        setAppearance();
        setAppearanceEvents();
        setMouseEvents();
    }

    private void setAppearanceEvents() {
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MGenresButton.this.setBackground(new Background(new BackgroundFill(Color.web("#fcfcfc"),
                        new CornerRadii(0.0), new Insets(
                        0.0))));
                MGenresButton.this.setEffect(new InnerShadow(3.0, 0.0, 0.0, Color.web("#6b6b6b")));
                label.setBackground(new Background(new BackgroundFill(Color.web("#fcfcfc"),
                        new CornerRadii(0.0), new Insets(
                        0.0))));
            }
        });

        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MGenresButton.this.setBackground(new Background(new BackgroundFill(Color.web("#fff"),
                        new CornerRadii(0.0), new Insets(0.0))));
                MGenresButton.this.setEffect(new DropShadow(3.0, 0.0, 0.0,
                        Color.web("#6b6b6b")));
                label.setBackground(new Background(new BackgroundFill(Color.web("#fff"),
                        new CornerRadii(0.0), new Insets(
                        0.0))));
            }
        });
    }

    protected void setMouseEvents(){
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.loadGenre(genre);
            }
        });
    }

    private void setAppearance(){
        this.setEffect(new DropShadow(3.0, 0.0, 0.0, Color.web("#6b6b6b")));
        this.setPrefSize(128, 128 + 50);
        this.setBackground(new Background(new BackgroundFill(Color.web("#fff"), new CornerRadii(0.0), new Insets(
                0.0))));
    }
}
