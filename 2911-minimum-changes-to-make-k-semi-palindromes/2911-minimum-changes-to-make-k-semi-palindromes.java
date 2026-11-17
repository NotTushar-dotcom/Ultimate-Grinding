class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int len = j - i + 1;
                int minChangesForSubstring = Integer.MAX_VALUE;
                
                for (int d = 1; d < len; d++) {
                    if (len % d == 0) {
                        int currentChanges = 0;
                        for (int start = 0; start < d; start++) {
                            int l = i + start;
                            int r = i + len - d + start;
                            while (l < r) {
                                if (s.charAt(l) != s.charAt(r)) {
                                    currentChanges++;
                                }
                                l += d;
                                r -= d;
                            }
                        }
                        minChangesForSubstring = Math.min(minChangesForSubstring, currentChanges);
                    }
                }
                cost[i][j] = minChangesForSubstring;
            }
        }

        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            java.util.Arrays.fill(dp[i], 1000000);
        }
        dp[0][0] = 0;

        for (int parts = 1; parts <= k; parts++) {
            for (int i = 1; i <= n; i++) {
                for (int j = (parts - 1) * 2; j < i - 1; j++) {
                    dp[i][parts] = Math.min(dp[i][parts], dp[j][parts - 1] + cost[j][i - 1]);
                }
            }
        }

        return dp[n][k];
    }
}