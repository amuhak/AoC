package year2025;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day6 {
    void main() throws FileNotFoundException {
        var start = System.nanoTime();
        var lines = new BufferedReader(new FileReader("input.txt")).lines()
                .filter(line -> !line.isEmpty())
                .map(String::toCharArray)
                .toArray(char[][]::new);
        var opp = Arrays.stream(new String(lines[4]).split(" "))
                .filter(b -> !b.isEmpty())
                .map(b -> b.charAt(0) == '+')
                .toArray(Boolean[]::new);
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < lines[0].length; i++) {
            if (lines[4][i] != ' ') {
                indices.add(i);
            }
        }
        var problems = process(lines[0], indices);
        var problems1 = process(lines[1], indices);
        var problems2 = process(lines[2], indices);
        var problems3 = process(lines[3], indices);
        for (int i = 0; i < problems.size(); i++) {
            var p = problems.get(i);
            var p1 = problems1.get(i);
            var p2 = problems2.get(i);
            var p3 = problems3.get(i);
            p.add(p1);
            p.add(p2);
            p.add(p3);
            p.add(opp[i]);
            problems.set(i, p);
        }

        long total = problems.stream()
                .mapToLong(Problem::solve)
                .sum();
        System.out.println("Total: " + total);

        var end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }

    ArrayList<Problem> process(char[] line, ArrayList<Integer> indices) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Problem> problems = new ArrayList<>();
        for (int i = 1; i < indices.size(); i++) {
            var problem = new Problem();
            sb.append(line, indices.get(i - 1), indices.get(i) - indices.get(i - 1));
            problem.add(sb.toString());
            sb.setLength(0);
            problems.add(problem);
        }
        var problem = new Problem();
        sb.append(line, indices.getLast(), line.length - indices.getLast());
        sb.append(" ");
        problem.add(sb.toString());
        problems.add(problem);
        return problems;
    }

    static class Problem {
        ArrayList<String> data;
        boolean opp;

        Problem() {
            data = new ArrayList<>();
            opp = false;
        }

        void add(String val) {
            data.add(val);
        }

        void add(boolean val) {
            opp = val;
        }

        void add(Problem val) {
            data.addAll(val.data);
            opp = val.opp;
        }

        void clean() {
            data = data.stream()
                    .map(d -> d.substring(0, d.length() - 1))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        long solve() {
            clean();
            char[][] grid = data.stream()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
            int len = grid[0].length;
            for (var each : grid) {
                assert len == each.length;
            }
            data.clear();
            for (int i = 0; i < grid[0].length; i++) {
                int num = 0;
                for (char[] chars : grid) {
                    int c = chars[i];
                    if (c == ' ' || c == '\n') {
                        continue;
                    }
                    num = num * 10 + (c - '0');
                }
                data.add(num + "");
            }

            if (opp) {
                return data.stream()
                        .mapToLong(Long::parseLong)
                        .sum();
            } else {
                return data.stream()
                        .mapToLong(Long::parseLong)
                        .reduce(1, (a, b) -> a * b);
            }
        }
    }
}
