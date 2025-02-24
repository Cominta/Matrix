package matrix.elements;

public class NumZ extends Element {
    private int countZ;
    private int number;

    public NumZ() {
        this.countZ = 2;
        this.number = 0;
    }

    public NumZ(int number) {
        this.number = number;
    }

    public NumZ(int number, int countZ) {
        this(number);
        this.countZ = countZ;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
