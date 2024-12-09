import java.io.File;
import java.util.Scanner;

public class Day9 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("Input.txt"));
        int[] arr = sc.nextLine().chars().map(i -> i - '0').toArray();
        int[] spaces = new int[arr.length / 2];
        int[] nos = new int[arr.length / 2 + 1];
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 1) {
                spaces[i / 2] = arr[i];
            } else {
                nos[i / 2] = arr[i];
            }
        }
        long ans = 0;
        int idx = nos[0];
        int nosL = 1, nosR = nos.length - 1, spacesL = 0;
        while (nosL <= nosR) {
            for (int space = spaces[spacesL++]; space > 0; space--) {
                if (nos[nosR]-- > 0) {
                    if (nosL > nosR) {
                        break;
                    }
                    ans += (long) (idx) * (long) nosR;
                    idx++;
                } else {
                    space++;
                    nosR--;
                }
            }

            for (int i = 0; i < nos[nosL]; i++) {
                ans += (long) (idx) * (long) nosL;
                idx++;
            }
            nosL++;
        }
        System.out.println(ans);
    }
}
