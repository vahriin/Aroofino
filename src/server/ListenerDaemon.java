package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by vahriin on 3/20/17.
 */
public class ListenerDaemon implements Runnable {
    public ListenerDaemon(int port) throws IOException {
        listenPort = new ServerSocket(port);
    }

    public void run() {
        while (true) {
            Socket clientSocket;
            try {
                clientSocket = listenPort.accept();
                Thread currentClientThread = new Thread(new ClientThread(clientSocket));
                currentClientThread.start();
            } catch (IOException ex) {
                System.err.println("ListenerDaemonEx: " + ex.getMessage());
            }
        }
    }

    private ServerSocket listenPort;
}
