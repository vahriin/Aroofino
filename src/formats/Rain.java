package formats;

import java.util.Map;

/**
 * Created by vahriin on 2/11/17.
 */
public class Rain extends Data {
    public final double ACCURACY = 1;

    public final double DRY = 1024;
    public final double DRIZZLE = 1000;
    public final double RAIN = 800;
    public final double SHOWER = 400;

    public Rain() {
        super("rain");
    }

    public Rain(double rainValue) {
        super("rain", rainValue);
    }


    public void updateValue(Map<String, Integer> dataList)
            throws NoValueException, NegativeMeaningException {
        Integer temporary = dataList.get(name);
        if (temporary != null) {
            if (temporary > 0 && temporary < 1024) {
                value = temporary.intValue() / ACCURACY;
            } else {
                throw new NegativeMeaningException("Rain sensor is break");
            }
        } else {
            throw new NoValueException("Rain is not of this map");
        }
    }

    public double getAsNumber() {
        return getValue();
    }

    public String getAsString() {
        if (value < SHOWER) {
            return "shower";
        } else if (value < RAIN) {
            return "rain";
        } else if (value < DRIZZLE) {
            return "drizzle";
        } else if (value < DRY) {
            return "dry";
        } else {
            return "";
        }
    }
}
