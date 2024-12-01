import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day18 {
    public static ArrayList<Instruction> instructions = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        String line;
        while ((line = r.readLine()) != null) {
            instructions.add(new Instruction(line));
        }
        long perimeter = 0;
        long area = 0;
        long x = 0;
        for (Instruction instruction : instructions) {
            switch (instruction.direction) {
            case 'R':
                x += instruction.length;
                break;
            case 'D':
                area += instruction.length * x * -1;
                break;
            case 'L':
                x -= instruction.length;
                break;
            case 'U':
                area += instruction.length * x;
                break;
            }
            perimeter += instruction.length;
        }
        System.out.println(area);
        System.out.println(perimeter);
        System.out.println(Math.abs(area) + perimeter / 2 + 1);

    }

    public static class Instruction {
        public char direction;
        int length;
        String color;


        public Instruction(String input) {
            String[] inputs = input.split(" ");
            this.color = inputs[2].strip();
            this.color = this.color.substring(2, this.color.length() - 1);
            switch (this.color.charAt(5)) {
            case '0':
                this.direction = 'R';
                break;
            case '1':
                this.direction = 'D';
                break;
            case '2':
                this.direction = 'L';
                break;
            case '3':
                this.direction = 'U';
                break;

            }
            this.length = Integer.parseInt(this.color.substring(0, this.color.length() - 1), 16);
        }
    }

}
