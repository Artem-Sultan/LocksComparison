package ru.sbt.locks;

import java.util.Random;

/**
 * Created by artem on 11.01.16.
 */
public class Backoff {
    final int minDelay, maxDelay;
    int limit;
    final Random random;
    public Backoff(int min, int max) {
        minDelay = min; maxDelay = min;
        limit = minDelay; random = new Random();
    }
    public void backoff() throws InterruptedException {
        int delay = random.nextInt(limit); limit = Math.min(maxDelay, 2 * limit); Thread.sleep(delay);
    }
}
