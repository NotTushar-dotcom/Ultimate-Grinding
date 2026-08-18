class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;
        int[] subarrayCount = new int[51]; 
        
        for (int i = 0; i <= n - k; i++) {
            boolean[] seenInSubarray = new boolean[51];
            for (int j = i; j < i + k; j++) {
                seenInSubarray[nums[j]] = true;
            }
            
            for (int val = 0; val <= 50; val++) {
                if (seenInSubarray[val]) {
                    subarrayCount[val]++;
                }
            }
        }

        int ans = -1;
        for (int val = 50; val >= 0; val--) {
            if (subarrayCount[val] == 1) {
                return val;
            }
        }

        return ans;
    }
}