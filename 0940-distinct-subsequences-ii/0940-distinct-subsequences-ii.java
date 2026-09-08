class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;
        // last[c] stores the count of distinct subsequences ending with character ('a' + c)
        int[] last = new int[26];
        int total = 0; // Total count of distinct non-empty subsequences formed so far

        for (char ch : s.toCharArray()) {
            int idx = ch - 'a';
            
            // Appending 'ch' to all existing subsequences plus the empty subsequence
            int currentEndsWithChar = (total + 1) % MOD;
            
            // Calculate new subsequences added by 'ch', subtracting duplicates previously ending in 'ch'
            int diff = (currentEndsWithChar - last[idx] + MOD) % MOD;
            
            total = (total + diff) % MOD;
            last[idx] = currentEndsWithChar;
        }

        return total;
    }
}
