package musicary.model.server;

import musicary.model.Artist;
import musicary.model.Genre;
import musicary.model.Song;
import musicary.model.User;
import sun.security.provider.MD5;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


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
            sendImage(uri.getPath());
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
            sendImage(uri.getPath());
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

            sendImage(uri.getPath());

            getRequest();

            uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "server" +
                    File.separator + "images" + File.separator + "artists" + File.separator + "cover" +
                    File.separator + artistId + ".png").toString());

            sendImage(uri.getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendGenreImages(String genreId){
        try {
            System.out.println(genreId);
            URI uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "server" +
                    File.separator + "images" + File.separator + "genres" + File.separator + "mins" + File.separator
                    + "image.png").toString());

            String path = uri.getPath().substring(0, uri.getPath().length() - 9);
            path += genreId + ".png";
            sendImage(path);

            getRequest();

            uri = new URI(getClass().getResource(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator + "server" +
                    File.separator + "images" + File.separator + "genres" + File.separator + "covers" + File.separator
                    + "image.png").toString());

            path = uri.getPath().substring(0, uri.getPath().length() - 9);
            path += genreId + ".png";

            sendImage(path);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendImage(String path){

        try {
            File f = new File(path);
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
            String userId = getRequest();
            String nameSong = getRequest();
            listenSong(nameSong, userId);
            sendAlbumCover();
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




    public void sendMostPlayedArtists(String userId) throws SQLException, IOException {
        PreparedStatement statement = db.statement("SELECT profilo_artista.nome, profilo_artista.id FROM " +
                "profilo_artista, numVisualizzazioniPerUtente " +
                "WHERE numVisualizzazioniPerUtente.idUtente = ? AND numVisualizzazioniPerUtente.idArtista = profilo_artista.id " +
                "ORDER BY numVisualizzazioniPerUtente.numVisual DESC LIMIT 10");
        statement.setString(1,userId.toLowerCase());
        ResultSet reslts = statement.executeQuery();
        artists.clear();
        while(reslts.next()){

            Artist artist = new Artist();
            artist.setNome(reslts.getString("nome"));
            artist.setId(reslts.getInt("id"));
            artists.add(artist);
        }

        objectOut.writeObject(artists.clone());
        objectOut.flush();
    }

    public void sendRecentPlayedArtists(String userId) throws SQLException, IOException {
        PreparedStatement statement = db.statement("SELECT profilo_artista.id, profilo_artista. nome " +
                "FROM profilo_artista " +
                "WHERE profilo_artista.id IN (SELECT DISTINCT(profilo_artista.id) " +
                "FROM profilo_artista, ascolti, brano " +
                "WHERE ascolti.id_utente = ? AND ascolti.id_brano = brano.id AND brano.artista = profilo_artista.id " +
                "ORDER BY ascolti.istante DESC) " +
                "LIMIT 10");
        statement.setString(1,userId.toLowerCase());
        ResultSet reslts = statement.executeQuery();
        artists.clear();
        while(reslts.next()){

            Artist artist = new Artist();
            artist.setNome(reslts.getString("nome"));
            artist.setId(reslts.getInt("id"));
            artists.add(artist);
        }

        objectOut.writeObject(artists.clone());
        objectOut.flush();
    }

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

    public void sendGenres() throws SQLException, IOException {

        PreparedStatement statement = db.statement("SELECT id, nome_genere FROM genere");
        ResultSet reslts = statement.executeQuery();
        ArrayList<Genre> genres = new ArrayList<>();
        while(reslts.next()){

            Genre genre = new Genre();
            genre.setId(reslts.getInt("id"));
            genre.setName(reslts.getString("nome_genere"));
            genres.add(genre);
        }

        objectOut.writeObject(genres);
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

    public void listenSong(String songId, String userId) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        PreparedStatement statement = db.statement("INSERT INTO ascolti VALUES(?, ?, ?)");
        try {
            statement.setString(1,songId);
            statement.setString(2,userId);
            statement.setTimestamp(3,timestamp);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Server: errore nella sinstassi della query inserita per l'inserimento di un ascolto");
        }
    }


    public void Login() throws SQLException, IOException, ClassNotFoundException{

        User user = (User) objectIn.readObject();
        PreparedStatement statement = db.statement("SELECT * FROM utente WHERE utente.username = ? AND utente.password = ?");

        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        ResultSet results = statement.executeQuery();

        if(results.next()){
            sendService("logged");
            statement = db.statement("SELECT utente.id FROM utente WHERE utente.username = ?");
            statement.setString(1, user.getUsername());
            results = statement.executeQuery();
            results.next();
            user.setId(results.getInt("id"));
            objectOut.writeObject(user);
            objectOut.flush();

        } else {
            sendService("not logged");
        }

    }

    public void changePassword() throws IOException, ClassNotFoundException, SQLException {
        User user = (User) objectIn.readObject();
        PreparedStatement statement = db.statement("UPDATE utente SET password = ? WHERE username = ?");

        statement.setString(1, user.getPassword());
        statement.setString(2, user.getUsername());

        int count = statement.executeUpdate();

        if(count > 0){
            sendService("changed");
        } else {
            sendService("not changed");
        }

    }


    public void SignUp() throws IOException, ClassNotFoundException {
        User user = (User) objectIn.readObject();
        PreparedStatement statement = db.statement("INSERT INTO utente (username, password, email, datanascita)" +
                " VALUES (?,?,?,?)");

        try {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setDate(4, user.getBornDate());
        } catch (SQLException e) {
            System.out.println("Server: I dati inseriti nello statement non corrispondono alle richiste");
        }


        try {
            statement.execute();
            sendService("registered");
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e){
            sendService("alreadyExistingFields");
        } catch (SQLException e) {
            System.out.println("Server: Errore di sintassi nella query di registrazione di un nuovo utente");
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
                } else if (req.equals("getforGenre")){
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
                } else if(req.equals("getGenres")){
                    sendGenres();
                } else if(req.equals("getGenresImages")){
                    sendGenreImages(getRequest());
                } else if(req.equals("getMostPlayedArtists")){
                    sendMostPlayedArtists(getRequest());
                } else if(req.equals("getRecentPlayedArtists")){
                    sendRecentPlayedArtists(getRequest());
                } else if(req.equals("changePassword")){
                    changePassword();
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
