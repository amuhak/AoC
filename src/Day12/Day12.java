import org.graalvm.collections.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Day12 {
    public static HashMap<String, Long> dp;

    public static void main(String[] args) throws Exception {
        long ans = 0;
        ArrayList<Pair<ArrayList<Character>, ArrayList<Integer>>> data = getPairs();
        for (Pair<ArrayList<Character>, ArrayList<Integer>> pair : data) {
            dp = new HashMap<>();
            ans += recursion(pair.getLeft(), pair.getRight(), 0, 0, 0);
            //System.out.println(a++);
        }
        System.out.println(ans);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    // Read input
    private static ArrayList<Pair<ArrayList<Character>, ArrayList<Integer>>> getPairs() throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        ArrayList<Pair<ArrayList<Character>, ArrayList<Integer>>> data = new ArrayList<>();
        String s;
        // Read input
        while ((s = r.readLine()) != null) {
            ArrayList<Character> row = new ArrayList<>();
            ArrayList<Integer> row2 = new ArrayList<>();
            for (char c : s.split(" ")[0].toCharArray()) {
                if (c == ' ') {
                    break;
                }
                row.add(c);
            }
            ArrayList copy = (ArrayList) row.clone();
            for (int i = 0; i < 4; i++) {
                row.add('?');
                row.addAll(copy);
            }
            StringTokenizer st = new StringTokenizer(s.split(" ")[1], ",");
            while (st.hasMoreTokens()) {
                row2.add(Integer.parseInt(st.nextToken()));
            }
            copy = (ArrayList) row2.clone();
            for (int i = 0; i < 4; i++) {
                row2.addAll(copy);
            }
            // optimize
            while (row.getFirst() == '.') {
                row.removeFirst();
            }
            while (row.getLast() == '.') {
                row.removeLast();
            }
            for (int i = 0; i < row.size() - 1; i++) {
                if (row.get(i) == '.' && row.get(i + 1) == '.') {
                    row.remove(i);
                    i--;
                }
            }
            Pair<ArrayList<Character>, ArrayList<Integer>> pair = Pair.create(row, row2);
            data.add(pair);
        }
        return data;
    }

    public static long recursion(ArrayList<Character> data, ArrayList<Integer> constraint, int whereInData,
                                 int whereInConstraint, int howMany) {
        if (whereInData == data.size()) {
            if (whereInConstraint == constraint.size() && howMany == 0) {
                return 1;
            } else if (whereInConstraint == constraint.size() - 1 && howMany == constraint.get(whereInConstraint)) {
                return 1;
            } else {
                return 0;
            }
        }
        if (dp.containsKey(whereInData + " " + whereInConstraint + " " + howMany)) {
            return dp.get(whereInData + " " + whereInConstraint + " " + howMany);
        }
        long ans = 0;
        if (data.get(whereInData) == '.' && howMany == 0) {
            ans += recursion(data, constraint, whereInData + 1, whereInConstraint, howMany);
        } else if (data.get(whereInData) == '.'
                && whereInConstraint < constraint.size()
                && howMany == constraint.get(whereInConstraint)) {
            ans += recursion(data, constraint, whereInData + 1, whereInConstraint + 1, 0);
        } else if (data.get(whereInData) == '#') {
            ans += recursion(data, constraint, whereInData + 1, whereInConstraint, howMany + 1);
        } else if (data.get(whereInData) == '?') {
            if (howMany == 0) {
                //Emulate '.'
                ans += recursion(data, constraint, whereInData + 1, whereInConstraint, howMany);
            } else if (whereInConstraint < constraint.size()
                    && howMany == constraint.get(whereInConstraint)) {
                //Emulate '.' but you have also completed the constraint
                ans += recursion(data, constraint, whereInData + 1, whereInConstraint + 1, 0);
            }
            //else you have failed

            // now for '#'
            ans += recursion(data, constraint, whereInData + 1, whereInConstraint, howMany + 1);
        }

        dp.put(whereInData + " " + whereInConstraint + " " + howMany, ans);
        return ans;
    }


}
