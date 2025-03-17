package console;

import console.inputResult.InputResult;
import console.inputResult.InputResultMatrixOp;
import console.inputResult.InputResultNewMatrix;
import console.inputResult.InputResultOp;
import exception.ExceptionHandler;
import exception.ExceptionObj;
import matrix.Matrix;
import matrix.elements.Element;
import matrix.elements.NumR;
import matrix.elements.NumZ;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;

public class CInput {
    public static InputResultNewMatrix readNewMatrixFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            System.out.println("Reading new matrix from file: " + filePath);
            // # MATRIX
            String check = br.readLine();
            if (check == null) throw new IOException("File is empty");
            if (!check.equals("# MATRIX")){ throw new Exception("File in wrong format");}

                String sizeLine = br.readLine();
                String[] sizes = sizeLine.split(" ");

                if (sizes.length != 2) throw new IllegalArgumentException("Invalid input size");

                int sizeX = Integer.parseInt(sizes[1]);
                int sizeY = Integer.parseInt(sizes[0]);
                if (sizeX <= 0 || sizeY <= 0) throw new IllegalArgumentException("Invalid input size");

                String mode = br.readLine();
                if (mode == null || mode.isEmpty()) throw new IllegalArgumentException("Invalid mode");

                Element.Types type;
                int krat = -1;

