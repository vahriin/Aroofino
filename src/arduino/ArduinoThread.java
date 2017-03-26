package arduino;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import parsers.ArduinoParser;
import userexcept.CorruptedDataException;

import java.io.IOException;
import java.util.Map;
import java.util.TooManyListenersException;

/**
 * Created by vahriin on 3/20/17.
 */
public class ArduinoThread implements Runnable {
    public ArduinoThread(String nameOfPort, int baudRate)
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException,
            IOException, TooManyListenersException {
        sensor = new ArduinoListener(nameOfPort, baudRate);
    }

    public void run() {
        try {
            dataMap = parser.parse(sensor.getMessage());
        } catch (CorruptedDataException ex) {
            System.err.println("ArduinoThreadEx: " + ex.getMessage());
        }
    }

    public Map<String, String> getDataMap() {
        return dataMap;
    }

    private Map<String, String> dataMap;
    private ArduinoParser parser;
    private ArduinoListener sensor;
}
