class Solution {
    
    class Node {
        int totalProd;
        int[] counts;
        
        Node(int k) {
            counts = new int[k];
            totalProd = 1;
        }
    }

    class SegmentTree {
        int[] treeProd;
        int[][] treeCounts;
        int n;
        int k;
        
        public SegmentTree(int[] arr, int k) {
            this.n = arr.length;
            this.k = k;
            treeProd = new int[4 * n];
            treeCounts = new int[4 * n][k];
            build(arr, 0, 0, n - 1);
        }
        
        private void build(int[] arr, int node, int start, int end) {
            if (start == end) {
                treeProd[node] = arr[start] % k;
                treeCounts[node][arr[start] % k] = 1;
                return;
            }
            int mid = start + (end - start) / 2;
            build(arr, 2 * node + 1, start, mid);
            build(arr, 2 * node + 2, mid + 1, end);
            merge(node, 2 * node + 1, 2 * node + 2);
        }
        
        // Merges two children nodes into the parent node
        private void merge(int node, int leftNode, int rightNode) {
            treeProd[node] = (treeProd[leftNode] * treeProd[rightNode]) % k;
            
            // Inherit all prefix products from the left segment
            for (int i = 0; i < k; i++) {
                treeCounts[node][i] = treeCounts[leftNode][i];
            }
            
            // Add prefix products from the right segment, offset by the left segment's total product
            for (int i = 0; i < k; i++) {
                int newMod = (treeProd[leftNode] * i) % k;
                treeCounts[node][newMod] += treeCounts[rightNode][i];
            }
        }
        
        public void update(int node, int start, int end, int idx, int val) {
            if (start == end) {
                treeProd[node] = val % k;
                for (int i = 0; i < k; i++) {
                    treeCounts[node][i] = 0;
                }
                treeCounts[node][val % k] = 1;
                return;
            }
            int mid = start + (end - start) / 2;
            if (idx <= mid) {
                update(2 * node + 1, start, mid, idx, val);
            } else {
                update(2 * node + 2, mid + 1, end, idx, val);
            }
            merge(node, 2 * node + 1, 2 * node + 2);
        }
        
        public Node query(int node, int start, int end, int l, int r) {
            if (r < start || end < l) {
                return null;
            }
            if (l <= start && end <= r) {
                Node res = new Node(k);
                res.totalProd = treeProd[node];
                for (int i = 0; i < k; i++) {
                    res.counts[i] = treeCounts[node][i];
                }
                return res;
            }
            int mid = start + (end - start) / 2;
            Node left = query(2 * node + 1, start, mid, l, r);
            Node right = query(2 * node + 2, mid + 1, end, l, r);
            return mergeNodes(left, right);
        }
        
        // Merges dynamically created nodes during range queries
        private Node mergeNodes(Node left, Node right) {
            if (left == null) return right;
            if (right == null) return left;
            
            Node res = new Node(k);
            res.totalProd = (left.totalProd * right.totalProd) % k;
            
            for (int i = 0; i < k; i++) {
                res.counts[i] += left.counts[i];
                int newMod = (left.totalProd * i) % k;
                res.counts[newMod] += right.counts[i];
            }
            return res;
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        SegmentTree st = new SegmentTree(nums, k);
        int[] ans = new int[queries.length];
        
        for (int i = 0; i < queries.length; i++) {
            int idx = queries[i][0];
            int val = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];
            
            // Persistently update the value
            st.update(0, 0, n - 1, idx, val);
            
            // Query the segment strictly starting from 'start' to the end of the array
            Node res = st.query(0, 0, n - 1, start, n - 1);
            
            // Extract the requested frequency
            ans[i] = (res != null) ? res.counts[x] : 0;
        }
        
        return ans;
    }
}