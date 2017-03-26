package formats;

import userexcept.NegativeMeaningException;
import userexcept.NoValueException;

import java.util.Map;

/**
 * Created by vahriin on 2/11/17.
 */

/**
 * Pressure in Pascal
 */
/*public class Pressure extends Data {
    private final double ACCURACY = 1;

    public Pressure() {
        super("pressure");
    }

    public Pressure(double pressureValue) {
        super("pressure", pressureValue);
    }


    public void updateValue(Map<String, Integer> dataList)
            throws NoValueException, NegativeMeaningException {
        Integer temporary = dataList.get(name);
        if (temporary != null) {
            if (temporary > 0) {
                value = temporary / ACCURACY;
            } else {
                throw new NegativeMeaningException("Pressure have negative meaning");
            }
        } else {
            throw new NoValueException("Pressure is not of this map");
        }
    }
}*/
