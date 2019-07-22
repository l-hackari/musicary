package musicary.model.server;

import musicary.model.Artist;
import musicary.model.Song;
import musicary.model.User;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ServiceManager implements Runnable {


    private DataInputStream in;
    private DataOutputStream out;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;

    private Socket socket;
    private DBserver db;

    private ArrayList<Song> songs = new ArrayList<Song>();
    private ArrayList<Artist> artists = new ArrayList<Artist>();

    public ServiceManager(Socket socket, DBserver db) throws InterruptedException, IOException, ClassNotFoundException {
        this.socket = socket;
        this.db = db;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectIn = new ObjectInputStream(socket.getInputStream());
    }

    public String getRequest() throws IOException {
       return in.readLine();
    }




    public String sendService(String service) throws IOException {
        try {
            out.writeBytes(service + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }

    public void sendAlbumCover(){

        String albumId= null;
        try {
            albumId = getRequest();
            URI uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "server" +
                    File.separator + "images" + File.separator + "album" + File.separator + albumId +
                    ".png").toString());
            sendImage(uri);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void sendArtistProfileImage(){
        String artistId = null;
        try {
            artistId = getRequest();
            URI uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "server" +
                    File.separator + "images" + File.separator + "artists" + File.separator + "profile" +
                    File.separator + artistId + ".png").toString());
            sendImage(uri);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void sendArtistImages(String artistId){
        try {
            URI uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "server" +
                    File.separator + "images" + File.separator + "artists" + File.separator + "profile" +
                    File.separator + artistId + ".png").toString());

            sendImage(uri);

            getRequest();

            uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "server" +
                    File.separator + "images" + File.separator + "artists" + File.separator + "cover" +
                    File.separator + artistId + ".png").toString());

            sendImage(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendImage(URI uri){

        try {
            File f = new File(uri.getPath());
            FileInputStream file = new FileInputStream(f);
            byte [] bytes = new byte [(int) f.length()];
            file.read(bytes);
            out.write(bytes);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendSong() throws IOException {
        try {

            sendAlbumCover();
            System.out.println("in attesa di un nome per la canzone");
            String nameSong = getRequest();
            System.out.println(nameSong);
            URI uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "server" +
                    File.separator + "songs" + File.separator + nameSong + ".mp3").toString());

            File songToSend = new File(uri.getPath());
            FileInputStream file = new FileInputStream(songToSend);
            byte [] bytes = new byte [(int) songToSend.length()];
            file.read(bytes);
            out.write(bytes);
            out.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }

    //VECCHIA VERSIONE
    /*public void sendSong() throws IOException {
        try {
            sendImage();
            System.out.println("in attesa di un nome per la canzone");
            String nameSong = getRequest();
            System.out.println(nameSong);
            URI uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "server" +
                    File.separator + "songs" + File.separator + nameSong + ".mp3").toString());
            File fileToStream = new File(uri.getPath());
            long size = fileToStream.length();
            System.out.println(size);
            String sizeToString = Long.toString(size);
            sendService(sizeToString);
            byte[] bytes = new byte[(int) size];
            FileInputStream fis = new FileInputStream(fileToStream);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(bytes);
            out.write(bytes);
            System.out.println("here");
            out.flush();
            fis.close();
            bis.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }*/

    public void sendArtistList(String genre) throws SQLException, IOException {

            PreparedStatement statement = db.statement("SELECT profilo_" +
                    "artista.nome, profilo_artista.id, profilo_artista.foto_profilo FROM profilo_artista, " +
                    "numVisualizzazioniArtisti WHERE profilo_artista.id = numVisualizzazioniArtisti.idArtista " +
                    "AND numVisualizzazioniArtisti.genereNome = ? " +
                    "ORDER BY numVisualizzazioniArtisti.numVisualiz DESC LIMIT 20");
            statement.setString(1,genre.toLowerCase());
            ResultSet reslts = statement.executeQuery();
            artists.clear();
            while(reslts.next()){

                Artist artist = new Artist();
                artist.setNome(reslts.getString("nome"));
                artist.setFotoprofilo(reslts.getString("foto_profilo"));
                artist.setId(reslts.getInt("id"));
                artists.add(artist);
            }

        objectOut.writeObject(artists.clone());
        objectOut.flush();

    }

    public void sendAlbumSongs(String albumId, ArrayList<ArrayList<Song>> albums, int index) throws SQLException {
        PreparedStatement statement = db.statement("SELECT brano.id, brano.titolo, brano.durata, " +
                "album.id AS album_id, album.anno_rilascio, album.nome, profilo_artista.nome AS nome_artista, brano.visualizzazioni, brano.track_number " +
                "FROM brano, profilo_artista, album WHERE brano.album = album.id AND brano.artista = " +
                        "profilo_artista.id AND "
                + "album.id = ? ORDER BY brano.track_number DESC");
        statement.setString(1,albumId);
        ResultSet results = statement.executeQuery();
        albums.add(new ArrayList<Song>());
        for (int i = 0; results.next(); i++) {
            Song song = new Song();
            song.setId(results.getInt("id"));
            song.setTitle(results.getString("titolo"));
            song.setArtist(results.getString("nome_artista"));
            song.setAlbumId(results.getInt("album_id"));
            song.setAlbum(results.getString("nome"));
            song.setDuration(results.getInt("durata"));
            song.setViews(results.getInt("visualizzazioni"));
            song.setTrack_number(results.getInt("track_number"));
            song.setYearReleaseDate(results.getInt("anno_rilascio"));
            albums.get(index).add(song);
        }
    }

    public void sendArtistAlbums(String artistId) throws SQLException, IOException {
        PreparedStatement statement = db.statement("SELECT brano.album " +
                "FROM brano, album " +
                "WHERE brano.album = album.id AND album.singolo = 0 AND brano.artista = ? " +
                "GROUP BY brano.album");
        statement.setString(1, artistId);
        ResultSet results = statement.executeQuery();
        ArrayList<ArrayList<Song>> albums = new ArrayList<ArrayList<Song>>();
        for (int i = 0; results.next(); i++) {
            sendAlbumSongs(results.getString("album"), albums, i);
        }

        objectOut.writeObject(albums);
        objectOut.flush();
    }

    public void sendArtistSingles(String artistId) throws SQLException, IOException {
        PreparedStatement statement = db.statement("SELECT brano.album " +
                "FROM brano, album " +
                "WHERE brano.album = album.id AND album.singolo = 1 AND brano.artista = ? " +
                "GROUP BY brano.album");
        statement.setString(1,artistId);
        ResultSet results = statement.executeQuery();
        ArrayList<ArrayList<Song>> singles = new ArrayList<ArrayList<Song>>();
        for (int i = 0; results.next(); i++) {
            sendAlbumSongs(results.getString("album"), singles, i);
        }

        System.out.println(singles.size());

        objectOut.writeObject(singles);
        objectOut.flush();

    }

    public void sendTrackListForGenre(String genre) throws SQLException, IOException {

        PreparedStatement statement = db.statement("SELECT brano.id, brano.titolo, brano.durata, " +
                "profilo_artista.nome AS nome_artista, " +
                "brano.visualizzazioni, album.id AS album_id, " +
                "brano.track_number, album.copertina, album.nome, genere.nome_genere " +
                "FROM profilo_artista, brano,genere,album,numVisualizzazioniSett " +
                "WHERE profilo_artista.id = brano.artista AND brano.id = numVisualizzazioniSett.idbrano AND " +
                "album.id = brano.album AND brano.genere = genere.id " +
                "AND genere.nome_genere = ? " +
                "ORDER BY numVisualizzazioniSett.numVisual DESC " +
                "LIMIT 20");
        statement.setString(1,genre.toLowerCase());
        ResultSet results = statement.executeQuery();
        songs.clear();
        for (int i = 0; results.next(); i++) {
            Song song = new Song();
            song.setId(results.getInt("id"));
            song.setTracklistIndex(i);
            song.setTitle(results.getString("titolo"));
            song.setArtist(results.getString("nome_artista"));
            song.setAlbumId(results.getInt("album_id"));
            song.setAlbum(results.getString("nome"));
            song.setDuration(results.getInt("durata"));
            song.setGenre(results.getString("nome_genere"));
            song.setViews(results.getInt("visualizzazioni"));
            song.setTrack_number(results.getInt("track_number"));
            song.setCover(results.getString("copertina"));
            songs.add(song);
        }

        objectOut.writeObject(songs.clone());
        objectOut.flush();
    }

    public void sendGlobalChart() throws SQLException, IOException {

        PreparedStatement statement = db.statement("SELECT brano.id, brano.titolo, brano.durata, " +
                "profilo_artista.nome AS nome_artista, " +
                "brano.visualizzazioni, album.id AS album_id, " +
                "brano.track_number, album.copertina, album.nome, genere.nome_genere " +
                "FROM profilo_artista, brano,genere,album,numVisualizzazioniSett " +
                "WHERE profilo_artista.id = brano.artista AND brano.id = numVisualizzazioniSett.idbrano AND " +
                "album.id = brano.album AND brano.genere = genere.id " +
                "ORDER BY numVisualizzazioniSett.numVisual DESC " +
                "LIMIT 20");
        ResultSet results = statement.executeQuery();
        songs.clear();
        for (int i = 0; results.next(); i++) {
            Song song = new Song();
            song.setId(results.getInt("id"));
            song.setTracklistIndex(i);
            song.setTitle(results.getString("titolo"));
            song.setArtist(results.getString("nome_artista"));
            song.setAlbumId(results.getInt("album_id"));
            song.setAlbum(results.getString("nome"));
            song.setDuration(results.getInt("durata"));
            song.setGenre(results.getString("nome_genere"));
            song.setViews(results.getInt("visualizzazioni"));
            song.setTrack_number(results.getInt("track_number"));
            song.setCover(results.getString("copertina"));
            songs.add(song);
        }

        objectOut.writeObject(songs.clone());
        objectOut.flush();
    }

    public void sendTrackListForArtist(String artist) throws SQLException, IOException {

        PreparedStatement statement = db.statement("SELECT * FROM brano,profilo_artista WHERE brano.artista = profilo_artista.id AND nome = ?");
        statement.setString(1, artist);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            Song song = new Song();
            song.setId(results.getInt("id"));
            song.setTitle(results.getString("titolo"));
            song.setArtist(results.getString("artista"));
            song.setAlbum(results.getString("nome"));
            song.setDuration(results.getInt("durata"));
            song.setGenre(results.getString("genere"));
            songs.add(song);
        }

        objectOut.writeObject(songs);
        objectOut.flush();

    }


    public void Login() throws SQLException, IOException, ClassNotFoundException, InterruptedException {

        User user = (User) objectIn.readObject();
        PreparedStatement statement = db.statement("SELECT * FROM utente WHERE utente.username = ? AND utente.password = ?");
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        ResultSet results = statement.executeQuery();
        if(results.next()) sendService("logged");
        else sendService("not logged");

    }


    public void SignUp() throws IOException, ClassNotFoundException {
        User user = (User) objectIn.readObject();
        PreparedStatement statement = db.statement("INSERT INTO utente (username, password, email) VALUES (?,?,?)");
        try {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            statement.execute();
            sendService("registered");
        }
        catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e){
            sendService("utente già esistente");
        }
        catch (SQLException e) {
            System.out.println("errore nell'invio della query");
            e.printStackTrace();
        }


    }

    @Override
    public void run() {

        while (true) {

            try {
                String req = getRequest();
                System.out.println(req);
                if (req.equals("getSong")) {
                    sendSong();
                    System.out.println("l'ho inivata");
                } else if (req.equals("message")) {
                    sendService("ciao sono il server");
                    System.out.println("inivato");
                } else if (req.equals("login")) {
                    Login();
                }
                else if(req.equals("signup")){
                    System.out.println("here");
                    SignUp();
                } else if(req.equals("getArtists")) {
                    sendArtistList(getRequest());
                }

                else if (req.equals("getforGenre")){

                    sendTrackListForGenre(getRequest());

                } else if(req.equals("getArtistProfileImage")){
                    sendArtistProfileImage();
                } else if(req.equals("getArtistImages")) {
                    sendArtistImages(getRequest());
                } else if(req.equals("getArtistAlbums")){
                    sendArtistAlbums(getRequest());
                } else if(req.equals("getArtistSingles")){
                    sendArtistSingles(getRequest());
                } else if(req.equals("getGlobalChart")){
                    sendGlobalChart();
                }
            }

             catch (Exception e) {
                 System.err.println("La Madonna ha detto");
                e.printStackTrace();
                 try {
                     System.out.println(objectIn.available());
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }

                 try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }

    }
}
