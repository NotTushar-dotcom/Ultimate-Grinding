class Solution {
    public String smallestPalindrome(String s) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }

        StringBuilder leftHalf = new StringBuilder();
        String middle = "";

        for (int i = 0; i < 26; i++) {
            if (counts[i] % 2 != 0) {
                middle = String.valueOf((char)(i + 'a'));
            }
            for (int j = 0; j < counts[i] / 2; j++) {
                leftHalf.append((char)(i + 'a'));
            }
        }

        StringBuilder rightHalf = new StringBuilder(leftHalf).reverse();
        return leftHalf.toString() + middle + rightHalf.toString();
    }
}