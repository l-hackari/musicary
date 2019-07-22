package musicary.model.client;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


import static java.lang.System.*;

public class Client {

    private Socket client = null;
    private int porta = 8000;
    private RequestManager requestManager;


    public Socket connect() throws UnsupportedAudioFileException {

        try {

            Socket clientSocket = new Socket(InetAddress.getLocalHost(), porta);
            requestManager = new RequestManager(clientSocket);
            Thread ThreadrequestManager = new Thread(requestManager);
            ThreadrequestManager.start();

        } catch (UnknownHostException e) {

            err.println("host sconosciuto");

        } catch (IOException e) {

            e.printStackTrace();

        }

        return client;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }
}