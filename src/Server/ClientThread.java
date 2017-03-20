package Server;

import java.io.*;
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
            DataInputStream dataInput = new DataInputStream(input);
            DataOutputStream dataOutput = new DataOutputStream(output);

            String request = dataInput.readUTF();
            //processing request
            dataOutput.writeUTF(response);


            clientSocket.close();
        } catch (IOException ex) {
            System.err.println("ClientThreadEx: No client available: " + ex.getMessage());
        }
    }

    private Socket clientSocket;
}
