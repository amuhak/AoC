// Uses io.github.tudo-aqua:z3-turnkey:RELEASE

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.IntSort;
import com.microsoft.z3.Model;
import com.microsoft.z3.Optimize;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

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

    long finalAns = input.parallelStream()
            .map(Machine::toProblem)
            .map(Problem::solveWithZ3)
            .mapToLong(solution -> Arrays.stream(solution)
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
        return new Problem(A, b);
    }
}

static class Problem {
    int[][] A;
    int[] b;

    Problem(int[][] A, int[] b) {
        this.A = A;
        this.b = b;
    }

    /**
     * Code by Gemini
     * Solves the integer linear programming problem using Z3 SMT solver.
     * Simply solves Ax = b with x >= 0 and minimizes the sum of x.
     * A is the matrix that holds all the buttons.
     * b is the vector that holds all the voltages (where we want to get to).
     * x is the vector that holds the number of presses for each button to reach the voltages in b.
     * x is unknown and to be solved for.
     *
     * @return An array of integers representing the solution, or an empty array if no solution exists.
     */
    int[] solveWithZ3() {
        Problem problem = this;
        int[][] A = problem.A;
        int[] b = problem.b;
        int numVars = A[0].length;
        int numEqs = A.length;

        try (Context ctx = new Context()) {
            Optimize opt = ctx.mkOptimize();

            IntExpr[] x = new IntExpr[numVars];
            for (int i = 0; i < numVars; i++) {
                x[i] = ctx.mkIntConst("x" + i);
                opt.Add(ctx.mkGe(x[i], ctx.mkInt(0)));
            }

            // Constraints
            for (int row = 0; row < numEqs; row++) {
                ArithExpr<IntSort> rowSum = ctx.mkInt(0);
                for (int col = 0; col < numVars; col++) {
                    if (A[row][col] != 0) {
                        ArithExpr<IntSort> term = ctx.mkMul(ctx.mkInt(A[row][col]), x[col]);
                        rowSum = ctx.mkAdd(rowSum, term);
                    }
                }
                opt.Add(ctx.mkEq(rowSum, ctx.mkInt(b[row])));
            }

            // Objective
            ArithExpr<IntSort> totalCost = ctx.mkInt(0);
            for (IntExpr var : x) {
                totalCost = ctx.mkAdd(totalCost, var);
            }

            opt.MkMinimize(totalCost);

            Status status = opt.Check();

            if (status == Status.SATISFIABLE) {
                Model model = opt.getModel();
                int[] result = new int[numVars];

                for (int i = 0; i < numVars; i++) {
                    Expr<IntSort> val = model.eval(x[i], true);
                    if (val.isIntNum()) {
                        result[i] = Integer.parseInt(val.toString());
                    }
                }
                return result;
            } else {
                return new int[0];
            }
        } catch (Z3Exception e) {
            e.printStackTrace();
            return new int[0];
        }
    }
}
