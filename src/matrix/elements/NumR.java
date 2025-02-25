package matrix.elements;

import exception.ExceptionHandler;
import exception.ExceptionObj;

public class NumR extends Element {
    private int numerator;
    private int denominator;

    public NumR() {
        this.numerator = 0;
        this.denominator = 1;
    }

    public NumR(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void multiply(int num) {

    }

    @Override
    public void divide(int num) {

    }

    @Override
    public void add(int num) {

    }

    @Override
    public void add(Element num) {

    }

    @Override
    public void subtract(int num) {
        ;
    }

    @Override
    public void subtract(Element num) {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
