package matrix.elements;

public abstract class Element {
    public enum Types {
        R,
        Z,
        O,
        V
    }
    public abstract int getNumerator();
    public abstract void setNumerator(int numerator);
    public abstract int getDenominator();
    public abstract void setDenominator(int denominator);
    public abstract void multiply(int num);
    public abstract void multiply(Element num);
    public abstract void divide(int num);
    public abstract void divide(Element num);
    public abstract void add(int num);
    public abstract void add(Element num);
    public abstract void subtract(int num);
    public abstract void subtract(Element num);
}
