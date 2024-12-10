import java.nio.file.Files;
import java.nio.file.Path;

public class Day10 {
    static int[][] map, dp;

    public static void main(String[] args) throws Exception {
        try (var s = Files.lines(Path.of("Input.txt"))) {
            map = s.map(i -> i.chars().map(c -> c - '0').toArray()).toArray(int[][]::new);
        }
        long ans = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                dp = new int[map.length][map[0].length];
                ans += dfs(i, j, 0);

            }
        }
        System.out.println(ans);
    }

    static int dfs(int x, int y, int toFind) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length || dp[x][y] == -1 || map[x][y] != toFind) {
            return 0;
        }
        if (dp[x][y] != 0) {
            return dp[x][y];
        }
        if (toFind == 9) {
            dp[x][y] = 1;
            return 1;
        }

        int ans = dfs(x + 1, y, toFind + 1)
                + dfs(x - 1, y, toFind + 1)
                + dfs(x, y + 1, toFind + 1)
                + dfs(x,y - 1, toFind + 1);

        if (ans == 0) {
            dp[x][y] = -1;
        } else {
            dp[x][y] = ans;
        }

        return ans;
    }
}
