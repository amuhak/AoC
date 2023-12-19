import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Day19 {
    public static HashMap<String, Workflow> workflows = new HashMap<>();

    public static void main(String[] args) throws Exception {
        AtomicLong ans = new AtomicLong();
        ArrayList<Part> parts = new ArrayList<>();
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        String line;
        while ((line = r.readLine()) != null && !line.isEmpty()) {
            String[] part = line.split("\\{");
            workflows.put(part[0].trim(), new Workflow(part[1].trim().substring(0, part[1].length() - 1)));
        }
        while ((line = r.readLine()) != null) {
            Part part = new Part(line);
            parts.add(part);
        }
        //long time = System.currentTimeMillis();
        // parts.parallelStream().forEach(... is slower 😭😭😭
        parts.forEach(part -> {
            if (workflows.get("in").run(part)) {
                ans.addAndGet(part.getSum());
            }
        });
        //System.out.println(System.currentTimeMillis() - time);
        System.out.println(ans);

    }

    public static class Part {
        public long x, m, a, s;

        private Part(String string) {
            String[] parts = string.substring(1, string.length() - 1).split(",");
            x = Long.parseLong(parts[0].substring(2));
            m = Long.parseLong(parts[1].substring(2));
            a = Long.parseLong(parts[2].substring(2));
            s = Long.parseLong(parts[3].substring(2));
        }

        @Override
        public String toString() {
            return "Part{" +
                    "x=" + x +
                    ", m=" + m +
                    ", a=" + a +
                    ", s=" + s +
                    '}';
        }

        public long getSum() {
            return x + m + a + s;
        }
    }

    public static class Workflow {
        ArrayList<Instruction> instructions = new ArrayList<>();
        public String Final;

        public Workflow(String string) {
            String[] parts = string.split(",");
            for (int i = 0; i < parts.length - 1; i++) {
                instructions.add(new Instruction(parts[i]));
            }
            Final = parts[parts.length - 1];
        }

        public boolean run(Part part) {
            for (Instruction instruction : instructions) {
                if (instruction.run(part)) {
                    switch (instruction.destination) {
                    case "A" -> {
                        return true;
                    }
                    case "R" -> {
                        return false;
                    }
                    default -> {
                        return workflows.get(instruction.destination).run(part);
                    }
                    }
                }
            }
            switch (Final) {
            case "A" -> {
                return true;
            }
            case "R" -> {
                return false;
            }
            default -> {
                return workflows.get(Final).run(part);
            }
            }
        }

        @Override
        public String toString() {
            return "Workflow{" +
                    "instructions=" + instructions +
                    ", Final='" + Final + '\'' +
                    '}';
        }
    }

    public static class Instruction {
        public char compare;
        public boolean greater;
        public long value;
        public String destination;

        @Override
        public String toString() {
            return "Instruction{" +
                    "compare=" + compare +
                    ", greater=" + greater +
                    ", value=" + value +
                    ", destination='" + destination + '\'' +
                    '}';
        }

        public Instruction(String string) {
            String[] parts = string.split(":");
            this.destination = parts[1];
            this.compare = parts[0].charAt(0);
            this.greater = parts[0].charAt(1) == '>';
            this.value = Long.parseLong(parts[0].substring(2));
        }

        public boolean run(Part part) {
            return switch (compare) {
                case 'x' -> greater ? part.x > value : part.x < value;
                case 'm' -> greater ? part.m > value : part.m < value;
                case 'a' -> greater ? part.a > value : part.a < value;
                case 's' -> greater ? part.s > value : part.s < value;
                default -> false;
            };
        }

    }
}
