/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concurrentca1;

import csv.DataReader;
import stddev.StandardDeviation;
import matrix.MatrixCalculation;
import matrix.MatrixCalcExecutor;
import sort.DataSorter;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author thiagogoncos
 */
public class ConcurrentRunner {

    public static void main(String[] args) throws IOException {

        double[] data = DataReader.readCSV("data.csv");

        double mean = Arrays.stream(data).average().orElse(0);
        ForkJoinPool pool = new ForkJoinPool();
        double squaredSum = pool.invoke(new StandardDeviation(data, 0, data.length, mean));
        double stdDev = Math.sqrt(squaredSum / data.length);
        System.out.printf("Standard Deviation: %.4f\n\n", stdDev);

        int size = 10;
        double[][] matrixA = new double[size][size];
        double[][] matrixB = new double[size][size];

        for (int i = 0; i < size; i++) {
            System.arraycopy(data, i * size, matrixA[i], 0, size);
            System.arraycopy(data, (i + size) * size, matrixB[i], 0, size);
        }

        double[][] resultThread = MatrixCalculation.multiply(matrixA, matrixB);
        System.out.println("Matrix Multiplication (Using Threads):\n");
        for (double[] row : resultThread) {
            System.out.println(Arrays.toString(row));
        }

        double[][] resultExecutor = MatrixCalcExecutor.multiply(matrixA, matrixB);
        System.out.println("\nMatrix Multiplication (Using ExecutorService):\n");
        for (double[] row : resultExecutor) {
            System.out.println(Arrays.toString(row));
        }

        double[] dataCopy = Arrays.copyOf(data, data.length);
        pool.invoke(new DataSorter(dataCopy, 0, dataCopy.length - 1));
        System.out.println("\nAll 200 Numbers Sorted (Descending):\n");

        for (int i = 0; i < dataCopy.length; i++) {
            System.out.print(dataCopy[i] + "\t");
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }
    }
}
