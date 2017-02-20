package formats;

import userexcept.NegativeMeaningException;
import userexcept.NoValueException;

import java.util.Map;

/**
 * Created by vahriin on 2/11/17.
 */

/**
 * Temperature in Kelvin
 */

public class Temperature extends Data {
    private final double ACCURACY = 10.0;

    public Temperature() {
        super("temperature");
    }

    public Temperature(int temperatureValue) {
        super("temperature", temperatureValue);
    }


    public void updateValue(Map<String, Integer> dataList)
            throws NoValueException, NegativeMeaningException {
        Integer temporary = dataList.get(name);
        if (temporary != null) {
            if (temporary > 0) {
                value = temporary.intValue() / ACCURACY;
            } else {
                throw new NegativeMeaningException("Temperature have negative meaning");
            }
        } else {
            throw new NoValueException("Temperature is not of this map");
        }
    }

    public double getAsKelvin() {
        return getValue();
    }

    public double getAsCelsius() {
        return getValue() - 273;
    }

    public double getAsFahrenheit() {
        return 1.8 * (getValue() - 273) + 32;
    }
}
