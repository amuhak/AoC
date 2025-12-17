package year2025;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.LongStream;

public class Day9 {
    static Point[] input;
    static ArrayList<Line> lines;
    static ArrayList<Line> verticalLines = new ArrayList<>();
    static ArrayList<Line> horizontalLines = new ArrayList<>();

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

        for (Line l : lines) {
            // Separate vertical and horizontal lines
            if (l.p1.x == l.p2.x) {
                verticalLines.add(l);
            } else {
                horizontalLines.add(l);
            }
        }

        // Sort lines for binary search
        verticalLines.sort(Comparator.comparingLong(l -> l.p1.x));
        horizontalLines.sort(Comparator.comparingLong(l -> l.p1.y));

        long ans = LongStream.range(0, input.length)
                //.parallel() // This slows it down now
                .flatMap(i -> LongStream.range(i + 1, input.length)
                        .map(j -> input[Math.toIntExact(i)].area(input[Math.toIntExact(j)])))
                .max()
                .orElse(Integer.MIN_VALUE);


        var end = System.nanoTime();
        System.out.println("Max Area: " + ans);
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }

    static int findStartIndexX(ArrayList<Line> list, long xThreshold) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = (low + high) / 2;
            if (list.get(mid).p1.x <= xThreshold) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    static int findStartIndexY(ArrayList<Line> list, long yThreshold) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = (low + high) / 2;
            if (list.get(mid).p1.y <= yThreshold) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    static class Line {
        Point p1, p2;

        Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
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
            long minX = Math.min(this.x, other.x);
            long maxX = Math.max(this.x, other.x);
            long minY = Math.min(this.y, other.y);
            long maxY = Math.max(this.y, other.y);

            int startIndex = findStartIndexX(verticalLines, minX);

            for (int i = startIndex; i < verticalLines.size(); i++) {
                Line l = verticalLines.get(i);

                // Too far
                if (l.p1.x >= maxX) {
                    break;
                }

                long wallMin = Math.min(l.p1.y, l.p2.y);
                long wallMax = Math.max(l.p1.y, l.p2.y);

                // Overlap?
                if (wallMin < maxY && wallMax > minY) {
                    return 0;
                }
            }

            startIndex = findStartIndexY(horizontalLines, minY);
            for (int i = startIndex; i < horizontalLines.size(); i++) {
                Line l = horizontalLines.get(i);

                // Too far
                if (l.p1.y >= maxY) {
                    break;
                }


                long wallMinX = Math.min(l.p1.x, l.p2.x);
                long wallMaxX = Math.max(l.p1.x, l.p2.x);

                // Overlap?
                if (wallMinX < maxX && wallMaxX > minX) {
                    return 0;
                }
            }

            return (maxX - minX + 1) * (maxY - minY + 1);
        }
    }
}
