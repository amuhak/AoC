import org.graalvm.collections.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Day12 {
    public static void main(String[] args) throws Exception {
        long ans = 0;
        ArrayList<Pair<ArrayList<Character>, ArrayList<Integer>>> data =
                getPairs();
        for (Pair<ArrayList<Character>, ArrayList<Integer>> pair : data) {
            HashSet<String> temp1 = new HashSet<>();
            // Create a bit mask
            for (long i = 0; i < (1L << pair.getRight().getLast()); i++) {
                ArrayList<Character> row = getCharacters(pair, i);
                // Check if the row is valid
                StringBuilder s2 = new StringBuilder();
                row.forEach(s2::append);
                if (isValid(s2.toString(), (ArrayList<Integer>) pair.getRight().clone())) {
                    temp1.add(s2.toString());
                }
            }
            ans += temp1.size();
        }
        System.out.println(ans);
    }

    private static ArrayList<Pair<ArrayList<Character>, ArrayList<Integer>>> getPairs() throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        ArrayList<Pair<ArrayList<Character>, ArrayList<Integer>>> data = new ArrayList<>();
        String s;
        // Read input
        while ((s = r.readLine()) != null) {
            ArrayList<Character> row = new ArrayList<>();
            ArrayList<Integer> row2 = new ArrayList<>();
            int noOfUnknowns = 0;
            for (char c : s.split(" ")[0].toCharArray()) {
                if (c == ' ') {
                    break;
                }
                row.add(c);
                if (c == '?') {
                    noOfUnknowns++;
                }
            }
            StringTokenizer st = new StringTokenizer(s.split(" ")[1], ",");
            while (st.hasMoreTokens()) {
                row2.add(Integer.parseInt(st.nextToken()));
            }
            row2.add(noOfUnknowns);
            Pair<ArrayList<Character>, ArrayList<Integer>> pair = Pair.create(row, row2);
            data.add(pair);
        }
        return data;
    }

    private static ArrayList<Character> getCharacters(Pair<ArrayList<Character>, ArrayList<Integer>> pair, long i) {
        StringBuilder s1 = new StringBuilder(Long.toBinaryString(i));
        while (s1.length() < pair.getRight().getLast()) {
            s1.insert(0, "0");
        }
        // Replace the unknowns with the bit mask
        ArrayList<Character> row = new ArrayList<>(pair.getLeft());
        int temp = 0;
        for (int j = 0; j < row.size(); j++) {
            if (row.get(j) == '?') {
                row.set(j, s1.charAt(temp++) == '0' ? '#' : '.');
            }
        }
        return row;
    }

    public static boolean isValid(String s, ArrayList<Integer> constraints) {
        constraints.removeLast();
        StringTokenizer st = new StringTokenizer(s, ".");

        while (st.hasMoreTokens()) {
            if (constraints.isEmpty()) {
                return false;
            }
            if (st.nextToken().length() == constraints.getFirst()) {
                constraints.removeFirst();
            } else {
                return false;
            }
        }
        return constraints.isEmpty();
    }
}
