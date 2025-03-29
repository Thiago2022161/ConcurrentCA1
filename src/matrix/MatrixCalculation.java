/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matrix;

/**
 *
 * @author thiagogoncos
 */
public class MatrixCalculation {

    private MatrixCalculation() {
    }

    public static double[][] multiply(double[][] a, double[][] b) {
        int size = a.length;
        double[][] result = new double[size][size];

        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            final int row = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        result[row][j] += a[row][k] * b[k][j];
                    }
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted during matrix calculation: " + e.getMessage());
            }
        }

        return result;
    }
}
