package musicary.model.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Runnable {


    private ServerSocket server = null;
    private Socket socket = null;
    private int porta = 8000;
    private DBserver db;


    public Server() {
        db = new DBserver();
        db.connect();
    }


    public void start() throws IOException {
        server = new ServerSocket(porta);
        Thread t = new Thread(this);
        t.start();
    }


    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("in attesa di una connesione");
                socket = server.accept();
                System.out.println("connessione stabilita con successo !");
                ServiceManager serviceManager = new ServiceManager(socket, db);
                Thread t = new Thread(serviceManager);
                t.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}



