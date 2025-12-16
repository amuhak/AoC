import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Day5 {
    static void main() throws FileNotFoundException {
        var start = System.nanoTime();
        ArrayList<Range> ranges = new ArrayList<>();
        new BufferedReader(new FileReader("input.txt")).lines()
                .forEach(line -> {
                    if (!line.isEmpty()) {
                        if (line.contains("-")) {
                            var parts = line.split("-");
                            ranges.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
                        }
                    }
                });

        int n = ranges.size();

        for (int iteration = 0; iteration < n; iteration++) {
            for (int i = 0; i < ranges.size(); i++) {
                for (int j = i + 1; j < ranges.size(); j++) {
                    if (ranges.get(i)
                            .overlaps(ranges.get(j))) {
                        ranges.get(i)
                                .merge(ranges.get(j));
                        ranges.remove(j);
                        j--;
                    }
                }
            }
        }

        System.out.println("Sum of ranges: " + ranges.stream()
                .mapToLong(Range::size)
                .sum());
        var end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }

    static class Range {
        long start;
        long end;

        Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        boolean overlaps(Range other) {
            return this.start <= other.end && other.start <= this.end;
        }

        void merge(Range other) {
            this.start = Math.min(this.start, other.start);
            this.end = Math.max(this.end, other.end);
        }

        long size() {
            return end - start + 1;
        }
    }
}
