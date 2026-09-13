import java.util.*;

class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        List<int[]> points1 = new ArrayList<>();
        List<int[]> points2 = new ArrayList<>();

        // Collect coordinates of all 1s
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (img1[r][c] == 1) points1.add(new int[]{r, c});
                if (img2[r][c] == 1) points2.add(new int[]{r, c});
            }
        }

        // Map translation vector (dr, dc) -> count of overlapping 1s
        Map<String, Integer> countMap = new HashMap<>();
        int maxOverlap = 0;

        for (int[] p1 : points1) {
            for (int[] p2 : points2) {
                int dr = p2[0] - p1[0];
                int dc = p2[1] - p1[1];
                String key = dr + "," + dc;
                
                int count = countMap.getOrDefault(key, 0) + 1;
                countMap.put(key, count);
                maxOverlap = Math.max(maxOverlap, count);
            }
        }

        return maxOverlap;
    }
}