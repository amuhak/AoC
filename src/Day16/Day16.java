// needs -Xss10m to run cause of stack overflow

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;

public class Day16 {
    public static char[][] grid;
    public static boolean[][] visited;
    public static HashSet<String> set = new HashSet<>();

    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        r.mark(100000);
        int n = 0, m = 0;
        String line;
        while ((line = r.readLine()) != null) {
            n++;
            m = line.length();
        }
        r.reset();
        grid = new char[n + 2][m + 2];
        visited = new boolean[n + 2][m + 2];
        n = 1;
        while ((line = r.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                grid[n][i + 1] = line.charAt(i);
            }
            n++;
        }
        System.out.println(Arrays.deepToString(grid));
        pathfinding1(1, 0, 'r');
        visited[1][0] = false;
        for (boolean[] booleans : visited) {
            System.out.println(Arrays.toString(booleans));
        }
        System.out.println(countTrue());
    }

    public static void pathfinding1(int x, int y, char direction) {
        common(x, y, direction);
    }

    private static void common(int x, int y, char direction) {
        if (set.contains(x + " " + y + " " + direction)) {
            return;
        }
        set.add(x + " " + y + " " + direction);
        visited[x][y] = true;
        switch (direction) {

        case 'u':
            if (x - 1 == 0) {
                break;
            }
            x--;
            switch (grid[x][y]) {
            case '.', '|':
                pathfinding(x, y, direction);
                break;
            case '-':
                pathfinding(x, y, 'r');
                pathfinding(x, y, 'l');
                break;
            case '/':
                pathfinding(x, y, 'r');
                break;
            case '\\':
                pathfinding(x, y, 'l');
                break;
            }
            break;

        case 'd':
            if (x + 1 == grid.length) {
                break;
            }
            x++;
            switch (grid[x][y]) {
            case '.', '|':
                pathfinding(x, y, direction);
                break;
            case '-':
                pathfinding(x, y, 'r');
                pathfinding(x, y, 'l');
                break;
            case '/':
                pathfinding(x, y, 'l');
                break;
            case '\\':
                pathfinding(x, y, 'r');
                break;
            }
            break;
        case 'l':
            if (y - 1 == 0) {
                break;
            }
            y--;
            switch (grid[x][y]) {
            case '.', '-':
                pathfinding(x, y, direction);
                break;
            case '|':
                pathfinding(x, y, 'u');
                pathfinding(x, y, 'd');
                break;
            case '/':
                pathfinding(x, y, 'd');
                break;
            case '\\':
                pathfinding(x, y, 'u');
                break;
            }


            break;
        case 'r':
            if (y + 1 == grid[0].length) {
                break;
            }
            y++;
            switch (grid[x][y]) {
            case '.', '-':
                pathfinding(x, y, direction);
                break;
            case '|':
                pathfinding(x, y, 'd');
                pathfinding(x, y, 'u');
                break;
            case '/':
                pathfinding(x, y, 'u');
                break;
            case '\\':
                pathfinding(x, y, 'd');
                break;
            }
            break;
        }
    }

    public static void pathfinding(int x, int y, char direction) {
        if (x == 0 || x == grid.length || y == 0 || y == grid[0].length) {
            return;
        }
        common(x, y, direction);
    }

    public static long countTrue() {
        long count = 0;
        for (boolean[] booleans : visited) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    count++;
                }
            }
        }
        return count;
    }
}

