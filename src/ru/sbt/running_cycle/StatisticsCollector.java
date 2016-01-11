package ru.sbt.running_cycle;

import ru.sbt.locks.LockType;

/**
 * Created by artem on 11.01.16.
 */
public class StatisticsCollector {
     int threadCount;
     int maxNumber;

     LockType lockType;
     long elapsedTime;
     int currentTry;

    public StatisticsCollector(int threadCount, int maxNumber, LockType lockType, long elapsedTime, int currentTry) {
        this.threadCount = threadCount;
        this.maxNumber = maxNumber;
        this.lockType = lockType;
        this.elapsedTime = elapsedTime;
        this.currentTry = currentTry;
    }

    @Override
    public String toString() {
        return "tries: " + currentTry + ", threadCount = " + threadCount + ", maxNumber = " + maxNumber + ", lockType = " + lockType + ", elapsedTime per try = " + elapsedTime/currentTry + " (ms)";
    }
}
