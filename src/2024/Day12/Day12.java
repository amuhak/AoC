import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day12 {
    public static long area, perimeter;
    public static int[][] map;
    public static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        try (var s = Files.lines(Path.of("Input.txt"))) {
            map = s.map(i -> i.chars().map(c -> c - 64).toArray()).toArray(int[][]::new);
        }
        long ans = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != -1) {
                    visited = new boolean[map.length][map[0].length];
                    area = 0;
                    perimeter = 0;
                    flood(i, j, map[i][j]);
                    ans += area * perimeter;
                }
            }
        }
        System.out.println(ans);
    }

    public static void flood(int x, int y, int target) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length) {
            perimeter++;
            return;
        }
        if (visited[x][y]) {
            return;
        }
        if (map[x][y] != target) {
            perimeter++;
            return;
        }
        visited[x][y] = true;
        map[x][y] = -1;
        area++;
        flood(x + 1, y, target);
        flood(x - 1, y, target);
        flood(x, y + 1, target);
        flood(x, y - 1, target);
    }
}
