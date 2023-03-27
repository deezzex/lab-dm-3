package com.deezzex;

import java.util.Arrays;

public class SalesmanProblemSolver {
    private final int[][] adjacencyMatrix;
    private final Integer numberOfNodes;
    private final int[] finalRoute;
    private final boolean[] visitedNodes;
    private Integer finalLengthOfRoute;

    public SalesmanProblemSolver(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.numberOfNodes = adjacencyMatrix[0].length;
        this.finalRoute = new int[this.numberOfNodes + 1];
        this.visitedNodes = new boolean[this.numberOfNodes];
        this.finalLengthOfRoute = Integer.MAX_VALUE;
    }

    public void solve() {
        int[] currPath = new int[numberOfNodes + 1];

        int currBound = 0;
        Arrays.fill(currPath, -1);
        Arrays.fill(visitedNodes, false);

        for (int i = 0; i < numberOfNodes; i++) {
            currBound += (findFirstMinimum(adjacencyMatrix, i) + findSecondMinimum(adjacencyMatrix, i));
        }

        currBound = (currBound == 1) ? 1 : currBound / 2;

        visitedNodes[0] = true;
        currPath[0] = 0;

        solveRecursively(adjacencyMatrix, currBound, 0, 1, currPath);

        printResults();
    }

    private void copyToFinal(int[] currPath) {
        if (numberOfNodes >= 0) {
            System.arraycopy(currPath, 0, finalRoute, 0, numberOfNodes);
        }

        finalRoute[numberOfNodes] = currPath[0];
    }

    private int findFirstMinimum(int[][] adjacencyMatrix, int i) {
        int min = Integer.MAX_VALUE;

        for (int k = 0; k < numberOfNodes; k++) {
            if (adjacencyMatrix[i][k] < min && i != k) {
                min = adjacencyMatrix[i][k];
            }
        }

        return min;
    }

    private int findSecondMinimum(int[][] adjacencyMatrix, int i) {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int j = 0; j < numberOfNodes; j++) {
            if (i == j) {
                continue;
            }

            if (adjacencyMatrix[i][j] <= first) {
                second = first;
                first = adjacencyMatrix[i][j];
            } else if (adjacencyMatrix[i][j] <= second && adjacencyMatrix[i][j] != first) {
                second = adjacencyMatrix[i][j];
            }

        }
        return second;
    }

    private void solveRecursively(int[][] adjacencyMatrix, int currentBound, int currentWeight, int level, int[] currentPath) {
        if (level == numberOfNodes) {
            if (adjacencyMatrix[currentPath[level - 1]][currentPath[0]] != 0) {

                int currentResult = currentWeight + adjacencyMatrix[currentPath[level - 1]][currentPath[0]];

                if (currentResult < finalLengthOfRoute) {
                    copyToFinal(currentPath);
                    finalLengthOfRoute = currentResult;
                }
            }
            return;
        }

        for (int i = 0; i < numberOfNodes; i++) {
            if (adjacencyMatrix[currentPath[level - 1]][i] != 0 && !visitedNodes[i]) {
                int temp = currentBound;
                currentWeight += adjacencyMatrix[currentPath[level - 1]][i];

                if (level == 1) {
                    currentBound -= ((findFirstMinimum(adjacencyMatrix, currentPath[level - 1]) + findFirstMinimum(adjacencyMatrix, i)) / 2);
                } else {
                    currentBound -= ((findSecondMinimum(adjacencyMatrix, currentPath[level - 1]) + findFirstMinimum(adjacencyMatrix, i)) / 2);
                }

                if (currentBound + currentWeight < finalLengthOfRoute) {
                    currentPath[level] = i;
                    visitedNodes[i] = true;

                    solveRecursively(adjacencyMatrix, currentBound, currentWeight, level + 1, currentPath);
                }

                currentWeight -= adjacencyMatrix[currentPath[level - 1]][i];
                currentBound = temp;

                Arrays.fill(visitedNodes, false);
                for (int j = 0; j <= level - 1; j++) {
                    visitedNodes[currentPath[j]] = true;
                }
            }
        }
    }

    private void printResults() {
        System.out.println("Вирішення задачі комівояжера методом гілок та границь.");
        System.out.println();
        System.out.println("Вхідна матриця суміжності: ");
        printInputMatrix(adjacencyMatrix);
        System.out.print("Оптимальний маршрут : ");
        for (int i = 0; i <= numberOfNodes; i++) {
            System.out.printf("%d ", finalRoute[i]);
        }
        System.out.println();
        System.out.println("Довжина маршруту : " + finalLengthOfRoute);
    }

    private void printInputMatrix(int[][] matrix) {
        for (int[] curRow : matrix) {
            for (int curCol : curRow) System.out.printf("%d ", curCol);
            System.out.println();
        }
        System.out.println();
    }
}
