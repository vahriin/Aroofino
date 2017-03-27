package formats;

/**
 * Created by vahriin on 3/26/17.
 */

import userexcept.NegativeMeaningException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class included all types of weather.
 */
public class Weather {
    public Weather(int numberOfItem) {
        dataArrayList = new ArrayList<>(numberOfItem);

        /*add automatic type detection when initializing*/
        dataArrayList.add(new Data("temperature"));
        dataArrayList.add(new Data("humidity"));
        dataArrayList.add(new Data("pressure"));
        dataArrayList.add(new Data("rain"));
    }

    public void updateValues(Map<String, String> valuesMap) {
        for (Data itemOfData : dataArrayList) {
            try {
                itemOfData.updateValue(valuesMap.get(itemOfData.getName()));
            } catch (NegativeMeaningException ex) {
                System.err.println("WeatherEx: " + ex.getMessage());
            }
        }
    }

    public Map<String, String> getValues(ArrayList<String> typesOfData) {
        Map<String, String> valuesMap = new HashMap<>(typesOfData.size());
        //переписать потом
        for (String itemOfType : typesOfData) {
            for (Data itemOfData : dataArrayList) {
                if (itemOfType.equals(itemOfData.getName())) {
                    valuesMap.put(itemOfType, itemOfData.getValue());
                } else {
                    valuesMap.put(itemOfType, "Error");
                }
            }
        }
        //досюда
        return valuesMap;
    }

    private final ArrayList<Data> dataArrayList;
}
