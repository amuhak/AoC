import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("Input.txt"));
        String in = sc.nextLine();
        var match = Pattern.compile("(mul\\([0-9]{1,3},[0-9]{1,3}\\)|don't\\(\\)|do\\(\\))");
        Matcher m = match.matcher(in);
        long ans = 0;
        boolean use = true;
        while (m.find()) {
            var temp = m.group();
            if (temp.equals("do()")) {
                use = true;
            } else if (temp.equals("don't()")) {
                use = false;
            } else if (use) {
                String[] t1 = m.group().substring(4, m.group().length() - 1).split(",");
                long a = Long.parseLong(t1[0]);
                long b = Long.parseLong(t1[1]);
                ans += a * b;
            }
        }
        System.out.println(ans);
    }
}
