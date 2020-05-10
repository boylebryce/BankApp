package impl;

import java.util.Random;
import java.util.Set;

public class IDUtils {
    private static final Random random = new Random();

    public static long generateID(Set<Long> used, int length, boolean leadingZerosAvailable) {
        long limit = (long) Math.pow(10, length);
        long id = -1;
        do {
            long r = random.nextLong();
            if (leadingZerosAvailable) {
                id = r < 0 ? ((-r) % limit) : (r % limit);
            } else {
                long base = limit / 10;
                id = r < 0 ? ((-r) % (limit - base)) : (r % (limit - base)) + base;
            }
        }
        while (used.contains(id));
        used.add(id);
        return id;
    }
}
