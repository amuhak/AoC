import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

// This question is BFS on a binary tree
public class Day8 {
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        HashMap<String, String[]> tree = new HashMap<>();
        int[] pattern = r.readLine().replace('R', (char) 1).replace('L', (char) 0).chars().toArray();
        long ans = 0;
        r.readLine();
        String line;
        while ((line = r.readLine()) != null) {
            String[] split = line.split(" ");
            tree.put(split[0], new String[]{split[2].substring(1, 4), split[3].substring(0, 3)});
        }
        r.close();
        line = "AAA";
        while (!line.equals("ZZZ")) {
            line = tree.get(line)[pattern[(int) (ans++ % pattern.length)]];
        }
        System.out.println(ans);
    }
}
