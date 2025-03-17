package console.inputResult;

import matrix.Matrix;

public class InputResultNewMatrix extends InputResult {
    private Matrix matrix;

    public InputResultNewMatrix(boolean cont, Matrix matrix) {
        super(cont);
        this.matrix = matrix;
        this.type = Types.NONE;
    }
    public InputResultNewMatrix(boolean cont, Matrix matrix, Types type) {
        super(cont);
        this.matrix = matrix;
        this.type = type;
    }


    public Matrix getMatrix() { return matrix; }
}
