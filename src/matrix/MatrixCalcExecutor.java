/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author thiagogoncos
 */
public final class MatrixCalcExecutor {

    private MatrixCalcExecutor() {
    }

    public static double[][] multiply(double[][] a, double[][] b) {
        int size = a.length;
        double[][] result = new double[size][size];

        ExecutorService executor = Executors.newFixedThreadPool(size);

        for (int row = 0; row < size; row++) {
            final int r = row;
            executor.submit(() -> {
                for (int col = 0; col < size; col++) {
                    for (int k = 0; k < size; k++) {
                        result[r][col] += a[r][k] * b[k][col];
                    }
                }
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Matrix calculation interrupted: " + e.getMessage());
        }

        return result;
    }
}
