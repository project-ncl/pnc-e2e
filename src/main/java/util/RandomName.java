package util;

import java.util.Random;

/**
 * Created by eunderhi on 09/09/15.
 */
public class RandomName {

    public static String getRandomName() {
        int largeRandomNumber = new Random().nextInt();
        return "pncweb" + largeRandomNumber;
    }
}
