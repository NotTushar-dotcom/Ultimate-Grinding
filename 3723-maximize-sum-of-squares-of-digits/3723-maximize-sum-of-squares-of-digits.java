class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if ((long) num * 9 < sum) {
            return "";
        }

        StringBuilder sb = new StringBuilder(num);

        int count9 = sum / 9;
        int rem = sum % 9;

        for (int i = 0; i < count9; i++) {
            sb.append('9');
        }

        if (rem > 0) {
            sb.append(rem);
        }

        while (sb.length() < num) {
            sb.append('0');
        }

        return sb.toString();
    }
}