class Solution {
    public int totalNumbers(int[] digits) {
        int[] count = new int[10];
        for (int d : digits) {
            count[d]++;
        }

        int total = 0;
       
        for (int num = 100; num < 1000; num += 2) {
            int d1 = num / 100;
            int d2 = (num / 10) % 10;
            int d3 = num % 10;

            int[] needed = new int[10];
            needed[d1]++;
            needed[d2]++;
            needed[d3]++;

            if (count[d1] >= needed[d1] && 
                count[d2] >= needed[d2] && 
                count[d3] >= needed[d3]) {
                total++;
            }
        }

        return total;
    }
}