import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Day14 {
    public static byte[][] grid;

    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        ArrayList<ArrayList<Byte>> temp = new ArrayList<>();
        String s;
        while ((s = r.readLine()) != null) {
            if (s.isEmpty()) {
                break;
            }
            ArrayList<Byte> row = new ArrayList<>();
            for (char c : s.toCharArray()) {
                if (c == '.') {
                    row.add((byte) 0);
                } else if (c == '#') {
                    row.add((byte) 1);
                } else {
                    row.add((byte) 2);
                }
            }
            temp.add(row);
        }
        grid = new byte[temp.size()][temp.getFirst().size()];
        IntStream.range(0, temp.size()).forEach(i -> IntStream.range(0, temp.getFirst().size()).forEach(j ->
                grid[i][j] = temp.get(i).get(j)));
        fallNorth();
        System.out.println(countNorth());
    }

    public static void fallNorth() {
        for (int i = 0; i < grid[0].length; i++) {
            int base = 0;
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i] == 1) {
                    base = j + 1;
                } else if (grid[j][i] == 2) {
                    if (base == j) {
                        base++;
                    } else {
                        grid[base++][i] = 2;
                        grid[j][i] = 0;
                    }
                }
            }
        }
    }

    public static long countNorth() {
        long ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                ans += grid[i][j] == 2 ? grid.length - i : 0;
            }
        }
        return ans;
    }
}
