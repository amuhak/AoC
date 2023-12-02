import java.io.BufferedReader;
import java.io.IOException;

public class Day1 {
    public static void main(String[] args) throws IOException {
        BufferedReader in =
                new BufferedReader(new java.io.FileReader("input.txt"));
        long sum = 0;
            for (int i = 0; i < 1000; i++) {
            String line = in.readLine();
            sum += FirstAndLast(line);
        }
        System.out.println(sum);
    }

    public static int FirstAndLast(String str) {
        int ans = 0;
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                ans = Character.getNumericValue(c) * 10;
                break;
            }
        }
        for (int i = str.length() - 1; i > 0; i--) {
            if (Character.isDigit(str.charAt(i))) {
                ans += Character.getNumericValue(str.charAt(i));
                break;
            }
        }
        return ans;
    }
}
