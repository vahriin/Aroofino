package formats;

import userexcept.NegativeMeaningException;
import userexcept.NoValueException;

import java.util.Map;

/**
 * Created by vahriin on 2/11/17.
 */
public abstract class Data {
    public Data(String typeOfData) {
        name = typeOfData;
        value = 0;
    }

    public Data(String typeOfData, double valueData) {
        name = typeOfData;
        value = valueData;
    }


    public abstract void updateValue(Map<String, Integer> dataList)
            throws NoValueException, NegativeMeaningException;


    public double getValue() {
        return value;
    }


    protected String name;
    protected double value;
}
