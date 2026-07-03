import java.util.*;

public class Solution {
    
    // Helper class to represent a directed edge
    private static class Edge {
        int to;
        long cost;
        
        Edge(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    // Renamed from maxPathScore to findMaxPathScore to fix the compilation error
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        
        // Build the adjacency list and compute in-degrees for topological sort
        List<List<Edge>> adj = new ArrayList<>();
        int[] inDegree = new int[n];
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        
        long maxEdgeCost = 0;
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int cost = edge[2];
            
            // If either connected node is offline, this edge cannot be used.
            if (!online[u] || !online[v]) {
                continue;
            }
            
            adj.get(u).add(new Edge(v, cost));
            inDegree[v]++;
            maxEdgeCost = Math.max(maxEdgeCost, cost);
        }
        
        // Generate Kahn's Topological Sort order of the graph
        int[] topoOrder = new int[n];
        int topoIdx = 0;
        Queue<Integer> queue = new LinkedList<>();
        
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        while (!queue.isEmpty()) {
            int u = queue.poll();
            topoOrder[topoIdx++] = u;
            for (Edge edge : adj.get(u)) {
                inDegree[edge.to]--;
                if (inDegree[edge.to] == 0) {
                    queue.offer(edge.to);
                }
            }
        }

        // Binary Search for the maximum possible "minimum edge-cost"
        long low = 0;
        long high = maxEdgeCost;
        long ans = -1;
        
        while (low <= high) {
            long mid = low + (high - low) / 2;
            
            if (check(mid, n, adj, topoOrder, k)) {
                ans = mid;       // 'mid' is achievable, try to find a larger minimum edge
                low = mid + 1;
            } else {
                high = mid - 1;  // 'mid' demands too much weight or breaks k limit, lower it
            }
        }
        
        return (int) ans;
    }
    
    // Check if a path from 0 to n-1 exists with total cost <= k 
    // using ONLY edges with cost >= minRequiredCost
    private boolean check(long minRequiredCost, int n, List<List<Edge>> adj, int[] topoOrder, long k) {
        long[] minDist = new long[n];
        Arrays.fill(minDist, Long.MAX_VALUE);
        minDist[0] = 0;
        
        // Process nodes in DAG topological order to find the shortest path
        for (int u : topoOrder) {
            if (minDist[u] == Long.MAX_VALUE) {
                continue; 
            }
            
            for (Edge edge : adj.get(u)) {
                // Filter out any edge that drops below our binary search threshold
                if (edge.cost < minRequiredCost) {
                    continue;
                }
                
                if (minDist[u] + edge.cost < minDist[edge.to]) {
                    minDist[edge.to] = minDist[u] + edge.cost;
                }
            }
        }
        
        // Valid if node n-1 is reachable and the total path cost does not exceed k
        return minDist[n - 1] <= k;
    }
}