package musicary.graphics.panes;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import musicary.graphics.components.MGenresButton;

import java.util.ArrayList;

public class MGenresGrid extends FlowPane {

    public MGenresGrid(ArrayList<MGenresButton> genres){
        addChildren(genres);
        setAppearance();
    }

    private void setAppearance(){
        this.setBackground(new Background(new BackgroundFill(Color.web("#f5f5f5"),
                new CornerRadii(0.0), new Insets(0.0))));
    }

    private void addChildren(ArrayList<MGenresButton> genres){
        for (int i = 0; i < genres.size(); i++) {
            this.getChildren().add(genres.get(i));
            this.setMargin(this.getChildren().get(i), new Insets(5.0));
        }
    }
}
