class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int MAX_XOR = 2048; 
        
        boolean[] present = new boolean[MAX_XOR];
        int uniqueCount = 0;
        for (int num : nums) {
            if (!present[num]) {
                present[num] = true;
                uniqueCount++;
            }
        }
        
        int[] uniqueNums = new int[uniqueCount];
        int idx = 0;
        for (int i = 0; i < MAX_XOR; i++) {
            if (present[i]) {
                uniqueNums[idx++] = i;
            }
        }
        
        boolean[] round1 = new boolean[MAX_XOR];
        for (int num : uniqueNums) {
            round1[num] = true;
        }
        
        boolean[] round2 = new boolean[MAX_XOR];
        for (int i = 0; i < MAX_XOR; i++) {
            if (round1[i]) {
                for (int num : uniqueNums) {
                    round2[i ^ num] = true;
                }
            }
        }
        
        boolean[] round3 = new boolean[MAX_XOR];
        int uniqueTripletsCount = 0;
        
        for (int i = 0; i < MAX_XOR; i++) {
            if (round2[i]) {
                for (int num : uniqueNums) {
                    int finalXor = i ^ num;
                    if (!round3[finalXor]) {
                        round3[finalXor] = true;
                        uniqueTripletsCount++;
                    }
                }
            }
        }
        
        return uniqueTripletsCount;
    }
}