package formats;

import userexcept.NegativeMeaningException;

/**
 * Created by vahriin on 2/11/17.
 */
class Data {
    Data(String typeOfData) {
        name = typeOfData;
        value = "0";
    }

// --Commented out by Inspection START (3/27/17 11:42 PM):
//    Data(String typeOfData, String valueData) {
//        name = typeOfData;
//        value = valueData;
//    }
// --Commented out by Inspection STOP (3/27/17 11:42 PM)


    void updateValue(String newValue) throws NegativeMeaningException {
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

    private final String name;
    private String value;
}
