package console;

import console.inputResult.InputResult;
import console.inputResult.InputResultMatrixOp;
import console.inputResult.InputResultNewMatrix;
import exception.ExceptionHandler;
import exception.ExceptionObj;
import matrix.Matrix;
import matrix.elements.Element;
import matrix.elements.NumR;
import matrix.elements.NumZ;

import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;

public class CInput {
    private static Matrix readNewMatrix() {
        Scanner sc = new Scanner(System.in);

        COutput.printMessage("Enter size: ");
        String sizeLine = sc.nextLine();
        String[] sizes = sizeLine.split(" ");

        if (sizes.length != 2) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid input size"));
            return null;
        }

        int sizeX;
        int sizeY;

        try {
            sizeX = Integer.parseInt(sizes[1]);
            sizeY = Integer.parseInt(sizes[0]);
        }

        catch (Exception e) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Size is not a number"));
            return null;
        }

        COutput.printMessage("Enter mode: ");
        String mode = sc.nextLine();
        Element.Types type;

        if (mode.equals("R")) {
            type = Element.Types.R;
        }

        else if (mode.equals("Z")) {
            type = Element.Types.Z;
        }

        else {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid mode"));
            return null;
        }

        Matrix matrix = new Matrix(sizeX, sizeY);
        matrix.setMode(type);

        for (int i = 0; i < matrix.getSizeY(); i++) {
            COutput.printMessage("Enter line(" + i + "): ");
            String line = sc.nextLine();
            String[] tokens = line.split(" ");

            if (tokens.length != matrix.getSizeX()) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid matrix size"));
                return null;
            }

            for (int j = 0; j < matrix.getSizeX(); j++) {
                Element element;

                if (type == Element.Types.R) {
                    element = new NumR();
                }

                else {
                    element = new NumZ(Integer.parseInt(tokens[j]));
                }

                matrix.setElement(element, j, i);
            }
        }

        return matrix;
    }

    public static InputResultMatrixOp readOp(String[] op) {
        ConcurrentSkipListMap<String, Integer> params = new ConcurrentSkipListMap<>();
        int countParams;

        switch (op[0]) {
            case "*":
                countParams = 2;
                params.put("op", 0);
                break;

            case "/":
                countParams = 2;
                params.put("op", 1);
                break;

            default:
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid op"));
                return null;
        }

        int n; // row
        int k; // coef

        if (op.length - 1 != countParams) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid number of parameters"));
        }

        try {
            n = Integer.parseInt(op[1]);
            k = Integer.parseInt(op[2]);
        }

        catch (Exception e) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Params are not a number"));
            return null;
        }

        params.put("row", n);
        params.put("coef", k);

        return new InputResultMatrixOp(true, params);
    }

    public static InputResult readCommand() {
        Scanner sc = new Scanner(System.in);

        COutput.printMessage("Enter command: ");
        String cmd = sc.nextLine();
        String[] cmdSplit = cmd.split(" ");
        cmd = cmd.replaceAll(" ", "");

        if (cmd.equals("new")) {
            Matrix result = CInput.readNewMatrix();

            if (!ExceptionHandler.isEmpty()) {
                return new InputResultNewMatrix(true, null);
            }

            return new InputResultNewMatrix(true, result);
        }

        else if (cmdSplit[0].equals("*") || cmdSplit[0].equals("/") || cmdSplit[0].equals("-") || cmdSplit[0].equals("+")) {
            for (int i = 0; i < cmdSplit.length; i++) {
                cmdSplit[i] = cmdSplit[i].replaceAll(" ", "");
            }

            InputResultMatrixOp result = CInput.readOp(cmdSplit);

            if (!ExceptionHandler.isEmpty()) {
                return new InputResultMatrixOp(true, null);
            }

            return result;
        }

        else if (cmd.equals("exit")) {
            return new InputResult(false, InputResult.Types.INPUT_OP);
        }

        else {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid command"));
            ExceptionHandler.printExceptions();
            ExceptionHandler.clear();

            return new InputResult(true, InputResult.Types.INPUT_OP);
        }
    }
}
