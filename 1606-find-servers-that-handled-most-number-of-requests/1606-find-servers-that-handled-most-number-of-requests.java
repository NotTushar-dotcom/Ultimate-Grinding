import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        int[] requestCount = new int[k];
        
        TreeSet<Integer> available = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            available.add(i);
        }
        
        PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        
        int n = arrival.length;
        for (int i = 0; i < n; i++) {
            int arrivalTime = arrival[i];
            int duration = load[i];
            
            while (!busy.isEmpty() && busy.peek()[0] <= arrivalTime) {
                available.add(busy.poll()[1]);
            }
            
            if (available.isEmpty()) {
                continue;
            }
            
            Integer serverId = available.ceiling(i % k);
            if (serverId == null) {
                serverId = available.first();
            }
            
            available.remove(serverId);
            busy.offer(new int[]{arrivalTime + duration, serverId});
            requestCount[serverId]++;
        }
        
        int maxRequests = 0;
        for (int count : requestCount) {
            maxRequests = Math.max(maxRequests, count);
        }
        
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (requestCount[i] == maxRequests) {
                result.add(i);
            }
        }
        
        return result;
    }
}