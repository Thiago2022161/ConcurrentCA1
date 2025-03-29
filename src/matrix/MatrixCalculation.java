/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matrix;

/**
 * This class is used to multiply two square matrices together using concurrency
 * (Threads).
 *
 * It creates one thread for each row of the result matrix so the calculations
 * can happen at the same time.
 *
 * This helps speed up the process when the matrices are large.
 *
 * We use `Thread[]` and `start()` + `join()` to work with basic concurrency
 * concepts from class.
 *
 * @author thiagogoncos
 */
public class MatrixCalculation {

    // Private constructor to stop this class from being created (because it's a utility class)
    private MatrixCalculation() {
    }

    /**
     * Multiplies two square matrices (same size) using threads.
     *
     * @param a First matrix
     * @param b Second matrix
     * @return The result of multiplying a * b
     */
    public static double[][] multiply(double[][] a, double[][] b) {
        int size = a.length; // Size of the matrix (10x10 in our case)
        double[][] result = new double[size][size]; // The final matrix result

        Thread[] threads = new Thread[size]; // One thread per row

        // Loop through each row and create a thread to calculate that row in the result
        for (int i = 0; i < size; i++) {
            final int row = i; // We need a final variable to use inside the thread
            threads[i] = new Thread(() -> {
                // This thread will fill in the row "row" in the result matrix
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        result[row][j] += a[row][k] * b[k][j]; // Standard matrix multiplication
                    }
                }
            });
            threads[i].start(); // Start the thread (it runs the code above)
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            try {
                t.join(); // Wait for the thread to finish its work
            } catch (InterruptedException e) {
                // If something goes wrong with the thread, stop and show a message
                Thread.currentThread().interrupt(); // Good practice: re-interrupt the thread
                System.err.println("Thread interrupted during matrix calculation: " + e.getMessage());
            }
        }

        // After all threads finish, return the full result
        return result;
    }
}
