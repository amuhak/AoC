import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("input.txt"));
        long ans = 0;
        ArrayList<Integer> list = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();

        while (sc.hasNext()) {
            list.add(sc.nextInt());
            int x = sc.nextInt();
            map.put(x, map.getOrDefault(x, 0) + 1);
        }
        list.sort(Integer::compareTo);
        for (int i = 0; i < list.size(); i++) {
            ans += Math.abs(list.get(i) * map.getOrDefault(list.get(i), 0));
        }
        System.out.println(ans);
    }
}
