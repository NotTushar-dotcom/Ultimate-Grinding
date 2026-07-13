import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> result = new ArrayList<>();
        for (int length = 2; length <= 9; length++) {
            for (int start = 1; start <= 10 - length; start++) {
                int num = 0;
                for (int i = 0; i < length; i++) {
                    num = num * 10 + (start + i);
                }
                if (num >= low && num <= high) {
                    result.add(num);
                }
            }
        }
        
        return result;
    }
}