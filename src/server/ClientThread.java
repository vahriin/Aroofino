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
    ClientThread(Socket sock, Weather weatherLink, ServerParser parserLink) {
        clientSocket = sock;
        weather = weatherLink;
        parser = parserLink;
    }

    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            DataInputStream dataInput = new DataInputStream(input);
            DataOutputStream dataOutput = new DataOutputStream(output);

            ArrayList<String> requestArray = parser.parse(dataInput.readUTF());
            dataOutput.writeUTF(parser.createResponse(weather.getValues(requestArray)));

            clientSocket.close();
        } catch (IOException ex) {
            System.err.println("ClientThreadEx: No client available: " + ex.getMessage());
        }
    }

    private final ServerParser parser;
    private Weather weather;
    private final Socket clientSocket;
}
