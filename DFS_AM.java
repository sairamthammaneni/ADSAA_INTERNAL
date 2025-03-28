import java.util.*;

public class DFS_AM {
    
    static void dfs(int[][] graph, boolean[] visited, int start) {
        System.out.print(start + " ");
        visited[start] = true;

        for (int i = 0; i < graph.length; i++) {
            if (graph[start][i] == 1 && !visited[i]) {
                dfs(graph, visited, i);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of vertices: ");
        int n = scanner.nextInt();

        int[][] graph = new int[n][n];
        boolean[] visited = new boolean[n];

        System.out.println("Enter the adjacency matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = scanner.nextInt();
            }
        }

        System.out.print("Enter the starting vertex (0 to " + (n-1) + "): ");
        int startVertex = scanner.nextInt();

        System.out.println("DFS traversal starting from vertex " + startVertex + ":");
        dfs(graph, visited, startVertex);

        scanner.close();
    }
}
