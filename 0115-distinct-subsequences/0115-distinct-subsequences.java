class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();

        // dp[j] stores the number of distinct subsequences of s[0...i-1] matching t[0...j-1]
        // Using unsigned/long or double if needed during intermediate accumulation, 
        // but problem guarantees answer fits in a 32-bit signed integer.
        int[] dp = new int[n + 1];

        // An empty string t can always be formed from any prefix of s in exactly 1 way
        dp[0] = 1;

        for (int i = 1; i <= m; i++) {
            char sc = s.charAt(i - 1);
            // Traverse backwards so dp[j - 1] represents the state from the previous i - 1 iteration
            for (int j = n; j >= 1; j--) {
                if (sc == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }

        return dp[n];
    }
}