import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day9 {
    static Point[] input;
    static ArrayList<Line> lines;

    static void main() throws FileNotFoundException {
        var start = System.nanoTime();
        input = new BufferedReader(new FileReader("input.txt")).lines()
                .map(line -> line.split(","))
                .map(i -> new Point(Integer.parseInt(i[0]), Integer.parseInt(i[1])))
                .toArray(Point[]::new);

        lines = new ArrayList<>();
        for (int i = 1; i <= input.length; i++) {
            lines.add(new Line(input[i - 1], input[i % input.length]));
        }

        lines.sort(Comparator.comparingLong((l) -> l.p1.x));

        long ans = LongStream.range(0, input.length)
                .parallel()
                .flatMap(i -> LongStream.range(i + 1, input.length)
                        .map(j -> input[Math.toIntExact(i)].area(input[Math.toIntExact(j)])))
                .max()
                .orElse(Integer.MIN_VALUE);


        var end = System.nanoTime();
        System.out.println("Max Area: " + ans);
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }

    static class Line {
        Point p1, p2;

        Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        Stream<Point> points() {
            if (p1.x == p2.x) {
                // vertical line
                long lowerY = Math.min(p1.y, p2.y);
                long higherY = Math.max(p1.y, p2.y);
                return LongStream.rangeClosed(lowerY, higherY)
                        .mapToObj(y -> new Point(p1.x, y));
            } else {
                // horizontal line
                long lowerX = Math.min(p1.x, p2.x);
                long higherX = Math.max(p1.x, p2.x);
                return LongStream.rangeClosed(lowerX, higherX)
                        .mapToObj(x -> new Point(x, p1.y));
            }
        }
    }

    static class Point {
        long x, y;

        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public final boolean equals(Object o) {
            if (!(o instanceof Point point)) {
                return false;
            }

            return x == point.x && y == point.y;
        }

        long area(Point other) {
            // there are 4 lines
            Line a = new Line(this, new Point(this.x, other.y));
            Line b = new Line(new Point(this.x, other.y), other);
            Line c = new Line(other, new Point(other.x, this.y));
            Line d = new Line(new Point(other.x, this.y), this);
            boolean allInside = Stream.of(a, b, c, d)
                    .flatMap(Line::points)
                    .allMatch(Point::inside);
            if (allInside) {
                return (Math.abs(other.x - this.x) + 1) * (Math.abs(other.y - this.y) + 1);
            }
            return 0;
        }

        boolean inside() {
            // Loop through all lines and check and count number of borders to the left
            int count = 0;
            for (Line line : lines) {
                Point p1 = line.p1;
                Point p2 = line.p2;

                if (p1.y == p2.y && p1.y == this.y) {
                    long minX = Math.min(p1.x, p2.x);
                    long maxX = Math.max(p1.x, p2.x);
                    if (this.x >= minX && this.x <= maxX) {
                        return true;
                    }
                }

                if (p1.x == p2.x && p1.x <= this.x) {
                    // Line of interest
                    long lowerY = Math.min(p1.y, p2.y);
                    long highY = Math.max(p1.y, p2.y);
                    if (lowerY <= this.y && this.y < highY) {
                        count++;
                        if (p1.x == this.x) {
                            return true;
                        }
                    }
                }
            }
            return count % 2 == 1;
        }
    }
}
