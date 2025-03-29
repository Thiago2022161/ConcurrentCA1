/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sort;

import java.util.concurrent.RecursiveAction;

/**
 *
 * @author thiagogoncos
 */

public class DataSorter extends RecursiveAction {

    private final double[] array;
    private final int left, right;

    public DataSorter(double[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (left < right) {
            int mid = (left + right) / 2;

            DataSorter leftTask = new DataSorter(array, left, mid);
            DataSorter rightTask = new DataSorter(array, mid + 1, right);

            invokeAll(leftTask, rightTask);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        double[] temp = new double[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (array[i] > array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= mid) temp[k++] = array[i++];
        while (j <= right) temp[k++] = array[j++];

        System.arraycopy(temp, 0, array, left, temp.length);
    }
}