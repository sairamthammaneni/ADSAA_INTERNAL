import java.util.*;

public class TSPBranchBoundSimple {
    static int N;
    static int finalRes = Integer.MAX_VALUE;
    static int[] finalPath;

    static int firstMin(int[][] graph, int i) {
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < N; k++)
            if (i != k && graph[i][k] < min)
                min = graph[i][k];
        return min;
    }

    static int secondMin(int[][] graph, int i) {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int j = 0; j < N; j++) {
            if (i == j) continue;
            if (graph[i][j] <= first) {
                second = first;
                first = graph[i][j];
            } else if (graph[i][j] < second)
                second = graph[i][j];
        }
        return second;
    }

    static void tspRecursive(int[][] graph, int currBound, int currWeight, int level, int[] currPath, boolean[] visited) {
        if (level == N) {
            if (graph[currPath[level - 1]][currPath[0]] != 0) {
                int currRes = currWeight + graph[currPath[level - 1]][currPath[0]];
                if (currRes < finalRes) {
                    System.arraycopy(currPath, 0, finalPath, 0, N);
                    finalPath[N] = currPath[0];
                    finalRes = currRes;
                }
            }
            return;
        }

        for (int i = 0; i < N; i++) {
            if (graph[currPath[level - 1]][i] != 0 && !visited[i]) {
                int temp = currBound;
                currWeight += graph[currPath[level - 1]][i];

                if (level == 1)
                    currBound -= (firstMin(graph, currPath[level - 1]) + firstMin(graph, i)) / 2;
                else
                    currBound -= (secondMin(graph, currPath[level - 1]) + firstMin(graph, i)) / 2;

                if (currBound + currWeight < finalRes) {
                    currPath[level] = i;
                    visited[i] = true;
                    tspRecursive(graph, currBound, currWeight, level + 1, currPath, visited);
                }

                currWeight -= graph[currPath[level - 1]][i];
                currBound = temp;
                Arrays.fill(visited, false);
                for (int j = 0; j < level; j++)
                    visited[currPath[j]] = true;
            }
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
            { 0, 10, 15, 20 },
            { 10, 0, 35, 25 },
            { 15, 35, 0, 30 },
            { 20, 25, 30, 0 }
        };

        N = graph.length;
        finalPath = new int[N + 1];
        int[] currPath = new int[N + 1];
        boolean[] visited = new boolean[N];

        int currBound = 0;
        for (int i = 0; i < N; i++)
            currBound += (firstMin(graph, i) + secondMin(graph, i));
        currBound = (currBound % 2 == 1) ? (currBound / 2 + 1) : (currBound / 2);

        visited[0] = true;
        currPath[0] = 0;

        tspRecursive(graph, currBound, 0, 1, currPath, visited);

        System.out.println("Minimum cost: " + finalRes);
        System.out.print("Path taken: ");
        for (int i = 0; i <= N; i++)
            System.out.print(finalPath[i] + " ");
        System.out.println();
    }
}
