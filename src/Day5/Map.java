public class Map {
    public Range destination;
    public Range source;

    public Map(Range destination, Range source) {
        this.destination = destination;
        this.source = source;
    }

    public long contains(long num) {
        if (source.contains(num)) {
            return destination.start + (num - source.start);
        }
        return -1;
    }
}