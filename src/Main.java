import console.CInput;
import console.inputResult.InputResult;
import console.inputResult.InputResultNewMatrix;
import exception.ExceptionHandler;
import matrix.Matrix;

public class Main {
    public static void main(String[] args) {
        InputResult result = new InputResult(true, InputResult.Types.NONE);
        Matrix matrix;

        while (result.cont) {
            result = CInput.readCommand();

            if (!ExceptionHandler.isEmpty()) {
                ExceptionHandler.printExceptions();
                ExceptionHandler.clear();
            }

            else if (result.type == InputResult.Types.NEW_MATRIX) {
                matrix = ((InputResultNewMatrix)result).getMatrix();
            }
        }
    }
}