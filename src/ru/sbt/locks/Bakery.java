package ru.sbt.locks;

/**
 * Created by artem on 11.01.16.
 */

import ru.sbt.running_cycle.ConcurrencyUtils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Bakery extends DummyConcurrentLock implements CustomLock {

    private AtomicBoolean[] flag;
    private AtomicInteger[] label;

    private int n;


    public Bakery(int nThreads) {
        this.n = nThreads;
        flag = new AtomicBoolean[nThreads];
        label = new AtomicInteger[nThreads];
        for (int i = 0; i < nThreads; i++) {
            flag[i] = new AtomicBoolean();
            label[i] = new AtomicInteger();
        }
    }


    @Override
    public void lock() {
        int i = ConcurrencyUtils.getCurrentThreadId();
        flag[i].set(true);
        label[i].set(findMaximumElement(label) + 1);
        for (int k = 0; k < n; k++) {
            while ((k != i) && flag[k].get() && ((label[k].get() < label[i].get()) || ((label[k].get() == label[i].get()) && k < i))) {
                //spin wait
            }
        }
    }

    @Override
    public void unlock() {
        flag[ConcurrencyUtils.getCurrentThreadId()].set(false);
    }

    private int findMaximumElement(AtomicInteger[] elementArray) {
        int maxValue = Integer.MIN_VALUE;
        for (AtomicInteger element : elementArray) {
            if (element.get() > maxValue) {
                maxValue = element.get();
            }
        }
        return maxValue;
    }
}
