import java.util.Arrays;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int maxFactor = 1;
        for (int[] item : items) {
            if (item[0] > maxFactor) {
                maxFactor = item[0];
            }
        }

        int[] factorCount = new int[maxFactor + 1];
        for (int[] item : items) {
            factorCount[item[0]]++;
        }

        int[] bonus = new int[n];
        for (int i = 0; i < n; i++) {
            int f = items[i][0];
            int totalDivisible = 0;
            for (int m = f; m <= maxFactor; m += f) {
                totalDivisible += factorCount[m];
            }
            bonus[i] = totalDivisible - 1;
        }

        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (a, b) -> Integer.compare(items[a][1], items[b][1]));

        final int NEG_INF = -1_000_000_000;
        int[] dp = new int[budget + 1];
        Arrays.fill(dp, NEG_INF);
        dp[0] = 0;

        for (int idx : order) {
            int price = items[idx][1];
            int firstGain = 1 + bonus[idx];

            for (int b = budget; b >= price; b--) {
                if (dp[b - price] != NEG_INF) {
                    dp[b] = Math.max(dp[b], dp[b - price] + firstGain);
                }
            }

            for (int b = price; b <= budget; b++) {
                if (dp[b - price] != NEG_INF) {
                    dp[b] = Math.max(dp[b], dp[b - price] + 1);
                }
            }
        }

        int ans = 0;
        for (int b = 0; b <= budget; b++) {
            if (dp[b] > ans) {
                ans = dp[b];
            }
        }
        return ans;
    }
}