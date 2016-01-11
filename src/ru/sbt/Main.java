package ru.sbt;

import ru.sbt.running_cycle.AlgorithmRunner;

/**
 * Created by artem on 11.01.16.
 */
public class Main {

    public static void main(String[] args) {
        AlgorithmRunner algorithmRunner = new AlgorithmRunner();

        try {
            algorithmRunner.runStatistics();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
