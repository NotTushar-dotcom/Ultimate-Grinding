import java.util.*;

class Solution {
    public int minScore(int n, int[][] roads) {
        // Build the adjacency list for the bi-directional graph
        // Each entry in the list will store an array: {neighbor, distance}
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
        
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int distance = road[2];
            adj.get(u).add(new int[]{v, distance});
            adj.get(v).add(new int[]{u, distance});
        }
        
        // Initialize BFS variables
        int minScore = Integer.MAX_VALUE;
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        
        // Start traversal from city 1
        queue.offer(1);
        visited[1] = true;
        
        // Traverse the entire connected component containing city 1
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            
            for (int[] neighbor : adj.get(curr)) {
                int nextNode = neighbor[0];
                int distance = neighbor[1];
                
                // Track the minimum road weight seen in this component
                minScore = Math.min(minScore, distance);
                
                // If the neighbor hasn't been visited, add it to the queue
                if (!visited[nextNode]) {
                    visited[nextNode] = true;
                    queue.offer(nextNode);
                }
            }
        }
        
        return minScore;
    }
}