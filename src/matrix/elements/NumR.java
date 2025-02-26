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

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
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
        numerator *= num;
        fit();
    }

    @Override
    public void multiply(Element num) {
        this.numerator *= num.getNumerator();
        this.denominator *= num.getDenominator();
        fit();
    }

    @Override
    public void divide(int num) {
        denominator *= num;
        fit();
    }

    @Override
    public void divide(Element num) {
        this.numerator *= num.getDenominator();
        this.denominator *= num.getNumerator();
        fit();
    }

    @Override
    public void add(int num) {
        numerator += num*denominator;
        fit();
    }

    @Override
    public void add(Element num) {
        this.numerator *= num.getDenominator();
        this.numerator += num.getNumerator() * denominator;
        denominator *= num.getDenominator();
        fit();
    }

    @Override
    public void subtract(int num) {
        numerator -= num*denominator;
        fit();
    }

    @Override
    public void subtract(Element num) {
        this.numerator *= num.getDenominator();
        this.numerator -= num.getNumerator() * denominator;
        denominator *= num.getDenominator();
        fit();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}