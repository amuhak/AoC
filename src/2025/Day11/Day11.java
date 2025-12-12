import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Day11 {
    static ConcurrentHashMap<String, ArrayList<String>> adjList = new ConcurrentHashMap<>();
    static ConcurrentHashMap<String, Long> visited = new ConcurrentHashMap<>();

    static void main() throws FileNotFoundException {
        var start = System.nanoTime();
        new BufferedReader(new FileReader("input.txt")).lines()
                .map(line -> line.split(" "))
                .peek(arr -> arr[0] = arr[0].replace(":", ""))
                .forEach(arr -> {
                    ArrayList<String> value = new ArrayList<>(List.of(Arrays.copyOfRange(arr, 1, arr.length)));
                    adjList.put(arr[0], value);
                });
        long InToA = dfs("svr", "dac");
        reset();
        long AToB = dfs("dac", "fft");
        reset();
        long BToOut = dfs("fft", "out");
        reset();
        long InToB = dfs("svr", "fft");
        reset();
        long BToA = dfs("fft", "dac");
        reset();
        long AToOut = dfs("dac", "out");
        long ans = InToA * AToB * BToOut + InToB * BToA * AToOut;
        var end = System.nanoTime();
        System.out.println("Final Answer: " + ans);
        System.out.println("Execution Time: " + (end - start) / 1_000_000 + " ms");
    }

    static void reset() {
        visited.clear();
    }

    static long dfs(String node, String out) {
        if (visited.getOrDefault(node, -1L) != -1) {
            return visited.get(node);
        }

        long count = 0;
        for (String neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
            if (neighbor.equals(out)) {
                count++;
            } else {
                count += dfs(neighbor, out);
            }
        }
        visited.put(node, count);
        return count;
    }
}
