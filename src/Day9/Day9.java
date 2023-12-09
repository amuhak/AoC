import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Day9 {
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        r.mark(1000000);
        String s = r.readLine();
        int l = getIntArr(s).length;
        ArrayList<int[]> list = new ArrayList<>();
        r.reset();
        while ((s = r.readLine()) != null) {
            list.add(getIntArr(s));
        }
        long ans = 0;
        for (int[] arr : list){
            ans+=predictNext(arr);
        }
        System.out.println(ans);
    }

    public static int[] getIntArr(String s) {
        return Arrays.stream(s.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    /*
    Create a function that takes an array and creates the sequence:
    1   3   6  10  15  21
      2   3   4   5   6
        1   1   1   1
          0   0   0
     */
    public static long predictNext(int[] arr) {
        long sum = 0;
        while (!allAre0(arr)) {
            sum += arr[arr.length - 1];
            arr = diff(arr);
        }
        return sum;
    }

    public static int[] diff(int[] arr) {
        if (arr.length == 1) {
            return new int[]{0};
        }
        int[] diff = new int[arr.length - 1];
        for (int i = 0; i < arr.length - 1; i++) {
            diff[i] = arr[i + 1] - arr[i];
        }
        return diff;
    }

    public static boolean allAre0(int[] arr) {
        for (int i : arr) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }
}
