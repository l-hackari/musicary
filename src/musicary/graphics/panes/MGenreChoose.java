package musicary.graphics.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import musicary.graphics.components.MGenreChooseButton;
import musicary.controllers.MainController;

public class MGenreChoose extends BorderPane {

    private Label title;
    private String genre;
    private HBox bricks;
    private MainController controller;

    public MGenreChoose(String genre, String text, MainController controller){
        this.controller = controller;
        this.genre = genre;
        this.bricks = new HBox();
        title = new Label("Cosa vuoi scoprire riguardo al genere " + text + " ?");
        setBricks();
        setTitleAppearance();
        setBricksAppearance();
        setAppearance();
        this.setCenter(bricks);
        this.setTop(title);

    }

    private void setBricks(){
        bricks.getChildren().add(new MGenreChooseButton("Artisti", "rock.png", controller,
                MGenreChooseButton.ARTIST, genre));
        bricks.getChildren().add(new MGenreChooseButton("Brani", "rock.png", controller,
                MGenreChooseButton.SONGS, genre));
    }

    private void setBricksAppearance(){
        bricks.setMaxHeight(128);
        bricks.setMargin(bricks.getChildren().get(0), new Insets(5.0));
        bricks.setMargin(bricks.getChildren().get(1), new Insets(5.0));
        bricks.setAlignment(Pos.CENTER);
        this.setAlignment(bricks, Pos.TOP_CENTER);
    }

    private void setTitleAppearance(){
        title.setTextFill(Color.web("#ff004e"));
        title.setFont(Font.font("System", FontWeight.NORMAL, 30.0));
        this.setAlignment(title, Pos.CENTER);
        this.setMargin(title, new Insets(10.0));
    }

    private void setAppearance(){
        this.setBackground(new Background(new BackgroundFill(Color.web("#f5f5f5"),
                new CornerRadii(0.0), new Insets(0.0))));
    }
}
