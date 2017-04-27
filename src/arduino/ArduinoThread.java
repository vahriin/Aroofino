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
    public ArduinoThread(String nameOfPort, int baudRate, int updateTime, Weather currentWeatherLink) throws
            NoSuchPortException, 
            PortInUseException, 
            UnsupportedCommOperationException,
            IOException, 
            TooManyListenersException {
        
        sleepTime = updateTime;
        weatherLink = currentWeatherLink;
        listener = new ArduinoListener(nameOfPort, baudRate);
        listener.start();
        
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 * sleepTime);
                weatherLink.updateValues(ArduinoParser.parse(listener.getInputMessage()));
            } catch (CorruptedDataException ex) {
                System.err.println("ArduinoThreadEx: " + ex.getMessage());
            } catch (InterruptedException ex) {
                System.err.println("ArduinoThreadEx: " + ex.getMessage());
            }
        }
    }

    private int sleepTime;
    private Weather weatherLink;
    private ArduinoListener listener;
}
