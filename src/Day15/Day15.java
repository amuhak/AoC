import java.io.BufferedReader;
import java.io.FileReader;

public class Day15 {
    public static void main(String[] args) throws Exception {
        long ans = 0;
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        String[] data = r.readLine().split(",");
        for (String s : data) {
            ans += (new AoCString(s)).hashCode();
        }
        System.out.println(ans);
    }

    private static class AoCString {
        public String text;

        public AoCString(String text) {
            this.text = text;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            for (int i = 0; i < text.length(); i++) {
                hash += text.charAt(i);
                hash *= 17;
                hash = hash % 256;
            }
            return hash;
        }
    }
}
