import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Day19 {
    public static HashMap<String, Workflow> workflows = new HashMap<>();
    public static ArrayList<Part> answers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        AtomicLong ans = new AtomicLong();
        ArrayList<Part> parts = new ArrayList<>();
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        String line;
        while ((line = r.readLine()) != null && !line.isEmpty()) {
            String[] part = line.split("\\{");
            workflows.put(part[0].trim(), new Workflow(part[1].trim().substring(0, part[1].length() - 1)));
        }
        //long time = System.currentTimeMillis();
        // parts.parallelStream().forEach(... is slower 😭😭😭
        parts.forEach(part -> {
            if (workflows.get("in").run(part)) {
                ans.addAndGet(part.getNoOfCombos());
            }
        });
        //System.out.println(System.currentTimeMillis() - time);
        System.out.println(ans);

    }

    public static class Part {
        public Range x, m, a, s;

        public Part(Range x, Range m, Range a, Range s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }

        public long getNoOfCombos() {
            return (long) x.length() * m.length() * a.length() * s.length();
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
                case 'x' -> {
                    if (!greater) {
                        yield part.x.setMax(value);
                    } else {
                        yield part.x.setMin(value);
                    }
                }

                case 'm' -> {
                    if (!greater) {
                        yield part.m.setMax(value);
                    } else {
                        yield part.m.setMin(value);
                    }
                }
                case 'a' -> {
                    if (!greater) {
                        yield part.a.setMax(value);
                    } else {
                        yield part.a.setMin(value);
                    }
                }
                case 's' -> {
                    if (!greater) {
                        yield part.s.setMax(value);
                    } else {
                        yield part.s.setMin(value);
                    }
                }
                default -> false;
            };
        }

    }

    public static class Range {
        public long start;
        public long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public boolean setMax(long max) {
            if (max > start) {
                if (max < end) {
                    end = max;
                }
                return true;
            }
            return false;
        }

        public boolean setMin(long min) {
            if (min < end) {
                if (min > start) {
                    start = min;
                }
                return true;
            }
            return false;
        }

        public boolean contains(long num) {
            return start <= num && num < end;
        }

        public int length() {
            return (int) (end - start);
        }

        public void setZero() {
            start = 0;
            end = 0;
        }

        public Range copy() {
            return new Range(start, end);
        }

        public Range difference(Range range) {
            if (range.start >= end || range.end <= start) {
                return this;
            }
            if (range.start <= start && range.end >= end) {
                return new Range(0, 0);
            }
            if (range.start <= start) {
                return new Range(range.end, end);
            }
            return new Range(start, range.start);
        }

        public boolean equals(Range range) {
            return range.start == start && range.end == end;
        }
    }
}



