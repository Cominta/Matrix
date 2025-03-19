package console;

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
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class InputHandler {
    public static void handleResult(InputResult result, ArrayList<Matrix> matrixList, AtomicInteger currentIndex, AtomicReference<Matrix> currentMatrix) throws CloneNotSupportedException {
        if (result.getType() == InputResult.Types.NEW_MATRIX) {
            Matrix matrix = ((InputResultNewMatrix)result).getMatrix();
            COutput.printMatrix(matrix, "New matrix: ");
            matrixList.add(matrix);
            currentMatrix.set(matrix);
            currentIndex.set(matrixList.size() - 1);
        }

        else if (result.getType() == InputResult.Types.OUTPUT_OP) {
            int index = -1;
            ConcurrentSkipListMap<String, String> params = ((InputResultOp)result).getParams();

            try {
                index = Integer.parseInt(params.get("index"));
            }

            catch (Exception e) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Index is not a number"));
                return;
            }

            if (index < 0 || index >= matrixList.size()) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.OUT_OF_RANGE, "Index is out of range"));
                return;
            }

            COutput.saveToFile(params.get("path"), matrixList.get(index));

            if (ExceptionHandler.isEmpty()) {
                COutput.printMatrix(matrixList.get(index), "Succeed save to file");
            }
        }

        else if (result.getType() == InputResult.Types.MATRIX_OP) {
            if (currentMatrix == null) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.NULL_MATRIX, "Null matrix"));
                return;
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
            currentMatrix.get().opLine(params);
            COutput.printMatrix(currentMatrix.get(), "Matrix after op: ");
        }

        else if (result.getType() == InputResult.Types.MWM_OP) {
            ConcurrentSkipListMap<String, Integer> params = ((InputResultMatrixOp)result).getParams();
            int m1;
            int m2;

            if (params.get("m1") > matrixList.size() || params.get("m2") > matrixList.size() || params.get("m1") < -2 || params.get("m2") < -2) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Out of range"));
                return;
            }

            // m1
            if (params.get("m1") == -1) {
                if (currentMatrix == null) {
                    ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.NULL_MATRIX, "Null matrix"));
                    return;
                }

                m1 = currentIndex.get();
            }

            else if (params.get("m1") == -2) {
                InputResultNewMatrix newM = CInput.readNewMatrix();
                matrixList.add(newM.getMatrix());
                m1 = matrixList.size() - 1;
                params.remove("m1");
                params.put("m1", matrixList.size() - 1);
            }

            else {
                m1 = params.get("m1");
            }

            // m2
            if (params.get("m2") == -1) {
                if (currentMatrix == null) {
                    ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.NULL_MATRIX, "Null matrix"));
                    return;
                }

                m2 = currentIndex.get();
            }

            else if (params.get("m2") == -2) {
                InputResultNewMatrix newM = CInput.readNewMatrix();
                matrixList.add(newM.getMatrix());
                m2 = matrixList.size() - 1;
                params.remove("m2");
                params.put("m2", matrixList.size() - 1);
            }

            else {
                m2 = params.get("m2");
            }

            matrixList.get(m1).opMWM(params.get("op"), matrixList.get(m2));
            COutput.printMatrix(matrixList.get(m1), "Matrix after op: ");
        }

        else if (result.getType() == InputResult.Types.SET_OP) {
            ConcurrentSkipListMap<String, String> params = ((InputResultOp)result).getParams();

            if (params.get("op").equals("currM")) {
                int n = 0;

                try {
                    n = Integer.parseInt(params.get("n"));

                    if (n >= matrixList.size() || n < 0) {
                        ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.OUT_OF_RANGE, "n > matrix list size: " + matrixList.size()));
                        return;
                    }
                }

                catch (Exception e) {
                    ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "n is not an integer"));
                    return;
                }

                currentMatrix.set(matrixList.get(n));
                currentIndex.set(n);
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
                    return;
                }

                String s = "Current matrix, mode = (" + currentMatrix.get().getMode().toString();

                if (currentMatrix.get().getMode() == Element.Types.Z) {
                    int countZ =  ((NumZ)currentMatrix.get().getElement(0, 0)).getCountZ();
                    s += countZ;
                }

                s += "): ";

                COutput.printMatrix(currentMatrix.get(), s);
            }
        }
    }
}
