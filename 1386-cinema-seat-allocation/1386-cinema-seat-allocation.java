import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];
            if (col >= 2 && col <= 9) {
                map.put(row, map.getOrDefault(row, 0) | (1 << (col - 1)));
            }
        }

        int count = (n - map.size()) * 2;

        int leftMask = (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4);
        int rightMask = (1 << 5) | (1 << 6) | (1 << 7) | (1 << 8);
        int midMask = (1 << 3) | (1 << 4) | (1 << 5) | (1 << 6);

        for (int mask : map.values()) {
            boolean left = (mask & leftMask) == 0;
            boolean right = (mask & rightMask) == 0;

            if (left && right) {
                count += 2;
            } else if (left || right || (mask & midMask) == 0) {
                count += 1;
            }
        }

        return count;
    }
}