/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csv;

import java.io.*;
import java.util.*;

/**
 * This class is used to read numbers from a CSV file. It reads the file line by
 * line, splits each line by comma, and converts each value into a double
 * number.
 *
 * It returns all the numbers as a double array.
 *
 * @author thiagogoncos
 */
public final class DataReader {

    // Private constructor to stop anyone from creating an object of this class
    // This is common in utility/helper classes
    private DataReader() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * This method reads numbers from a CSV file and returns them as a double
     * array.
     *
     * @param path The path to the CSV file.
     * @return A double array with all the numbers from the file.
     * @throws IOException If the file cannot be read.
     */
    public static double[] readCSV(String path) throws IOException {
        // We store all numbers here before turning them into an array
        List<Double> numbers = new ArrayList<>();

        // Try-with-resources: this makes sure the file is closed after reading
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            // Read one line at a time
            while ((line = reader.readLine()) != null) {
                // Split the line into values using comma
                String[] values = line.split(",");
                // Go through each value
                for (String val : values) {
                    // Trim spaces and turn into a double, then add to the list
                    numbers.add(Double.parseDouble(val.trim()));
                }
            }
        }

        // Turn the list of Doubles into a primitive double array
        return numbers.stream().mapToDouble(d -> d).toArray();
    }
}
