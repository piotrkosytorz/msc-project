package main;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class BenchmarkRunner {

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @Fork(0)
    @Warmup(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
    public void sampleMethod() {
        fibonacci();
    }

    public void fibonacci() {
        // Set it to the number of elements you want in the Fibonacci Series
        int maxNumber = 10;
        int previousNumber = 0;
        int nextNumber = 1;

//        System.out.print("Fibonacci Series of " + maxNumber + " numbers:");

        for (int i = 1; i <= maxNumber; ++i) {
//            System.out.print(previousNumber + " ");
            /* On each iteration, we are assigning second number
             * to the first number and assigning the sum of last two
             * numbers to the second number
             */


            int sum = previousNumber + nextNumber;
            previousNumber = nextNumber;
            nextNumber = sum;
        }
    }
}
