import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Day4 {
    public static void main(String[] args) throws FileNotFoundException {
        var start = System.nanoTime();
        var input = new BufferedReader(new FileReader("input.txt"));
        boolean[][] arr = input.lines()
                .map(line -> {
                    boolean[] row = new boolean[line.length()];
                    for (int i = 0; i < line.length(); i++) {
                        row[i] = line.charAt(i) == '@';
                    }
                    return row;
                })
                .toArray(boolean[][]::new);
        boolean edited = true;
        int ans = 0;
        while (edited) {
            edited = false;
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[0].length; j++) {
                    if (arr[i][j]) {
                        int numberOfNeighbors = 0;
                        for (int k = -1; k <= 1; k++) {
                            for (int l = -1; l <= 1; l++) {
                                try {
                                    if (arr[i + k][j + l] && (k != 0 || l != 0)) {
                                        numberOfNeighbors++;
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                }
                            }
                        }
                        if (numberOfNeighbors < 4) {
                            ans++;
                            arr[i][j] = false;
                            edited = true;
                        }
                    }
                }
            }
        }

        System.out.println(ans);

        var end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
    }
}
