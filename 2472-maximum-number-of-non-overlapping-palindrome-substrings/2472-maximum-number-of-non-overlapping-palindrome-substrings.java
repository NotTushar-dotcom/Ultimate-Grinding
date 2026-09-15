class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        boolean[][] isPalindrome = new boolean[n][n];
        
        //  Precompute all palindromes using DP
        for (int length = 1; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    if (length <= 2) {
                        isPalindrome[i][j] = true;
                    } else {
                        isPalindrome[i][j] = isPalindrome[i + 1][j - 1];
                    }
                }
            }
        }
        
        // DP to find the maximum non-overlapping palindromes
        // dp[i] represents the answer for the prefix s[0...i-1]
        int[] dp = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            // Base case: we don't include the character at i-1 in any new palindrome
            dp[i] = dp[i - 1];
            
            // Check all possible substrings ending at i-1 that have length >= k
            for (int j = 0; j <= i - k; j++) {
                if (isPalindrome[j][i - 1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        
        return dp[n];
    }
}