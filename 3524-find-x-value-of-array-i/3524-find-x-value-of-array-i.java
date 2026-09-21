class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] result = new long[k];
        long[] count = new long[k];
        
        for (int num : nums) {
            int val = num % k;
            long[] nextCount = new long[k];
            
            nextCount[val]++;
            
            for (int r = 0; r < k; r++) {
                if (count[r] > 0) {
                    nextCount[(r * val) % k] += count[r];
                }
            }
            for (int r = 0; r < k; r++) {
                result[r] += nextCount[r];
            }
            
            count = nextCount;
        }
        
        return result;
    }
}