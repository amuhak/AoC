import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Day7 {
    static Node[][] grid;
    static long[][] memo;

    public static void main(String[] args) throws FileNotFoundException {
        var start = System.nanoTime();
        var input = new BufferedReader(new FileReader("input.txt"));
        grid = input.lines()
                .map(line -> (line.chars()
                        .mapToObj(c -> switch (c) {
                            case '.' -> Node.Blank;
                            case '^' -> Node.Split;
                            case 'S' -> Node.S;
                            default -> throw new IllegalArgumentException("Unexpected character: " + (char) c);
                        })
                        .toArray(Node[]::new)))
                .toArray(Node[][]::new);

        memo = new long[grid.length][grid[0].length];

        long ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == Node.S) {
                    ans = dfs(j, i + 1, grid);
                    break;
                }
            }
            if (ans != 0) {
                break;
            }
        }

        System.out.println("Split Count: " + ans);

        var end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }

    enum Node {
        Blank, Split, S, laser
    }

    private static boolean inBounds(int x, int y, Node[][] grid) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }

    private static long dfs(int x, int y, Node[][] grid) {
        if (!inBounds(x, y, grid)) {
            return 0;
        }
        if (memo[y][x] != 0) {
            return memo[y][x];
        }

        if (grid[y][x] == Node.Split) {
            return memo[y][x] = dfs(x - 1, y, grid) + dfs(x + 1, y, grid);
        } else if (grid[y][x] == Node.Blank) {
            if (inBounds(x, y + 1, grid)) {
                return memo[y][x] = dfs(x, y + 1, grid);
            } else {
                return memo[y][x] = 1;
            }
        }
        return 0;
    }
}
