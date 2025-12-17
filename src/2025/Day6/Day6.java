void main() throws FileNotFoundException {
    var start = System.nanoTime();
    var lines = new BufferedReader(new FileReader("input.txt")).lines()
            .filter(line -> !line.isEmpty())
            .toList();
    int[][] grid = new int[lines.size() - 1][];
    for (int i = 0; i < lines.size() - 1; i++) {
        var line = lines.get(i);
        grid[i] = Arrays.stream(line.split(" "))
                .filter(s -> !s.isBlank())
                .mapToInt(Integer::parseInt)
                .toArray();
    }
    String[] parts = lines.getLast()
            .trim()
            .split("\\s+");
    boolean[] opps = new boolean[parts.length];
    for (int i = 0; i < parts.length; i++) {
        opps[i] = parts[i].equals("+");
    }
    ArrayList<Long> ans = new ArrayList<>(grid[0].length);
    for (int i = 0; i < grid[0].length; i++) {
        long sum = opps[i] ? 0 : 1;
        for (int[] ints : grid) {
            if (opps[i]) {
                sum += ints[i];
            } else {
                sum *= ints[i];
            }
        }
        ans.add(sum);
    }

    System.out.println(ans.stream().mapToLong(Long::longValue).sum());

    var end = System.nanoTime();
    System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
}
