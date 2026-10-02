import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, "", 0, 0, n);
        return result;
    }
    
    private void backtrack(List<String> result, String currentString, int open, int close, int max) {
        if (currentString.length() == max * 2) {
            result.add(currentString);
            return;
        }
        
        if (open < max) {
            backtrack(result, currentString + "(", open + 1, close, max);
        }
        
        if (close < open) {
            backtrack(result, currentString + ")", open, close + 1, max);
        }
    }
}