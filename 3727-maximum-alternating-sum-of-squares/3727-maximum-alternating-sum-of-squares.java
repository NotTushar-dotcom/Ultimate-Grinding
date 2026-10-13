import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long[] sq = new long[n];
        
        for (int i = 0; i < n; i++) {
            sq[i] = (long) nums[i] * nums[i];
        }
        
        Arrays.sort(sq);
        
        int negCount = n / 2;
        
        long total = 0;
        for (int i = 0; i < negCount; i++) {
            total -= sq[i];
        }
        
        for (int i = negCount; i < n; i++) {
            total += sq[i];
        }
        
        return total;
    }
}