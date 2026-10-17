import java.util.HashMap;
import java.util.Map;

class Solution {
    private static final int MOD = 1_000_000_007;

    private static class SegmentTree {
        int n;
        long[] sum;
        long[] sqSum;
        long[] lazy;

        SegmentTree(int n) {
            this.n = n;
            this.sum = new long[4 * n];
            this.sqSum = new long[4 * n];
            this.lazy = new long[4 * n];
        }

        private void apply(int node, int l, int r, long val) {
            long len = r - l + 1;
            val %= MOD;
            long term2 = (2 * val % MOD) * sum[node] % MOD;
            long term3 = (val * val % MOD) * len % MOD;

            sqSum[node] = (sqSum[node] + term2 + term3) % MOD;
            sum[node] = (sum[node] + val * len) % MOD;
            lazy[node] = (lazy[node] + val) % MOD;
        }

        private void push(int node, int l, int r) {
            if (lazy[node] != 0) {
                int mid = l + (r - l) / 2;
                apply(2 * node, l, mid, lazy[node]);
                apply(2 * node + 1, mid + 1, r, lazy[node]);
                lazy[node] = 0;
            }
        }

        public void update(int node, int l, int r, int ql, int qr, long val) {
            if (ql <= l && r <= qr) {
                apply(node, l, r, val);
                return;
            }
            push(node, l, r);
            int mid = l + (r - l) / 2;
            if (ql <= mid) {
                update(2 * node, l, mid, ql, qr, val);
            }
            if (qr > mid) {
                update(2 * node + 1, mid + 1, r, ql, qr, val);
            }
            sum[node] = (sum[2 * node] + sum[2 * node + 1]) % MOD;
            sqSum[node] = (sqSum[2 * node] + sqSum[2 * node + 1]) % MOD;
        }

        public long getSqSum() {
            return sqSum[1];
        }
    }

    public int sumCounts(int[] nums) {
        int n = nums.length;
        SegmentTree st = new SegmentTree(n);
        Map<Integer, Integer> lastSeen = new HashMap<>();

        long totalAns = 0;

        for (int i = 0; i < n; i++) {
            int prevIdx = lastSeen.getOrDefault(nums[i], -1);
            st.update(1, 0, n - 1, prevIdx + 1, i, 1);
            totalAns = (totalAns + st.getSqSum()) % MOD;

            lastSeen.put(nums[i], i);
        }

        return (int) totalAns;
    }
}