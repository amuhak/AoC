import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

public class Day12 {
    static void main() throws FileNotFoundException {
        var start = System.nanoTime();
        var input = new BufferedReader(new FileReader("input.txt")).lines()
                .map(line -> {
                    var spit = line.split(":");
                    return spit[0] + " " + spit[1].trim();
                })
                .map(line -> line.split(" "))
                .mapToInt(line -> {
                    var first = line[0].split("x");
                    var prod = Integer.parseInt(first[0]) * Integer.parseInt(first[1]);
                    int sum = Arrays.stream(line)
                            .skip(1)
                            .mapToInt(Integer::parseInt)
                            .sum() * 9;
                    return prod >= sum ? 1 : 0;
                })
                .sum();
        var end = System.nanoTime();
        System.out.println("Answer: " + input);
        System.out.println("Execution Time: " + (end - start) / 1_000_000 + " ms");
    }
}
