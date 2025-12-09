import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Day9 {
    public static void main(String[] args) throws FileNotFoundException {
        var start = System.nanoTime();
        var input = new BufferedReader(new FileReader("input.txt")).lines()
                .map(line -> line.split(","))
                .map(i -> new Point(Integer.parseInt(i[0]), Integer.parseInt(i[1])))
                .toArray(Point[]::new);
        long ans = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                ans = Math.max(ans, input[i].area(input[j]));
            }
        }
        var end = System.nanoTime();
        System.out.println("Max Area: " + ans);
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }

    static class Point {
        long x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        long area(Point other) {
            return (Math.abs(this.x - other.x) + 1) * (Math.abs(this.y - other.y) + 1);
        }
    }
}
