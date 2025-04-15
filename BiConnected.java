import java.util.*;

// This class represents a directed graph using adjacency list representation
public class BiConnected {
    private int V, E; // Number of vertices and edges
    private LinkedList<Integer>[] adj; // Adjacency List

    // Count is number of biconnected components. time is used to find discovery times
    private static int count = 0, time = 0;

    static class Edge {
        int u;
        int v;
        Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }

    // Constructor
    @SuppressWarnings("unchecked")
    BiConnected(int v) {
        V = v;
        E = 0;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList<>();
    }

    // Function to add an edge into the graph
    void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    // A recursive function that finds and prints biconnected components using DFS traversal
    void BCCUtil(int u, int[] disc, int[] low, LinkedList<Edge> st, int[] parent) {
        disc[u] = low[u] = ++time;
        int children = 0;

        for (int v : adj[u]) {
            if (disc[v] == -1) {
                children++;
                parent[v] = u;

                st.add(new Edge(u, v));
                BCCUtil(v, disc, low, st, parent);

                low[u] = Math.min(low[u], low[v]);

                if ((disc[u] == 1 && children > 1) || (disc[u] > 1 && low[v] >= disc[u])) {
                    System.out.println("Biconnected Component:");
                    while (st.getLast().u != u || st.getLast().v != v) {
                        Edge e = st.removeLast();
                        System.out.print(e.u + "--" + e.v + " ");
                    }
                    Edge e = st.removeLast();
                    System.out.println(e.u + "--" + e.v);
                    count++;
                }
            } else if (v != parent[u] && disc[v] < disc[u]) {
                low[u] = Math.min(low[u], disc[v]);
                st.add(new Edge(u, v));
            }
        }
    }

    // The function to do DFS traversal. It uses BCCUtil()
    void BCC() {
        int[] disc = new int[V];
        int[] low = new int[V];
        int[] parent = new int[V];
        LinkedList<Edge> st = new LinkedList<>();

        Arrays.fill(disc, -1);
        Arrays.fill(low, -1);
        Arrays.fill(parent, -1);

        for (int i = 0; i < V; i++) {
            if (disc[i] == -1) {
                BCCUtil(i, disc, low, st, parent);

                if (!st.isEmpty()) {
                    System.out.println("Biconnected Component:");
                    while (!st.isEmpty()) {
                        Edge e = st.removeLast();
                        System.out.print(e.u + "--" + e.v + " ");
                    }
                    System.out.println();
                    count++;
                }
            }
        }
    }

    public static void main(String[] args) {
        BiConnected g = new BiConnected(12);
        g.addEdge(0, 1);
        g.addEdge(1, 0);
        g.addEdge(1, 2);
        g.addEdge(2, 1);
        g.addEdge(1, 3);
        g.addEdge(3, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 2);
        g.addEdge(2, 4);
        g.addEdge(4, 2);
        g.addEdge(3, 4);
        g.addEdge(4, 3);
        g.addEdge(1, 5);
        g.addEdge(5, 1);
        g.addEdge(0, 6);
        g.addEdge(6, 0);
        g.addEdge(5, 6);
        g.addEdge(6, 5);
        g.addEdge(5, 7);
        g.addEdge(7, 5);
        g.addEdge(5, 8);
        g.addEdge(8, 5);
        g.addEdge(7, 8);
        g.addEdge(8, 7);
        g.addEdge(8, 9);
        g.addEdge(9, 8);
        g.addEdge(10, 11);
        g.addEdge(11, 10);

        g.BCC();

        System.out.println("Above are " + count + " biconnected components in graph");
    }
}
