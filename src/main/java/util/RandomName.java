package util;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by eunderhi on 09/09/15.
 */
public class RandomName {

    public static String getRandomName() {
        return "pncweb" + getSufix();
    }

    /**
     * Returns almost-unique string that can be concatenated at the end of a name with unique constraint.
     *
     * @return string in format "-2016-04-18-76c4"
     */
    public static String getSufix() {
        return "-" + LocalDate.now() + "-" + UUID.randomUUID().toString().substring(0, 4);
    }
}
