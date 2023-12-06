import java.util.Arrays;

// https://www.desmos.com/calculator/mqfc634wuf
public class Day6 {
    public static void main(String[] args) {
        // Hardcode cause small data set and I'm lazy
        int[] time = {57, 72, 69, 92};
        int[] distance = {291, 1172, 1176, 2026};
        long answer = 1;
        for (int i = 0; i < time.length; i++) {
            double[] ans = quadraticFormula(1, -time[i], distance[i]);
            // sort so that the first value is the lower bound and the second value is the upper bound
            Arrays.sort(ans);
            ans[0] = Math.ceil(ans[0]) == ans[0] ? ans[0] + 1 : ans[0];
            ans[1] = Math.floor(ans[1]) == ans[1] ? ans[1] - 1 : ans[1];
            answer *= (long) (Math.floor(ans[1]) - Math.ceil(ans[0]) + 1);
        }
        System.out.println(answer);
    }

    public static double[] quadraticFormula(int a, int b, int c) {
        double sqrt = Math.sqrt(Math.pow(b, 2) - 4 * a * c);
        return new double[]{
                ((-b + sqrt) / (2 * a)),
                ((-b - sqrt) / (2 * a))
        };
    }
}
