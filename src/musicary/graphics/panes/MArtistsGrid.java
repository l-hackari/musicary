package musicary.graphics.panes;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import musicary.graphics.components.MArtistButton;

import java.util.ArrayList;

public class MArtistsGrid extends FlowPane {

    private ArrayList<MArtistButton> artists;

    public MArtistsGrid (ArrayList<MArtistButton> artists){
        this.artists = artists;
        addChildren(artists);
        setAppearance();
        setArtistsAppearance();
    }

    private void addChildren(ArrayList<MArtistButton> artists){
        for (int i = 0; i < artists.size(); i++) {
            this.getChildren().add(artists.get(i));
        }
    }
    
    private void setArtistsAppearance(){
        for (int i = 0; i < this.getChildren().size(); i++) {
            this.setMargin(getChildren().get(i), new Insets(10.0));
        }
    }

    private void setAppearance(){
        this.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        this.setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        this.setBackground(new Background(new BackgroundFill(Color.web("#f5f5f5"),
                new CornerRadii(0.0), new Insets(0.0))));
    }
}
