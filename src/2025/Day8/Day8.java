import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Day8 {
    public static void main(String[] args) throws FileNotFoundException {
        var start = System.nanoTime();
        var input = new BufferedReader(new FileReader("input.txt")).lines()
                .map(line -> line.split(","))
                .map(line -> new Point(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2])))
                .toArray(Point[]::new);

        PriorityQueue<Distance> distances = new PriorityQueue<>(Comparator.comparingLong(a -> a.distance));

        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                distances.add(input[i].distanceTo(input[j], i, j));
            }
        }
        long ans;
        DSU set = new DSU(input.length);
        while (true) {
            Distance d = distances.poll();
            assert d != null;
            if (set.find(d.to) != set.find(d.from)) {
                set.link(d.to, d.from);
            }
            if (isDone(set)) {
                ans = input[d.to].x * input[d.from].x;
                break;
            }
        }
        System.out.println("Ans: " + ans);
        var end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }

    static boolean isDone(DSU set) {
        int root = set.find(0);
        for (int i = 1; i < set.parent.length; i++) {
            if (set.find(i) != root) {
                return false;
            }
        }
        return true;
    }

    static class DSU {
        int[] parent;

        DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int a) {
            if (parent[a] != a) {
                parent[a] = find(parent[a]);
            }
            return parent[a];
        }

        void link(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB) {
                return;
            }
            if (rootA < rootB) {
                parent[rootB] = rootA;
            } else {
                parent[rootA] = rootB;
            }
        }
    }

    static class Point {
        long x;
        long y;
        long z;

        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        Distance distanceTo(Point other, int toIndex, int fromIndex) {
            long dist = (long) (Math.pow(Math.abs(this.x - other.x), 2) + Math.pow(Math.abs(this.y - other.y), 2)
                    + Math.pow(Math.abs(this.z - other.z), 2));
            return new Distance(toIndex, fromIndex, dist);
        }

        @Override
        public String toString() {
            return "Point{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
        }
    }

    static class Distance {
        long distance;
        int to, from;

        Distance(int to, int from, long distance) {
            this.to = to;
            this.from = from;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "Distance{" + "distance=" + distance + ", to=" + to + ", from=" + from + '}';
        }
    }
}
