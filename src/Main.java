import console.CInput;
import console.COutput;
import console.inputResult.InputResult;
import console.inputResult.InputResultMatrixOp;
import console.inputResult.InputResultNewMatrix;
import console.inputResult.InputResultOp;
import exception.ExceptionHandler;
import exception.ExceptionObj;
import matrix.Matrix;
import matrix.elements.Element;
import matrix.elements.NumZ;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class Main {
    public static void main(String[] args) throws Exception {
        InputResult result = new InputResult(true, InputResult.Types.NONE);
        List<Matrix> matrixList = new ArrayList<Matrix>();
        Matrix currentMatrix = null;

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
                Matrix matrix = ((InputResultNewMatrix)result).getMatrix();
                COutput.printMatrix(matrix, "New matrix: ");
                matrixList.add(matrix);
                currentMatrix = matrix;
            }

            else if (result.getType() == InputResult.Types.MATRIX_OP) {
                if (currentMatrix == null) {
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
                currentMatrix.opLine(params);
                COutput.printMatrix(currentMatrix, "Matrix after op: ");
            }

            else if (result.getType() == InputResult.Types.SET_OP) {
                ConcurrentSkipListMap<String, String> params = ((InputResultOp)result).getParams();

                if (params.get("op").equals("currM")) {
                    int n = 0;

                    try {
                        n = Integer.parseInt(params.get("n"));

                        if (n >= matrixList.size()) {
                            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.OUT_OF_RANGE, "n > matrix list size: " + matrixList.size()));
                            continue;
                        }
                    }

                    catch (Exception e) {
                        ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "n is not an integer"));
                        continue;
                    }

                    currentMatrix = matrixList.get(n);
                }
            }

            else if (result.getType() == InputResult.Types.PRINT_OP) {
                ConcurrentSkipListMap<String, String> params = ((InputResultOp)result).getParams();

                if (params.get("op").equals("mlist")) {
                    for (int i = 0; i < matrixList.size(); i++) {
                        String s = "Matrix[" + i + "], mode = (" + matrixList.get(i).getMode().toString();

                        if (matrixList.get(i).getMode() == Element.Types.Z) {
                            int countZ =  ((NumZ)matrixList.get(i).getElement(0, 0)).getCountZ();
                            s += countZ;
                        }

                        s += "): ";

                        COutput.printMatrix(matrixList.get(i), s);
                    }
                }

                else if (params.get("op").equals("currM")) {
                    if (currentMatrix == null) {
                        ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.NULL_MATRIX, "Null matrix"));
                        continue;
                    }

                    String s = "Current matrix, mode = (" + currentMatrix.getMode().toString();

                    if (currentMatrix.getMode() == Element.Types.Z) {
                        int countZ =  ((NumZ)currentMatrix.getElement(0, 0)).getCountZ();
                        s += countZ;
                    }

                    s += "): ";

                    COutput.printMatrix(currentMatrix, s);
                }
            }
        }
    }
}