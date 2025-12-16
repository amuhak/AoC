import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Day5 {
    static void main() throws FileNotFoundException {
        var start = System.nanoTime();
        ArrayList<Range> ranges = new ArrayList<>();
        ArrayList<Long> numbers = new ArrayList<>();
        new BufferedReader(new FileReader("input.txt")).lines()
                .forEach(line -> {
                    if (!line.isEmpty()) {
                        if (line.contains("-")) {
                            var parts = line.split("-");
                            ranges.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
                        } else {
                            numbers.add(Long.parseLong(line));
                        }
                    }
                });

        long count = numbers.stream()
                .filter(num -> ranges.stream().anyMatch(range -> range.contains(num)))
                .count();

        System.out.println("Count: " + count);
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

        boolean contains(long value) {
            return value >= start && value <= end;
        }
    }
}
