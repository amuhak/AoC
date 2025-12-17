package year2025;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.AbstractMap;
import java.util.Arrays;

public class Day2 {
    static void main() throws FileNotFoundException {
        var start = System.nanoTime();
        var input = new BufferedReader(new FileReader("input.txt")).lines()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .map(range -> range.split("-"))
                .map(range -> new AbstractMap.SimpleEntry<>(Long.parseLong(range[0]), Long.parseLong(range[1])))
                .flatMapToLong(range -> {
                    var startRange = range.getKey();
                    var endRange = range.getValue();
                    return java.util.stream.LongStream.rangeClosed(startRange, endRange);
                })
                .mapToObj(Long::toString)
                .filter(Day2::isError)
                .mapToLong(Long::parseLong)
                .sum();
        System.out.println("Result: " + input);
        var end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }

    static boolean isError(String number) {
        for (int i = 1; i <= number.length() / 2; i++) {
            if (number.length() % i != 0) {
                continue;
            }
            var pattern = number.substring(0, i);
            var repeated = pattern.repeat(number.length() / i);
            if (repeated.equals(number)) {
                return true;
            }
        }
        return false;
    }
}
