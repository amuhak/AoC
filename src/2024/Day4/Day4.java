import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Day4 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("Input.txt"));
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        while (sc.hasNextLine()) {
            var str = sc.nextLine();
            ArrayList<Integer> array = new ArrayList<>();
            for (var i : str.getBytes()) {
                array.add(switch (i) {
                    case 'X' -> 0;
                    case 'M' -> 1;
                    case 'A' -> 2;
                    case 'S' -> 3;
                    default -> 0;
                });
            }
            arr.add(array);
        }
        System.out.println(arr);
        long ans = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.getFirst().size(); j++) {
                if (arr.get(i).get(j) == 2) {
                    ans += dfs(i, j, arr);
                }
            }
        }
        System.out.println(ans);
    }

    public static long dfs(int i, int j, ArrayList<ArrayList<Integer>> arr) {
        if (i < 1 || i >= arr.size() - 1 || j < 1 || j >= arr.getFirst().size() - 1) {
            return 0;
        }
        int[][] d = {{-1, 1}, {1, -1}, {-1, -1}, {1, 1}};
        for (var k : d) {
            if (arr.get(i + k[0]).get(j + k[1]) == 0 || arr.get(i + k[0]).get(j + k[1]) == 2) {
                return 0;
            }
        }

        return !Objects.equals(arr.get(i - 1).get(j - 1), arr.get(i + 1).get(j + 1)) &&
                !Objects.equals(arr.get(i - 1).get(j + 1), arr.get(i + 1).get(j - 1)) ? 1 : 0;
    }
}
