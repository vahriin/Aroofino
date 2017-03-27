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
    public ThreadPool (int port, Weather currentData)
            throws IOException, ParserConfigurationException, TransformerConfigurationException {
        threadPool = Executors.newCachedThreadPool();
        listenedPort = new ServerSocket(port);
        data = currentData;
        parser = new ServerParser();
    }

    public void run() {
        Socket client;
        while (true) {
            try {
                client = listenedPort.accept();
                threadPool.execute(new ClientThread(client, data, parser));
            } catch (IOException ex) {
                threadPool.shutdown();
                System.err.println("ListenerDaemonEx: " + ex.getMessage());
            }
        }
    }

    private ServerParser parser;
    private Weather data;
    private ServerSocket listenedPort;
    private final ExecutorService threadPool;
}
