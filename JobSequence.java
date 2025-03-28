import java.util.*;

class JobSequence {

    static ArrayList<Integer> jobSequencing(int[] id, int[] deadline, int[] profit) {
        int n = id.length;
        ArrayList<Integer> ans = new ArrayList<>(Arrays.asList(0, 0));

        // Find the maximum deadline to create the result array
        int maxDeadline = Arrays.stream(deadline).max().getAsInt();

        // Pair the profit and deadline of all the jobs together
        ArrayList<int[]> jobs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            jobs.add(new int[]{profit[i], deadline[i]});
        }

        // Sort the jobs based on profit in decreasing order
        jobs.sort((a, b) -> b[0] - a[0]);

        // Array to store result of job sequence
        int[] result = new int[maxDeadline];
        Arrays.fill(result, -1);

        for (int i = 0; i < n; i++) {
            int start = Math.min(maxDeadline, jobs.get(i)[1]) - 1;
            for (int j = start; j >= 0; j--) {

                // If slot is empty
                if (result[j] == -1) {
                    result[j] = i;
                    break;
                }
            }
        }

        for (int i = 0; i < maxDeadline; i++) {
            if (result[i] != -1) {
                ans.set(1, ans.get(1) + jobs.get(result[i])[0]);
                ans.set(0, ans.get(0) + 1);
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] id = {1, 2, 3, 4, 5};
        int[] deadline = {2, 1, 2, 1, 1};
        int[] profit = {100, 19, 27, 25, 15};
        ArrayList<Integer> ans = jobSequencing(id, deadline, profit);
        System.out.println(ans.get(0) + " " + ans.get(1));
    }
}