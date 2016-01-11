package ru.sbt.running_cycle;

/**
 * Created by artem on 11.01.16.
 */
public final class ConcurrencyUtils {
    public static int getCurrentThreadId(int capacity) {
        return (int) (Thread.currentThread().getId() % capacity);
    }
    public static int getCurrentThreadId() {
        return Integer.parseInt(Thread.currentThread().getName());
    }
}
