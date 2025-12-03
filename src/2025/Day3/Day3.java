import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.IntStream;

record IndexedChar(int value, int originalIndex) {
}

public class Day3 {
    public static void main(String[] args) throws FileNotFoundException {
        var start = System.nanoTime();
        var input = new BufferedReader(new FileReader("input.txt"));
        System.out.println(input.lines()
                .map(line -> IntStream.range(0, line.length())
                        .mapToObj(i -> new IndexedChar(line.charAt(i) - '0', i))
                        .sorted((a, b) -> -1 * Integer.compare(a.value(), b.value()))
                        .toList())
                .mapToInt(line -> {
                    int out = line.getFirst().value() * 10;
                    int idx = line.getFirst().originalIndex();
                    for (var c : line) {
                        if (c.originalIndex() > idx) {
                            out += c.value();
                            return out;
                        }
                    }
                    out /= 10;
                    out += line.get(1).value() * 10;
                    return out;
                })
                .sum());
        System.out.println("Time: " + (System.nanoTime() - start) / 1_000_000 + " ms");
    }
}
