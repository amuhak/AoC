// needs -Xss10m to run cause of stack overflow

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Day16 {
    public static char[][] grid;
    public static boolean[][] visited;

    public static void main(String[] args) throws Exception {
        long max = 0;
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        r.mark(100000);
        int n = 0, m = 0;
        String line;
        while ((line = r.readLine()) != null) {
            n++;
            m = line.length();
        }
        r.reset();
        grid = new char[n + 2][m + 2];
        visited = new boolean[n + 2][m + 2];
        n = 1;
        while ((line = r.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                grid[n][i + 1] = line.charAt(i);
            }
            n++;
        }
        long time = System.nanoTime();
        try (ExecutorService executor = Executors.newFixedThreadPool(16)) {
            ArrayList<Future<Long>> futures = new ArrayList<>(1000);
            for (int i = 1; i < grid.length - 1; i++) {
                Callable<Long> callable = new pf(i, 0, 'r', grid);
                futures.add(executor.submit(callable));
            }
            for (int i = 1; i < grid.length - 1; i++) {
                Callable<Long> callable = new pf(i, grid[0].length - 1, 'l', grid);
                futures.add(executor.submit(callable));
            }
            for (int i = 1; i < grid[0].length - 1; i++) {
                Callable<Long> callable = new pf(0, i, 'd', grid);
                futures.add(executor.submit(callable));
            }
            for (int i = 1; i < grid[0].length - 1; i++) {
                Callable<Long> callable = new pf(grid.length - 1, i, 'u', grid);
                futures.add(executor.submit(callable));
            }
            for (Future<Long> future : futures) {
                long l = future.get();
                if (l > max) {
                    max = l;
                }
            }
        }
        System.out.println((System.nanoTime() - time) + "ns");
        System.out.println(max);
    }
}

