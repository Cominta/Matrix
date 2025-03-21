package matrix.elements;

import exception.ExceptionHandler;
import exception.ExceptionObj;
import matrix.Matrix;

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
        this.fit();
    }


    public void fit() {
        if (denominator == 0) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.DIVIDE_BY_ZERO, "Divide by zero"));
            return;
        }

        if(denominator < 0) {
            denominator *= -1;
            numerator *= -1;
        }
        if(numerator > denominator && numerator % denominator == 0) {
            numerator /= denominator;
            denominator = 1;
        }

        int gcd = Math.min(numerator, denominator);
        for(; gcd > 1; gcd--) {
            if(numerator % gcd == 0 && denominator % gcd == 0) {
                numerator /= gcd;
                denominator /= gcd;
                break;
            }
        }
    }

    @Override
    public String toString() {
        if(denominator == 1) {
            return Integer.toString(numerator);
        }
        return numerator + "/" + denominator;
    }

    @Override
    public void multiply(int num) {
        multiply(num, 1);
    }

    @Override
    public void multiply(Element num) {
        NumR numR = (NumR) num;
        multiply(numR.numerator, numR.denominator);
    }

    public void multiply(int numerator, int denominator) {
        this.numerator *= numerator;
        this.denominator *= denominator;
        fit();
    }

    @Override
    public void divide(int num) {
        divide(num, 1);
    }

    @Override
    public void divide(Element num) {
        NumR numR = (NumR) num;
        divide(numR.numerator, numR.denominator);
    }

    public void divide(int numerator, int denominator){
        this.numerator *= denominator;
        this.denominator *= numerator;
        fit();
    }

    @Override
    public void add(int num) {
        add(num, 1);
    }

    @Override
    public void add(Element num) {
        NumR numR = (NumR) num;
        add(numR.numerator, numR.denominator);
    }

    public void add(int numerator, int denominator){
        this.numerator *= denominator;
        this.numerator += numerator * this.denominator;
        this.denominator *= denominator;
        fit();
    }

    @Override
    public void subtract(int num) {
        subtract(num, 1);
    }

    @Override
    public void subtract(Element num) {
        NumR numR = (NumR) num;
        subtract(numR.numerator, numR.denominator);
    }

    public void subtract(int numerator, int denominator){
        this.numerator *= denominator;
        this.numerator -= numerator * this.denominator;
        this.denominator *= denominator;
        fit();
    }

    public int getNumerator() { return this.numerator; }

    public int getDenominator() { return this.denominator; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NumR num)) {
            return false;
        }

        NumR numR = (NumR)obj;

        if (this.numerator != numR.numerator || this.denominator != numR.denominator) {
            return false;
        }

        return true;
    }
}