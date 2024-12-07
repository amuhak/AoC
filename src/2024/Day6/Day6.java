import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day6 {
    public static void main(String[] args) throws FileNotFoundException {
        int[][] map;
        try (var s = Files.lines(Path.of("Input.txt"))) {
            map = s.map(i -> i.chars().map(c -> switch (c) {
                case '.' -> 0;
                case '^' -> 2;
                default -> 1;
            }).toArray()).toArray(int[][]::new);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int x = -1, y = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 2) {
                    x = j;
                    y = i;
                    break;
                }
            }
            if (x != -1) {
                break;
            }
        }

        Direction d = Direction.UP;
        try{
            while (true) {
                int[] next = move(x, y, d);
                if (map[next[1]][next[0]] == 1) {
                    d = turnRight(d);
                } else {
                    x = next[0];
                    y = next[1];
                }
                map[y][x] = 2;
            }
        } catch (Exception e) {
            // Do nothing
        }
        int count = 0;
        for (int[] ints : map) {
            for (int anInt : ints) {
                if (anInt == 2) {
                    count++;
                }
            }
        }
        System.out.println(Arrays.deepToString(map));
        System.out.println(count);
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    static Direction turnRight(Direction d) {
        return switch (d) {
            case UP -> Direction.RIGHT;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
            case RIGHT -> Direction.DOWN;
        };
    }
    static int[] move(int x, int y, Direction d) {
        return switch (d) {
            case UP -> new int[]{x, y - 1};
            case DOWN -> new int[]{x, y + 1};
            case LEFT -> new int[]{x - 1, y};
            case RIGHT -> new int[]{x + 1, y};
        };
    }
}
