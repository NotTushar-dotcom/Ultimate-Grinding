import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[][] sortedNodes = new int[n][2];
        for (int i = 0; i < n; i++) {
            sortedNodes[i][0] = nums[i];
            sortedNodes[i][1] = i;
        }
        
        Arrays.sort(sortedNodes, Comparator.comparingInt(a -> a[0]));
        
        int[] pos = new int[n];
        for (int i = 0; i < n; i++) {
            pos[sortedNodes[i][1]] = i;
        }
        
        int[] R = new int[n];
        int right = 0;
        for (int i = 0; i < n; i++) {
            while (right + 1 < n && sortedNodes[right + 1][0] - sortedNodes[i][0] <= maxDiff) {
                right++;
            }
            R[i] = right;
        }
        
        int LOG = 18;
        int[][] up = new int[n][LOG];
        
        for (int i = 0; i < n; i++) {
            up[i][0] = R[i];
        }
        
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                up[i][j] = up[ up[i][j-1] ][ j-1 ];
            }
        }
        
        int[] ans = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int u = pos[queries[q][0]];
            int v = pos[queries[q][1]];
            
            if (u > v) {
                int temp = u; 
                u = v; 
                v = temp;
            }
            
            if (u == v) {
                ans[q] = 0;
                continue;
            }
            
            if (up[u][LOG - 1] < v) {
                ans[q] = -1;
                continue;
            }
            
            int steps = 0;
            int curr = u;
            
            for (int j = LOG - 1; j >= 0; j--) {
                if (up[curr][j] < v) {
                    curr = up[curr][j];
                    steps += (1 << j);
                }
            }
            
            ans[q] = steps + 1;
        }
        
        return ans;
    }
}