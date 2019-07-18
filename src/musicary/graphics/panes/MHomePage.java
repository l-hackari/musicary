package musicary.graphics.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import musicary.graphics.components.MArtistButton;

import java.util.ArrayList;

public class MHomePage extends VBox {

    private Label recentsText;
    private Label favouritesText;
    private MArtistsGrid recents;
    private MArtistsGrid favourites;

    public MHomePage(ArrayList<MArtistButton> recents, ArrayList<MArtistButton> favourites){
        recentsText = new Label("Artisti ascoltati di recente");
        favouritesText = new Label("I tuoi artisti preferiti");
        setTitlesAppearance();
        setAppearance();
        this.recents = new MArtistsGrid(recents);
        this.favourites = new MArtistsGrid(favourites);
        addChildren();
    }

    private void addChildren(){
        this.getChildren().add(recentsText);
        this.getChildren().add(recents);
        this.getChildren().add(favouritesText);
        this.getChildren().add(favourites);
    }

    private void setTitlesAppearance(){
        recentsText.setTextFill(Color.web("#ff004e"));
        favouritesText.setTextFill(Color.web("#ff004e"));
        recentsText.setFont(Font.font("System", FontWeight.NORMAL, 30.0));
        favouritesText.setFont(Font.font("System", FontWeight.NORMAL, 30.0));
        this.setAlignment(Pos.CENTER);
        this.setMargin(recentsText, new Insets(20.0));
        this.setMargin(favouritesText, new Insets(20.0));
    }

    private void setAppearance(){
        this.setBackground(new Background(new BackgroundFill(Color.web("#f5f5f5"),
                new CornerRadii(0.0), new Insets(0.0))));
    }
}
