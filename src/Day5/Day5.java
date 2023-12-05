import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day5 {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        ArrayList<Long> seeds;
        seeds = getList(r.readLine().substring(6));
        long ans = Long.MAX_VALUE;
        ArrayList<ArrayList<Map>> maps = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            maps.add(new ArrayList<>());
        }
        String line;
        byte i = -1;
        while ((line = r.readLine()) != null) {
            if (line.isBlank()) {
                continue;
            } else if (line.contains(":")) {
                i++;
                continue;
            }
            ArrayList<Long> temp = getList(line);
            Range r1 = new Range(temp.get(0), temp.get(2) + temp.get(0));
            Range r2 = new Range(temp.get(1), temp.get(2) + temp.get(1));
            maps.get(i).add(new Map(r1, r2));
        }

        for (long seed : seeds) {
            for (ArrayList<Map> map : maps) {
                long a = -1;
                for (Map m : map) {
                    a = m.contains(seed);
                    if (a != -1) {
                        break;
                    }
                }
                if (a != -1) {
                    seed = a;
                }
            }
            if (seed < ans){
                ans = seed;
            }
        }
        System.out.println(ans);
    }

    public static ArrayList<Long> getList(String s) {
        return Arrays.stream(s.strip().split(" ")).map(Long::parseLong).collect(ArrayList::new, ArrayList::add,
                ArrayList::addAll);
    }


}
