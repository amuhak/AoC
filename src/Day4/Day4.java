import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Day4 {
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        long ans = 0;
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
            if (ans1 != 0) {
                System.out.println(Math.pow(2, ans1 - 1));
                ans += (long) Math.pow(2, ans1 - 1);
            }
            set.clear();
        }
        System.out.println(ans);
    }
}
