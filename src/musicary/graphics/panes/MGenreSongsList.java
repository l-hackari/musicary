package musicary.graphics.panes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import musicary.graphics.components.MSong;

import java.io.File;
import java.util.ArrayList;

public class MGenreSongsList extends VBox {

    private MSongListHeader tableHeader;
    private BorderPane header;
    private Label title;
    private ImageView playImage;
    private ImageView coverImage;
    private Scene scene;

    public MGenreSongsList(ArrayList<MSong> songs, String title, String imagePath, double startWidth, Scene scene){
        header = new BorderPane();
        tableHeader = new MSongListHeader();
        this.title = new Label(title);
        this.scene = scene;
        loadCoverImage(imagePath);
        loadPlayImage("play.png");
        setTitleAppearance();
        setCoverImageAppearance(startWidth);
        createMainSectionResizeListener(scene);
        header.setCenter(coverImage);
        header.setBottom(this.title);
        this.getChildren().add(header);
        this.getChildren().add(tableHeader);
        addSongs(songs);
    }

    private void addSongs(ArrayList<MSong> songs){
        for (int i = 0; i < songs.size(); i++) {
            this.getChildren().add(songs.get(i));
        }
    }

    private void setTitleAppearance(){
        title.setTextFill(Color.web("#ff004e"));
        title.setFont(Font.font("System", FontWeight.NORMAL, 30.0));
        header.setAlignment(title, Pos.CENTER);
        header.setMargin(title, new Insets(20.0));
    }

    private void createMainSectionResizeListener(Scene scene){
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                BorderPane pane = (BorderPane) scene.getRoot();
                VBox vBox = (VBox) pane.getLeft();
                setCoverImageAppearance(newValue.intValue() - vBox.getWidth());
            }
        });
    }

    private void setCoverImageAppearance(double imageWidth){
        coverImage.setPreserveRatio(true);
        coverImage.setFitWidth(imageWidth);
    }

    private void loadPlayImage(String imagePath){
        playImage = new ImageView((new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                ".." + File.separator + ".." + File.separator + "res" + File.separator + "components" + File.separator +
                "images" + File.separator + imagePath),
                30.0, 30.0, true,
                ImageView.SMOOTH_DEFAULT)));
    }

    private void loadCoverImage(String imagePath){
       coverImage = new ImageView(new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                ".." + File.separator + ".." + File.separator + "res" + File.separator +  "client" + File.separator +
                "images" + File.separator + "genres" + File.separator + "covers" + File.separator + imagePath + ".png")));
    }
}
