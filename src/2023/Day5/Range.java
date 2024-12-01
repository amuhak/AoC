public class Range {
    public long start;
    public long end;

    public Range(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public boolean contains(long num) {
        return start <= num && num < end;
    }
}