import java.util.Arrays;
import java.util.List;

class Solution {
    public int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();
        int MOD = 1_000_000_007;
        
        // dp[r][c] stores the max score from 'S' to (r, c)
        int[][] dp = new int[n][n];
        // paths[r][c] stores the number of paths to get the max score at (r, c)
        int[][] paths = new int[n][n];
        
        // Initialize DP table with -1 indicating unreachable cells
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
        
        // Base case: Starting position
        dp[n - 1][n - 1] = 0;
        paths[n - 1][n - 1] = 1;
        
        // Directions: Down, Right, Down-Right (relative to iterating backwards)
        int[][] dirs = {{1, 0}, {0, 1}, {1, 1}};
        
        // Iterate from bottom-right to top-left
        for (int r = n - 1; r >= 0; r--) {
            for (int c = n - 1; c >= 0; c--) {
                char ch = board.get(r).charAt(c);
                
                // Skip the start and obstacles
                if (ch == 'S' || ch == 'X') {
                    continue;
                }
                
                int maxScore = -1;
                int ways = 0;
                
                // Check all 3 possible previous cells
                for (int[] d : dirs) {
                    int nr = r + d[0];
                    int nc = c + d[1];
                    
                    // If the previous cell is within bounds and reachable
                    if (nr < n && nc < n && dp[nr][nc] != -1) {
                        if (dp[nr][nc] > maxScore) {
                            maxScore = dp[nr][nc];
                            ways = paths[nr][nc];
                        } else if (dp[nr][nc] == maxScore) {
                            ways = (ways + paths[nr][nc]) % MOD;
                        }
                    }
                }
                
                // If the current cell is reachable from at least one valid path
                if (maxScore != -1) {
                    int val = (ch == 'E') ? 0 : (ch - '0');
                    dp[r][c] = maxScore + val;
                    paths[r][c] = ways;
                }
            }
        }
        
        // If the destination 'E' is unreachable
        if (dp[0][0] == -1) {
            return new int[]{0, 0};
        }
        
        return new int[]{dp[0][0], paths[0][0]};
    }
}