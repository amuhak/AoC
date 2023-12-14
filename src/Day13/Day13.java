import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day13 {
    public static ArrayList<ArrayList<Boolean>> grid = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        String s;
        long ans = 0;
        while ((s = r.readLine()) != null) {
            if (s.isEmpty()) {
                ans += solve();
                grid.clear();
                continue;
            }
            ArrayList<Boolean> row = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                row.add(s.charAt(i) == '#');
            }
            grid.add(row);
        }
        ans += solve();
        System.out.println(ans);
    }

    public static int solve() {
        for (int i = 0; i < grid.size() - 1; i++) {
            if (isValidVerticalReflection(i)) {
                return (i + 1) * 100;
            }
        }
        for (int i = 0; i < grid.getFirst().size() - 1; i++) {
            if (isValidHorizontalReflection(i)) {
                return i + 1;
            }
        }
        return -1;
    }

    public static boolean isValidHorizontalReflection(int x) {
        for (int left = x; left >= 0; left--) {
            int right = x + x + 1 - left;
            if (right >= grid.getFirst().size()) {
                break;
            }
            for (ArrayList<Boolean> row : grid) {
                if (row.get(left) != row.get(right)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidVerticalReflection(int y) {
        for (int top = y; top >= 0; top--) {
            int bottom = y + y + 1 - top;
            if (bottom >= grid.size()) {
                break;
            }
            if (!grid.get(top).equals(grid.get(bottom))) {
                return false;
            }
        }
        return true;
    }
}
