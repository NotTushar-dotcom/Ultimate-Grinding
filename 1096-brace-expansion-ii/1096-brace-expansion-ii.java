class Solution {
    public List<String> braceExpansionII(String expression) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Set<String> result = new TreeSet<>(); // TreeSet automatically sorts the elements
        
        queue.offer(expression);
        visited.add(expression);
        
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            
            // If there are no more braces, it's a fully expanded string
            if (!curr.contains("{")) {
                result.add(curr);
                continue;
            }
            
            // Find the innermost brace pair
            int l = 0, r = 0;
            while (curr.charAt(r) != '}') {
                if (curr.charAt(r) == '{') {
                    l = r; // Update left to the most recent '{'
                }
                r++;
            }
            
            // Extract the parts before, inside, and after the innermost braces
            String before = curr.substring(0, l);
            String after = curr.substring(r + 1);
            String[] strs = curr.substring(l + 1, r).split(",");
            
            // Generate new expressions and add them to the queue
            for (String s : strs) {
                String nextStr = before + s + after;
                if (visited.add(nextStr)) {
                    queue.offer(nextStr);
                }
            }
        }
        
        return new ArrayList<>(result);
    }
}