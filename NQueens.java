import java.util.*;

public class NQueens {
    final int N;

    NQueens(int N) {
        this.N = N;
    }

    // Utility function to print the solution board
    void printSolution(int[][] board) {
        for (int[] row : board) {
            for (int val : row)
                System.out.print(val + " ");
            System.out.println();
        }
    }

    // Check if a queen can be placed at board[row][col]
    boolean isSafe(int[][] board, int row, int col) {
        // Check column above
        for (int i = 0; i < row; i++)
            if (board[i][col] == 1)
                return false;

        // Check upper-left diagonal
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        // Check upper-right diagonal
        for (int i = row, j = col; i >= 0 && j < N; i--, j++)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    // Recursive utility to solve N-Queens
    boolean solveNQueensUtil(int[][] board, int row) {
        if (row >= N)
            return true; // All queens placed

        for (int col = 0; col < N; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 1; // Place queen

                if (solveNQueensUtil(board, row + 1))
                    return true; // Recurse for next row

                board[row][col] = 0; // Backtrack
            }
        }

        return false; // No valid column in this row
    }

    // Main function to solve N-Queens problem
    void solve() {
        int[][] board = new int[N][N];

        if (!solveNQueensUtil(board, 0)) {
            System.out.println("Solution does not exist");
        } else {
            printSolution(board);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of queens (N): ");
        int N = sc.nextInt();

        NQueens queenSolver = new NQueens(N);
        queenSolver.solve();

        sc.close();
    }
}
