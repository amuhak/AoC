import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day25 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<schema> locks = new ArrayList<>(), keys = new ArrayList<>();
        Scanner sc = new Scanner(new File("Input.txt"));
        while (sc.hasNext()) {
            String s = sc.next();
            boolean isLock = s.charAt(0) == '#';
            schema sch = new schema();
            int[] highs = sch.hights;
            for (int i = 1; i < 6; i++) {
                s = sc.next();
                for (int j = 0; j < 5; j++) {
                    if (s.charAt(j) == '#') {
                        highs[j]++;
                    }
                }
            }
            sc.next();
            if (isLock) {
                locks.add(sch);
            } else {
                keys.add(sch);
            }
        }
        int ans = 0;
        for (schema key : keys) {
            for (schema lock : locks) {
                boolean works = true;
                for (int i = 0; i < 5; i++) {
                    if (key.hights[i] + lock.hights[i] > 5) {
                        works = false;
                        break;
                    }
                }
                if (works) {
                    ans++;
                }
            }
        }
        System.out.println(ans);
    }
    static class schema{
        public int[] hights;
        public schema(){
            hights = new int[5];
        }
    }
}
