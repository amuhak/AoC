import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Day11 {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("Input.txt"));
        ArrayList<Long> list = new ArrayList<>();
        while (sc.hasNext()) {
            list.add(sc.nextLong());
        }
        var ans = list.parallelStream().mapToLong(x -> r(x, 25)).sum();
        System.out.println(ans);
    }

    public static long r(long no, int toGo) {
        if (toGo == 0) {
            return 1;
        } else if (no == 0) {
            return r(1, toGo - 1);
        } else if ((int) (Math.log10(no) + 1) % 2 == 0) {
            int len = (int) (Math.log10(no) + 1) / 2;
            long divisor = (long) Math.pow(10, len);
            long top = no / divisor;
            long bot = no % divisor;
            return r(top, toGo - 1) + r(bot, toGo - 1);
        } else {
            return r(no * 2024, toGo - 1);
        }
    }
}
