/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concurrentca1;

// Import the necessary classes for reading data, doing standard deviation, matrix work, and sorting
import csv.DataReader;
import stddev.StandardDeviation;
import matrix.MatrixCalculation;
import matrix.MatrixCalcExecutor;
import sort.DataSorter;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

/**
 * This is the main class that runs the full program. It reads the CSV file,
 * calculates the standard deviation, multiplies two matrices (with two
 * concurrency methods), and sorts all the numbers in descending order.
 *
 * @author thiagogoncos
 */
public class ConcurrentRunner {

    public static void main(String[] args) throws IOException {

        //Just an output introduction 
        System.out.println("==================================================");
        System.out.println("This program performs 3 tasks using concurrency:");
        System.out.println("1. Calculates the standard deviation of 200 numbers from a CSV file.");
        System.out.println("2. Multiplies two 10x10 matrices using two different concurrency methods:");
        System.out.println("   - Using manual Threads");
        System.out.println("   - Using ExecutorService");
        System.out.println("3. Sorts all 200 numbers in descending order using merge sort with Fork/Join.");
        System.out.println("Below you can see the results of each task:");
        System.out.println("==================================================\n");

        // Step 1: Read all numbers from the CSV file into an array
        double[] data = DataReader.readCSV("data.csv");

        // Step 2: Calculate the average (mean) of all numbers
        double mean = Arrays.stream(data).average().orElse(0);

        // Step 3: Use ForkJoinPool to calculate the sum of squared differences for standard deviation
        ForkJoinPool pool = new ForkJoinPool();
        double squaredSum = pool.invoke(new StandardDeviation(data, 0, data.length, mean));

        // Step 4: Final standard deviation calculation
        double stdDev = Math.sqrt(squaredSum / data.length);
        System.out.printf("Standard Deviation: %.4f\n\n", stdDev);

        // Step 5: Split the 200 numbers into two 10x10 matrices (A and B)
        int size = 10;
        double[][] matrixA = new double[size][size];
        double[][] matrixB = new double[size][size];

        // Fill matrixA with the first 100 numbers and matrixB with the next 100
        for (int i = 0; i < size; i++) {
            System.arraycopy(data, i * size, matrixA[i], 0, size);
            System.arraycopy(data, (i + size) * size, matrixB[i], 0, size);
        }

        // Step 6: Multiply the matrices using threads
        double[][] resultThread = MatrixCalculation.multiply(matrixA, matrixB);
        System.out.println("Matrix (10x10) Multiplication (Using Threads):\n");
        for (double[] row : resultThread) {
            System.out.println(Arrays.toString(row));
        }

        // Step 7: Multiply the matrices again using ExecutorService
        double[][] resultExecutor = MatrixCalcExecutor.multiply(matrixA, matrixB);
        System.out.println("\nMatrix (10x10) Multiplication (Using ExecutorService):\n");
        for (double[] row : resultExecutor) {
            System.out.println(Arrays.toString(row));
        }

        // Step 8: Sort a copy of the original data using merge sort (in descending order)
        double[] dataCopy = Arrays.copyOf(data, data.length);
        pool.invoke(new DataSorter(dataCopy, 0, dataCopy.length - 1));

        System.out.println("\nAll 200 Numbers Sorted (Descending):\n");

        // Step 9: Print the sorted numbers with 10 numbers per line
        for (int i = 0; i < dataCopy.length; i++) {
            System.out.print(dataCopy[i] + "\t");
            if ((i + 1) % 10 == 0) {
                System.out.println(); // Line break every 10 numbers
            }
        }
    }
}
