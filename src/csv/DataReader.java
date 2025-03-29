/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csv;

import java.io.*;
import java.util.*;

/**
 *
 * @author thiagogoncos
 */
public final class DataReader {

    private DataReader() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static double[] readCSV(String path) throws IOException {
        List<Double> numbers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (String val : values) {
                    numbers.add(Double.parseDouble(val.trim()));
                }
            }
        }

        return numbers.stream().mapToDouble(d -> d).toArray();
    }
}
