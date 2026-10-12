class Solution {
    public int maxSum(List<Integer> nums, int k) {
        int[] count = new int[31];
        for (int x : nums) {
            for (int i = 0; i < 31; i++) {
                if (((x >> i) & 1) == 1) {
                    count[i]++;
                }
            }
        }

        long ans = 0;
        long mod = 1_000_000_007L;

        for (int step = 0; step < k; step++) {
            long val = 0;
            for (int i = 0; i < 31; i++) {
                if (count[i] > 0) {
                    val |= (1L << i);
                    count[i]--;
                }
            }
            ans = (ans + (val * val) % mod) % mod;
        }

        return (int) ans;
    }
}