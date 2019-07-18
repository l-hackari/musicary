package musicary.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import musicary.graphics.components.*;
import musicary.graphics.panes.*;

import java.io.File;
import java.util.ArrayList;

public class MainController {

    @FXML
    private MMainSection mainSection;
    @FXML
    private MMenuChartsButton chartsHomeButton;
    @FXML
    private MMenuHomeButton homeButton;
    @FXML
    private MAudioPicture playingAudioPicture;
    @FXML
    private MPlayerLink playingAudioAlbum;
    @FXML
    private MPlayerLink playingAudioArtist;
    @FXML
    private ImageView nextAudioButton;
    @FXML
    private ImageView playAudioButton;
    @FXML
    private ImageView backAudioButton;
    @FXML
    private MMenuProfileButton profileHomeButton;


    private Image playImage;
    private Image playerPlayImage;
    private Image playerPauseImage;
    private Image pauseImage;
    private Scene scene;
    private MSong inPlay;
    private boolean isPlaying = false;
    private Scene loginScene;
    private Stage stage;
    public static final int ARTIST = 1;
    public static final int SONGS = 2;


    @FXML
    private void initialize(){
        loadHome(null);
    }

    @FXML
    private void loadGenres(MouseEvent mouseEvent) {
        ArrayList<MGenresButton> genres = new ArrayList<>();
        genres.add(new MGenresButton("Rock", "rock.png", this));
        genres.add(new MGenresButton("Indie", "indie.png", this));
        genres.add(new MGenresButton("EDM", "edm.png", this));
        genres.add(new MGenresButton("Pop", "pop.png", this));
        genres.add(new MGenresButton("Rock", "rock.png", this));
        genres.add(new MGenresButton("Indie", "indie.png", this));
        genres.add(new MGenresButton("EDM", "edm.png", this));
        genres.add(new MGenresButton("Pop", "pop.png", this));
        genres.add(new MGenresButton("Rock", "rock.png", this));
        genres.add(new MGenresButton("Indie", "indie.png", this));
        genres.add(new MGenresButton("EDM", "edm.png", this));
        genres.add(new MGenresButton("Pop", "pop.png", this));
        mainSection.loadSection(new MGenresGrid(genres));
    }

