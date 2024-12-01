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
            long[] nums = new long[3];
            st = new StringTokenizer(line);
            st.nextToken();
            st.nextToken();
            String a;
            while (st.hasMoreTokens()) {
                int currentNo = Integer.parseInt(st.nextToken());
                a = st.nextToken();
                if (Character.isAlphabetic(a.charAt(a.length() - 1))) {
                    assert true;
                } else {
                    a = a.substring(0, a.length() - 1);
                }

                if (nums[map.get(a)] < currentNo) {
                    nums[map.get(a)] = currentNo;
                }

            }
            ans += (nums[0] * nums[1] * nums[2]);
        }
        System.out.println(ans);
    }
}
