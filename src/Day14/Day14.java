import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day14 {
    public static byte[][] grid;

    public static void main(String[] args) throws Exception {
        HashSet<String> visited = new HashSet<>();
        LinkedList<String> queue = new LinkedList<>();
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
        int cycleLength = 0;
        String cycleStart = "";
        while (true) {
            fall();
            rotateMatrix();
            fall();
            rotateMatrix();
            fall();
            rotateMatrix();
            fall();
            rotateMatrix();
            if (visited.contains(Arrays.deepToString(grid.clone()))) {
                if (cycleLength == 0) {
                    cycleStart = (Arrays.deepToString(grid.clone()) + countNorth());
                } else if (cycleStart.equals(Arrays.deepToString(grid.clone()) + countNorth())) {
                    break;
                }
                cycleLength++;
            } else {
                cycleLength = 0;
            }
            visited.add(Arrays.deepToString(grid.clone()));
            queue.add(Arrays.deepToString(grid.clone()) + countNorth());
        }
        long l = 0;
        for (var s1 : queue) {
            l++;
            if (s1.equals(cycleStart)) {
                break;
            }
        }
        //\d{1,}[0-9]
        String ans = queue.get((int) ((1000000000 - l) % cycleLength) + (int) l - 1);
        Pattern pattern = Pattern.compile("\\d+[0-9]");
        Matcher matcher = pattern.matcher(ans);
        matcher.find();
        System.out.println(matcher.group());
    }

    public static void fall() {
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
            for (int j = 0; j < grid[0].length; j++) {
                ans += grid[i][j] == 2 ? grid.length - i : 0;
            }
        }
        return ans;
    }

    public static void rotateMatrix() {
        int rows = grid.length;
        int cols = grid[0].length;

        // Create a new matrix to store the rotated values
        byte[][] rotatedMatrix = new byte[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Rotate 90 degrees clockwise formula
                rotatedMatrix[j][rows - 1 - i] = grid[i][j];
            }
        }
        grid = rotatedMatrix;
    }

}
