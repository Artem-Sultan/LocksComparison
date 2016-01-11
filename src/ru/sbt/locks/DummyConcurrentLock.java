package ru.sbt.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by artem on 11.01.16.
 */
public abstract class DummyConcurrentLock implements Lock {

    private static final String NOT_SUPPORTED = "Not supported!";

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }


    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }
}
