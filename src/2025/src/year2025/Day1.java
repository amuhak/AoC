package year2025;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Day1 {
    static void main() throws FileNotFoundException {
        var start = System.nanoTime();
        var input = new BufferedReader(new FileReader("input.txt")).lines()
                .mapToInt(line -> {
                    char first = line.charAt(0);
                    int num = Integer.parseInt(line.substring(1));
                    return first == 'R' ? num : -num;
                })
                .toArray();
        int position = 50;
        int ans = 0;
        for (var move : input) {
            int old = position;
            position += move;
            if (move > 0) {
                ans += Math.floorDiv(position, 100) - Math.floorDiv(old, 100);
            } else {
                ans += Math.floorDiv(old - 1, 100) - Math.floorDiv(position - 1, 100);
            }
        }
        System.out.println("Answer: " + ans);
        var end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }
}
