package console.inputResult;

import matrix.Matrix;

public class InputResultNewMatrix extends InputResult {
    private Matrix matrix;

    public InputResultNewMatrix(boolean cont, Matrix matrix) {
        super(cont);
        this.matrix = matrix;
        this.type = InputResult.Types.NEW_MATRIX;
    }

    public Matrix getMatrix() { return matrix; }
}
