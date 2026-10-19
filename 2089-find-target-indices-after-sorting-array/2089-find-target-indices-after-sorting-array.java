class Solution {
    public List<Integer> targetIndices(int[] nums, int target) {
        int lessThanCount = 0;
        int targetCount = 0;

        for (int num : nums) {
            if (num < target) {
                lessThanCount++;
            } else if (num == target) {
                targetCount++;
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < targetCount; i++) {
            result.add(lessThanCount + i);
        }

        return result;
    }
}