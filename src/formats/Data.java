package formats;

import userexcept.NegativeMeaningException;
import userexcept.NoValueException;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vahriin on 2/11/17.
 */
public class Data {
    public Data(String typeOfData) {
        name = typeOfData;
        value = "0";
    }

    public Data(String typeOfData, String valueData) {
        name = typeOfData;
        value = valueData;
    }


    public void updateValue(String newValue) throws NegativeMeaningException {
        if (!isNegative(newValue)) {
            value = newValue;
        } else {
            throw new NegativeMeaningException("DataEx: " + name + " have negative meaning");
        }
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


    private boolean isNegative(String value) {
        return value.charAt(0) == '-';
    }

    protected String name;
    protected String value;
}
