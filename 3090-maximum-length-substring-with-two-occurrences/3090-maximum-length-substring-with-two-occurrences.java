class Solution {
    public int maximumLengthSubstring(String s) {
        // Array to store the frequency of each lowercase English letter
        int[] freq = new int[26];
        int maxLen = 0;
        int left = 0;
        
        // Expand the window with the right pointer
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            freq[rightChar - 'a']++;
            
            // If the character count exceeds 2, shrink the window from the left
            while (freq[rightChar - 'a'] > 2) {
                char leftChar = s.charAt(left);
                freq[leftChar - 'a']--;
                left++;
            }
            
            // Update the maximum length found so far
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
}