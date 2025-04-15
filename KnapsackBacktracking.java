import java.util.*;

public class KnapsackBacktracking {
    static int maxProfit = 0;

    // Recursive backtracking function
    static void knapsack(int[] weights, int[] values, int capacity, int index, int currentProfit, int currentWeight) {
        // If weight exceeds capacity, return
        if (currentWeight > capacity) {
            return;
        }

        // Update maxProfit if currentProfit is higher
        if (currentProfit > maxProfit) {
            maxProfit = currentProfit;
        }

        // Explore further items
        for (int i = index; i < weights.length; i++) {
            knapsack(weights, values, capacity, i + 1,
                    currentProfit + values[i], currentWeight + weights[i]);
        }
    }

    public static void main(String[] args) {
        // Example input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        int[] weights = new int[n];
        int[] values = new int[n];

        System.out.println("Enter weights of items:");
        for (int i = 0; i < n; i++)
            weights[i] = sc.nextInt();

        System.out.println("Enter values of items:");
        for (int i = 0; i < n; i++)
            values[i] = sc.nextInt();

        System.out.print("Enter capacity of knapsack: ");
        int capacity = sc.nextInt();

        // Call backtracking function
        knapsack(weights, values, capacity, 0, 0, 0);

        // Print result
        System.out.println("Maximum value in knapsack = " + maxProfit);
        sc.close();
    }
}
