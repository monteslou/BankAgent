package utils;

import java.util.Random;

public class RandomGenerator {
    Random random = new Random();

    public Integer getRandomTime(int min, int max) {
        random.setSeed(System.currentTimeMillis());
        return random.nextInt((max - min) + 1) + min;
    }

    public Integer getRandomInt(int min, int max) {
        return random.nextInt(max - 1 - min + 1) + min;
    }
}
