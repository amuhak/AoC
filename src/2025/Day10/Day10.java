void main() throws FileNotFoundException {
    var start = System.nanoTime();
    var input = new BufferedReader(new FileReader("input.txt")).lines()
            .map(line -> {
                Scanner sc = new Scanner(line);
                String lights = sc.next();
                boolean[] lightsArray = new boolean[lights.length() - 2];
                for (int i = 1; i < lights.length() - 1; i++) {
                    lightsArray[i - 1] = lights.charAt(i) == '#';
                }
                ArrayList<ArrayList<Integer>> buttons = new ArrayList<>();
                ArrayList<String> outputs = new ArrayList<>();
                while (sc.hasNext()) {
                    String next = sc.next();
                    if (!next.isEmpty()) {
                        outputs.add(next);
                    }
                }
                for (int i = 0; i < outputs.size() - 1; i++) {
                    String output = outputs.get(i);
                    output = output.replace("(", "");
                    output = output.replace(")", "");
                    ArrayList<Integer> button = Arrays.stream(output.split(","))
                            .mapToInt(Integer::parseInt)
                            .boxed()
                            .collect(Collectors.toCollection(ArrayList::new));
                    buttons.add(button);
                }
                String output = outputs.getLast();
                output = output.replace("{", "");
                output = output.replace("}", "");
                ArrayList<Integer> voltages = Arrays.stream(output.split(","))
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .collect(Collectors.toCollection(ArrayList::new));
                return new Machine(lightsArray, buttons, voltages);
            })
            .toList();
    System.out.println(input.stream()
            .mapToInt(Machine::solve)
            .sum());
    var end = System.nanoTime();
    System.out.println("Execution Time: " + (end - start) / 1_000_000 + " ms");
}

static class Machine {
    int lights;
    ArrayList<Integer> buttons;
    ArrayList<Integer> voltages;

    Machine(boolean[] lights, ArrayList<ArrayList<Integer>> buttons, ArrayList<Integer> voltages) {
        int lightsBitmask = 0;
        for (int i = 0; i < lights.length; i++) {
            if (lights[i]) {
                lightsBitmask |= 1 << i;
            }
        }
        this.lights = lightsBitmask;
        ArrayList<Integer> Buttons = new ArrayList<>();
        for (var button : buttons) {
            int b = 0;
            for (var light : button) {
                b |= 1 << light;
            }
            Buttons.add(b);
        }
        this.buttons = Buttons;
        this.voltages = voltages;
    }

    @Override
    public String toString() {
        return "Machine{" + "lights=" + Integer.toBinaryString(lights) + ", buttons=" + buttons + ", voltages="
                + voltages + '}';
    }

    int solve() {
        int min = Integer.MAX_VALUE;
        int steps = buttons.size();
        for (int i = 0; i < 1 << steps; i++) {
            int num = 0;
            for (int j = 0; j < steps; j++) {
                if ((i & (1 << j)) != 0) {
                    num ^= buttons.get(j);
                }
            }
            if (num == lights) {
                min = Math.min(min, Integer.bitCount(i));
            }
        }
        return min;
    }
}
