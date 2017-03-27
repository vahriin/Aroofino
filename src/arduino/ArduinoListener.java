package arduino;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
//mport java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 * Created by vahriin on 2/18/17.
 */
class ArduinoListener implements SerialPortEventListener {
    private static final int TIME_OUT = 2000;

    ArduinoListener(String nameOfPort, int dataRate)
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException,
            IOException, TooManyListenersException {
        inputMessage = new byte[0];
        /*try{*/
        SerialPort serialPort = (SerialPort) CommPortIdentifier.getPortIdentifier(nameOfPort)
                .open(this.getClass().getName(), TIME_OUT); //set name
        serialPort.setSerialPortParams(dataRate,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        input = serialPort.getInputStream();
        //output = serialPort.getOutputStream();
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
    }

// --Commented out by Inspection START (3/27/17 11:28 PM):
//    public synchronized void close() {
//        if (serialPort != null) {
//            serialPort.removeEventListener();
//            serialPort.close();
//        }
//    }
// --Commented out by Inspection STOP (3/27/17 11:28 PM)

    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                int available = input.available();
                byte data[] = new byte[available];
                input.read(data, 0, available);
                inputMessage = data;
                //System.out.println(new String(inputMessage));
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public synchronized byte[] getMessage() {
        return inputMessage;
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

    private InputStream input;
    // --Commented out by Inspection (3/27/17 11:36 PM):private OutputStream output;
    private byte[] inputMessage;
}


