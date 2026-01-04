package Util;

import java.io.*;
import java.util.*;

public class ATSPReader {

    public static int[][] read(File file) throws IOException {
        int dimension = -1;
        List<Integer> values = new ArrayList<>();
        boolean readingMatrix = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("DIMENSION")) {
                    dimension = Integer.parseInt(line.split(":")[1].trim());
                }
                else if (line.equals("EDGE_WEIGHT_SECTION")) {
                    readingMatrix = true;
                }
                else if (readingMatrix) {
                    if (line.equals("EOF")) break;

                    String[] parts = line.split("\\s+");
                    for (String p : parts) {
                        values.add(Integer.parseInt(p));
                    }
                }
            }
        }

        if (dimension <= 0) {
            throw new IllegalArgumentException("Dimensión inválida");
        }

        int[][] matrix = new int[dimension][dimension];
        int index = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = values.get(index++);
            }
        }

        return matrix;
    }
}
