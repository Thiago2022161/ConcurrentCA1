/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sort;

import java.util.concurrent.RecursiveAction;

/**
 * This class uses Merge Sort to sort an array of numbers in descending order
 * (from largest to smallest).
 *
 * It uses Fork/Join (concurrency) to split the sorting into smaller tasks and
 * process them in parallel.
 *
 * We extend RecursiveAction because we don't return a result — we just sort the
 * array directly.
 *
 * @author thiagogoncos
 */
public class DataSorter extends RecursiveAction {

    private final double[] array; // The array we want to sort
    private final int left, right; // The range (left to right index) this task will sort

    /**
     * Constructor that sets the part of the array we will work on
     */
    public DataSorter(double[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    /**
     * This is the method that runs when the task starts. It checks if the array
     * needs to be split, and if so, it does that and runs both halves.
     */
    @Override
    protected void compute() {
        // Only do something if the left index is smaller than the right
        if (left < right) {
            int mid = (left + right) / 2; // Find the middle of the range

            // Create two smaller tasks for left and right halves
            DataSorter leftTask = new DataSorter(array, left, mid);
            DataSorter rightTask = new DataSorter(array, mid + 1, right);

            // Run both tasks at the same time (concurrently)
            invokeAll(leftTask, rightTask);

            // After sorting both parts, merge them together
            merge(left, mid, right);
        }
    }

    /**
     * This method merges two sorted parts into one sorted part (from largest to
     * smallest).
     */
    private void merge(int left, int mid, int right) {
        // Create a temporary array to hold the merged result
        double[] temp = new double[right - left + 1];
        int i = left;       // Pointer for the left part
        int j = mid + 1;    // Pointer for the right part
        int k = 0;          // Pointer for the temp array

        // Compare values from both halves and add the biggest one first
        while (i <= mid && j <= right) {
            if (array[i] > array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        // Add remaining values from the left half (if any)
        while (i <= mid) {
            temp[k++] = array[i++];
        }

        // Add remaining values from the right half (if any)
        while (j <= right) {
            temp[k++] = array[j++];
        }

        // Copy the sorted temp array back into the original array
        System.arraycopy(temp, 0, array, left, temp.length);
    }
}
