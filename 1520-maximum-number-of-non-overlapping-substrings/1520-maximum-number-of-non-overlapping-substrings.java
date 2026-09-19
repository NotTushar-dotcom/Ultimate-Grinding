import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int[] L = new int[26];
        int[] R = new int[26];
        Arrays.fill(L, -1);
        
        // 1. Find the first (L) and last (R) occurrence of each character
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            if (L[c] == -1) {
                L[c] = i;
            }
            R[c] = i;
        }
        
        List<int[]> validRanges = new ArrayList<>();
        
        // 2. Validate boundaries to form self-contained substrings
        for (int i = 0; i < 26; i++) {
            if (L[i] != -1) {
                int right = checkValid(i, s, L, R);
                if (right != -1) {
                    validRanges.add(new int[]{L[i], right});
                }
            }
        }
        
        // 3. Greedily select the maximum non-overlapping intervals
        // Sort by end time to maximize the count of non-overlapping components
        validRanges.sort((a, b) -> Integer.compare(a[1], b[1]));
        
        List<String> result = new ArrayList<>();
        int lastEnd = -1;
        
        for (int[] range : validRanges) {
            if (range[0] > lastEnd) {
                result.add(s.substring(range[0], range[1] + 1));
                lastEnd = range[1];
            }
        }
        
        return result;
    }
    
    private int checkValid(int i, String s, int[] L, int[] R) {
        int left = L[i];
        int right = R[i];
        
        for (int j = left; j <= right; j++) {
            int c = s.charAt(j) - 'a';
            // If a character inside our range started earlier than our starting point,
            // this range is invalid because it overlaps into another territory.
            if (L[c] < left) {
                return -1;
            }
            // Expand the right boundary to include the current character's full span
            right = Math.max(right, R[c]);
        }
        
        return right;
    }
}