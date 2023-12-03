import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day3 {
    public static long answer = 0;
    final static int SIZE = 140;
    public static boolean[][] lookedAt = new boolean[SIZE + 2][SIZE + 2];
    public static char[][] map = new char[SIZE + 2][SIZE + 2];

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        for (int i = 0; i < SIZE; i++) {
            char[] a = r.readLine().toCharArray();
            System.arraycopy(a, 0, map[i + 1], 1, SIZE);
        }
        for (int i = 1; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                if (map[i][j] == '.') {
                    continue;
                } else if (!Character.isDigit(map[i][j])) {
                    findInt(i, j);
                }
            }
        }
        System.out.println(answer);
    }

    public static void findInt(int x, int y) {
        // look in a square around the point
        byte no = 0;
        long product = 1;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (map[i][j] == '.') {
                    lookedAt[i][j] = true;
                    continue;
                } else if (!lookedAt[i][j] && Character.isDigit(map[i][j])) {
                    product *= fillIn(i, j);
                    no++;
                }
            }
        }
        if (no == 2) {
            answer += product;
        }
    }

    public static int fillIn(int x, int y) {
        // We found a number, so we need to fill it in and add it to the answer

        int starting = y;
        int ending = y;
        for (int i = 1; i <= 3; i++) {
            if (Character.isDigit(map[x][y - i])) {
                starting = y - i;
            } else {
                break;
            }
        }
        for (int i = 1; i < 3; i++) {
            if (Character.isDigit(map[x][y + i])) {
                ending = y + i;
            } else {
                break;
            }
        }
        StringBuilder ans = new StringBuilder();
        for (int i = starting; i <= ending; i++) {
            ans.append(map[x][i]);
            lookedAt[x][i] = true;
        }
        System.out.println(Integer.parseInt(String.valueOf(ans)));
        return Integer.parseInt(String.valueOf(ans));
    }
}
