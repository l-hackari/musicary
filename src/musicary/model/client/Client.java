package musicary.model.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private Socket client = null;
    private int porta = 8000;
    private RequestManager requestManager;


    public Socket connect() throws IOException {

        Socket clientSocket = new Socket(InetAddress.getLocalHost(), porta);
        requestManager = new RequestManager(clientSocket);
        Thread ThreadrequestManager = new Thread(requestManager);
        ThreadrequestManager.start();

        return client;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }
}