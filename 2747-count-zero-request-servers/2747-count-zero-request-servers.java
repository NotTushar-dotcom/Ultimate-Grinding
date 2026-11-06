import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        int m = queries.length;
        int[][] sortedQueries = new int[m][2];
        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }

        Arrays.sort(sortedQueries, (a, b) -> Integer.compare(a[0], b[0]));
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        int[] ans = new int[m];
        Map<Integer, Integer> countMap = new HashMap<>();
        int left = 0;
        int right = 0;
        int logLen = logs.length;

        for (int i = 0; i < m; i++) {
            int qTime = sortedQueries[i][0];
            int qIdx = sortedQueries[i][1];
            int startTime = qTime - x;

            while (right < logLen && logs[right][1] <= qTime) {
                int server = logs[right][0];
                countMap.put(server, countMap.getOrDefault(server, 0) + 1);
                right++;
            }

            while (left < logLen && logs[left][1] < startTime) {
                int server = logs[left][0];
                int cnt = countMap.get(server);
                if (cnt == 1) {
                    countMap.remove(server);
                } else {
                    countMap.put(server, cnt - 1);
                }
                left++;
            }

            ans[qIdx] = n - countMap.size();
        }

        return ans;
    }
}