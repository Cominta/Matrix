package matrix.elements;

import exception.ExceptionHandler;
import exception.ExceptionObj;

public class NumZ extends Element {
    private int countZ;
    private int number;

    public NumZ() {
        this.countZ = -1;
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

    @Override
    public void multiply(int num) {
        this.number = this.number * num;
    }

    @Override
    public void divide(int num) {
        if (num == 0) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.DIVIDE_BY_ZERO, "Divide by zero"));
            return;
        }

        this.number = this.number / num;
    }
}
