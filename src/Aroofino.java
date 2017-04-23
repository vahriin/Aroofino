import arduino.ArduinoThread;
import formats.Weather;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.apache.commons.cli.*;
import server.ThreadPool;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.util.TooManyListenersException;

/**
 * Created by vahriin on 3/20/17.
 */
class Aroofino {
    public static void main(String[] args) throws InterruptedException {
        CommandLine commandLine = parseCLI(args);
        Weather currentWeather = getWeather(commandLine);
        Thread weatherGetter = new Thread(startArduinoThread(commandLine, currentWeather));
        weatherGetter.start();
        Thread server = new Thread(startServer(commandLine, currentWeather));
        server.start();
    }

    /*Rewrite using exceptions*/
    private static Weather getWeather(CommandLine args) {
        Weather result = null;
        if (args.hasOption("n")) {
            result = new Weather(Integer.parseInt(args.getOptionValue("n")));
        } else {
            System.out.println("One or more options were not specified");
            System.exit(1);
        }
        return result;
    }

    private static ThreadPool startServer(CommandLine args, Weather sharedVar) {
        System.out.print("Starting server... ");
        ThreadPool server = null;
        if (args.hasOption("p")) {
            try {
                server = new ThreadPool(Integer.parseInt(args.getOptionValue("p")), sharedVar);
            } catch (IOException ex) {
                System.out.println("Internal programm error. Try again. Information: " +
                        ex.getMessage());
                System.exit(1);
            } catch (ParserConfigurationException ex) {
                System.out.println("Internal programm error. Try again. Information: " +
                        ex.getMessage());
                System.exit(1);
            } catch (TransformerConfigurationException ex) {
                System.out.println("Internal programm error. Try again. Information: " +
                        ex.getMessage());
                System.exit(1);
            }
        }
        System.out.println("done");
        System.out.println("Listening port: " + args.getOptionValue("p"));
        return server;
    }

    private static ArduinoThread startArduinoThread(CommandLine args, Weather data) {
        System.out.print("Arduino connection... ");
        ArduinoThread result = null;
        if (args.hasOption("a") && args.hasOption("b") && args.hasOption("t")) {
            try {
                String port = args.getOptionValue("a");
                int baudRate = Integer.parseInt(args.getOptionValue("b"));
                int updateTime = Integer.parseInt(args.getOptionValue("t"));
                result = new ArduinoThread(port, baudRate, updateTime, data);
            } catch (NoSuchPortException ex) {
                System.out.println("This port is not available. Information: " + ex.getMessage());
                System.exit(1);
            } catch (PortInUseException ex) {
                System.out.println("This port already use. Information: " + ex.getMessage());
                System.exit(1);
            } catch (UnsupportedCommOperationException ex) {
                System.out.println("Internal programm error. Try again. Information: " +
                        ex.getMessage());
                System.exit(1);
            } catch (TooManyListenersException ex) {
                System.out.println("This port already use. Information: " + ex.getMessage());
                System.exit(1);
            } catch (IOException ex) {
                System.out.println("Internal programm error. Try again. Information: " +
                        ex.getMessage());
                System.exit(1);
            }
        } else {
            System.out.println("One or more options were not specified");
            System.exit(1);
        }
        System.out.println("done");
        return result;
    }

    private static CommandLine parseCLI(String[] args) {
        Options options = new Options();

        /*Listened port options*/
        Option portOption = new Option("p", "port", true,
                "Listened server port");
        portOption.setArgs(1);
        portOption.setArgName("Listened port");
        options.addOption(portOption);

        Option arduinoOption = new Option("a", "arduino", true,
                "Port of arduino connection (e.g. /dev/ttyACM0)");
        portOption.setArgs(1);
        portOption.setArgName("Arduino port");
        options.addOption(arduinoOption);

        Option baudOption = new Option("b", "baud", true,
                "Baud Rate of arduino connection");
        portOption.setArgs(1);
        portOption.setArgName("Baud Rate");
        options.addOption(baudOption);

        Option numberWeatherOption = new Option("n", "number", true,
                "Number of weather item (e.g. 2 for \"temperature\" and \"pressure\"");
        portOption.setArgs(1);
        portOption.setArgName("Baud Rate");
        options.addOption(numberWeatherOption);

        Option updateTimeOption = new Option("t", "time", true,
                "Time between values updates (in seconds)");
        portOption.setArgs(1);
        portOption.setArgName("Baud Rate");
        options.addOption(updateTimeOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine result = null;

        try {
            result = parser.parse(options, args);
        } catch (ParseException ex) {
            System.err.println("parseCLI-Ex: " + ex.getMessage());
        }
        return result;
    }
}
