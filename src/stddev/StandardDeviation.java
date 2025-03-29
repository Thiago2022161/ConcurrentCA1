/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stddev;

import java.util.concurrent.RecursiveTask;

/**
 * This class calculates the sum of squared differences from the mean, which is
 * a step in finding the standard deviation of a dataset.
 *
 * It uses Fork/Join to split the task into smaller parts to run in parallel
 * (concurrent programming). This helps make it faster, especially with a lot of
 * data.
 *
 * @author thiagogoncos
 */
public class StandardDeviation extends RecursiveTask<Double> {

    // The dataset (all the numbers)
    private final double[] data;

    // The range (start and end positions) that this task will handle
    private final int start, end;

    // The average (mean) of the whole dataset
    private final double mean;

    // If the task has 20 or fewer elements, it will be done directly without splitting
    private static final int THRESHOLD = 20;

    /**
     * Constructor to set up the task
     *
     * @param data All the numbers
     * @param start Where this task should start
     * @param end Where this task should stop
     * @param mean The average of all numbers (needed for the formula)
     */
    public StandardDeviation(double[] data, int start, int end, double mean) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.mean = mean;
    }

    /**
     * This method is called when the task runs. It checks if the task is small
     * enough to do directly, or if it should be split into smaller parts (left
     * and right).
     */
    @Override
    protected Double compute() {
        // If the range is small, calculate it directly
        if (end - start <= THRESHOLD) {
            double sum = 0;
            // For each number, calculate (number - mean)^2 and add it to the sum
            for (int i = start; i < end; i++) {
                sum += Math.pow(data[i] - mean, 2);
            }
            return sum;
        } else {
            // If the range is big, split it in half
            int mid = (start + end) / 2;

            // Create two smaller tasks
            StandardDeviation leftTask = new StandardDeviation(data, start, mid, mean);
            StandardDeviation rightTask = new StandardDeviation(data, mid, end, mean);

            // Run the left task in another thread
            leftTask.fork();

            // Run the right task in this thread
            double rightResult = rightTask.compute();

            // Wait for the left task to finish and get the result
            double leftResult = leftTask.join();

            // Add both results and return
            return leftResult + rightResult;
        }
    }
}
