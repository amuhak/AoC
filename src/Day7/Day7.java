import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Day7 {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        long ans = 0;
        ArrayList<Hand> input = new ArrayList<>();
        String line;
        while ((line = r.readLine()) != null) {
            input.add(new Hand(line));
        }
        Collections.shuffle(input);
        input.sort(Hand::compareTo);
        int i = 1;
        for (Hand o : input) {
            ans += (long) o.bet * i++;
        }
        System.out.println(ans);
    }
}
