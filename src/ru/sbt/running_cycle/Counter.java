package ru.sbt.running_cycle;

import ru.sbt.locks.BackoffLock;
import ru.sbt.locks.Bakery;
import ru.sbt.locks.Filter;
import ru.sbt.locks.LockType;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by artem on 11.01.16.
 */

public class Counter implements Runnable {
    private long value;
    private Lock lock;
    private LockType lockType;
    private int maxNumber;
    private int capacity;

    public Counter(long value, LockType lockType, int nThreads, int maxNumber) {
        this.value = value;
        this.lockType = lockType;
        this.capacity = nThreads;
        this.maxNumber = maxNumber;
        switch (this.lockType) {
            case FILTER:
                lock = new Filter(nThreads);
                break;
            case BAKERY:
                lock = new Bakery(nThreads);
                break;
            case BACKOFF:
                lock = new BackoffLock();
                break;
            case REENTRANT:
            default:
                lock = new ReentrantLock();
        }
    }

    /**
     * gets and increments value up to a maximum number
     *
     * @return value before increment if it didn't exceed a defined maximum number. Otherwise returns maximum number.
     */
    public long getAndIncrement() {
        long temp;
        lock.lock();
        try {
            if (value >= maxNumber) {
                return value;
            }
            temp = value;
            value = temp + 1;
        } finally {
            lock.unlock();
        }
        return temp;
    }

    @Override
    public void run() {
        while (getAndIncrement() < maxNumber) {
            //do nothing
        }
    }

    public long getValue() {
        return value;
    }

    public LockType getLockType() {
        return lockType;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getCapacity() {
        return capacity;
    }
}
