package arduino;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 * Created by vahriin on 2/18/17.
 */
public class ArduinoDialog implements SerialPortEventListener {
    private static final int TIME_OUT = 2000;
    //private static final int DATA_RATE = 9600;

    public void initialize(String nameOfPort, int dataRate) {
        CommPortIdentifier portId = null;
        try{
            portId = CommPortIdentifier.getPortIdentifier(nameOfPort); //temporary variable for open port
        } catch (NoSuchPortException ex) {
            System.err.println("Failed to open Arduino connection: specified port was not found");
            System.exit(1);
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT); //set name
            portId = null; // send pointer to GC
        } catch (PortInUseException ex) {
            System.err.println("Failed to open Arduino connection: this device is already in use");
            System.exit(1);
        }

        try {
            serialPort.setSerialPortParams(dataRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException ex) {
            System.err.println("Failed to open Arduino connection: this connection is not Serial Port");
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

                inputMessage = data;
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

    public synchronized byte[] getMessage() {
        return inputMessage;
    }

    /*public synchronized void sendCommand(String command) {

    }*/


    public static void main(String[] args) {
        ArduinoDialog x = new ArduinoDialog();
        x.initialize("/dev/ttyACM0", 9600);
    }

    private SerialPort serialPort;
    private InputStream input;
    private OutputStream output;
    private byte[] inputMessage;
}


