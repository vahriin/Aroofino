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
    public static Map<String, String> parse(byte[] message)
            throws CorruptedDataException {
        System.out.println(new String(message));
        if (message[0] == '<' && message[message.length - 2] == '>') { //check correct of data

            /*cut first '<' and last '>' and split*/
            String[] itemsString = new String(message).substring(1,message.length - 2).split("><");
            Map<String, String> result = new HashMap<>(itemsString.length);

            for (String item : itemsString) {
                String[] tempItem = item.split(":");
                result.put(tempItem[0].toLowerCase(), tempItem[1].toLowerCase());
            }
            return result;
        } else {
            throw new CorruptedDataException("Data is corrupted");
        }
    }

    /*future feature*/

    /*public static byte[] command(String command) {

    }*/

}
