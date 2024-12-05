import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("Input.txt"));
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            arr.add(new ArrayList<>());
        }

        while (sc.hasNextLine()) {
            var str = sc.nextLine().split("\\|");
            int l = Integer.parseInt(str[0]), r = Integer.parseInt(str[1]);
            arr.get(l).add(r);
        }

        sc = new Scanner(new File("Input1.txt"));
        int ans = sc.tokens().parallel()
                .map(i -> i.split(","))
                .map(i -> Arrays.stream(i).map(Integer::parseInt).toList())
                .mapToInt(a -> {
                    for (int i = 1; i < a.size(); i++) {
                        var set = arr.get(a.get(i));
                        for (int j = 0; j < i; j++) {
                            if (set.contains(a.get(j))) {
                                return 0;
                            }
                        }
                    }
                    return a.get(a.size() / 2);
                })
                .sum();

        System.out.println(ans);
    }
}
