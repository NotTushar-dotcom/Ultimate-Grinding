class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1_000_000_007;

        int[][][] memo = new int[m][n][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                memo[i][j][0] = -2;
                memo[i][j][1] = -2;
            }
        }

        int[][] dp = new int[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == 0 || (i == m - 1 && j == n - 1)) {
                    continue;
                }

                int rightTarget = getTarget(i, j + 1, 0, grid, memo, m, n);
                if (rightTarget != -1) {
                    int rx = rightTarget / n;
                    int ry = rightTarget % n;
                    dp[rx][ry] = (dp[rx][ry] + dp[i][j]) % MOD;
                }

                int downTarget = getTarget(i + 1, j, 1, grid, memo, m, n);
                if (downTarget != -1) {
                    int dx = downTarget / n;
                    int dy = downTarget % n;
                    dp[dx][dy] = (dp[dx][dy] + dp[i][j]) % MOD;
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    private int getTarget(int r, int c, int dir, int[][] grid, int[][][] memo, int m, int n) {
        if (r >= m || c >= n) {
            return -1;
        }
        if (grid[r][c] == 0) {
            return r * n + c;
        }
        if (memo[r][c][dir] != -2) {
            return memo[r][c][dir];
        }

        int res;
        if (dir == 0) {
            res = getTarget(r + 1, c, 1, grid, memo, m, n);
        } else {
            res = getTarget(r, c + 1, 0, grid, memo, m, n);
        }

        return memo[r][c][dir] = res;
    }
}