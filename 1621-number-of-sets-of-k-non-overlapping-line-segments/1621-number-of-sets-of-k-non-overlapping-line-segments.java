class Solution {
    private static final int MOD = 1_000_000_007;

    public int numberOfSets(int n, int k) {
        int N = n + k - 1;
        int K = 2 * k;
        
        if (K > N) return 0;
        
        long[] fact = new long[N + 1];
        long[] inv = new long[N + 1];
        
        // Precompute factorials
        fact[0] = 1;
        inv[0] = 1;
        for (int i = 1; i <= N; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }
        
        // Precompute inverse factorials
        inv[N] = power(fact[N], MOD - 2);
        for (int i = N - 1; i >= 1; i--) {
            inv[i] = (inv[i + 1] * (i + 1)) % MOD;
        }
        
        // Calculate C(N, K) = N! / (K! * (N - K)!) % MOD
        long ans = (fact[N] * inv[K]) % MOD;
        ans = (ans * inv[N - K]) % MOD;
        
        return (int) ans;
    }

    // (base^exp) % MOD
    private long power(long base, long exp) {
        long res = 1;
        base %= MOD;
        while (exp > 0) {
            if (exp % 2 == 1) res = (res * base) % MOD;
            base = (base * base) % MOD;
            exp /= 2;
        }
        return res;
    }
}