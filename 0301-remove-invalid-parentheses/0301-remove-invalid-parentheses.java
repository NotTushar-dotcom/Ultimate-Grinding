class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        if (s == null) return result;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(s);
        visited.add(s);
        
        boolean foundValidAtCurrentLevel = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (isValid(current)) {
                result.add(current);
                foundValidAtCurrentLevel = true;
            }

            if (foundValidAtCurrentLevel) {
                continue;
            }

            for (int i = 0; i < current.length(); i++) {
                char c = current.charAt(i);
                if (c != '(' && c != ')') {
                    continue;
                }

                String nextState = current.substring(0, i) + current.substring(i + 1);
                
                if (!visited.contains(nextState)) {
                    queue.add(nextState);
                    visited.add(nextState);
                }
            }
        }

        return result;
    }

    private boolean isValid(String s) {
        int balance = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
                if (balance < 0) {
                    return false;
                }
            }
        }
        return balance == 0;
    }
}