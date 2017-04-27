package server;

import formats.Weather;
import parsers.ServerParser;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by vahriin on 3/6/17.
 */
class ClientThread implements Runnable {
    ClientThread(Socket sock, Weather currentWeatherLink) {
        clientSocket = sock;
        weatherLink = currentWeatherLink;
    }

    public void run() {
        try {
            DataInputStream dataInput = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutput = new DataOutputStream(clientSocket.getOutputStream());

            ArrayList<String> request = ServerParser.parse(dataInput.readUTF());
            dataOutput.writeUTF(ServerParser.createResponse(weatherLink.getValues(request)));

            clientSocket.close();
        } catch (IOException ex) {
            System.err.println("ClientThreadEx: No client available: " + ex.getMessage());
        }
    }

    private Weather weatherLink;
    private final Socket clientSocket;
}
