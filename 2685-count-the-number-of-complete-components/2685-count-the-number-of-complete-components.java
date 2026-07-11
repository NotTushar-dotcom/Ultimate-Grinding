class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }
        
        boolean[] visited = new boolean[n];
        int count = 0;
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int[] stats = new int[2];
                dfs(i, adj, visited, stats);
                if (stats[1] == stats[0] * (stats[0] - 1)) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private void dfs(int node, List<List<Integer>> adj, boolean[] visited, int[] stats) {
        visited[node] = true;
        stats[0]++;
        stats[1] += adj.get(node).size();
        
        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adj, visited, stats);
            }
        }
    }
}