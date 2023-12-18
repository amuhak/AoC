import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class Day18 {
    public static ArrayList<Instruction> instructions = new ArrayList<>();
    public static String[][] grid;

    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        String line;
        while ((line = r.readLine()) != null) {
            instructions.add(new Instruction(line));
        }
        long up = 0, down = 0, left = 0, right = 0;
        for (Instruction i : instructions) {
            switch (i.direction) {
            case 'U', 'u':
                up += i.length;
                break;
            case 'D', 'd':
                down += i.length;
                break;
            case 'L', 'l':
                left += i.length;
                break;
            case 'R', 'r':
                right += i.length;
                break;
            }
        }
        grid = new String[(int) (up + down + 2)][(int) (left + right + 2)];
        Point start = new Point((int) (left), (int) (up));
        for (Instruction instruction : instructions) {
            switch (instruction.direction) {
            case 'u', 'U':
                for (int i = 0; i < instruction.length; i++) {
                    grid[start.y - i][start.x] = instruction.color;
                }
                start.y -= instruction.length;
                break;
            case 'd', 'D':
                for (int i = 0; i < instruction.length; i++) {
                    grid[start.y + i][start.x] = instruction.color;
                }
                start.y += instruction.length;
                break;
            case 'l', 'L':
                for (int i = 0; i < instruction.length; i++) {
                    grid[start.y][start.x - i] = instruction.color;
                }
                start.x -= instruction.length;
                break;
            case 'r', 'R':
                for (int i = 0; i < instruction.length; i++) {
                    grid[start.y][start.x + i] = instruction.color;
                }
                start.x += instruction.length;
                break;
            }
        }
        floodFill(0, 0);
        long count = 0;
        for (String[] s : grid) {
            for (String s1 : s) {
                if (!Objects.equals(s1, "Blank")) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    public static class Instruction {
        public char direction;
        byte length;
        String color;


        public Instruction(char direction, byte length, String color) {
            this.direction = direction;
            this.length = length;
            this.color = color;
        }

        public Instruction(String input) {
            String[] inputs = input.split(" ");
            this.direction = inputs[0].charAt(0);
            this.length = Byte.parseByte(inputs[1]);
            this.color = inputs[2].strip();
            this.color = this.color.substring(1, this.color.length() - 1);
        }
    }

    public static void floodFill(int x, int y) {
        if (x < 0 || x >= grid[0].length || y < 0 || y >= grid.length) {
            return;
        }
        if (grid[y][x] != null || Objects.equals(grid[y][x], "Blank")) {
            return;
        }
        grid[y][x] = "Blank";
        floodFill(x + 1, y);
        floodFill(x - 1, y);
        floodFill(x, y + 1);
        floodFill(x, y - 1);
    }
}
