import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// This question is BFS on a binary tree
public class Day8 {
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        HashMap<String, String[]> tree = new HashMap<>();
        int[] pattern = r.readLine().replace('R', (char) 1).replace('L', (char) 0).chars().toArray();
        ArrayList<String> nodes = new ArrayList<>();
        long ans = 0;
        r.readLine();
        String line;
        while ((line = r.readLine()) != null) {
            String[] split = line.split(" ");
            tree.put(split[0], new String[]{split[2].substring(1, 4), split[3].substring(0, 3)});
            if (split[0].charAt(2) == 'A') {
                nodes.add(split[0]);
            }
        }
        r.close();
        long[] fistZ = new long[nodes.size()];
        long[] secondZ = new long[nodes.size()];
        while (!non0(fistZ, secondZ)) {
            for (int i = 0; i < nodes.size(); i++) {
                nodes.set(i, tree.get(nodes.get(i))[pattern[(int) (ans % pattern.length)]]);
                if (nodes.get(i).charAt(2) == 'Z' && fistZ[i] == 0) {
                    fistZ[i] = ans + 1;
                } else if (nodes.get(i).charAt(2) == 'Z' && secondZ[i] == 0) {
                    secondZ[i] = ans - fistZ[i] + 1;
                }
            }
            if (ans % 10000000 == 0) {
                System.out.println(ans);
            }
            ans++;
        }
        System.out.println(Arrays.toString(fistZ));
        System.out.println(Arrays.toString(secondZ));
        System.out.println(lcm_of_array_elements(secondZ.clone()));
    }

    public static boolean non0(long[] a, long[] b) {
        for (int i = 0; i < a.length; i++) {
            if (b[i] == 0 || a[i] == 0) {
                return false;
            }
        }
        return true;
    }

    public static BigInteger lcm_of_array_elements(long[] element_array) {
        BigInteger lcm_of_array_elements = new BigInteger("1");
        long divisor = 2;

        while (true) {
            long counter = 0;
            boolean divisible = false;

            for (int i = 0; i < element_array.length; i++) {

                // lcm_of_array_elements (n1, n2, ... 0) = 0.
                // For negative number we convert into
                // positive and calculate lcm_of_array_elements.

                if (element_array[i] == 0) {
                    return new BigInteger("0");
                } else if (element_array[i] < 0) {
                    element_array[i] = element_array[i] * (-1);
                }
                if (element_array[i] == 1) {
                    counter++;
                }

                // Divide element_array by devisor if complete
                // division i.e. without remainder then replace
                // number with quotient; used for find next factor
                if (element_array[i] % divisor == 0) {
                    divisible = true;
                    element_array[i] = element_array[i] / divisor;
                }
            }

            // If divisor able to completely divide any number
            // from array multiply with lcm_of_array_elements
            // and store into lcm_of_array_elements and continue
            // to same divisor for next factor finding.
            // else increment divisor
            if (divisible) {
                lcm_of_array_elements = lcm_of_array_elements.multiply(new BigInteger(String.valueOf(divisor)));
            } else {
                divisor++;
            }

            // Check if all element_array is 1 indicate
            // we found all factors and terminate while loop.
            if (counter == element_array.length) {
                return lcm_of_array_elements;
            }
        }
    }
}
