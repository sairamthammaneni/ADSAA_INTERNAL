import java.util.*;

public class TSPBranchAndBound {
    static int N; // Number of cities
    static int[][] graph; // Cost matrix
    static int finalRes = Integer.MAX_VALUE; // Final result (minimum cost)
    static int[] finalPath; // Path of the final result

    // Function to copy temporary solution to final path
    static void copyToFinal(int[] currPath) {
        for (int i = 0; i < N; i++)
            finalPath[i] = currPath[i];
        finalPath[N] = currPath[0];
    }

    // Function to find the minimum edge cost from a node
    static int firstMin(int[][] graph, int i) {
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < N; k++)
            if (graph[i][k] < min && i != k)
                min = graph[i][k];
        return min;
    }

    // Function to find the second minimum edge cost from a node
    static int secondMin(int[][] graph, int i) {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int j = 0; j < N; j++) {
            if (i == j)
                continue;
            if (graph[i][j] <= first) {
                second = first;
                first = graph[i][j];
            } else if (graph[i][j] <= second && graph[i][j] != first)
                second = graph[i][j];
        }
        return second;
    }

    // Recursive function to solve TSP
    static void TSPRec(int[][] graph, int currBound, int currWeight, int level, int[] currPath, boolean[] visited) {
        if (level == N) {
            if (graph[currPath[level - 1]][currPath[0]] != 0) {
                int currRes = currWeight + graph[currPath[level - 1]][currPath[0]];
                if (currRes < finalRes) {
                    copyToFinal(currPath);
                    finalRes = currRes;
                }
            }
            return;
        }

        for (int i = 0; i < N; i++) {
            if (graph[currPath[level - 1]][i] != 0 && !visited[i]) {
                int temp = currBound;
                currWeight += graph[currPath[level - 1]][i];

                // Calculate new bound
                if (level == 1)
                    currBound -= ((firstMin(graph, currPath[level - 1]) + firstMin(graph, i)) / 2);
                else
                    currBound -= ((secondMin(graph, currPath[level - 1]) + firstMin(graph, i)) / 2);

                if (currBound + currWeight < finalRes) {
                    currPath[level] = i;
                    visited[i] = true;

                    TSPRec(graph, currBound, currWeight, level + 1, currPath, visited);
                }

                // Backtrack
                currWeight -= graph[currPath[level - 1]][i];
                currBound = temp;

                Arrays.fill(visited, false);
                for (int j = 0; j <= level - 1; j++)
                    visited[currPath[j]] = true;
            }
        }
    }

    // Main function to set up and solve TSP
    static void tsp(int[][] costMatrix) {
        N = costMatrix.length;
        graph = costMatrix;
        int[] currPath = new int[N + 1];
        boolean[] visited = new boolean[N];
        int currBound = 0;

        finalPath = new int[N + 1];
        Arrays.fill(currPath, -1);
        Arrays.fill(finalPath, -1);

        // Compute initial bound
        for (int i = 0; i < N; i++)
            currBound += (firstMin(graph, i) + secondMin(graph, i));
        currBound = (currBound % 2 == 1) ? (currBound / 2) + 1 : currBound / 2;

        visited[0] = true;
        currPath[0] = 0;

        TSPRec(graph, currBound, 0, 1, currPath, visited);

        // Print result
        System.out.print("Minimum cost: " + finalRes + "\nPath Taken: ");
        for (int i = 0; i <= N; i++)
            System.out.print(finalPath[i] + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] costMatrix = {
            { 0, 10, 15, 20 },
            { 10, 0, 35, 25 },
            { 15, 35, 0, 30 },
            { 20, 25, 30, 0 }
        };

        tsp(costMatrix);
    }
}
