import java.util.HashSet;
import java.util.concurrent.Callable;

public class pf implements Callable<Long> {
    public char[][] grid;
    public boolean[][] visited;
    public HashSet<String> set = new HashSet<>();
    public int x, y;
    public char direction;

    public pf(int x, int y, char direction, char[][] grid) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.grid = grid;
        this.visited = new boolean[grid.length][grid[0].length];
    }

    private void common(int x, int y, char direction) {
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

    public void pathfinding(int x, int y, char direction) {
        if (x == 0 || x == grid.length || y == 0 || y == grid[0].length) {
            return;
        }
        common(x, y, direction);
    }

    @Override
    public Long call() {
        common(x, y, direction);
        return countTrue() - 1;
    }

    public long countTrue() {
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
