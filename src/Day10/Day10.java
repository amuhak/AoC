import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

public class Day10 {
    public static int[][] path;
    public static char[][] points;
    public static HashMap<Character, boolean[][]> map = new HashMap<>();
    public static int count = 2;

    public static void main(String[] args) throws Exception {
        map.put('|', new boolean[][]{
                {false, true, false},
                {false, false, false},
                {false, true, false}
        });
        map.put('-', new boolean[][]{
                {false, false, false},
                {true, false, true},
                {false, false, false}
        });
        map.put('L', new boolean[][]{
                {false, true, false},
                {false, false, true},
                {false, false, false}
        });
        map.put('J', new boolean[][]{
                {false, true, false},
                {true, false, false},
                {false, false, false}
        });
        map.put('7', new boolean[][]{
                {false, false, false},
                {true, false, false},
                {false, true, false}
        });
        map.put('F', new boolean[][]{
                {false, false, false},
                {false, false, true},
                {false, true, false}
        });
        map.put('.', new boolean[][]{
                {false, false, false},
                {false, false, false},
                {false, false, false}
        });
        map.put('S', new boolean[][]{
                {true, true, true},
                {true, false, true},
                {true, true, true}
        });
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        r.mark(1000000);
        String s;
        int y = 0, b = 0;
        while ((s = r.readLine()) != null) {
            y++;
            b = s.length();
        }
        r.reset();
        points = new char[y + 2][b + 2];
        path = new int[y + 2][b + 2];
        y = 1;
        Point START = new Point();
        while ((s = r.readLine()) != null) {
            for (int x = 1; x < b + 1; x++) {
                if (s.charAt(x - 1) == 'S') {
                    START = new Point(x, y);
                }
                points[y][x] = s.charAt(x - 1);
            }
            y++;
        }
        path[START.y][START.x] = 1;
        System.out.println(Arrays.deepToString(points));
        System.out.println(Arrays.deepToString(path));
        System.out.println(START);
        Point p = (Point) START.clone();
        do {
            p = findNext(p);
        } while (p != null);
        for (int[] arr : path) {
            System.out.println(Arrays.toString(arr));
        }
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                if (path[i][j] == (count - 1) / 2) {
                    p = new Point(j, i);
                }
            }
        }
        System.out.println((count - 1) / 2);
        System.out.println(p);
        System.out.println(points[p.y][p.x]);
    }

    public static Point findNext(Point p) {
        boolean[][] arr = map.get(points[p.y][p.x]);
        for (int y = 0; y < arr.length; y++) {
            for (int x = 0; x < arr.length; x++) {
                if (arr[y][x]) {
                    char c = points[p.y + y - 1][p.x + x - 1];
                    if (c != 0) {
                        boolean[][] arr2 = map.get(c);
                        if (arr2[(2 - y)][2 - x] && path[p.y + y - 1][p.x + x - 1] == 0) {
                            path[p.y + y - 1][p.x + x - 1] = count++;
                            return new Point(p.x + x - 1, p.y + y - 1);
                        }
                    }
                }
            }
        }
        return null;
    }
}
