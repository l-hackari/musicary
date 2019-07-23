package musicary.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import musicary.graphics.components.*;
import musicary.graphics.panes.*;
import musicary.model.Artist;
import musicary.model.Song;
import musicary.model.User;
import musicary.model.client.RequestManager;
import musicary.model.Genre;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    public static final int TYPE_GENRE = 0;
    public static final int TYPE_CHART = 1;
    public static final int TYPE_ARTIST = 2;

    private Image playImage;
    private Image playerPlayImage;
    private Image playerPauseImage;
    private Image pauseImage;
    private Scene scene;
    private MSong inPlay;
    private Song playingSong;
    private boolean isPlaying = false;
    private Scene loginScene;
    private Stage stage;
    public static final int ARTIST = 1;
    public static final int SONGS = 2;
    private RequestManager requestManager;
    private ArrayList<MSong> currentTracklist;
    private ArrayList<MSong> requestedTrackList;
    private Pane executingPane;
    private Pane currentPane;
    private String currentGenre;
    private String executingGenre;
    private int currentArtist;
    private int executingArtist;
    private int currentPaneType;
    private int executingPaneType;
    private User loggedUser;
    private ProfileController profileController;
    private LoginController loginController;


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
    @FXML
    private MProgressBar progressBar;
    @FXML
    private Label actualSongTime;
    @FXML
    private Label endSongTime;

    @FXML
    private void initialize(){

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
    }

    @FXML
    private void loadGenres(MouseEvent mouseEvent) {

        try {
            ArrayList<Genre> genres = requestManager.getGenres();
            ArrayList<MGenresButton> mgenres = new ArrayList<>();
            for (int i = 0; i < genres.size(); i++) {
                mgenres.add(new MGenresButton(genres.get(i).getName(), Integer.toString(genres.get(i).getId()),
                        this));
            }
            mainSection.loadSection(new MGenresGrid(mgenres));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void loadCharts(MouseEvent mouseEvent) {

        if(executingPaneType == TYPE_CHART) {
            mainSection.loadSection(executingPane);
        } else {

            requestedTrackList = new ArrayList<>();

            try {
                ArrayList<Song> songs = requestManager.getGlobalChart();
                for (int i = 0; i < songs.size(); i++) {
                    requestedTrackList.add(new MSong(songs.get(i), playImage, pauseImage, this));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            currentPaneType = TYPE_CHART;
            currentPane = new MChart(requestedTrackList, "Le canzoni più ascoltate del momento", "chart.jpg",
                    mainSection.getWidth(), scene);
            mainSection.loadSection(currentPane);
        }
    }

    @FXML
    private void doLogOut(MouseEvent mouseEvent) throws IOException {
        BorderPane borderPane = (BorderPane) loginScene.getRoot();
        BorderPane thisBorderPane = (BorderPane) scene.getRoot();
        borderPane.setPrefWidth(thisBorderPane.getWidth());
        borderPane.setPrefHeight(thisBorderPane.getHeight());
        requestManager.logOut();
        stage.setScene(loginScene);
    }

    @FXML
    private void loadProfilePage(MouseEvent mouseEvent) {
        mainSection.loadSection(new MProfilePage(this));
    }

    @FXML
    private void loadHome(MouseEvent mouseEvent) {

        ArrayList<MArtistButton> martists = new ArrayList<>();
        ArrayList<MArtistButton> martists2 = new ArrayList<>();
        ArrayList<Artist> artists;
        ArrayList<Artist> artists2;
        try {
            artists = requestManager.getMostPlayedArtists(Integer.toString(loggedUser.getId()));
            artists = requestManager.getRecentPlayedArtists(Integer.toString(loggedUser.getId()));
            for (int i = 0; i < artists.size(); i++) {
                martists.add(new MArtistButton(artists.get(i).getNome(), artists.get(i).getId() + ".png"
                        , artists.get(i).getId(), this));
            }

            for (int i = 0; i < artists.size(); i++) {
                martists2.add(new MArtistButton(artists.get(i).getNome(), artists.get(i).getId() + ".png"
                        , artists.get(i).getId(), this));
            }

            mainSection.loadSection(new MHomePage(martists, martists2));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadGenre(String text, String genreId){
        mainSection.loadSection(new MGenreChoose(genreId, text,this));
    }

    public void setRequestManager(RequestManager requestManager, User loggedUser) {
        this.requestManager = requestManager;
        this.requestManager.setController(this);
        this.loggedUser = loggedUser;
        loadHome(null);
    }

    private void requestSong(Song song, boolean canIStream){
        try {
            requestManager.getSong(song, canIStream, loggedUser);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setPlayingSongImage(){
        playingAudioPicture.setImage(new Image(getClass().getResourceAsStream( ".." +
                File.separator + ".." + File.separator + ".." + File.separator + "res" + File.separator +
                "client" + File.separator + "images" + File.separator +
                "album" + File.separator + "image.png"), 100.0, 100.0,true,
                ImageView.SMOOTH_DEFAULT));
    }

    public void changePlaySong(Song song, MSong msong){

        if(currentTracklist != requestedTrackList) {
            currentTracklist = requestedTrackList;
            executingPane = currentPane;
            executingGenre = currentGenre;
            executingPaneType = currentPaneType;
            executingArtist = currentArtist;
            playingAudioAlbum.setText(song.getTitle());
            playingAudioArtist.setText(song.getArtist());
        }

        if(playingSong != song){

            try {
                requestManager.sendRequest("getSong");
                System.out.println("QUI");
            } catch (IOException e) {
                e.printStackTrace();
            }
            setAudioPause();

            inPlay = msong;
            playingSong = song;
            setAudioPlay(song, true);

        } else {

            setAudioPlay(song, false);
        }

    }

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
        profileController.setRequestManager(requestManager);
        profileController.setLoggedUser(loggedUser);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLoginScene(Scene loginScene) { this.loginScene = loginScene;}

    public void incrementPBarSecond(double percentage){
        progressBar.setSeconds(percentage);
    }

    public void setCurrentSongTime(String value){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                actualSongTime.setText(value);
            }
        });
    }

    public void setEndSongTime(String value){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                endSongTime.setText(value);
            }
        });
    }

    public void setAudioPause(){
        isPlaying = false;
        playAudioButton.setImage(playerPlayImage);

        if(playingSong != null)
            requestManager.Pause();

        if(inPlay != null)
            inPlay.setToPlay();
    }

    public void setAudioPlay(Song song, boolean canIStream){
        requestSong(song, canIStream);
        isPlaying = true;
        playAudioButton.setImage(playerPauseImage);
        if(inPlay != null)
            inPlay.setToPause();
    }

    @FXML
    private void playNextAudio(MouseEvent mouseEvent) {
        if(playingSong != null) {
            try {
                if(playingSong.getTracklistIndex() + 1 <= requestManager.getCurrentTrackList().size() - 1) {
                    System.out.println(playingSong.getTitle());
                    System.out.println(requestManager.getCurrentTrackList().size());
                    setAudioPause();
                    requestManager.sendRequest("getSong");
                    inPlay = currentTracklist.get(playingSong.getTracklistIndex() + 1);
                    setAudioPlay(requestManager.getCurrentTrackList().get(playingSong.getTracklistIndex() + 1), true);
                    playingSong = requestManager.getCurrentTrackList().get(playingSong.getTracklistIndex() + 1);
                    playingAudioAlbum.setText(playingSong.getTitle());
                    playingAudioArtist.setText(playingSong.getArtist());
                } else {
                    System.out.println(playingSong.getTitle());
                    System.out.println(requestManager.getCurrentTrackList().size());
                    setAudioPause();
                    requestManager.sendRequest("getSong");
                    inPlay = currentTracklist.get(0);
                    setAudioPlay(requestManager.getCurrentTrackList().get(0), true);
                    playingSong = requestManager.getCurrentTrackList().get(0);
                    playingAudioAlbum.setText(playingSong.getTitle());
                    playingAudioArtist.setText(playingSong.getArtist());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void playPreviousAudio(MouseEvent mouseEvent) {
        if(playingSong != null) {
            try {
                if(playingSong.getTracklistIndex() - 1 >= 0) {
                    setAudioPause();
                    requestManager.sendRequest("getSong");
                    inPlay = currentTracklist.get(playingSong.getTracklistIndex() - 1);
                    setAudioPlay(requestManager.getCurrentTrackList().get(playingSong.getTracklistIndex() - 1), true);
                    playingSong = requestManager.getCurrentTrackList().get(playingSong.getTracklistIndex() - 1);
                    playingAudioAlbum.setText(playingSong.getTitle());
                    playingAudioArtist.setText(playingSong.getArtist());
                } else {
                    setAudioPause();
                    requestManager.sendRequest("getSong");
                    inPlay = currentTracklist.get(requestManager.getCurrentTrackList().size() - 1);
                    setAudioPlay(requestManager.getCurrentTrackList().get(requestManager.getCurrentTrackList().size()
                            - 1), true);
                    playingSong = requestManager.getCurrentTrackList().get(requestManager.getCurrentTrackList().size()
                            - 1);
                    playingAudioAlbum.setText(playingSong.getTitle());
                    playingAudioArtist.setText(playingSong.getArtist());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void playOrPauseClick(MouseEvent mouseEvent) {
        if(playingSong != null) {
            if (isPlaying)
                setAudioPause();
            else
                setAudioPlay(playingSong, false);
        }
    }

    public void loadArtist(String artistId, String artistName){
        try {

            if(executingPaneType == TYPE_ARTIST && executingArtist == Integer.parseInt(artistId)){
                mainSection.loadSection(executingPane);
            } else {
                requestManager.getArtistImages(artistId);
                ArrayList<ArrayList<Song>> albums = requestManager.getArtistAlbums(artistId);
                ArrayList<ArrayList<Song>> singles = requestManager.getArtistSingles(artistId);
                ArrayList<MAlbum> mAlbums = new ArrayList<>();
                ArrayList<MAlbum> mSingles = new ArrayList<>();
                requestedTrackList = new ArrayList<>();
                for (int i = 0; i < albums.size(); i++) {
                    ArrayList<MSong> songs = new ArrayList<>();

                    for (int j = 0; j < albums.get(i).size(); j++) {
                        songs.add(new MSong(albums.get(i).get(j), playImage, pauseImage, this));
                    }

                    for (int j = 0; j < songs.size(); j++) {
                        requestedTrackList.add(songs.get(j));
                    }

                    mAlbums.add(new MAlbum(albums.get(i).get(0).getAlbum(),
                            albums.get(i).get(0).getYearReleaseDate(), songs));
                }

                for (int i = 0; i < singles.size(); i++) {
                    ArrayList<MSong> songs = new ArrayList<>();

                    for (int j = 0; j < singles.get(i).size(); j++) {
                        songs.add(new MSong(singles.get(i).get(j), playImage, pauseImage, this));
                    }

                    for (int j = 0; j < songs.size(); j++) {
                        requestedTrackList.add(songs.get(j));
                    }

                    mSingles.add(new MAlbum(singles.get(i).get(1).getAlbum(),
                            singles.get(i).get(1).getYearReleaseDate(), songs));
                }

                currentPaneType = TYPE_ARTIST;
                currentArtist = Integer.parseInt(artistId);
                currentPane = new MArtistPage(artistName, mainSection.getWidth(),
                        mAlbums, mSingles, scene);
                mainSection.loadSection(currentPane);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadGenreChoose(int genreChoose, String genre, String genreId){


        if(genreChoose == ARTIST){
            try {
                ArrayList<MArtistButton> artistButtons = new ArrayList<>();
                ArrayList<Artist> artists = requestManager.getArtistsList(genre);
                for (int i = 0; i < artists.size(); i++) {
                    artistButtons.add(new MArtistButton(artists.get(i).getNome(), artists.get(i).getId() + ".png"
                    , artists.get(i).getId(), this));
                }

                mainSection.loadSection(new MArtistsGrid(artistButtons));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } else if(genreChoose == SONGS){
            if(executingPaneType == TYPE_GENRE && genre.equals(executingGenre)) {
                mainSection.loadSection(executingPane);
            } else {

                requestedTrackList = new ArrayList<>();

                try {
                    ArrayList<Song> songs = requestManager.getTrackListForGenre(genre);
                    for (int i = 0; i < songs.size(); i++) {
                        requestedTrackList.add(new MSong(songs.get(i), playImage, pauseImage, this));
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                currentPaneType = TYPE_GENRE;
                currentGenre = genre;
                currentPane = new MGenreSongsList(requestedTrackList, "Brani " + genre + " in evidenza", genreId,
                        mainSection.getWidth(), scene);
                mainSection.loadSection(currentPane);
            }
        }
    }

    @FXML
    private void redirectArtist(MouseEvent mouseEvent) {
        /*ArrayList<MSong> songs = new ArrayList<>();
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
                songs.add(new MSong("-", playImage, pauseImage,songCoverImage, "Dani California",
                        "Red Hot Chili Peppers",
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
                "Red Hot Chili Peppers", mainSection.getWidth(), albums, singles, scene));*/
    }
}
