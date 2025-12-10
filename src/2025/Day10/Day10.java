import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

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
    AtomicInteger num = new AtomicInteger();
    int finalAns = input.parallelStream()
            .map(Machine::toProblem)
            .map(problem -> {
                Model model = new Model("AoC Integer Solver");
                int[][] A = problem.A;
                int[] b = problem.b;
                // int[] x = problem.x;
                int numVars = A[0].length;
                int numEqs = A.length;
                IntVar[] x = model.intVarArray("x", numVars, 0, 500);
                for (int row = 0; row < numEqs; row++) {
                    // scalar product: A[row] * x = b[row]
                    model.scalar(x, A[row], "=", b[row])
                            .post();
                }
                IntVar cost = model.intVar("cost", 0, 500);
                model.sum(x, "=", cost)
                        .post();

                model.setObjective(Model.MINIMIZE, cost);

                // 6. Solve
                Solution solution = model.getSolver()
                        .findOptimalSolution(cost, Model.MINIMIZE);
                if (solution != null) {
                    System.out.println("Solution found!");
                    System.out.println("Total Cost: " + solution.getIntVal(cost));
                    for (int i = 0; i < numVars; i++) {
                        System.out.printf("x[%d] = %d\n", i, solution.getIntVal(x[i]));
                    }
                    System.out.println("Done with " + (num.getAndIncrement()));
                } else {
                    System.out.println("No integer solution exists.");
                }
                return Arrays.stream(x)
                        .mapToInt(i -> solution.getIntVal(i))
                        .toArray();
            })
            .mapToInt(arr -> Arrays.stream(arr)
                    .sum())
            .sum();
    System.out.println("Final Answer: " + finalAns);
    var end = System.nanoTime();
    System.out.println("Execution Time: " + (end - start) / 1_000_000 + " ms");
}

static class Machine {
    boolean[] lights;
    ArrayList<ArrayList<Integer>> buttons;
    ArrayList<Integer> voltages;

    Machine(boolean[] lights, ArrayList<ArrayList<Integer>> buttons, ArrayList<Integer> voltages) {
        this.lights = lights;
        this.buttons = buttons;
        this.voltages = voltages;
    }

    @Override
    public String toString() {
        return "Machine{" + "lights=" + Arrays.toString(lights) + ", buttons=" + buttons + ", voltages=" + voltages
                + '}';
    }

    Problem toProblem() {
        int[] b = new int[voltages.size()];
        for (int i = 0; i < voltages.size(); i++) {
            b[i] = voltages.get(i);
        }
        int[][] A = new int[voltages.size()][buttons.size()];
        for (int j = 0; j < buttons.size(); j++) {
            ArrayList<Integer> button = buttons.get(j);
            for (int i : button) {
                A[i][j] = 1;
            }
        }
        int[] x = new int[buttons.size()];
        return new Problem(A, x, b);
    }
}

static class Problem {
    int[][] A;
    int[] x, b;

    Problem(int[][] A, int[] x, int[] b) {
        this.A = A;
        this.x = x;
        this.b = b;
    }
}
