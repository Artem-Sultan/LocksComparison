package ru.sbt.locks;

import ru.sbt.running_cycle.ConcurrencyUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by artem on 11.01.16.
 */
public class Filter extends DummyConcurrentLock implements CustomLock {

    private AtomicInteger[] level;
    private AtomicInteger[] victim;

    private int n;


    public Filter(int n) {
        this.n = n;
        level = new AtomicInteger[n];
        victim = new AtomicInteger[n];
        for (int i = 0; i < n; i++) {
            level[i] = new AtomicInteger();
            victim[i] = new AtomicInteger();
        }
    }

    @Override
    public void lock() {
        int me = ConcurrencyUtils.getCurrentThreadId();
        for (int i = 1; i < n; i++) {
            level[me].set(i);
            victim[i].set(me);
            for (int k = 0; k < n; k++) {
                while ((k != me) && (level[k].get() >= i && victim[i].get() == me)) {
                    //spin wait
                }
            }
        }
    }

    @Override
    public void unlock() {
        int me = ConcurrencyUtils.getCurrentThreadId();
        level[me].set(0);
    }
}