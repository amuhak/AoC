import org.graalvm.collections.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day7 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("Input.txt"));
        ArrayList<Pair<Long, ArrayList<Long>>> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            var str = sc.nextLine().split(":");
            var no = Long.parseLong(str[0]);
            var arr = new ArrayList<Long>();
            var str1 = str[1].strip().split(" ");
            for (var s : str1) {
                arr.add(Long.parseLong(s));
            }
            list.add(Pair.create(no, arr));
        }
        var ans = list.stream().parallel().filter(p -> {
            var no = p.getLeft();
            var arr = p.getRight();
            int len = arr.size() - 1;
            // if 0 add, if 1 multiply
            for (int i = 0; i < 1 << len; i++) {
                long sum = arr.getFirst();
                for (int j = 0; j < len; j++) {
                    if ((i & 1 << j) != 0) {
                        sum += arr.get(j + 1);
                    } else {
                        sum *= arr.get(j + 1);
                    }
                }
                if (sum == no) {
                    return true;
                }
            }
            return false;
        }).mapToLong(Pair::getLeft).sum();

        System.out.println(ans);
    }
}
