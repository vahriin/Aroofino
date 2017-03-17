package arduino;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 * Created by vahriin on 2/18/17.
 */
public class ArduinoListener implements SerialPortEventListener {
    private static final int TIME_OUT = 2000;

    public ArduinoListener() {
        inputMessage = new byte[0];
    }

    public void initialize(String nameOfPort, int dataRate) {
        CommPortIdentifier portId = null;
        try{
            portId = CommPortIdentifier.getPortIdentifier(nameOfPort); //temporary variable for open port
        } catch (NoSuchPortException ex) {
            System.err.println("Failed to open ArduinoParser connection: specified port was not found");
            System.exit(1);
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT); //set name
            portId = null; // send pointer to GC
        } catch (PortInUseException ex) {
            System.err.println("Failed to open ArduinoParser connection: this device is already in use");
            System.exit(1);
        }

        try {
            serialPort.setSerialPortParams(dataRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException ex) {
            System.err.println("Failed to open ArduinoParser connection: this connection is not Serial Port");
            System.exit(1);
        }

        try {
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        try{
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (TooManyListenersException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }



    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {

                int available = input.available();
                byte data[] = new byte[available];
                input.read(data, 0, available);
                //System.out.println(new String(data));


                inputMessage = data;
                //System.out.println(new String(inputMessage));
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

    public synchronized byte[] getMessage() {
        return inputMessage;
    }

    public synchronized void sendCommand(byte[] command) {
        try {
            output.write(command);
        } catch (IOException ex) {
            System.err.println("Cannot send command to ArduinoParser: " + ex.getMessage());
        }
    }

    private SerialPort serialPort;
    private InputStream input;
    private OutputStream output;
    private byte[] inputMessage;
}


