package musicary.model;

import org.tritonus.share.sampled.file.TAudioFileFormat;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class Song implements Serializable {

    String rootPath;

    AudioInputStream audioIn = null;
    AudioFileFormat audioFileFormat = null;
    Map properties = null;

    private int id;
    private String title;
    private int duration;
    private String artist;
    private String artistId;
    private String album;
    private String date;
    private String genre;
    private int views;
    private int track_number;
    private String cover;
    private int chartPosition;
    private int tracklistIndex;
    private int albumId;
    private int yearReleaseDate;


    public Song(){

        title = "";
        artist = "";
        album = "";
        date = "";
        genre = "";
        duration = 0;

    }

    public Song(File fileSong) throws IOException, UnsupportedAudioFileException {
        audioFileFormat = AudioSystem.getAudioFileFormat(fileSong);
        properties = ((TAudioFileFormat) audioFileFormat).properties();
        title = ((String) properties.get("title"));
        duration = ((Long) properties.get("duration")).intValue()/1000000;
        artist = ((String) properties.get("author"));
        album = ((String) properties.get("album"));
        date = ((String) properties.get("date"));
    }

    public int getId(){return id;}
    public int getDuration(){ return duration;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getAlbum(){return album;}
    public String getDate(){return date;}
    public String getGenre(){return genre;}
    public int getChartPosition() { return chartPosition; }
    public int getTracklistIndex() { return tracklistIndex; }
    public int getTrack_number() {
        return track_number;
    }
    public String getCover() {
        return cover;
    }
    public int getViews() {
        return views;
    }
    public int getAlbumId() { return albumId; }
    public int getYearReleaseDate() { return yearReleaseDate; }

    public String getArtistId() {
        return artistId;
    }

    public void setId(int id){this.id = id;}
    public void setDuration(int duration) {this.duration = duration;}
    public void setTitle(String title) {this.title = title;}
    public void setArtist(String artist) {this.artist = artist;}
    public void setAlbum(String album) {this.album = album;}
    public void setDate(String date) {this.date = date;}
    public void setGenre(String genre){this.genre = genre;}
    public void setCover(String filename) { this.cover = filename; }
    public void setViews(int views) { this.views = views; }
    public void setTrack_number(int track_number) { this.track_number = track_number; }
    public void setChartPosition(int chartPosition) { this.chartPosition = chartPosition; }
    public void setTracklistIndex(int tracklistIndex) { this.tracklistIndex = tracklistIndex; }
    public void setAlbumId(int albumId) { this.albumId = albumId; }
    public void setYearReleaseDate(int yearReleaseDate) { this.yearReleaseDate = yearReleaseDate; }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public AudioInputStream getAudioInput() throws IOException, UnsupportedAudioFileException {
        URI uri = null;
        try {
            uri = new URI(getClass().getResource(".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "client" +
                    File.separator + "songs" + File.separator + "fileToStream" + ".mp3").toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        rootPath = uri.getPath();
        File fileSong = new File(rootPath);
        System.out.println(fileSong.getAbsolutePath());
        audioIn = AudioSystem.getAudioInputStream(fileSong);
        return audioIn;
    }


    public boolean equals(Song right) throws IOException {

        return  this.id == right.getId();

    }


    @Override
    public String toString() {

        return  artist       + " " +
                title        + " " +
                duration     + " " +
                album        + " " +
                date         + " " +
                track_number + " " +
                views        + " "
                ;
    }
}
