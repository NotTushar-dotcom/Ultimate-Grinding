import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int startR = -1, startC = -1;
        int litterCount = 0;
        int[][] litterId = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(litterId[i], -1);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);
                if (ch == 'S') {
                    startR = i;
                    startC = j;
                } else if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        // Target bitmask representing all litter collected
        int targetMask = (1 << litterCount) - 1;
        if (targetMask == 0) return 0;

        // bestEnergy[r][c][mask] stores the maximum remaining energy seen for that state
        int[][][] bestEnergy = new int[m][n][1 << litterCount];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(bestEnergy[i][j], -1);
            }
        }

        // Queue holds: {r, c, mask, remaining_energy}
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{startR, startC, 0, energy});
        bestEnergy[startR][startC][0] = energy;

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int steps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int s = 0; s < size; s++) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];
                int mask = curr[2];
                int e = curr[3];

                if (mask == targetMask) {
                    return steps;
                }

                if (e == 0) continue; // Cannot move without energy

                for (int[] d : dirs) {
                    int nr = r + d[0];
                    int nc = c + d[1];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;

                    char cell = classroom[nr].charAt(nc);
                    if (cell == 'X') continue;

                    int nextEnergy = e - 1;
                    int nextMask = mask;

                    if (cell == 'L') {
                        nextMask |= (1 << litterId[nr][nc]);
                    } else if (cell == 'R') {
                        nextEnergy = energy;
                    }

                    if (nextEnergy > bestEnergy[nr][nc][nextMask]) {
                        bestEnergy[nr][nc][nextMask] = nextEnergy;
                        queue.offer(new int[]{nr, nc, nextMask, nextEnergy});
                    }
                }
            }
            steps++;
        }

        return -1;
    }
}