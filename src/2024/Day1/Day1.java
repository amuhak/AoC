import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("input.txt"));
        long ans = 0;
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> list1 = new ArrayList<>();

        while (sc.hasNext()) {
            list.add(sc.nextInt());
            list1.add(sc.nextInt());
        }
        list.sort(Integer::compareTo);
        list1.sort(Integer::compareTo);
        for (int i = 0; i < list.size(); i++) {
            ans += Math.abs(list.get(i) - list1.get(i));
        }
        System.out.println(ans);
    }
}
