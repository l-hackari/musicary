package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import musicary.controllers.MainController;
import musicary.model.Song;
import musicary.model.client.RequestManager;

import java.io.IOException;

public class MSong extends HBox {

    private ImageView playButton;
    private MSongLabel songName;
    private MSongLabel duration;
    private MSongLabel views;
    private MSongLabel artist;
    private MSongLabel album;
    private MSongLabel chartPosition;
    private Image playImage;
    private Image pauseImage;
    private Pane spacing;
    private boolean isPlaying = false;
    private MainController controller;
    private Song song;

    public MSong(Song song, Image playImage, Image pauseImage, MainController controller){
        this.song = song;
        this.controller = controller;
        this.chartPosition = new MSongLabel(Integer.toString(song.getTrack_number()));
        songName = new MSongLabel(song.getTitle());
        this.playImage = playImage;
        this.pauseImage = pauseImage;
        duration = new MSongLabel(secToString(song.getDuration()));
        this.views = new MSongLabel(Integer.toString(song.getViews()));
        this.artist = new MSongLabel(song.getArtist());
        this.album = new MSongLabel(song.getAlbum());
        spacing = new Pane();
        playButton = new ImageView(playImage);
        setPlayMouseEvent();
        setDurationAppearance();
        setImageAppearance();
        setSongNameAppearance();
        setSpacingAppearance();
        setViewsAppearance();
        setAlbumAppearance();
        setArtistAppearance();
        setChartPositionAppearance();
        setArtistAppearance();
        addChildren();
        setAppearance();

    }

    private String secToString(int totalSecs){
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    private void setPlayMouseEvent(){
        playButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isPlaying){
                    setToPlay();
                    controller.setAudioPause();
                } else {
                    setToPause();
                    controller.changePlaySong(song, MSong.this);
                }
            }
        });
    }

    protected void addChildren(){
        this.getChildren().add(chartPosition);
        this.getChildren().add(playButton);
        this.getChildren().add(songName);
        this.getChildren().add(this.artist);
        this.getChildren().add(this.album);
        this.getChildren().add(duration);
        this.getChildren().add(spacing);
        this.getChildren().add(this.views);
    }

    public void setToPause(){
        playButton.setImage(pauseImage);
        isPlaying = true;
    }

    public void setToPlay(){
        playButton.setImage(playImage);
        isPlaying = false;
    }

    public MSongLabel getAlbum() {
        return album;
    }

    public MSongLabel getArtist() {
        return artist;
    }

    public MSongLabel getSongName() {
        return songName;
    }

    private void setAppearance(){
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MSong.this.setBackground(new Background(new BackgroundFill(Color.web("#D6D6D6"), new CornerRadii(0.0), new
                        Insets(0.0))));
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MSong.this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0.0), new
                        Insets(0.0))));
            }
        });

        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0.0), new
                Insets(0.0))));
        this.setBorder(new Border(new BorderStroke(Color.web("#B4B4B4"), Color.TRANSPARENT,
                Color.TRANSPARENT, Color.TRANSPARENT, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(0.0), BorderStroke.THIN,
                new Insets(0.0))));
        this.setMaxHeight(30);
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

    private void setImageAppearance(){
        playButton.setFitHeight(30.0);
        playButton.setFitWidth(30.0);
        this.setMargin(playButton, new Insets(5.0));
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
}
