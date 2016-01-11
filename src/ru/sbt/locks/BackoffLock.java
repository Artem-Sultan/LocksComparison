package ru.sbt.locks;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by artem on 11.01.16.
 */
public class BackoffLock extends DummyConcurrentLock implements CustomLock {
    private AtomicBoolean state = new AtomicBoolean(false);
    private static final int MIN_DELAY = 1;
    private static final int MAX_DELAY = 16;

    public void lock() {
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while (true) {
            while (state.get()) {
            }
            if (!state.getAndSet(true)) {
                return;
            } else {
                try {
                    backoff.backoff();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void unlock() {
        state.set(false);
    }
}