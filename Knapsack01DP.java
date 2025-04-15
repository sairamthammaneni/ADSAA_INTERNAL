import java.util.*;

public class Knapsack01DP {
    // Function to solve 0/1 Knapsack using Dynamic Programming
    static int knapsack(int W, int[] weights, int[] values, int n) {
        int[][] dp = new int[n + 1][W + 1];

        // Build table dp[][] in bottom-up manner
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0)
                    dp[i][w] = 0;
                else if (weights[i - 1] <= w)
                    dp[i][w] = Math.max(values[i - 1] + dp[i - 1][w - weights[i - 1]], dp[i - 1][w]);
                else
                    dp[i][w] = dp[i - 1][w];
            }
        }

        // Return the maximum value that can be put in a knapsack of capacity W
        return dp[n][W];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        int[] values = new int[n];
        int[] weights = new int[n];

        System.out.println("Enter the values of items:");
        for (int i = 0; i < n; i++)
            values[i] = sc.nextInt();

        System.out.println("Enter the weights of items:");
        for (int i = 0; i < n; i++)
            weights[i] = sc.nextInt();

        System.out.print("Enter capacity of knapsack: ");
        int W = sc.nextInt();

        int maxValue = knapsack(W, weights, values, n);
        System.out.println("Maximum value that can be obtained = " + maxValue);

        sc.close();
    }
}
