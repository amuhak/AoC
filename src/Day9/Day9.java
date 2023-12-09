import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Day9 {
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        String s;
        ArrayList<int[]> list = new ArrayList<>();
        while ((s = r.readLine()) != null) {
            list.add(getIntArr(s));
        }
        long ans = 0;
        for (int[] arr : list) {
            ans += predictNext(arr);
        }
        System.out.println(ans);
    }

    public static int[] getIntArr(String s) {
        return Arrays.stream(s.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    public static long predictNext(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        long diff = 0;
        while (!allAre0(arr)) {
            stack.push(arr[0]);
            arr = diff(arr);
        }
        while (!stack.isEmpty()) {
            diff = stack.pop() - diff;
        }
        return diff;
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
