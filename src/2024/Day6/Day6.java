import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

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
        int x1 = x;
        int y1 = y;
        Direction d1 = Direction.UP;
        int[][] copy = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            System.arraycopy(map[i], 0, copy[i], 0, map[0].length);
        }
        Set<pos> positions = new HashSet<>();
        Direction d = Direction.UP;
        try {
            while (true) {
                int[] next = move(x, y, d);
                if (map[next[1]][next[0]] == 1) {
                    d = turnRight(d);
                } else {
                    x = next[0];
                    y = next[1];
                }

                positions.add(new pos(x, y));
                map[y][x] = 2;
            }
        } catch (Exception e) {
            // Do nothing
        }
        int ans = 0;
        positions.remove(new pos(x1, y1));
        for (final pos p : positions) {
            x = x1;
            y = y1;
            d = d1;
            for (int i = 0; i < map.length; i++) {
                System.arraycopy(copy[i], 0, map[i], 0, map[0].length);
            }
            map[p.y()][p.x()] = 1;
            int count = 0;
            try {
                while (true) {
                    count++;
                    // This will be an infinite loop (100%)
                    if (count > map.length * map[0].length) {
                        ans++;
                        break;
                    }
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
        }
        System.out.println(ans);
    }

    record pos(int x, int y) {
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
