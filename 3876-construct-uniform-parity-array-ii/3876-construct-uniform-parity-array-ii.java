class Solution {
    public boolean uniformArray(int[] nums1) {
        int min = Integer.MAX_VALUE;
        boolean hasOdd = false;

        for (int x : nums1) {
            if (x < min) {
                min = x;
            }
            if ((x & 1) == 1) {
                hasOdd = true;
            }
        }

        return (min & 1) == 1 || !hasOdd;
    }
}