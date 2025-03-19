package matrix.elements;

import exception.ExceptionHandler;
import exception.ExceptionObj;

public class NumZ extends Element implements Cloneable {
    private int countZ;
    private int number;

    public NumZ() {
        this.countZ = -1;
        this.number = 0;
    }

    public NumZ(int number) {
        this.countZ = -1;
        this.number = number;
    }

    public NumZ(int number, int countZ) {
        this(number);
        this.countZ = countZ;
        this.handleCountZ();
    }

    private void handleCountZ() {
        if (this.countZ != -1) {
            this.number = this.number % this.countZ;
        }
    }

    public static boolean isPrime(int number) {
        for(int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }

    @Override
    public void multiply(int num) {
        this.number = this.number * num;
        this.handleCountZ();
    }

    @Override
    public void multiply(Element num) {
        this.multiply(((NumZ)num).number);
    }

    @Override
    public void divide(int num) {
        if (num == 0) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.DIVIDE_BY_ZERO, "Divide by zero"));
            return;
        }

        this.number = this.number / num;
        this.handleCountZ();
    }

    @Override
    public void divide(Element num) {
        this.divide(((NumZ)num).number);
    }

    @Override
    public void add(int num) {
        this.number = this.number + num;
        this.handleCountZ();
    }

    @Override
    public void add(Element num) {
        this.add(((NumZ)num).number);
    }

    @Override
    public void subtract(int num) {
        this.number = this.number - num;
        this.handleCountZ();
    }

    @Override
    public void subtract(Element num) {
        this.subtract(((NumZ)num).number);
    }

    public int getCountZ() { return this.countZ; }

    public int getNumber() { return this.number; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NumZ num)) {
            return false;
        }

        NumZ numZ = (NumZ)obj;

        if (this.number != numZ.number) {
            return false;
        }

        return true;
    }
}
