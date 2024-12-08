import org.graalvm.collections.Pair;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class Day8 {
    public static void main(String[] args) throws Exception {
        int[][] map;
        try (var s = Files.lines(Path.of("Input.txt"))) {
            map = s.map(i -> i.chars().map(c -> c - '.').toArray()).toArray(int[][]::new);
        }

        HashMap<Integer, ArrayList<Pair<Integer, Integer>>> m = new HashMap<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != 0) {
                    m.computeIfAbsent(map[i][j], _ -> new ArrayList<>()).add(Pair.create(j, i));
                }
            }
        }

        m.values().parallelStream().forEach(entry -> {
            for (int i = 0; i < entry.size(); i++) {
                for (int j = 0; j < entry.size(); j++) {
                    try {
                        if (i != j) {
                            Pair<Integer, Integer> a = entry.get(i);
                            Pair<Integer, Integer> b = entry.get(j);
                            map[2 * b.getLeft() - a.getLeft()][2 * b.getRight() - a.getRight()] = -1;
                        }
                    } catch (Exception e) {
                        // Do nothing
                    }
                }
            }
        });
        int ans = 0;
        for (var i : map) {
            for (var j : i) {
                if (j == -1) {
                    ans++;
                }
            }
        }
        System.out.println(ans);
    } // main
}
