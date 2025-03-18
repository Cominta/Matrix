package matrix;

import exception.ExceptionHandler;
import exception.ExceptionObj;
import matrix.elements.Element;
import matrix.elements.NumR;
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

    private void multiplyLine(int n,  int k, Element[][] newElements) {
        for (int i = 0; i < this.sizeX; i++) {
            newElements[n][i].multiply(k);

            if (!ExceptionHandler.isEmpty()) {
                return;
            }
        }
    }

    private void divideLine(int n,  int k, Element[][] newElements) {
        for (int i = 0; i < this.sizeX; i++) {
            newElements[n][i].divide(k);

            if (!ExceptionHandler.isEmpty()) {
                return;
            }
        }
    }

    private void sumLine(int n,  int k, int multiplier, Element[][] newElements) throws CloneNotSupportedException {
        for (int i = 0; i < this.sizeX; i++) {
            Element element = (Element)newElements[n][i].clone();
            element.multiply(multiplier);
            newElements[k][i].add(element);

            if (!ExceptionHandler.isEmpty()) {
                return;
            }
        }
    }

    private void subtractLine(int n,  int k, int multiplier, Element[][] newElements) throws CloneNotSupportedException {
        for (int i = 0; i < this.sizeX; i++) {
            Element element = (Element)newElements[k][i].clone();
            element.multiply(multiplier);
            newElements[n][i].subtract(element);

            if (!ExceptionHandler.isEmpty()) {
                return;
            }
        }
    }

    public void opLine(ConcurrentSkipListMap<String, Integer> params) throws CloneNotSupportedException {
        Element[][] newElements = this.elements.clone();
        int op = params.get("op");
        int n = params.get("n");
        int k = params.get("k");
        int multiplier = 0;

        if (n >= this.sizeY || n < 0) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.OUT_OF_RANGE, "Out of range of matrix (n > sizeY || n < 0)"));
            return;
        }

        if (op == 4) {
            if (k >= this.sizeY || k < 0) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.OUT_OF_RANGE, "Out of range of matrix (k > sizeY || k < 0)"));
                return;
            }

            Element[] temp = newElements[n].clone();
            newElements[n] = newElements[k].clone();
            newElements[k] = temp;
        }

        else {
            if (op >= 2) {
                if (k >= this.sizeY || k < 0) {
                    ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.OUT_OF_RANGE, "Out of range of matrix (k > sizeY || k < 0)"));
                    return;
                }

                multiplier = params.get("multiplier");
            }

            switch (op) {
                case 0:
                    this.multiplyLine(n, k, newElements);
                    break;

                case 1:
                    this.divideLine(n, k, newElements);
                    break;

                case 2:
                    this.sumLine(n, k, multiplier, newElements);
                    break;

                case 3:
                    this.subtractLine(n, k, multiplier, newElements);
                    break;
            }
        }

        this.elements = newElements;
    }

    public void opMWM(int op, Matrix matrix) throws CloneNotSupportedException {
        if (this.sizeX != matrix.sizeY) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INVALID_SIZES, "Invalid sizes of matrix"));
            return;
        }

        if (this.mode != matrix.mode) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INVALID_MODES, "Invalid modes"));
            return;
        }

        if (op == 5) {
            this.sumMWM(matrix);
        }

        else if (op == 6) {
            this.multiplyMWM(matrix);
        }
    }

    private void multiplyMWM(Matrix matrix) throws CloneNotSupportedException {
        Element[][] newElements = new Element[this.sizeY][matrix.sizeX];
        Element[][] thisElements = this.elements.clone();

        for (int i = 0; i < this.sizeY; i++) {
            for (int j = 0; j < matrix.sizeX; j++) {
                Element sum;

                if (this.mode == Element.Types.Z) {
                    sum = new NumZ(0, ((NumZ)thisElements[0][0]).getCountZ());
                }

                else {
                    sum = new NumR();
                }

                for (int k = 0; k < this.sizeX; k++) {
                    Element toSum = (Element)thisElements[i][k].clone();
                    toSum.multiply(matrix.getElement(k, j));
                    sum.add(toSum);

                    if (!ExceptionHandler.isEmpty()) {
                        return;
                    }
                }

                newElements[i][j] = sum;
            }
        }

        this.elements = newElements.clone();
    }

    private void sumMWM(Matrix matrix) {
        Element[][] newElements = new Element[this.sizeY][this.sizeX];

        for (int i = 0; i < this.sizeY; i++) {
            for (int j = 0; j < this.sizeX; j++) {
                if (this.mode == Element.Types.Z) {
                    NumZ newElement = new NumZ(((NumZ)this.elements[i][j]).getNumber());
                    newElement.add(matrix.getElement(j, i));
                    newElements[i][j] = newElement;
                }

                else if (this.mode == Element.Types.R) {
                    NumR newElement = new NumR(((NumR)this.elements[i][j]).getNumerator(), ((NumR)this.elements[i][j]).getDenominator());
                    newElement.add(matrix.getElement(j, i));
                    newElements[i][j] = newElement;
                }
            }
        }

        this.elements = newElements;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix matrix)) {
            return false;
        }

        if (matrix.getMode() != this.mode || matrix.sizeX != this.sizeX || matrix.sizeY != this.sizeY) {
            return false;
        }

        for (int i = 0; i < this.sizeY; i++) {
            for (int j = 0; j < this.sizeX; j++) {
                if (this.elements[i][j] != matrix.getElement(j, i)) {
                    return false;
                }
            }
        }

        return true;
    }
}
