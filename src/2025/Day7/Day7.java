import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Day7 {
    static int splitCount = 0;

    public static void main(String[] args) throws FileNotFoundException {
        var start = System.nanoTime();
        var input = new BufferedReader(new FileReader("input.txt"));
        Node[][] grid = input.lines()
                .map(line -> (line.chars()
                        .mapToObj(c -> switch (c) {
                            case '.' -> Node.Blank;
                            case '^' -> Node.Split;
                            case 'S' -> Node.S;
                            default -> throw new IllegalArgumentException("Unexpected character: " + (char) c);
                        })
                        .toArray(Node[]::new)))
                .toArray(Node[][]::new);
        Queue<Cord> q = new ArrayDeque<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == Node.S) {
                    q.add(new Cord(j, i + 1));
                }
            }
        }

        while (!q.isEmpty()) {
            var curr = q.poll();
            int x = curr.x;
            int y = curr.y;

            if (grid[y][x] == Node.Split) {
                splitCount++;
                grid[y][x] = Node.laser;
                if (inBounds(x - 1, y, grid)) {
                    q.add(new Cord(x - 1, y));
                }
                if (inBounds(x + 1, y, grid)) {
                    q.add(new Cord(x + 1, y));
                }
            } else if (grid[y][x] == Node.Blank) {
                grid[y][x] = Node.laser;
                if (inBounds(x, y + 1, grid)) {
                    q.add(new Cord(x, y + 1));
                }
            }
        }
        System.out.println("Split Count: " + splitCount);

        var end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }

    enum Node {
        Blank, Split, S, laser
    }

    record Cord(int x, int y) {
    }

    public static boolean inBounds(int x, int y, Node[][] grid) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }
}
