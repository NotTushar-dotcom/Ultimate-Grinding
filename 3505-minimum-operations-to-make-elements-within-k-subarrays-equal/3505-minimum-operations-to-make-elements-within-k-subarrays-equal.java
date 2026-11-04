import java.util.Arrays;

class Solution {
    private int[] bitCount;
    private long[] bitSum;
    private int U;

    private void update(int idx, int countDelta, long valDelta) {
        for (; idx <= U; idx += idx & -idx) {
            bitCount[idx] += countDelta;
            bitSum[idx] += valDelta;
        }
    }

    private int queryCount(int idx) {
        int c = 0;
        for (; idx > 0; idx -= idx & -idx) {
            c += bitCount[idx];
        }
        return c;
    }

    private long querySum(int idx) {
        long s = 0;
        for (; idx > 0; idx -= idx & -idx) {
            s += bitSum[idx];
        }
        return s;
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int uniqueCount = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                sorted[uniqueCount++] = sorted[i];
            }
        }

        int[] vals = Arrays.copyOf(sorted, uniqueCount);
        U = uniqueCount;
        bitCount = new int[U + 1];
        bitSum = new long[U + 1];

        int[] ranks = new int[n];
        for (int i = 0; i < n; i++) {
            ranks[i] = Arrays.binarySearch(vals, nums[i]) + 1;
        }

        long[] winCost = new long[n];
        long totalWindowSum = 0;
        int nLow = (x + 1) / 2;
        int nHigh = x - nLow;

        int highestStep = 1;
        while (highestStep <= U) {
            highestStep <<= 1;
        }
        highestStep >>= 1;

        for (int i = 0; i < n; i++) {
            update(ranks[i], 1, nums[i]);
            totalWindowSum += nums[i];

            if (i >= x) {
                update(ranks[i - x], -1, -nums[i - x]);
                totalWindowSum -= nums[i - x];
            }

            if (i >= x - 1) {
                int target = nLow;
                int idx = 0;
                for (int step = highestStep; step > 0; step >>= 1) {
                    if (idx + step <= U && bitCount[idx + step] < target) {
                        idx += step;
                        target -= bitCount[idx];
                    }
                }
                int medianIdx = idx + 1;
                long M = vals[medianIdx - 1];

                int cLess = queryCount(medianIdx - 1);
                long sLess = querySum(medianIdx - 1);

                long sLow = sLess + (long) (nLow - cLess) * M;
                long sHigh = totalWindowSum - sLow;

                winCost[i] = ((long) nLow * M - sLow) + (sHigh - (long) nHigh * M);
            }
        }

        long[] prev = new long[n + 1];
        long[] curr = new long[n + 1];

        for (int c = 1; c <= k; c++) {
            Arrays.fill(curr, Long.MAX_VALUE / 2);
            for (int j = c * x; j <= n; j++) {
                curr[j] = curr[j - 1];
                if (prev[j - x] < Long.MAX_VALUE / 2) {
                    curr[j] = Math.min(curr[j], prev[j - x] + winCost[j - 1]);
                }
            }
            long[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }
}