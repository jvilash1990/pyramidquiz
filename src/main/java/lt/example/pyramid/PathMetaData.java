package lt.example.pyramid;

public class PathMetaData implements Comparable<PathMetaData> {

    private long sum;
    private int lastPositionIndex;
    private Parity parity;

    /**
     * Used for initial path calculation.
     *
     * @param initialNumber - nuumber of pyramid's top.
     */
    public PathMetaData(int initialNumber) {
        sum = initialNumber;
        lastPositionIndex = 0;
        parity = Parity.getParity(initialNumber);
    }

    public PathMetaData(long sum, int lastPositionIndex, Parity parity) {
        this.sum = sum;
        this.lastPositionIndex = lastPositionIndex;
        this.parity = parity;
    }

    /**
     * Used for path split during new line decision.
     *
     * @return Cloned metadata.
     */
    public PathMetaData clone() {
        return new PathMetaData(sum, lastPositionIndex, parity);
    }

    public int getLastPositionIndex() {
        return lastPositionIndex;
    }

    public long getSum() {
        return sum;
    }

    public Parity getParity() {
        return parity;
    }

    @Override
    public int compareTo(PathMetaData m2) {
        long difference = (m2.getSum() - this.getSum());
        return difference == 0 ? 0
                : (difference < 0 ? -1 : 1);
    }
}
