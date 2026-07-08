class Solution {
    public int[] sumAndMultiply(String s, int[][] queries) {
        int m = s.length();
        long MOD = 1_000_000_007L;

        int k = 0;
        for (int i = 0; i < m; i++) {
            if (s.charAt(i) != '0') {
                k++;
            }
        }

        int[] D = new int[k];
        int idx = 0;
        for (int i = 0; i < m; i++) {
            if (s.charAt(i) != '0') {
                D[idx++] = s.charAt(i) - '0';
            }
        }

        int[] prevNonZero = new int[m];
        int cur = -1;
        for (int i = 0; i < m; i++) {
            if (s.charAt(i) != '0') {
                cur++;
            }
            prevNonZero[i] = cur;
        }

        int[] nextNonZero = new int[m];
        cur = -1;
        int digitCount = k - 1;
        for (int i = m - 1; i >= 0; i--) {
            if (s.charAt(i) != '0') {
                cur = digitCount;
                digitCount--;
            }
            nextNonZero[i] = cur;
        }

        long[] pow10 = new long[k + 1];
        pow10[0] = 1;
        for (int i = 1; i <= k; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        long[] P = new long[k + 1];
        long[] S = new long[k + 1];
        for (int i = 0; i < k; i++) {
            P[i + 1] = (P[i] * 10 + D[i]) % MOD;
            S[i + 1] = S[i] + D[i];
        }

        int q = queries.length;
        int[] ans = new int[q];

        for (int i = 0; i < q; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            int L = nextNonZero[l];
            int R = prevNonZero[r];

            if (L == -1 || R == -1 || L > R) {
                ans[i] = 0;
            } else {
                int len = R - L + 1;
                long x = (P[R + 1] - (P[L] * pow10[len]) % MOD + MOD) % MOD;
                long sum = S[R + 1] - S[L];
                ans[i] = (int) ((x * (sum % MOD)) % MOD);
            }
        }

        return ans;
    }
}