package musicary.graphics.panes;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import musicary.graphics.components.MSongHeaderLabel;

public class MSongListHeader extends HBox {

    private MSongHeaderLabel songName;
    private MSongHeaderLabel duration;
    private MSongHeaderLabel views;
    private MSongHeaderLabel artist;
    private MSongHeaderLabel album;
    private MSongHeaderLabel chartPosition;
    private Pane spacing;
    private Pane spacing2;

    public MSongListHeader(){
        chartPosition = new MSongHeaderLabel("#");
        songName = new MSongHeaderLabel("Brano");
        duration = new MSongHeaderLabel("Durata");
        this.views = new MSongHeaderLabel("Ascolti");
        this.artist = new MSongHeaderLabel("Artista");
        this.album = new MSongHeaderLabel("Album");
        spacing = new Pane();
        spacing2 = new Pane();
        setDurationAppearance();
        setSpacing2Appearance();
        setSongNameAppearance();
        setSpacingAppearance();
        setViewsAppearance();
        setChartPositionAppearance();
        setAlbumAppearance();
        setArtistAppearance();
        addChildren();

    }

    private void addChildren(){
        this.getChildren().add(chartPosition);
        this.getChildren().add(spacing2);
        this.getChildren().add(songName);
        this.getChildren().add(this.artist);
        this.getChildren().add(this.album);
        this.getChildren().add(duration);
        this.getChildren().add(spacing);
        this.getChildren().add(this.views);
    }

    private void setSpacingAppearance(){
        this.setHgrow(spacing, Priority.ALWAYS);
    }

    private void setArtistAppearance(){
        artist.setPrefSize(200, 50);
        this.setMargin(artist, new Insets(13.0));
    }

    private void setChartPositionAppearance(){
        chartPosition.setPrefSize(30, 50);
        this.setMargin(chartPosition, new Insets(10.0));
    }

    private void setAlbumAppearance(){
        album.setPrefSize(200, 50);
        this.setMargin(album, new Insets(13.0));
    }

    private void setSongNameAppearance(){
        songName.setPrefSize(200, 50);
        this.setMargin(songName, new Insets(10.0));
    }

    private void setDurationAppearance(){
        duration.setPrefSize(100, 50);
        this.setMargin(duration, new Insets(13.0));
    }

    private void setViewsAppearance(){
        views.setPrefSize(80, 50);
        this.setMargin(views, new Insets(13.0));
    }

    private void setSpacing2Appearance(){
        spacing2.setPrefSize(30, 50);
        this.setMargin(spacing2, new Insets(5.0));
    }
}
