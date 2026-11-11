class Solution {
    public int climbStairs(int n, int[] costs) {
        int[] dp = new int[n + 1];
        dp[0] = 0;

        for (int j = 1; j <= n; j++) {
            int minPrev = dp[j - 1] + 1;
            if (j >= 2) {
                minPrev = Math.min(minPrev, dp[j - 2] + 4);
            }
            if (j >= 3) {
                minPrev = Math.min(minPrev, dp[j - 3] + 9);
            }
            dp[j] = minPrev + costs[j - 1];
        }

        return dp[n];
    }
}