package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import musicary.controllers.MainController;

import java.io.File;

public class MArtistButton extends BorderPane {

    private ImageView image;
    private MArtistsGridText label;
    private MainController controller;
    private int artistId;

    public MArtistButton (String text, String imagePath, int artistId, MainController controller){
        this.artistId = artistId;
        this.controller = controller;
        label = new MArtistsGridText(text);
        image = new ImageView((new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                ".." + File.separator + ".." + File.separator + "res" + File.separator + "client" + File.separator +
                "images" + File.separator + "artists" + File.separator + imagePath),
                128.0, 128.0, true, ImageView.SMOOTH_DEFAULT)));
        setAppearance();
        setImageAppearance();
        setMouseEvents();
        this.setBottom(label);
        this.setTop(image);
    }

    private void setMouseEvents(){
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.loadArtist(Integer.toString(artistId),
                        label.getText());
            }
        });

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(0.7);
                image.setEffect(colorAdjust);
                label.setAlternativeText();
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                image.setEffect(null);
                label.setNormalText();
            }
        });
    }

    private void setImageAppearance(){
        Circle circle = new Circle(64, 64, 64);
        image.setClip(circle);
    }

    private void setAppearance(){
        this.setMargin(label, new Insets(5.0));
        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0.0), new Insets(
                0.0))));
        this.setCursor(Cursor.HAND);
    }
}
