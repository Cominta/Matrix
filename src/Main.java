import console.CInput;
import console.COutput;
import console.inputResult.InputResult;
import console.inputResult.InputResultMatrixOp;
import console.inputResult.InputResultNewMatrix;
import exception.ExceptionHandler;
import exception.ExceptionObj;
import matrix.Matrix;

import java.util.concurrent.ConcurrentSkipListMap;

public class Main {
    public static void main(String[] args) {
        InputResult result = new InputResult(true, InputResult.Types.NONE);
        Matrix matrix = null;

        while (result.isCont()) {
            if (!ExceptionHandler.isEmpty()) {
                ExceptionHandler.printExceptions();
                ExceptionHandler.clear();
            }

            result = CInput.readCommand();

            if (!ExceptionHandler.isEmpty()) {
                continue;
            }

            else if (result.getType() == InputResult.Types.NEW_MATRIX) {
                matrix = ((InputResultNewMatrix)result).getMatrix();
                COutput.printMatrix(matrix, "New matrix: ");
            }

            else if (result.getType() == InputResult.Types.MATRIX_OP) {
                if (matrix == null) {
                    ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.NULL_MATRIX, "Null matrix"));
                    continue;
                }

                ConcurrentSkipListMap<String, Integer> params = ((InputResultMatrixOp)result).getParams();
                String s = "";

                for (String key : params.keySet()) {
                    if (key.equals("op")) {
                        continue;
                    }

                    s += key + ": " + params.get(key) + " | ";
                }

                COutput.printMessage("Op: " + params.get("op") + ", params = " + s + "\n");
                matrix.op(params);
                COutput.printMatrix(matrix, "Matrix after op: ");
            }
        }
    }
}