    @FXML
    private void loadCharts(MouseEvent mouseEvent) {

        ArrayList<MSong> songs = new ArrayList<>();
        ImageView songCoverImage = new ImageView(new Image(getClass().getResourceAsStream(".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res"
                + File.separator + "images" + File.separator +
                "cover2.jpg"), 128.0, 128.0,true, ImageView.SMOOTH_DEFAULT));

        ImageView songCoverImage2 = new ImageView(new Image(getClass().getResourceAsStream(".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator +
                "images" + File.separator +
                "cover.jpg"), 128.0, 128.0,true, ImageView.SMOOTH_DEFAULT));

        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0){
                songs.add(new MSong(Integer.toString(i + 1), playImage, pauseImage, songCoverImage,"Dani California", "Red Hot Chili Peppers",
                        "Stadium Arcadium", "03:15", 120000, this));
            } else {
                songs.add(new MSong(Integer.toString(i + 1), playImage, pauseImage,songCoverImage2, "Hysteria", "Muse",
                        "Apocalypse", "02:15", 120000, this));
            }
        }

        mainSection.loadSection(new MChart(songs, "Le canzoni più ascoltate del momento", "rock.jpg",
                mainSection.getWidth(), scene));
    }

    @FXML
    private void doLogOut(MouseEvent mouseEvent) {
        BorderPane borderPane = (BorderPane) loginScene.getRoot();
        BorderPane thisBorderPane = (BorderPane) scene.getRoot();
        borderPane.setPrefWidth(thisBorderPane.getWidth());
        borderPane.setPrefHeight(thisBorderPane.getHeight());
        stage.setScene(loginScene);
    }

    @FXML
    private void loadProfilePage(MouseEvent mouseEvent) {
        mainSection.loadSection(new MProfilePage());
    }

    @FXML
    private void loadHome(MouseEvent mouseEvent) {

        playImage = new Image(getClass().getResourceAsStream( ".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator +
                "components" + File.separator + "images" + File.separator +
                "play.png"), 30.0, 30.0,true, ImageView.SMOOTH_DEFAULT);

        pauseImage = new Image(getClass().getResourceAsStream(".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator +
                "components" + File.separator + "images" + File.separator +
                "pause.png"), 30.0, 30.0,true, ImageView.SMOOTH_DEFAULT);

        playerPlayImage = new Image(getClass().getResourceAsStream(".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator +
                "components" + File.separator + "images" + File.separator +
                "play.png"));

        playerPauseImage = new Image(getClass().getResourceAsStream(".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator +
                "components" + File.separator + "images" + File.separator +
                "pause.png"));

        ArrayList<MArtistButton> artists = new ArrayList<>();
        ArrayList<MArtistButton> artists2 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0)
                artists.add(new MArtistButton("Red Hot Chili Peppers", "redhot.jpg", this));
            else
                artists.add(new MArtistButton("Muse", "redhot.jpg", this));
        }

        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0)
                artists2.add(new MArtistButton("Red Hot Chili Peppers", "redhot.jpg", this));
            else
                artists2.add(new MArtistButton("Muse", "redhot.jpg", this));
        }

        mainSection.loadSection(new MHomePage(artists, artists2));
    }

    public void loadGenre(String genre){
        mainSection.loadSection(new MGenreChoose(genre, "Rock",this));
    }

    public void changePlaySong(MSong song){

        if(inPlay != song){
            if(inPlay != null)
                inPlay.setToPlay();

            playingAudioAlbum.setText(song.getSongName().getText());
            playingAudioArtist.setText(song.getArtist().getText());
            playingAudioPicture.setImage(song.getCoverImage().getImage());
            inPlay = song;
            setAudioPlay();
        }

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void setAudioPause(){
        isPlaying = false;
        playAudioButton.setImage(playerPlayImage);
        if(inPlay != null)
            inPlay.setToPlay();
    }

    public void setAudioPlay(){
        isPlaying = true;
        playAudioButton.setImage(playerPauseImage);
        if(inPlay != null)
            inPlay.setToPause();
    }

    @FXML
    private void playOrPauseClick(MouseEvent mouseEvent) {
        if(isPlaying)
            setAudioPause();
        else
            setAudioPlay();
    }

    public void loadArtist(String artist){
        ArrayList<MSong> songs = new ArrayList<>();
        ArrayList<MSong> songs2 = new ArrayList<>();
        ArrayList<MAlbum> albums = new ArrayList<>();
        ArrayList<MAlbum> singles = new ArrayList<>();
        ArrayList<MSong> ssongs = new ArrayList<>();
        ArrayList<MSong> ssongs2 = new ArrayList<>();
        ImageView songCoverImage = new ImageView(new Image(getClass().getResourceAsStream(".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator + "images" + File.separator +
                "cover2.jpg"), 128.0, 128.0,true, ImageView.SMOOTH_DEFAULT));

        ImageView songCoverImage2 = new ImageView(new Image(getClass().getResourceAsStream(".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator + "images"
                + File.separator +
                "cover.jpg"), 128.0, 128.0,true, ImageView.SMOOTH_DEFAULT));


        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0){
                songs.add(new MSong("-", playImage, pauseImage,songCoverImage, "Dani California", "Red Hot Chili Peppers",
                        "Stadium Arcadium", "03:15", 120000, this));
            } else {
                songs.add(new MSong("-", playImage, pauseImage,songCoverImage2,"Hysteria", "Muse",
                        "Apocalypse", "02:15", 120000, this));
            }
        }

        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0){
                songs2.add(new MSong("-", playImage, pauseImage,songCoverImage, "Dani California", "Red Hot Chili Peppers",
                        "Stadium Arcadium", "03:15", 120000, this));
            } else {
                songs2.add(new MSong("-", playImage, pauseImage, songCoverImage2, "Hysteria", "Muse",
                        "Apocalypse", "02:15", 120000, this));
            }
        }

        ssongs.add(new MSong("-", playImage, pauseImage, songCoverImage, "Fortune Faded", "Red Hot Chili Peppers", "Fortune Faded",
                "03:15", 120000, this));
        ssongs2.add(new MSong("-", playImage, pauseImage, songCoverImage2, "Fortune Faded", "Red Hot Chili Peppers", "Fortune Faded",
                "03:15", 120000, this));
        albums.add(new MAlbum("Stadium Aracadium", 2006, songs));
        albums.add(new MAlbum("By The Way", 2002, songs2));
        singles.add(new MAlbum("Fortune Faded", 2004, ssongs));
        singles.add(new MAlbum("Fortune Faded", 2004, ssongs2));

        mainSection.loadSection(new MArtistPage("rhcp.jpg", "redhot.jpg",
                "Red Hot Chili Peppers", mainSection.getWidth(), albums, singles, scene));
    }

    public void loadGenreChoose(int genreChoose, String genre){
        ArrayList<MSong> songs = new ArrayList<>();
        ArrayList<MArtistButton> artists = new ArrayList<>();

        ImageView songCoverImage = new ImageView(new Image(getClass().getResourceAsStream(".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator +
                "images" + File.separator +
                "cover2.jpg"), 128.0, 128.0,true, ImageView.SMOOTH_DEFAULT));

        ImageView songCoverImage2 = new ImageView(new Image(getClass().getResourceAsStream(".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator +
                "images" + File.separator +
                "cover.jpg"), 128.0, 128.0,true, ImageView.SMOOTH_DEFAULT));
        
        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0){
                songs.add(new MSong("-", playImage, pauseImage,songCoverImage,
                        "Dani California", "Red Hot Chili Peppers",
                        "Stadium Arcadium", "03:15", 120000, this));
            } else {
                songs.add(new MSong("-", playImage, pauseImage,songCoverImage2,
                        "Hysteria", "Muse",
                        "Apocalypse", "02:15", 120000, this));
            }
        }

        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0)
                artists.add(new MArtistButton("Red Hot Chili Peppers", "redhot.jpg", this));
            else
                artists.add(new MArtistButton("Muse", "redhot.jpg", this));
        }

        if(genreChoose == ARTIST)
            mainSection.loadSection(new MArtistsGrid(artists));
        else if(genreChoose == SONGS)
            mainSection.loadSection(new MGenreSongsList(songs, "Brani Rock in eveidenza", "rock.jpg",
                    mainSection.getWidth(), scene));
    }

    @FXML
    private void redirectArtist(MouseEvent mouseEvent) {
        ArrayList<MSong> songs = new ArrayList<>();
        ArrayList<MSong> songs2 = new ArrayList<>();
        ArrayList<MAlbum> albums = new ArrayList<>();
        ArrayList<MAlbum> singles = new ArrayList<>();
        ArrayList<MSong> ssongs = new ArrayList<>();
        ArrayList<MSong> ssongs2 = new ArrayList<>();
        ImageView songCoverImage = new ImageView(new Image(getClass().getResourceAsStream(".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator + "images" + File.separator +
                "cover2.jpg"), 128.0, 128.0,true, ImageView.SMOOTH_DEFAULT));

        ImageView songCoverImage2 = new ImageView(new Image(getClass().getResourceAsStream(".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator + "images"
                + File.separator +
                "cover.jpg"), 128.0, 128.0,true, ImageView.SMOOTH_DEFAULT));


        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0){
                songs.add(new MSong("-", playImage, pauseImage,songCoverImage, "Dani California", "Red Hot Chili Peppers",
                        "Stadium Arcadium", "03:15", 120000, this));
            } else {
                songs.add(new MSong("-", playImage, pauseImage,songCoverImage2,"Hysteria", "Muse",
                        "Apocalypse", "02:15", 120000, this));
            }
        }

        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0){
                songs2.add(new MSong("-", playImage, pauseImage,songCoverImage, "Dani California", "Red Hot Chili Peppers",
                        "Stadium Arcadium", "03:15", 120000, this));
            } else {
                songs2.add(new MSong("-", playImage, pauseImage, songCoverImage2, "Hysteria", "Muse",
                        "Apocalypse", "02:15", 120000, this));
            }
        }

        ssongs.add(new MSong("-", playImage, pauseImage, songCoverImage, "Fortune Faded", "Red Hot Chili Peppers", "Fortune Faded",
                "03:15", 120000, this));
        ssongs2.add(new MSong("-", playImage, pauseImage, songCoverImage2, "Fortune Faded", "Red Hot Chili Peppers", "Fortune Faded",
                "03:15", 120000, this));
        albums.add(new MAlbum("Stadium Aracadium", 2006, songs));
        albums.add(new MAlbum("By The Way", 2002, songs2));
        singles.add(new MAlbum("Fortune Faded", 2004, ssongs));
        singles.add(new MAlbum("Fortune Faded", 2004, ssongs2));

        mainSection.loadSection(new MArtistPage("rhcp.jpg", "redhot.jpg",
                "Red Hot Chili Peppers", mainSection.getWidth(), albums, singles, scene));
    }
}
