package arduino;

import userexcept.CorruptedDataException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vahriin on 2/20/17.
 */
public class DataParser {
    public static Map<String, Integer> parseInput(byte[] message)
            throws CorruptedDataException {
        if (message[0] == '<' && message[message.length - 2] == '>') {
            String[] itemsString = new String(message).substring(1,message.length - 2).split("><");
            Map<String, Integer> result = new HashMap<>(itemsString.length);
            for (int i = 0; i < itemsString.length; i++) {
                String[] tempItem = itemsString[i].split(":");
                result.put(tempItem[0], Integer.parseInt(tempItem[1]));
            }
            return result;
        } else {
            throw new CorruptedDataException("Data is corrupted");
        }
    }

    
    /*future feature*/

    /*public static String shapeCommand(String command) {
    }
     */
}
