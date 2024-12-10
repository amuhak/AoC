import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day9 {
    public static void main(String[] args) throws Exception {
        var start = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("Input.txt"));
        int[] arr = sc.nextLine().chars().map(i -> i - '0').toArray();
        int[] spaces = new int[arr.length / 2];
        int[] nos = new int[arr.length / 2 + 1];
        int[] str = new int[Arrays.stream(arr).sum() * 2];
        ArrayList<Pair> s = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 1) {
                spaces[i / 2] = arr[i];
            } else {
                nos[i / 2] = arr[i];
            }
        }
        int l = nos[0];
        for (int i = 0; i < spaces.length; i++) {
            s.add(new Pair(l, spaces[i]));
            l += spaces[i];
            for (int j = 0; j < nos[i + 1]; j++) {
                str[l++] = (i + 1);
            }
        }
        for (int i = nos.length - 1; i > 0; i--) {
            if (nos[i] == 0) {
                continue;
            }
            for (int j = 0; j <= i; j++) {
                if (nos[i] <= s.get(j).right) {
                    var pair = s.get(j);
                    s.set(j, new Pair(pair.left + nos[i], pair.right - nos[i]));
                    for (int k = s.get(j).left; k < str.length; k++) {
                        if (str[k] == i) {
                            str[k] = 0;
                        }
                    }
                    // Find the jth space
                    for (int k = pair.left; k < s.get(j).left; k++) {
                        str[k] = i;
                    }
                    break;
                }
            }
        }
        // System.out.println(Arrays.toString(str));
        long ans = 0;
        for (int i = 0; i < str.length; i++) {
            ans += (long) i * str[i];
        }
        System.out.println(ans);
        System.out.println(System.currentTimeMillis() - start);
    }

    static class Pair {
        public int left;
        public int right;

        public Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }
}
