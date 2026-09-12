import java.util.*;

class Solution {
    static class Interval {
        int l, r, weight, id;

        Interval(int l, int r, int weight, int id) {
            this.l = l;
            this.r = r;
            this.weight = weight;
            this.id = id;
        }
    }

    static class Result {
        long score;
        List<Integer> ids;

        Result(long score, List<Integer> ids) {
            this.score = score;
            this.ids = ids;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Interval[] arr = new Interval[n];
        for (int i = 0; i < n; i++) {
            List<Integer> it = intervals.get(i);
            arr[i] = new Interval(it.get(0), it.get(1), it.get(2), i);
        }

        // Sort by start time l ascending. On ties, break by original id
        Arrays.sort(arr, (a, b) -> {
            if (a.l != b.l) return Integer.compare(a.l, b.l);
            return Integer.compare(a.id, b.id);
        });

        // Precompute next compatible interval for each interval arr[i] using binary search
        int[] nextIdx = new int[n];
        for (int i = 0; i < n; i++) {
            int low = i + 1, high = n - 1, ans = n;
            while (low <= high) {
                int mid = (low + high) >>> 1;
                if (arr[mid].l > arr[i].r) {
                    ans = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            nextIdx[i] = ans;
        }

        // dp[k][i]: best Result choosing at most k intervals from suffix arr[i..n-1]
        Result[][] dp = new Result[5][n + 1];
        for (int k = 0; k <= 4; k++) {
            for (int i = 0; i <= n; i++) {
                dp[k][i] = new Result(0, new ArrayList<>());
            }
        }

        
        for (int i = n - 1; i >= 0; i--) {
            for (int k = 1; k <= 4; k++) {
                // Option 1: Skip interval i
                Result best = dp[k][i + 1];

                // Option 2: Take interval i
                int next = nextIdx[i];
                Result fromNext = dp[k - 1][next];
                long takeScore = (long) arr[i].weight + fromNext.score;

                List<Integer> takeIds = new ArrayList<>();
                takeIds.add(arr[i].id);
                takeIds.addAll(fromNext.ids);

                Result candidate = new Result(takeScore, takeIds);

                if (isBetter(candidate, best)) {
                    best = candidate;
                }

                dp[k][i] = best;
            }
        }

        List<Integer> resList = dp[4][0].ids;
        int[] ans = new int[resList.size()];
        for (int i = 0; i < resList.size(); i++) {
            ans[i] = resList.get(i);
        }
        Arrays.sort(ans);
        return ans;
    }

    private boolean isBetter(Result a, Result b) {
        if (a.score != b.score) {
            return a.score > b.score;
        }
        // If scores are equal, prefer lexicographically smaller sorted indices
        List<Integer> listA = new ArrayList<>(a.ids);
        List<Integer> listB = new ArrayList<>(b.ids);
        Collections.sort(listA);
        Collections.sort(listB);

        int minLen = Math.min(listA.size(), listB.size());
        for (int i = 0; i < minLen; i++) {
            if (!listA.get(i).equals(listB.get(i))) {
                return listA.get(i) < listB.get(i);
            }
        }
        return listA.size() < listB.size();
    }
}