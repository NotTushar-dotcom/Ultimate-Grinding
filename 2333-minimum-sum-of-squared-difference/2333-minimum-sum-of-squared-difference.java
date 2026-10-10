class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int maxDiff = 0;
        int[] diff = new int[n];

        for (int i = 0; i < n; i++) {
            int d = nums1[i] - nums2[i];
            if (d < 0) {
                d = -d;
            }
            diff[i] = d;
            if (d > maxDiff) {
                maxDiff = d;
            }
        }

        int[] count = new int[maxDiff + 1];
        for (int i = 0; i < n; i++) {
            count[diff[i]]++;
        }

        long k = (long) k1 + k2;

        for (int v = maxDiff; v > 0 && k > 0; v--) {
            if (count[v] == 0) {
                continue;
            }

            if (k >= count[v]) {
                k -= count[v];
                count[v - 1] += count[v];
                count[v] = 0;
            } else {
                count[v] -= (int) k;
                count[v - 1] += (int) k;
                k = 0;
            }
        }

        long ans = 0;
        for (int v = 1; v <= maxDiff; v++) {
            if (count[v] > 0) {
                ans += (long) count[v] * v * v;
            }
        }

        return ans;
    }
}