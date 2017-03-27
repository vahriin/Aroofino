package arduino;

import formats.Weather;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import parsers.ArduinoParser;
import userexcept.CorruptedDataException;

import java.io.IOException;
import java.util.TooManyListenersException;

/**
 * Created by vahriin on 3/20/17.
 */
public class ArduinoThread implements Runnable {
    public ArduinoThread(String nameOfPort, int baudRate, int updateTime, Weather data)
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException,
            IOException, TooManyListenersException {
        sensor = new ArduinoListener(nameOfPort, baudRate);
        sleepTime = updateTime;
        currentWeather = data;
    }

    public void run() {
        while (true) {
            try {
                currentWeather.updateValues(ArduinoParser.parse(sensor.getMessage()));
                Thread.sleep(1000 * sleepTime);
            } catch (CorruptedDataException ex) {
                System.err.println("ArduinoThreadEx: " + ex.getMessage());
            } catch (InterruptedException ex) {
                System.err.println("ArduinoThreadEx: " + ex.getMessage());
            }
        }
    }

    private int sleepTime;
    private Weather currentWeather;
    private ArduinoListener sensor;
}
