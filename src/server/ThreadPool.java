package server;

import formats.Weather;
import parsers.ServerParser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vahriin on 3/26/17.
 */
public class ThreadPool implements Runnable {
    public ThreadPool (int port, Weather currentWeatherLink)
            throws IOException, ParserConfigurationException, TransformerConfigurationException {
        threadPool = Executors.newCachedThreadPool();
        listenedPort = new ServerSocket(port);
        weatherLink = currentWeatherLink;
    }

    public void run() {
        while (true) {
            try {
                Socket client = listenedPort.accept();
                threadPool.execute(new ClientThread(client, weatherLink));
            } catch (IOException ex) {
                threadPool.shutdown();
                System.err.println("ListenerDaemonEx: " + ex.getMessage());
            }
        }
    }

    private Weather weatherLink;
    private ServerSocket listenedPort;
    private final ExecutorService threadPool;
}
