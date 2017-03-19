package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by vahriin on 3/6/17.
 */
public class ClientThread implements Runnable {
    public ClientThread(Socket sock) {
        clientSocket = sock;
    }

    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();



            clientSocket.close();
        } catch (IOException ex) {
            System.err.println("No client available: " + ex.getMessage());
        }
    }

    private Socket clientSocket;
}
