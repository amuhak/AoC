import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day15 {
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        Box[] boxes = new Box[256];
        for (int i = 0; i < 256; i++) {
            boxes[i] = new Box();
        }
        long ans = 0;
        String[] data = r.readLine().split(",");
        for (String s : data) {
            String[] parts = s.split("[-=]");
            if (parts.length > 1) {
                boxes[new AoCString(parts[0]).hashCode()].add(parts[0], Integer.parseInt(parts[1]));
            } else {
                boxes[new AoCString(parts[0]).hashCode()].remove(parts[0]);
            }
        }

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[i].sizes.size(); j++) {
                ans += (long) boxes[i].sizes.get(j) * (i + 1) * (j + 1);
            }
        }
        System.out.println(ans);
    }

    private static class AoCString {
        public String text;

        public AoCString(String text) {
            this.text = text;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            for (int i = 0; i < text.length(); i++) {
                hash += text.charAt(i);
                hash *= 17;
                hash = hash % 256;
            }
            return hash;
        }
    }

    public static class Box {
        public ArrayList<String> contents;
        public ArrayList<Integer> sizes;

        public Box() {
            contents = new ArrayList<>(10);
            sizes = new ArrayList<>(10);
        }

        public void add(String s, int n) {
            if (contents.contains(s)) {
                for (int i = 0; i < contents.size(); i++) {
                    if (contents.get(i).equals(s)) {
                        sizes.set(i, n);
                        break;
                    }
                }
            } else {
                contents.add(s);
                sizes.add(n);
            }
        }

        public void remove(String s) {
            for (int i = 0; i < contents.size(); i++) {
                if (contents.get(i).equals(s)) {
                    contents.remove(i);
                    sizes.remove(i);
                    break;
                }
            }

        }
    }
}
