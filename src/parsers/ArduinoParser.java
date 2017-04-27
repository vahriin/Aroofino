package parsers;

import userexcept.CorruptedDataException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vahriin on 3/13/17.
 */

/**
 * Data from ArduinoParser format:
 * <Object1:Value><Object2:Value>...<ObjectN:Value>\n
 *
 * Parse to map key:value:
 * key: NameOfObject, value: ValueToInt
 */

/**
 * Data for ArduinoParser format:
 * <Object:CommandOrValue>\n
 */

public class ArduinoParser {
    public static Map<String, String> parse(byte[] request)
            throws CorruptedDataException, ArrayIndexOutOfBoundsException {
        if (request[0] == '<' && request[request.length - 2] == '>') { // check correct of data

            /* cut first '<' and last '>' and split */
            String[] requestedWeatherTypes = new String(request).substring(1,request.length - 2).split("><");
            Map<String, String> mapTypesAndValues = new HashMap<>(requestedWeatherTypes.length);

            for (String item : requestedWeatherTypes) {
                String[] tempItem = item.split(":");
                mapTypesAndValues.put(tempItem[0].toLowerCase(), tempItem[1].toLowerCase());
            }

            return mapTypesAndValues;
        } else {
            throw new CorruptedDataException("Data is corrupted");
        }
    }

    /*future feature*/

    /*public static byte[] command(String command) {

    }*/

}
