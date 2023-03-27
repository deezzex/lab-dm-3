package com.deezzex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class MatrixUtil {
    public static int[][] getInputMatrix(String fileName) {
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(getFile(fileName))));
            boolean firstLine = true;
            int N = 0;
            int[][] matrix = new int[0][];

            while (sc.hasNextLine()) {
                if (firstLine) {
                    String[] line = sc.nextLine().trim().split(" ");
                    N = Integer.parseInt(line[0]);
                    firstLine = false;
                }

                matrix = new int[N][N];

                for (int i = 0; i < matrix.length; i++) {
                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j = 0; j < line.length; j++) {
                        matrix[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }

            sc.close();

            return matrix;
        } catch (Exception exception) {
            throw new RuntimeException("Cannot read the input file.");
        }
    }

    private static File getFile(String fileName) throws URISyntaxException {
        ClassLoader classLoader = MatrixUtil.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new RuntimeException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }
}
