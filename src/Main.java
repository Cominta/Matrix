import console.CInput;
import console.InputHandler;
import console.inputResult.InputResult;
import exception.ExceptionHandler;
import matrix.Matrix;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) throws Exception {
        InputResult result = new InputResult(true, InputResult.Types.NONE);
        ArrayList<Matrix> matrixList = new ArrayList<Matrix>();
        AtomicReference<Matrix> currentMatrix = new AtomicReference<>(null);
        CInput.scanner = new Scanner(System.in);

        AtomicInteger currentIndex = new AtomicInteger(-1);

        while (result.isCont()) {
            if (!ExceptionHandler.isEmpty()) {
                ExceptionHandler.printExceptions();
                ExceptionHandler.clear();
            }

            result = CInput.readCommand();

            if (!ExceptionHandler.isEmpty()) {
                continue;
            }

            InputHandler.handleResult(result, matrixList, currentIndex, currentMatrix);
        }

        CInput.scanner.close();
    }
}