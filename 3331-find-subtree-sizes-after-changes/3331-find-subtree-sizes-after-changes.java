import java.util.*;

class Solution {
    private List<Integer>[] originalTree;
    private List<Integer>[] finalTree;
    private int[] newParent;
    private int[] answer;
    private String s;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        this.s = s;
        this.originalTree = new ArrayList[n];
        this.finalTree = new ArrayList[n];
        this.newParent = new int[n];
        this.answer = new int[n];

        for (int i = 0; i < n; i++) {
            originalTree[i] = new ArrayList<>();
            finalTree[i] = new ArrayList<>();
            newParent[i] = parent[i];
        }

        for (int i = 1; i < n; i++) {
            originalTree[parent[i]].add(i);
        }

        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);
        findNewParents(0, lastSeen);

        for (int i = 1; i < n; i++) {
            finalTree[newParent[i]].add(i);
        }

        computeSizes(0);
        return answer;
    }

    private void findNewParents(int u, int[] lastSeen) {
        int ch = s.charAt(u) - 'a';
        int prevAncestor = lastSeen[ch];

        if (prevAncestor != -1 && u != 0) {
            newParent[u] = prevAncestor;
        }

        lastSeen[ch] = u;

        for (int v : originalTree[u]) {
            findNewParents(v, lastSeen);
        }

        lastSeen[ch] = prevAncestor;
    }

    private int computeSizes(int u) {
        int size = 1;
        for (int v : finalTree[u]) {
            size += computeSizes(v);
        }
        answer[u] = size;
        return size;
    }
}