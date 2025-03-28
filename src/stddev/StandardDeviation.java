/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stddev;

import java.util.concurrent.RecursiveTask;

/**
 *
 * @author thiagogoncos
 */

public class StandardDeviation extends RecursiveTask<Double> {

    private final double[] data;
    private final int start, end;
    private final double mean;
    private static final int THRESHOLD = 20;

    public StandardDeviation(double[] data, int start, int end, double mean) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.mean = mean;
    }

    @Override
    protected Double compute() {
        if (end - start <= THRESHOLD) {
            double sum = 0;
            for (int i = start; i < end; i++) {
                sum += Math.pow(data[i] - mean, 2);
            }
            return sum;
        } else {
            int mid = (start + end) / 2;
            StandardDeviation leftTask = new StandardDeviation(data, start, mid, mean);
            StandardDeviation rightTask = new StandardDeviation(data, mid, end, mean);

            leftTask.fork(); 
            double rightResult = rightTask.compute(); 
            double leftResult = leftTask.join(); 

            return leftResult + rightResult;
        }
    }
}
