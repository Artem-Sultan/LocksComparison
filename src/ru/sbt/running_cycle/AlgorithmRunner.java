package ru.sbt.running_cycle;

import ru.sbt.locks.LockType;

import java.util.concurrent.TimeUnit;

/**
 * Created by artem on 11.01.16.
 */
public class AlgorithmRunner {

    public static final int[] DEFAULT_THREAD_COUNTS = {1, 2, 3, 4};
    public static final int DEFAULT_MAX_NUMBER = 4000000;
    public static final int DEFAULT_RETRY_COUNT = 20;
    private static final LockType[] LOCK_TYPES = {
            LockType.BAKERY,
            LockType.FILTER,
            LockType.REENTRANT,
            LockType.BACKOFF
    };


    public void runStatistics() throws InterruptedException {
        StatisticsCollector statisticsCollector;

        for (LockType lockType : LOCK_TYPES) {
            for (int threadCount : DEFAULT_THREAD_COUNTS) {
                statisticsCollector = new StatisticsCollector(threadCount, DEFAULT_MAX_NUMBER, lockType, 0, 0);
                for (int currentTry = 0; currentTry < DEFAULT_RETRY_COUNT; currentTry++) {
                    Counter counter = new Counter(0, lockType, threadCount, DEFAULT_MAX_NUMBER);
                    long elapsedTime = runTest(counter, threadCount);
                    statisticsCollector.currentTry++;
                    statisticsCollector.elapsedTime += elapsedTime;
                }
                System.out.println(statisticsCollector);
            }
        }
    }

    public long runTest(Counter counter, int threadCount) throws InterruptedException {
        Thread[] threads = new Thread[threadCount];
        long threadNumber = 0;

        long startTime = System.nanoTime();

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(counter);
            threads[i].setName(String.valueOf(threadNumber++));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long finishTime = System.nanoTime();
        return TimeUnit.NANOSECONDS.toMillis(finishTime - startTime);
    }
}
