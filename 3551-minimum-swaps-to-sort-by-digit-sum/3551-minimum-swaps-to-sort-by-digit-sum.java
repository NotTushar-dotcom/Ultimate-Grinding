class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;

        int[][] arr = new int[n][3];
        for (int i = 0; i < n; i++) {
            arr[i][0] = i;
            arr[i][1] = getDigitSum(nums[i]);
            arr[i][2] = nums[i];
        }

        mergeSort(arr, 0, n - 1, new int[n][3]);

        boolean[] visited = new boolean[n];
        int cycles = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }

            int curr = i;
            while (!visited[curr]) {
                visited[curr] = true;
                curr = arr[curr][0];
            }
            cycles++;
        }

        return n - cycles;
    }

    private int getDigitSum(int val) {
        int sum = 0;
        while (val > 0) {
            sum += val % 10;
            val /= 10;
        }
        return sum;
    }

    private int compare(int[] a, int[] b) {
        if (a[1] != b[1]) {
            return Integer.compare(a[1], b[1]);
        }
        return Integer.compare(a[2], b[2]);
    }

    private void mergeSort(int[][] arr, int left, int right, int[][] temp) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid, temp);
        mergeSort(arr, mid + 1, right, temp);

        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            if (compare(arr[i], arr[j]) <= 0) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (int p = left; p <= right; p++) {
            arr[p] = temp[p];
        }
    }
}