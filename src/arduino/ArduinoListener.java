package arduino;

import gnu.io.*;
import userexcept.CorruptedDataException;

import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.io.IOException;

import java.util.TooManyListenersException;

/**
 * Created by vahriin on 2/18/17.
 */
class ArduinoListener extends Thread {
    private static final int TIME_OUT = 2000;

    ArduinoListener(String nameOfPort, int dataRate)
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException,
            IOException, TooManyListenersException {
        super();
        SerialPort serialPort = (SerialPort) CommPortIdentifier.getPortIdentifier(nameOfPort)
                .open(this.getClass().getName(), TIME_OUT); //set name
        serialPort.setSerialPortParams(
                dataRate,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        inputStream = new DataInputStream(serialPort.getInputStream());
        //outputStream = new DataInputStream(serialPort.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (inputStream.available() > 0) {
                    inputMessage = new byte[inputStream.available()];
                    inputStream.read(inputMessage);
                } else {
                    Thread.sleep(1000);
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

// --Commented out by Inspection START (3/27/17 11:28 PM):
//    public synchronized void sendCommand(byte[] command) {
//        try {
//            output.write(command);
//        } catch (IOException ex) {
//            System.err.println("Cannot send command to ArduinoParser: " + ex.getMessage());
//        }
//    }
// --Commented out by Inspection STOP (3/27/17 11:28 PM)

    byte[] getInputMessage() throws CorruptedDataException {
        if (inputMessage != null) {
            return inputMessage;
        } else {
            throw new CorruptedDataException("No data available");
        }
    }

    private DataInputStream inputStream;
    // --Commented out by Inspection (3/27/17 11:36 PM):private DataOutputStream output;
    private byte[] inputMessage;
}


