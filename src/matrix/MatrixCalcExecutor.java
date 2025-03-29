/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class multiplies two square matrices (like 10x10) using a thread pool
 * (ExecutorService).
 *
 * Instead of starting threads manually, we use Executors to handle them for us.
 * This way is more modern and safer when we deal with lots of tasks.
 *
 * Each row of the result matrix is calculated in a separate task.
 *
 * This shows understanding of a different type of concurrency than basic
 * threads.
 *
 * @author thiagogoncos
 */
public final class MatrixCalcExecutor {

    // Private constructor to prevent creating an object from this utility class
    private MatrixCalcExecutor() {
    }

    /**
     * Multiplies two matrices using a fixed thread pool.
     *
     * @param a The first matrix
     * @param b The second matrix
     * @return The resulting matrix after multiplication
     */
    public static double[][] multiply(double[][] a, double[][] b) {
        int size = a.length; // Size of the square matrix
        double[][] result = new double[size][size]; // Final result matrix

        // Create a fixed thread pool with a number of threads equal to the number of rows
        ExecutorService executor = Executors.newFixedThreadPool(size);

        // For each row, we submit a task to the executor
        for (int row = 0; row < size; row++) {
            final int r = row; // Need a final variable for use inside the lambda expression
            executor.submit(() -> {
                // This task calculates one full row in the result matrix
                for (int col = 0; col < size; col++) {
                    for (int k = 0; k < size; k++) {
                        result[r][col] += a[r][k] * b[k][col]; // Standard matrix multiplication logic
                    }
                }
            });
        }

        // After submitting all tasks, we shut down the executor
        executor.shutdown();
        try {
            // Wait for all tasks to finish (up to 60 seconds)
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                // If not all finished in time, force shutdown
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // Handle thread interruption (good practice)
            Thread.currentThread().interrupt();
            System.err.println("Matrix calculation interrupted: " + e.getMessage());
        }

        // Return the final result
        return result;
    }
}
