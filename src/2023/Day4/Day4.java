import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Day4 {
    public static ArrayList<Long> answers = new ArrayList<>(200);
    public static long ans = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        HashSet<Integer> set = new HashSet<>();
        String line;
        while ((line = r.readLine()) != null) {
            int ans1 = 0;
            st = new StringTokenizer(line);
            st.nextToken();
            st.nextToken();
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                if (s.equals("|")) {
                    break;
                }
                set.add(Integer.parseInt(s));
            }
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                if (set.contains(Integer.parseInt(s))) {
                    ans1++;
                }
            }
            answers.add(ans1 != 0 ? (long) ans1 : 0L);
            set.clear();
        }
        for (int i = 0; i < answers.size(); i++) {
            ans += part2(i);
        }
        System.out.println(ans);
    }

    public static int part2(int a) {
        int ans1 = 1;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= answers.get(a); i++) {
            list.add(i + a);
        }
        for (int l : list) {
            ans1 += part2(l);
        }
        return ans1;
    }
}
