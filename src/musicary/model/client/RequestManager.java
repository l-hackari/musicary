package musicary.model.client;

import musicary.controllers.MainController;
import musicary.model.Artist;
import musicary.model.Song;
import musicary.model.User;
import musicary.model.Genre;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;


public class RequestManager implements Runnable {


    private Socket socket;

    private ArrayList<Song> currentTrackList;
    private ArrayList<Song> requestedTracklist;
    private ArrayList<Artist> artistsList = new ArrayList<Artist>();

    PlayerAudio player;
    private DataInputStream in;
    private DataOutputStream out;

    private ObjectInputStream objectIn;
    private ObjectOutputStream objctOut;
    private MainController controller;

    private String comando = "";

    private int num = 0;
    int track_count;

    private Scanner s = new Scanner(System.in);
    private Scanner m = new Scanner((System.in));

    private Song currentSong;



    public RequestManager(Socket socket) throws IOException {
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        objectIn = new ObjectInputStream(socket.getInputStream());
        objctOut = new ObjectOutputStream(socket.getOutputStream());
        currentTrackList = new ArrayList<>();
        requestedTracklist = new ArrayList<>();
    }



    public String getRequest() throws IOException {
        return in.readLine();
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public ArrayList<ArrayList<Song>> getArtistAlbums(String artistId) throws IOException, ClassNotFoundException {
        sendRequest("getArtistAlbums");
        sendRequest(artistId);
        ArrayList<ArrayList<Song>> albums = (ArrayList<ArrayList<Song>>)objectIn.readObject();
        requestedTracklist = new ArrayList<>();
        track_count = 0;
        for(int i = 0; i < albums.size(); i++){

            for(int j = 0; j < albums.get(i).size(); j++) {
                albums.get(i).get(j).setTracklistIndex(track_count);
                requestedTracklist.add(albums.get(i).get(j));
                track_count++;
            }

        }

        return albums;
    }

    public ArrayList<ArrayList<Song>> getArtistSingles(String artistId) throws IOException, ClassNotFoundException {
        sendRequest("getArtistSingles");
        sendRequest(artistId);
        ArrayList<ArrayList<Song>> singles = (ArrayList<ArrayList<Song>>)objectIn.readObject();
        for(int i = 0; i < singles.size(); i++){

            for(int j = 0; j < singles.get(i).size(); j++) {
                singles.get(i).get(j).setTracklistIndex(track_count);
                requestedTracklist.add(singles.get(i).get(j));
                track_count++;
            }

        }

        return singles;
    }

    public ArrayList<Artist> getArtistsList(String genre) throws IOException, ClassNotFoundException {
        sendRequest("getArtists");
        sendRequest(genre);
        artistsList = (ArrayList<Artist>)objectIn.readObject();
        for (int i = 0; i < artistsList.size(); i++) {
            sendRequest("getArtistProfileImage");
            getArtistImage(Integer.toString(artistsList.get(i).getId()));
        }
        return artistsList;
    }

    public ArrayList<Genre> getGenres() throws IOException, ClassNotFoundException {
        sendRequest("getGenres");
        ArrayList<Genre> genres = (ArrayList<Genre>)objectIn.readObject();
        for (int i = 0; i < genres.size(); i++) {
            sendRequest("getGenresImages");
            getGenreImages(Integer.toString((genres.get(i).getId())));
        }

        return genres;
    }


    public ArrayList<Song> getTrackListForGenre(String genre) throws IOException, ClassNotFoundException {
        sendRequest("getforGenre");
        sendRequest(genre);
        requestedTracklist = (ArrayList<Song>) objectIn.readObject();
        return requestedTracklist;
    }

    public ArrayList<Song> getGlobalChart() throws IOException, ClassNotFoundException {
        sendRequest("getGlobalChart");
        requestedTracklist = (ArrayList<Song>) objectIn.readObject();
        return requestedTracklist;
    }

    public ArrayList<Song> getTrackListForArtist(String artist) throws IOException, ClassNotFoundException {

        sendRequest(artist);
        requestedTracklist = (ArrayList<Song>) objectIn.readObject();
        return requestedTracklist;

    }


    public void PlayBack() {
        Thread playBack = new Thread(new Runnable() {
            @Override
            public void run() {
                player.play();
            }

        });

        playBack.start();
    }

    public void Pause() {

        Thread pause = new Thread(new Runnable() {
            @Override
            public void run() {
                player.pause();
            }
        });

        pause.start();

    }

    public ArrayList<Song> getCurrentTrackList(){ return currentTrackList;}

    public void getAlbumCover(String albumId){
        try {
            URI uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                        ".." + File.separator + ".." + File.separator + "res" + File.separator + "client" +
                        File.separator + "images" + File.separator + "album" + File.separator
                        + "image.png").toString());
            sendRequest(albumId);
            getImage(uri.getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getArtistImage(String artistId){
        try {
            URI uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "client" +
                    File.separator + "images" + File.separator + "artists" + File.separator
                    + "image.png").toString());
            sendRequest(artistId);
            String path = uri.getPath().substring(0, uri.getPath().length() - 9);
            path += artistId + ".png";
            getImage(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getGenreImages(String genreId){
        try {
            sendRequest(genreId);
            URI uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "client" +
                    File.separator + "images" + File.separator + "genres" + File.separator + "mins" + File.separator
                    + "image.png").toString());
            String path = uri.getPath().substring(0, uri.getPath().length() - 9);
            path += genreId + ".png";
            getImage(path);

            sendRequest("nextImage");

            uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "client" +
                    File.separator + "images" + File.separator + "genres" + File.separator + "covers" + File.separator
                    + "image.png").toString());

            path = uri.getPath().substring(0, uri.getPath().length() - 9);
            path += genreId + ".png";
            getImage(path);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getArtistImages(String artistId){
        try {
            sendRequest("getArtistImages");
            sendRequest(artistId);
            URI uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "client" +
                    File.separator + "images" + File.separator + "singleArtist" + File.separator
                    + "profile.png").toString());

            getImage(uri.getPath());

            sendRequest("nextImage");
            uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "client" +
                    File.separator + "images" + File.separator + "singleArtist" + File.separator
                    + "cover.png").toString());

            getImage(uri.getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getImage(String path){

        try{

            File fileImage = new File(path);
            FileOutputStream fos = new FileOutputStream(fileImage);
            int bufSize = 1024;
            byte [] bytes = new byte[1024];
            int count;
            while((count = in.read(bytes)) > 0){
                fos.write(bytes, 0, count);
                if(count < 1024){
                    break;
                }
            }

            fos.flush();


            controller.setPlayingSongImage();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void getSong(Song songToStream, boolean canIStream) throws IOException, UnsupportedAudioFileException, InterruptedException {

        if(currentTrackList != requestedTracklist){
            if(requestedTracklist.contains(songToStream)) {
                currentTrackList = requestedTracklist;
            }
        }

        Thread getSongTread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (canIStream) {
                        getAlbumCover(Integer.toString(songToStream.getAlbumId()));
                        sendRequest(Integer.toString(songToStream.getId()));
                        int bufSize = 1024 * 16;
                        byte[] bytes = new byte[bufSize];

                        URI uri = null;
                        try {
                            uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "client" +
                                    File.separator + "songs" + File.separator + "fileToStream" + ".mp3").toString());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }


                        player = new PlayerAudio(songToStream);
                        player.setController(controller);
                        boolean play = false;

                        File songToStream = new File(uri.getPath());
                        FileOutputStream fos = new FileOutputStream(songToStream);
                        int count;
                        while((count = in.read(bytes)) > 0){
                            fos.write(bytes, 0, count);
                            if(count < 1024*16)
                                break;
                            if(!play){
                                PlayBack();
                                play = true;
                            }
                        }

                        fos.flush();
                        fos.close();


                    } else {
                        System.out.println("canzone gia riprodotta quindi non la scarico ");
                        while(player.isPlaying()) {
                            //sleep
                        }
                        
                        PlayBack();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        getSongTread.start();

    }

   /* public void getSong(Song songToStream, boolean canIStream) throws IOException, UnsupportedAudioFileException, InterruptedException {

        if(currentTrackList != requestedTracklist){
            if(requestedTracklist.contains(songToStream)) {
                currentTrackList = requestedTracklist;
                System.out.println("SCAMBIATO");
            }
        }

        Thread getSongTread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (canIStream) {
                        getImage(Integer.toString(songToStream.getAlbumId()));
                        sendRequest(Integer.toString(songToStream.getId()));
                        System.out.println("TO MAMMA");
                        int size = Integer.parseInt(getRequest());
                        int bufSize = 1024 * 16;
                        int remainder;
                        int readed = 1;
                        int currentlyRead;
                        byte[] bytes = new byte[bufSize];

                        URI uri = null;
                        try {
                            uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "client" +
                                    File.separator + "songs" + File.separator + "fileToStream" + ".mp3").toString());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        System.out.println(uri);
                        File streamingFile = new File(uri.getPath());
                        FileOutputStream fos = new FileOutputStream(streamingFile);
                        BufferedOutputStream bos = new BufferedOutputStream(fos);

                        player = new PlayerAudio(songToStream);
                        player.setController(controller);
                        PlayBack();

                        while ((currentlyRead = in.read(bytes)) > 0) {

                            readed += currentlyRead;
                            remainder = size - readed;
                            if (remainder < bufSize) bytes = new byte[remainder + 1];
                            bos.write(bytes);
                            System.out.println("ho scritto " + readed);

                        }

                        bos.flush();
                        System.out.println("File  downloaded (" + readed + " bytes read)");
                        bos.close();
                        fos.close();

                    } else {
                        System.out.println("canzone gia riprodotta quindi non la scarico ");
                        PlayBack();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        getSongTread.start();

    }*/

    public boolean Login(User user) throws IOException {

        objctOut.writeObject(user);
        objctOut.flush();

        if(getRequest().equals("logged")) return true;
        else return false;

    }

    public boolean SignUp(User user) throws IOException {

        objctOut.writeObject(user);
        objctOut.flush();

        String req = getRequest();
        System.out.println(req);
        if(req.equals("registered")) return true;
        else return false;
    }


    public void sendRequest(String request) throws IOException {
        try {
            out.writeBytes(request + "\r\n");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {

            System.out.println("Inserisci comando...");
            comando = s.nextLine();

            if (comando.equals("send")) {

            } else if (comando.equals("pause")) {
                Pause();
            } else if (comando.equals("play")) {
                PlayBack();
            } else if (comando.equals("login")) {
                try {
                    sendRequest("login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                User user = new User("Davide", "davide99");
                try {
                    if (Login(user)) {
                        System.out.println("logged");
                    } else {
                        System.out.println("not logged");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                else if(comando.equals("signup")){
                    try {
                        sendRequest("signup");
                        User user = new User("germano", "mosconi", "macheeohhh@dio.com");
                        SignUp(user);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(comando.equals("getArtist")){
                try {
                    sendRequest("getArtist");
                    getArtistsList(".");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
             else if (comando.equals("close")) {
                URI uri = null;
                try {
                    uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                            ".." + File.separator + ".." + File.separator + "res" + File.separator + "client" +
                            File.separator + "songs" + File.separator + "fileToStream" + ".mp3").toString());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                File fileToDelete = new File(uri.getPath());
                fileToDelete.delete();
                try {

                    out.close();
                    objectIn.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
