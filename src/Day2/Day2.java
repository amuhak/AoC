import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Day2 {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        long ans = 0;
        String line;
        StringTokenizer st;
        HashMap<String, Integer> map = new HashMap<>();
        map.put("red", 0);
        map.put("green", 1);
        map.put("blue", 2);
        final int REDSUM = 12;
        final int GREENSUM = 13;
        final int BLUESUM = 14;
        final int[] RGB = {REDSUM, GREENSUM, BLUESUM};
        while ((line = r.readLine()) != null) {
            int[] nums = new int[3];
            st = new StringTokenizer(line);
            st.nextToken();
            String a = st.nextToken();
            int gameNo = Integer.parseInt(a.substring(0, a.length() - 1));
            boolean flag = true;
            boolean reset = false;
            while (st.hasMoreTokens()) {
                if (reset) {
                    nums = new int[3];
                    reset = false;
                }
                int currentNo = Integer.parseInt(st.nextToken());
                a = st.nextToken();
                if (Character.isAlphabetic(a.charAt(a.length() - 1))) {
                    assert true;
                } else if (a.charAt(a.length() - 1) == ';') {
                    a = a.substring(0, a.length() - 1);
                    reset = true;
                } else {
                    a = a.substring(0, a.length() - 1);
                }
                nums[map.get(a)] += currentNo;

                for (int i = 0; i < 3; i++) {
                    if (!(RGB[i] >= nums[i])) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                //System.out.println(gameNo);
                ans += gameNo;
            }
        }
        System.out.println(ans);
    }
}
