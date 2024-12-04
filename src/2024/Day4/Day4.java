import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day4 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("Input.txt"));
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        while (sc.hasNextLine()) {
            var str = sc.nextLine();
            ArrayList<Integer> array = new ArrayList<>();
            for (var i : str.getBytes()) {
                array.add(switch (i) {
                    case 'X' -> 0;
                    case 'M' -> 1;
                    case 'A' -> 2;
                    case 'S' -> 3;
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                });
            }
            arr.add(array);
        }
        long ans = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.getFirst().size(); j++) {
                if (arr.get(i).get(j) == 0) {
                    for (Direction direction : Direction.values()) {
                        ans += dfs(i, j, arr, direction, 0);
                    }
                }
            }
        }
        System.out.println(ans);
    }

    public static long dfs(int i, int j, ArrayList<ArrayList<Integer>> arr, Direction direction, int toFind) {
        if (i < 0 || i >= arr.size() || j < 0 || j >= arr.getFirst().size() || arr.get(i).get(j) != toFind) {
            return 0;
        }
        if (toFind == 3) {
            return 1;
        }
        return switch (direction) {
            case DOWN -> dfs(i + 1, j, arr, direction, toFind + 1);
            case UP -> dfs(i - 1, j, arr, direction, toFind + 1);
            case LEFT -> dfs(i, j - 1, arr, direction, toFind + 1);
            case RIGHT -> dfs(i, j + 1, arr, direction, toFind + 1);
            case DOWN_LEFT -> dfs(i + 1, j - 1, arr, direction, toFind + 1);
            case DOWN_RIGHT -> dfs(i + 1, j + 1, arr, direction, toFind + 1);
            case UP_LEFT -> dfs(i - 1, j - 1, arr, direction, toFind + 1);
            case UP_RIGHT -> dfs(i - 1, j + 1, arr, direction, toFind + 1);
        };
    }

    public enum Direction {
        DOWN, UP, LEFT, RIGHT, DOWN_LEFT, DOWN_RIGHT, UP_LEFT, UP_RIGHT
    }
}
