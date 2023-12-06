import java.util.Arrays;

// https://www.desmos.com/calculator/mqfc634wuf
// Light work, solve the quadratic equation and then round up and down to get the bounds
public class Day6 {
    public static void main(String[] args) {
        // Hardcode cause small data set and I'm lazy
        long[] time = {57726992};
        long[] distance = {291117211762026L};
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

    public static double[] quadraticFormula(long a, long b, long c) {
        double sqrt = Math.sqrt(Math.pow(b, 2) - 4 * a * c);
        return new double[]{
                ((-b + sqrt) / (2 * a)),
                ((-b - sqrt) / (2 * a))
        };
    }
}
