import java.util.Arrays;

class Solution {
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> (a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]));
        
        int remaining = 0;
        int maxEnd = -1;
        
        for (int[] interval : intervals) {
            if (interval[1] > maxEnd) {
                remaining++;
                maxEnd = interval[1];
            }
        }
        
        return remaining;
    }
}