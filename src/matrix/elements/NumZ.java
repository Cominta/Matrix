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
        this.number = number;
        this.handleCountZ();
    }

    public NumZ(int number, int countZ) {
        this(number);
        this.countZ = countZ;
    }

    private void handleCountZ() {
        if (this.countZ != -1) {
            this.number = this.number % this.countZ;
        }
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

    @Override
    public void add(int num) {
        this.number = this.number + num;
        this.handleCountZ();
    }

    @Override
    public void add(Element num) {
        this.number = this.number + ((NumZ)num).number;
        this.handleCountZ();
    }

    @Override
    public void subtract(int num) {
        this.number = this.number - num;
        this.handleCountZ();
    }

    @Override
    public void subtract(Element num) {
        this.number = this.number - ((NumZ)num).number;
        this.handleCountZ();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
