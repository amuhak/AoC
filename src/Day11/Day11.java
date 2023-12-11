import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day11 {
    public static void main(String[] args) throws Exception {
        final int SIZE = 1000000 - 1;
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        LinkedList<LinkedList<Character>> data = new LinkedList<>();
        ArrayList<Point> points = new ArrayList<>();
        long ans;
        String s;
        while ((s = r.readLine()) != null) {
            LinkedList<Character> row = new LinkedList<>();
            for (char c : s.toCharArray()) {
                row.add(c);
            }
            data.add(row);
        }
        int temp = 0;
        ArrayList<Integer> rowNos = new ArrayList<>();
        ArrayList<Integer> colNos = new ArrayList<>();
        for (LinkedList<Character> l : data) {
            if (isAllDot(l)) {
                rowNos.add(temp);
            }
            temp++;
        }
        // Iterate down the columns
        for (int i = 0; i < data.getFirst().size(); i++) {
            boolean allDot = true;
            for (LinkedList<Character> datum : data) {
                if (datum.get(i) != '.') {
                    allDot = false;
                    break;
                }
            }
            if (allDot) {
                colNos.add(i);
            }
        }
        colNos.sort(Integer::compareTo);
        rowNos.sort(Integer::compareTo);

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.getFirst().size(); j++) {
                if (data.get(i).get(j) == '#') {
                    int rowCounter = 0;
                    int colCounter = 0;
                    for (int c : colNos) {
                        if (c <= j) {
                            colCounter += SIZE;
                        } else {
                            break;
                        }
                    }
                    for (int row : rowNos) {
                        if (row <= i) {
                            rowCounter += SIZE;
                        } else {
                            break;
                        }
                    }
                    points.add(new Point(i + rowCounter, j + colCounter));
                }
            }
        }
        ans = 0;
        //data.forEach(System.out::println);
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                ans += distance(points.get(i), points.get(j));
            }
        }
        System.out.println(ans);
    }

    public static boolean isAllDot(List<Character> data) {
        for (char c : data) {
            if (c != '.') {
                return false;
            }
        }
        return true;
    }

    public static int distance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}
