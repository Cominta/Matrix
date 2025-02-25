package matrix;

import exception.ExceptionHandler;
import exception.ExceptionObj;
import matrix.elements.Element;
import matrix.elements.NumZ;

import java.util.concurrent.ConcurrentSkipListMap;

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

    public void opLine(ConcurrentSkipListMap<String, Integer> params) throws CloneNotSupportedException {
        Element[][] newElements = this.elements.clone();
        int op = params.get("op");
        int n = params.get("n");
        int k = params.get("k");
        int multiplier = 0;

        if (n >= this.sizeY) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.OUT_OF_RANGE, "Out of range of matrix (n > sizeY)"));
            return;
        }

        if (op >= 0 && op <= 3) {
            if (op >= 2) {
                if (k >= this.sizeY) {
                    ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.OUT_OF_RANGE, "Out of range of matrix (k > sizeY)"));
                    return;
                }

                multiplier = params.get("multiplier");
            }

            for (int i = 0; i < sizeX; i++) {
                Element element = null;

                if (op >= 2) {
                    element = (NumZ)((NumZ)newElements[k][i]).clone();
                }

                switch (op) {
                    case 0:
                        newElements[n][i].multiply(k);
                        break;

                    case 1:
                        newElements[n][i].divide(k);
                        break;

                    case 2:
                        element.multiply(multiplier);
                        newElements[n][i].add(element);
                        break;

                    case 3:
                        element.multiply(multiplier);
                        newElements[n][i].subtract(element);
                        break;
                }

                if (!ExceptionHandler.isEmpty()) {
                    return;
                }
            }
        }

        else if (op == 4) {
            if (k >= this.sizeY) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.OUT_OF_RANGE, "Out of range of matrix (k > sizeY)"));
                return;
            }

            Element[] temp = newElements[n].clone();
            newElements[n] = newElements[k].clone();
            newElements[k] = temp;
        }

        else {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid op"));
            return;
        }

        this.elements = newElements;
    }

    public void op(ConcurrentSkipListMap<String, Integer> params) {

    }

    public int getSizeX() { return sizeX; }

    public int getSizeY() { return sizeY; }

    public Element.Types getMode() { return mode; }

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
