import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) throws FileNotFoundException {
        // page ordering rules
        Scanner sc = new Scanner(new File("Input.txt"));
        ArrayList<HashSet<Integer>> arr = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            arr.add(new HashSet<>());
        }

        while (sc.hasNextLine()) {
            var str = sc.nextLine().split("\\|");
            int l = Integer.parseInt(str[0]), r = Integer.parseInt(str[1]);
            arr.get(l).add(r);
        }

        // pages to produce in each update
        sc = new Scanner(new File("Input1.txt"));
        int ans = sc.tokens()
                .parallel()
                .map(i -> i.split(","))
                .map(i -> Arrays.stream(i).map(Integer::parseInt).toList())
                .filter(i -> !isValid(i, arr))
                .map(ArrayList::new)
                .mapToInt(i -> {
                    while (!isValid(i, arr)) {
                        // find the first invalid pair and swap them
                        for (int j = 1; j < i.size(); j++) {
                            var set = arr.get(i.get(j));
                            for (int k = 0; k < j; k++) {
                                if (set.contains(i.get(k))) {
                                    int temp = i.get(j);
                                    i.set(j, i.get(k));
                                    i.set(k, temp);
                                    break;
                                }
                            }
                        }
                    }
                    return i.get(i.size() / 2);
                })
                .sum();

        System.out.println(ans);
    }

    public static boolean isValid(List<Integer> a, ArrayList<HashSet<Integer>> arr) {
        for (int i = 1; i < a.size(); i++) {
            var set = arr.get(a.get(i));
            for (int j = 0; j < i; j++) {
                if (set.contains(a.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
