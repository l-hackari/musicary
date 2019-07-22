package musicary.graphics.panes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import musicary.graphics.components.MArtistProfileImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MArtistPage extends VBox {

    private VBox content;
    private ImageView coverImage;
    private MArtistProfileImage profileImage;
    private Label artistName;
    private StackPane stackPane;

    public MArtistPage(String artistName, double startWidth,
                       ArrayList<MAlbum> albums, ArrayList<MAlbum> singles, Scene scene){
        loadContent();
        this.getChildren().add(content);
        setImages();
        loadImages();
        setImagesAppearance(startWidth);
        setArtistName();
        this.artistName.setText(artistName);
        setAlbums(albums);
        setSingles(singles);
        createMainSectionResizeListener(scene);
    }

    private void createMainSectionResizeListener(Scene scene){
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                BorderPane pane = (BorderPane) scene.getRoot();
                VBox vBox = (VBox) pane.getLeft();
                setImagesAppearance(newValue.intValue() - vBox.getWidth());
            }
        });
    }

    private void setAlbums(ArrayList<MAlbum> albums){
        Accordion accordion = (Accordion) content.getChildren().get(3);
        for (int i = 0; i < albums.size(); i++) {
            accordion.getPanes().add(albums.get(i));
        }
    }

    private void setSingles(ArrayList<MAlbum> singles){
        Accordion accordion = (Accordion) content.getChildren().get(5);
        for (int i = 0; i < singles.size(); i++) {
            accordion.getPanes().add(singles.get(i));
        }
    }

    private void setArtistName(){
        artistName = (Label) content.getChildren().get(1);
    }

    private void setImages(){
        coverImage = (ImageView) stackPane.getChildren().get(0);
        profileImage = (MArtistProfileImage) stackPane.getChildren().get(1);
    }

    private void loadContent(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(".." + File.separator + "scenes" +
                File.separator + "artist.fxml"));
        try {
            content = (VBox) loader.load();
            stackPane = (StackPane) content.getChildren().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImages(){
        coverImage.setImage(new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                ".." + File.separator + ".." + File.separator + "res" + File.separator +
                "client" + File.separator + "images" + File.separator + "singleArtist" + File.separator + "cover.png")));
        profileImage.setImage(new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                ".." + File.separator + ".." + File.separator + "res" + File.separator +
                "client" + File.separator + "images" + File.separator + "singleArtist" + File.separator + "profile.png")));
    }

    private void setImagesAppearance(double width){
        coverImage.setPreserveRatio(true);
        coverImage.setFitWidth(width);
    }
}
