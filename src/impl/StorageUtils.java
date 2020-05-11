package impl;

import api.Card;

import java.util.Collection;

public class StorageUtils {
    public static String append(String output, String appendString) {
        String delimiter = ",";

        if (output.contains(delimiter)) {
            return output + delimiter + appendString;
        }
        else {
            return output + appendString;
        }
    }
}
