package matrix.elements;

public abstract class Element implements Cloneable {
    public enum Types {
        R,
        Z
    }

    public abstract void multiply(int num);
    public abstract void multiply(Element num);
    public abstract void divide(int num);
    public abstract void divide(Element num);
    public abstract void add(int num);
    public abstract void add(Element num);
    public abstract void subtract(int num);
    public abstract void subtract(Element num);
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
