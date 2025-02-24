package matrix;

import exception.ExceptionHandler;
import exception.ExceptionObj;
import matrix.elements.Element;

public class Matrix {
    Element[][] elements;
    private int sizeX;
    private int sizeY;
    Element.Types mode;

    public Matrix() {

    }

    public Matrix(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.elements = new Element[sizeY][sizeX];
    }

    public Matrix(int sizeX, int sizeY, Element[][] elements, Element.Types mode) {
        this(sizeX, sizeY);
        this.elements = elements;
        this.mode = mode;
    }

    public int getSizeX() { return sizeX; }

    public int getSizeY() { return sizeY; }

    public Element getElement(int x, int y) {
        if (x >= 0 && x < sizeX && y >= 0 && y < sizeY) {
            return this.elements[y][x];
        }

        ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.OUT_OF_RANGE, "getElement: x = " + x + ", y = " + y));
        return null;
    }

    public void setElements(Element[][] elements) { this.elements = elements; }

    public void setElement(Element element, int x, int y) { this.elements[y][x] = element; }

    public void setMode(Element.Types mode) { this.mode = mode; }
}
