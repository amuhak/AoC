// dijkstra's algorithm

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Day17 {
    public static byte[][] grid;
    public static long[][] values;
    public static HashSet<String> done = new HashSet<>();
    public static PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> {
        if (values[a[0]][a[1]] == values[b[0]][b[1]]) {
            return Long.compare(a[2], b[2]);
        }
        return Long.compare(values[a[0]][a[1]], values[b[0]][b[1]]);
    });

    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        r.mark(100000);
        String line;
        int x = 0;
        int y = 0;
        while ((line = r.readLine()) != null) {
            x = line.length();
            y++;
        }
        r.reset();
        grid = new byte[x][y];
        values = new long[x][y];
        y = 0;
        while ((line = r.readLine()) != null) {
            for (x = 0; x < line.length(); x++) {
                grid[y][x] = (byte) Character.getNumericValue(line.charAt(x));
            }
            y++;
        }
        r.close();
        for (int i = 0; i < x; i++) {
            Arrays.fill(values[i], Long.MAX_VALUE);
        }
        grid[0][0] = 0;
        values[0][0] = 0;
        dijkstra(0, 0, 'd', (byte) 0);
        System.out.println("No path found");
    }

    public static void dijkstra(int x, int y, char direction, byte length) {
        if (x == 11 && y == 11) {
            System.out.println("hi");
        }
        if (length == 3) {
            switch (direction) {
            case 'u', 'd':
                addLeft(x, y, length, direction);
                addRight(x, y, length, direction);
                break;
            case 'l', 'r':
                addUp(x, y, length, direction);
                addDown(x, y, length, direction);
                break;
            }
        } else {
            switch (direction) {
            case 'u':
                addUp(x, y, length, direction);
                addLeft(x, y, length, direction);
                addRight(x, y, length, direction);
                break;
            case 'd':
                addDown(x, y, length, direction);
                addLeft(x, y, length, direction);
                addRight(x, y, length, direction);
                break;
            case 'l':
                addLeft(x, y, length, direction);
                addUp(x, y, length, direction);
                addDown(x, y, length, direction);
                break;
            case 'r':
                addRight(x, y, length, direction);
                addUp(x, y, length, direction);
                addDown(x, y, length, direction);
                break;
            }
        }

        if (done.size() == grid.length * grid[0].length - 1) {
            System.out.println(values[grid.length - 1][grid.length - 1]);
            //q.forEach(a -> System.out.println(Arrays.toString(a)));
            for (int i = 0; i < grid.length; i++) {
                System.out.println(Arrays.toString(values[i]));
            }
            System.exit(0);
        }

        var temp = q.poll();
        while (true) {
            assert temp != null;
            if (!done.contains(temp[0] * 10 + " " + temp[1] * 10)) {
                break;
            }
            temp = q.poll();
        }

        done.add(temp[0] * 10 + " " + temp[1] * 10);
        dijkstra(temp[0], temp[1], (char) temp[3], (byte) temp[2]);
    }

    public static void addRight(int x, int y, int length, char direction) {
        if (y + 1 < grid[0].length && x < grid.length && x >= 0) {
            values[x][y + 1] = Math.min(values[x][y + 1], values[x][y] + grid[x][y + 1]);
            q.add(new int[]{x, y + 1, direction == 'r' ? length + 1 : 1, 'r'});
        }
    }

    public static void addLeft(int x, int y, int length, char direction) {
        if (y - 1 >= 0 && x < grid.length && x >= 0) {
            values[x][y - 1] = Math.min(values[x][y - 1], values[x][y] + grid[x][y - 1]);
            q.add(new int[]{x, y - 1, direction == 'l' ? length + 1 : 1, 'l'});
        }
    }

    public static void addUp(int x, int y, int length, char direction) {
        if (x - 1 >= 0 && y < grid[0].length && y >= 0) {
            values[x - 1][y] = Math.min(values[x - 1][y], values[x][y] + grid[x - 1][y]);
            q.add(new int[]{x - 1, y, direction == 'u' ? length + 1 : 1, 'u'});
        }
    }

    public static void addDown(int x, int y, int length, char direction) {
        if (x + 1 < grid.length && y < grid[0].length && y >= 0) {
            values[x + 1][y] = Math.min(values[x + 1][y], values[x][y] + grid[x + 1][y]);
            q.add(new int[]{x + 1, y, direction == 'd' ? length + 1 : 1, 'd'});
        }
    }
}
