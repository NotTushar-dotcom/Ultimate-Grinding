class Solution {
    public long sumAndMultiply(int n) {
        if (n == 0) {
            return 0;
        }
        
        long x = 0; 
        int sum = 0;
        
        String numStr = String.valueOf(n);
        
        for (char c : numStr.toCharArray()) {
            if (c != '0') {
                int digit = c - '0';
                x = x * 10 + digit;
                sum += digit;
            }
        }
        return x * sum;
    }
}