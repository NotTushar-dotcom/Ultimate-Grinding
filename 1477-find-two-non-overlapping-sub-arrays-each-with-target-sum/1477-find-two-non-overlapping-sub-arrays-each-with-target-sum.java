import java.util.Arrays;

public class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        // minLen[i] stores the minimum length of a valid subarray found in arr[0...i]
        int[] minLen = new int[n];
        Arrays.fill(minLen, Integer.MAX_VALUE);
        
        int left = 0;
        int currentSum = 0;
        int minTotalLength = Integer.MAX_VALUE;
        int currentMinLen = Integer.MAX_VALUE;
        
        for (int right = 0; right < n; right++) {
            currentSum += arr[right];
            
            // Shrink the window from the left if the sum exceeds the target
            while (currentSum > target) {
                currentSum -= arr[left];
                left++;
            }
            
            // If we found a valid subarray matching the target
            if (currentSum == target) {
                int currentLength = right - left + 1;
                
                // Check if a valid non-overlapping subarray exists to the left of our window
                if (left > 0 && minLen[left - 1] != Integer.MAX_VALUE) {
                    minTotalLength = Math.min(minTotalLength, minLen[left - 1] + currentLength);
                }
                
                // Track the smallest single subarray length seen so far up to the current right index
                currentMinLen = Math.min(currentMinLen, currentLength);
            }
            
            // Propagate the minimum length found so far to the DP array
            minLen[right] = currentMinLen;
        }
        
        // Return the minimum total length if found, otherwise return -1
        return minTotalLength == Integer.MAX_VALUE ? -1 : minTotalLength;
    }
}