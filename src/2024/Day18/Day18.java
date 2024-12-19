import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Day18 {
    public static int[][] bytes;
    final public static int SIZE = 70 + 1;

    public static void main(String[] args) throws Exception {
        try (var s = Files.lines(Path.of("Input.txt"))) {
            bytes = s.map(line -> Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        }
        System.out.println(Arrays.deepToString(bytes));
        boolean[][] visited = new boolean[SIZE][SIZE];
        for (int i = 0; i < 1024; i++) {
            var b = bytes[i];
            visited[b[1]][b[0]] = true;
        }
        Arrays.stream(visited).map(Arrays::toString).forEach(System.out::println);
        // A* algorithm
        PriorityQueue<Node> q = new PriorityQueue<>();
        visited[0][0] = true;
        q.add(new Node(0, 0, 0));
        while (!q.isEmpty()) {
            Node n = q.poll();
            int x = n.x;
            int y = n.y;
            if (x == SIZE - 1 && y == SIZE - 1) {
                System.out.println(n.cost);
                return;
            }
            for (var i : new int[][]{{x, y + 1}, {x, y - 1}, {x + 1, y}, {x - 1, y}}) {
                int x1 = i[0];
                int y1 = i[1];
                if (x1 < 0 || x1 >= SIZE || y1 < 0 || y1 >= SIZE || visited[x1][y1]) {
                    continue;
                }
                visited[x1][y1] = true;
                q.add(new Node(x1, y1, n.cost + 1));
            }
        }
        Arrays.stream(visited).map(Arrays::toString).forEach(System.out::println);
    }

    static class Node implements Comparable<Node>{
        int x, y;
        double sum;
        int cost;
        double heuristic;

        public Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
            int end = SIZE - 1;
            heuristic = Math.sqrt(Math.pow(x - end, 2) + Math.pow(y - end, 2));
            sum = cost + heuristic;
        }

        public int compareTo(Node n) {
            return Double.compare(sum, n.sum);
        }
    }
}
