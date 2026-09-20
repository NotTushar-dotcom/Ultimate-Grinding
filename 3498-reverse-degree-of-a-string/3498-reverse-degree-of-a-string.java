class Solution {
    public int reverseDegree(String s) {
        int sum = 0;
        for(int i = 0; i< s.length(); i++){
            int reversedPos = 26 - (s.charAt(i) - 'a');
            int stringPos = i + 1;
            sum += reversedPos * stringPos;
        }
        return sum;
    }
}