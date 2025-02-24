package console;

import exception.ExceptionHandler;
import exception.ExceptionObj;
import matrix.Matrix;
import matrix.elements.Element;
import matrix.elements.NumR;
import matrix.elements.NumZ;

import java.util.Scanner;

public class CInput {
    private static void readNewMatrix() {
        Scanner sc = new Scanner(System.in);

        COutput.printMessage("Enter size: ");
        String sizeLine = sc.nextLine();
        String[] sizes = sizeLine.split(" ");

        if (sizes.length != 2) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid input size"));
            return;
        }

        int sizeX;
        int sizeY;

        try {
            sizeX = Integer.parseInt(sizes[1]);
            sizeY = Integer.parseInt(sizes[0]);
        }

        catch (Exception e) {
            ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Size is not a number"));
            return;
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
            return;
        }

        Matrix matrix = new Matrix(sizeX, sizeY);
        matrix.setMode(type);

        for (int i = 0; i < matrix.getSizeY(); i++) {
            COutput.printMessage("Enter line(" + i + "): ");
            String line = sc.nextLine();
            String[] tokens = line.split(" ");

            if (tokens.length != matrix.getSizeX()) {
                ExceptionHandler.report(new ExceptionObj(ExceptionObj.Types.INPUT_ERROR, "Invalid matrix size"));
                return;
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

        COutput.printMatrix(matrix, "New matrix: ");
    }

    public static void readCommand() {
        Scanner sc = new Scanner(System.in);

        COutput.printMessage("Enter command: ");
        String cmd = sc.nextLine().replaceAll(" ", "");

        if (cmd.equals("new")) {
            CInput.readNewMatrix();

            if (!ExceptionHandler.isEmpty()) {
                ExceptionHandler.printExceptions();
                ExceptionHandler.clear();
            }
        }
    }
}