                if (mode.equals("R")) {
                    type = Element.Types.R;
                } else if (mode.charAt(0) == 'Z' || mode.charAt(0) == 'z') {
                    type = Element.Types.Z;
                    if (mode.length() > 1) {
                        krat = Integer.parseInt(Character.toString(mode.charAt(1)));
                        if (!NumZ.isPrime(krat)) {
                            throw new IllegalArgumentException("Mode is not a prime number");
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Invalid mode");
                }

                Matrix matrix = new Matrix(sizeX, sizeY);
                matrix.setMode(type);

                for (int i = 0; i < sizeY; i++) {
                    String line = br.readLine();
                    if (line == null) throw new IOException("Not enough rows in the file");
                    String[] tokens = line.split(" ");
                    if (tokens.length != sizeX) throw new IllegalArgumentException("Invalid matrix size");

                    for (int j = 0; j < sizeX; j++) {
                        Element element = (type == Element.Types.R) ? new NumR() : new NumZ(Integer.parseInt(tokens[j]), krat);
                        matrix.setElement(element, j, i);
                    }
                }
                return new InputResultNewMatrix(true, matrix);

        } catch (Exception e) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, e.getMessage()));
            return new InputResultNewMatrix(true, null);
        }
    }

    public static InputResultNewMatrix readNewMatrix() {
        COutput.printMessage("Reading new matrix\n");
        Scanner sc = new Scanner(System.in);

        COutput.printMessage("Enter size(r/c): ");
        String sizeLine = sc.nextLine();
        String[] sizes = sizeLine.split(" ");

        if (sizes.length != 2) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid input size"));
            return new InputResultNewMatrix(true, null);
        }

        int sizeX;
        int sizeY;

        try {
            sizeX = Integer.parseInt(sizes[1]);
            sizeY = Integer.parseInt(sizes[0]);

            if (sizeX <= 0 || sizeY <= 0) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid input size"));
                return new InputResultNewMatrix(true, null);
            }
        }

        catch (Exception e) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Size is not a number"));
            return new InputResultNewMatrix(true, null);
        }

        COutput.printMessage("Enter mode(Z/R): ");
        String mode = sc.nextLine();
        Element.Types type;
        int krat = -1;

        if (mode.equals("R")) {
            type = Element.Types.R;
        }

        else if (!mode.isEmpty() && (mode.charAt(0) == 'Z' || mode.charAt(0) == 'z')) {
            type = Element.Types.Z;

            if (mode.length() > 1) {

                try {
                    krat = Integer.parseInt(Character.toString(mode.charAt(1)));
                    if(!NumZ.isPrime(krat)) {
                        ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Mode is not a prime number"));
                        return new InputResultNewMatrix(true, null);
                    }
                }

                catch (Exception e) {
                    ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Mode is not a number"));
                    return new InputResultNewMatrix(true, null);
                }
            }
        }

        else {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid mode"));
            return new InputResultNewMatrix(true, null);
        }

        Matrix matrix = new Matrix(sizeX, sizeY);
        matrix.setMode(type);

        COutput.printMessage("Enter matrix:\n");

        for (int i = 0; i < matrix.getSizeY(); i++) {
            String line = sc.nextLine();
            String[] tokens = line.split(" ");

            if (tokens.length != matrix.getSizeX()) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid matrix size"));
                return new InputResultNewMatrix(true, null);
            }

            for (int j = 0; j < matrix.getSizeX(); j++) {
                Element element;

                if (type == Element.Types.R) {
                    int numerator;
                    int denomerator = 1;
                    String[] number = tokens[j].split("/");

                    try {
                        numerator = Integer.parseInt(number[0]);

                        if (number.length != 1) {
                            denomerator = Integer.parseInt(number[1]);
                        }
                    }

                    catch (Exception e) {
                        ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Wrong number"));
                        return new InputResultNewMatrix(true, null);
                    }

                    element = new NumR(numerator, denomerator);
                }

                else {
                    element = new NumZ(Integer.parseInt(tokens[j]), krat);
                }

                matrix.setElement(element, j, i);
            }
        }

        return new InputResultNewMatrix(true, matrix);
    }

    private static InputResultMatrixOp readOp(String[] op) {
        ConcurrentSkipListMap<String, Integer> params = new ConcurrentSkipListMap<>();
        int countParams; //p1-first R p2-second R p3 - a(constanta)

        switch (op[0]) {
            case "*":
                countParams = 2;                    // 1 2 3             1a 2a 3a
                params.put("op", 0);                // 2 4 6  R1 * a  == 2  4  6
                break;                              // 3 6 5             3  6  5

            case "/":
                countParams = 2;                    // 1 2 3             1/a 2/a 3/a
                params.put("op", 1);                // 2 4 6  R1 / a  ==  2   4   6
                break;                              // 3 6 5              3   6   5

            case "+":
                countParams = 3;                    // 1 2 3             3 6 9
                params.put("op", 2);                // 2 4 6  R1 + R2 == 2 4 6
                break;                              // 3 6 5             3 6 5

            case "-":
                countParams = 3;                    // 1 2 3             -1 -2 -3
                params.put("op", 3);                // 2 4 6  R1 - R2 == 2   4  6
                break;                              // 3 6 5             3   6  5

            case "swap":
                countParams = 2;                    // 1 2 3             2 4 6
                params.put("op", 4);                // 2 4 6  R1 swap R2 == 1 2 3
                break;                              // 3 6 5             3 6 5

            case "+s":                              // 1 2 3             3 6 9                      9 6 9
                countParams = 3;                    // 2 4 6  R1 + R2 == 2 4 6          S1 + S2 ==  6 4 6
                params.put("op", 5);                // 3 6 5             3 6 5                      9 6 5
                break;

            case "*s":                              // 1 2 3             1a  2a  3a               1aa 2a 3a
                countParams = 2;                    // 2 4 6  R1 * a  == 2   4   6      S1 * a ==  2a 4  6
                params.put("op", 6);                // 3 6 5             3   6   5                 3a 6  5
                break;

            case "-s":                              // 1 2 3             -3 -2 -3                  -1 -2 -3
                countParams = 3;                    // 2 4 6  R1 - R2 ==  2  4  6       S1 - S2 ==  -2  4  6
                params.put("op", 7);                // 3 6 5              3  6  5                  -3  6  5
                break;

            case "/s":                              // 1 2 3             1/a 2/a 3/a               1/aa 2/a 3/a
                countParams = 3;                    // 2 4 6  R1 / a ==  2    4   6     S1 / a ==  2/a   4   6
                params.put("op", 8);                // 3 6 5             3    6   5                3/a   6   5
                break;

            default:
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid op"));
                return new InputResultMatrixOp(true, null, InputResult.Types.NONE);
        }

        int n; // row
        int k; // coef
        int multiplier = 0; // multiplier (for + and -)

        if (op.length - 1 != countParams) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid number of parameters"));
        }

        try {
            n = Integer.parseInt(op[1]);
            k = Integer.parseInt(op[2]);

            if (countParams == 3) {
                multiplier = Integer.parseInt(op[3]);
            }
        }

        catch (Exception e) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Params are not a number"));
            return new InputResultMatrixOp(true, null, InputResult.Types.NONE);
        }

        params.put("n", n);
        params.put("k", k);

        if (countParams == 3) {
            params.put("multiplier", multiplier);
        }

        return new InputResultMatrixOp(true, params, InputResult.Types.MATRIX_OP);
    }

    private static InputResultOp readSet(String[] op) {
        ConcurrentSkipListMap<String, String> params = new ConcurrentSkipListMap<String, String>();

        if (op.length < 2) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Not enough parameters"));
            return new InputResultOp(true, null, InputResult.Types.NONE);
        }

        if (op[1].equals("currM")) {
            if (op.length != 3) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Not enough parameters"));
                return new InputResultOp(true, null, InputResult.Types.NONE);
            }

            params.put("op", "currM");
            params.put("n", op[2]);
        }

        else {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid set parameter"));
            return new InputResultOp(true, null, InputResult.Types.NONE);
        }

        return new InputResultOp(true, params, InputResult.Types.SET_OP);
    }

    private static InputResultOp readPrint(String[] op) {
        ConcurrentSkipListMap<String, String> params = new ConcurrentSkipListMap<String, String>();

        if (op.length < 2) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Not enough parameters"));
            return new InputResultOp(true, null, InputResult.Types.NONE);
        }

        if (op[1].equals("mlist")) {
            params.put("op", "mlist");
        }

        else if (op[1].equals("currM")) {
            params.put("op", "currM");
        }

        else {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid print parameter"));
            return new InputResultOp(true, null, InputResult.Types.NONE);
        }

        return new InputResultOp(true, params, InputResult.Types.PRINT_OP);
    }

    private static InputResultMatrixOp readMWMOp(String[] op) {
        ConcurrentSkipListMap<String, Integer> params = new ConcurrentSkipListMap<String, Integer>();

        if (op[0].charAt(0) == '+') {
            params.put("op", 5);
        }

        else {
            params.put("op", 6);
        }

        // -1 = current
        // -2 = new
        if (op.length == 1) {
            params.put("m1", -1); // current
            params.put("m2", -2); // new
        }

        else if (op.length == 2) {
            params.put("m1", -1); // current
            int m2 = 0;

            try {
                m2 = Integer.parseInt(op[1]);
            }

            catch (Exception e) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Params are not a number"));
                return new InputResultMatrixOp(true, null, InputResult.Types.NONE);
            }

            params.put("m2", m2);
        }

        else if (op.length == 3) {
            int m1 = 0;
            int m2 = 0;

            try {
                m1 = Integer.parseInt(op[1]);
                m2 = Integer.parseInt(op[2]);
            }

            catch (Exception e) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Params are not a number"));
                return new InputResultMatrixOp(true, null, InputResult.Types.NONE);
            }

            params.put("m1", m1);
            params.put("m2", m2);
        }

        else {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid count of parameters"));
            return new InputResultMatrixOp(true, null, InputResult.Types.NONE);
        }

        return new InputResultMatrixOp(true, params, InputResult.Types.MWM_OP);
    }

    private static String[] deleteSpaces(String[] input) {
        String[] cmdSplit = input.clone();

        for (int i = 0; i < cmdSplit.length; i++) {
            cmdSplit[i] = cmdSplit[i].replaceAll(" ", "");
        }

        return cmdSplit;
    }

    public static InputResult readCommand() {
        Scanner sc = new Scanner(System.in);

        COutput.printMessage("Enter command: ");
        String cmd = sc.nextLine();
        String[] cmdSplit = cmd.split(" ");
        cmd = cmd.replaceAll(" ", "");

        if (cmd.equals("new")) {
            return CInput.readNewMatrix();
        }

        else if (cmdSplit[0].equals("*") || cmdSplit[0].equals("/") || cmdSplit[0].equals("-") || cmdSplit[0].equals("+") || cmdSplit[0].equals("s")) {
            InputResultMatrixOp result = CInput.readOp(CInput.deleteSpaces(cmdSplit));

            if (!ExceptionHandler.isEmpty()) {
                return new InputResultMatrixOp(true, null, InputResult.Types.NONE);
            }

            return result;
        }

        else if (cmdSplit[0].equals("+matrix") || cmdSplit[0].equals("*matrix")) {
            return CInput.readMWMOp(CInput.deleteSpaces(cmdSplit));
        }

        else if (cmdSplit[0].equals("set")) {
            return CInput.readSet(CInput.deleteSpaces(cmdSplit));
        }

        else if (cmdSplit[0].equals("print")) {
            return CInput.readPrint(CInput.deleteSpaces(cmdSplit));
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
