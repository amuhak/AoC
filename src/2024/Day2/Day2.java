import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("input.txt"));
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().split(" ");
            ArrayList<Integer> temp = new ArrayList<>();
            for (String s : line) {
                temp.add(Integer.parseInt(s));
            }
            list.add(temp);
        }
        int ans = 0;

        for (var i : list) {
            if (valid(i)) {
                ans++;
            }
        }
        System.out.println(ans);
    }

    private static boolean valid(ArrayList<Integer> x) {
        boolean ans = true;
        for (int k = -1; k < x.size(); k++) {
            ArrayList<Integer> i = new ArrayList<>(x);
            if (k != -1) {
                i.remove(k);
            }
            ans = true;
            for (int j = 0; j < i.size() - 1; j++) {
                int diff = i.get(j) - i.get(j + 1);
                if (diff > 3 || diff < 1) {
                    ans = false;
                    break;
                }
            }
            if (ans) {
                return true;
            }
            ans = true;
            for (int j = 0; j < i.size() - 1; j++) {
                int diff = i.get(j) - i.get(j + 1);
                if (diff > -1 || diff < -3) {
                    ans = false;
                    break;
                }
            }
            if (ans) {
                return true;
            }
        }
        return ans;
    }
}
