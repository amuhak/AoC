import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Day3 {
    public static void main(String[] args) throws FileNotFoundException {
        var start = System.nanoTime();
        var input = new BufferedReader(new FileReader("input.txt"));
        long sum = input.lines().mapToLong(line -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                while (!sb.isEmpty() && sb.charAt(sb.length() - 1) < c && line.length() - i > 12 - sb.length()) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                if (sb.length() < 12) {
                    sb.append(c);
                }
            }
            long ans = Long.parseLong(sb.toString());
            System.out.println(ans);
            return ans;
        }).sum();
        System.out.println(sum);
        System.out.println("Time: " + (System.nanoTime() - start) / 1_000_000 + " ms");
    }
}
