package matrix.elements;

public abstract class Element {
    public enum Types {
        R,
        Z
    }

    public abstract void multiply(int num);
    public abstract void divide(int num);
    public abstract void add(int num);
    public abstract void add(Element num);
    public abstract void subtract(int num);
    public abstract void subtract(Element num);
}
