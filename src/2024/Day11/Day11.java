import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day11 {
    public static HashMap<Pair, Long> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("Input.txt"));
        ArrayList<Long> list = new ArrayList<>();
        while (sc.hasNext()) {
            list.add(sc.nextLong());
        }

        System.out.println(list.stream().mapToLong(no -> r(no, 75)).sum());

    }

    public static long r(long no, int toGo) {
        if (map.containsKey(new Pair(no, toGo))) {
            return map.get(new Pair(no, toGo));
        }
        if (toGo == 0) {
            return 1;
        } else if (no == 0) {
            map.put(new Pair(no, toGo), r(1, toGo - 1));
        } else if ((int) (Math.log10(no) + 1) % 2 == 0) {
            int len = (int) (Math.log10(no) + 1) / 2;
            long divisor = (long) Math.pow(10, len);
            long top = no / divisor;
            long bot = no % divisor;
            map.put(new Pair(no, toGo), r(top, toGo - 1) + r(bot, toGo - 1));
        } else {
            map.put(new Pair(no, toGo), r(no * 2024, toGo - 1));
        }
        return map.get(new Pair(no, toGo));
    }

    public static class Pair {
        long a;
        long b;

        Pair(long a, long b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public final boolean equals(Object o) {
            if (!(o instanceof Pair pair)) {
                return false;
            }

            return a == pair.a && b == pair.b;
        }

        @Override
        public int hashCode() {
            int result = Long.hashCode(a);
            result = 31 * result + Long.hashCode(b);
            return result;
        }
    }
}

