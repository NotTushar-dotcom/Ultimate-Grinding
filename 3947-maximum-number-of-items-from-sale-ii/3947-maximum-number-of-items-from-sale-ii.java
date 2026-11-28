import java.util.Arrays;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] cnt = new int[n + 1];
        long minPrice = Long.MAX_VALUE;
        for (int[] item : items) {
            cnt[item[0]]++;
            if (item[1] < minPrice) {
                minPrice = item[1];
            }
        }

        int[] multCnt = new int[n + 1];
        for (int d = 1; d <= n; d++) {
            int sum = 0;
            for (int m = d; m <= n; m += d) {
                sum += cnt[m];
            }
            multCnt[d] = sum;
        }

        Integer[] order = new Integer[n];
        int[] gains = new int[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
            gains[i] = multCnt[items[i][0]] - 1;
        }

        Arrays.sort(order, (a, b) -> Integer.compare(items[a][1], items[b][1]));

        long rem = budget;
        long ans = 0;
        long threshold = 2L * minPrice;

        for (int idx : order) {
            long price = items[idx][1];
            if (price >= threshold) {
                break;
            }
            int g = gains[idx];
            if (g <= 0 || rem < price) {
                continue;
            }
            long take = Math.min((long) g, rem / price);
            ans += 2L * take;
            rem -= take * price;
        }

        ans += rem / minPrice;
        return (int) ans;
    }
}