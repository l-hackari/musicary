package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MProgressBar extends ProgressBar {

    public MProgressBar(){
        this.setProgress(0.0);
        setMouseEvents();
        setAppearance();
    }

    private void setMouseEvents(){
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Bounds bd = MProgressBar.this.localToScene(MProgressBar.this.getBoundsInLocal());
                double d = event.getSceneX() - bd.getMinX();
                MProgressBar.this.setProgress((d / MProgressBar.this.getWidth()));
            }
        });
    }

    private void setAppearance(){
        this.setBackground(new Background(new BackgroundFill(Color.web("#000"),
                new CornerRadii(0.0), new Insets(10.0, 10.0, 10.0, 10.0))));
        this.setPadding(new Insets(0.0));
    }
}
