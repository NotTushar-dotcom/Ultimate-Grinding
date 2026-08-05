import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        // Step 1: Build adjacency list
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] inv : invocations) {
            adj[inv[0]].add(inv[1]);
        }
        
        // Step 2: Find all suspicious methods using BFS
        boolean[] isSuspicious = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(k);
        isSuspicious[k] = true;
        
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int next : adj[curr]) {
                if (!isSuspicious[next]) {
                    isSuspicious[next] = true;
                    queue.add(next);
                }
            }
        }
        
        // Step 3: Check if any non-suspicious method invokes a suspicious method
        boolean canRemove = true;
        for (int[] inv : invocations) {
            if (!isSuspicious[inv[0]] && isSuspicious[inv[1]]) {
                canRemove = false;
                break;
            }
        }
        
        // Step 4: Build the result
        List<Integer> result = new ArrayList<>();
        if (canRemove) {
            for (int i = 0; i < n; i++) {
                if (!isSuspicious[i]) {
                    result.add(i);
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                result.add(i);
            }
        }
        
        return result;
    }
}