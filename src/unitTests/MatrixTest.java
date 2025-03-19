package unitTests;

import console.CInput;
import console.COutput;
import console.InputHandler;
import console.inputResult.InputResult;
import console.inputResult.InputResultMatrixOp;
import console.inputResult.InputResultOp;
import matrix.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MatrixTest {
    final int matrixCountZ = 5;
    final String path = Paths.get("").toAbsolutePath().toString() + "/src/unitTests/";

    private void loadMatrixs(int count, ArrayList<Matrix> array) {
        for (int z = 1; z <= count; z++) {
            CInput.scanner.nextLine();
            array.add(CInput.readNewMatrix().getMatrix());

            if (CInput.scanner.hasNextLine()) {
                CInput.scanner.nextLine();
            }
        }
    }

    @DisplayName("Test line sum (Z numbers)")
    @Test
    public void testLineSumZ() throws FileNotFoundException, CloneNotSupportedException {
        final int countOfResults = 13;
        ArrayList<Matrix> matrixs = new ArrayList<>();
        ArrayList<Matrix> matrixResults = new ArrayList<>();
        COutput.isPrint = false;

        CInput.scanner = new Scanner(new FileInputStream(path + "matrixs/mZ.txt"));
        loadMatrixs(matrixCountZ, matrixs);
        CInput.scanner.close();

        CInput.scanner = new Scanner(new FileInputStream(path + "testLineSumZ/result.txt"));
        loadMatrixs(countOfResults, matrixResults);
        CInput.scanner.close();

        CInput.scanner = new Scanner(new FileInputStream(path + "testLineSumZ/commands.txt"));
        InputResult result = new InputResult(true);
        AtomicInteger currentIndex = new AtomicInteger(-1);
        int currentIndexResults = 0;
        AtomicReference<Matrix> currentMatrix = new AtomicReference<>(null);

        while (result.isCont()) {
            result = CInput.readCommand();

            InputHandler.handleResult(result, matrixs, currentIndex, currentMatrix);

            if (result.getType() == InputResult.Types.MATRIX_OP) {
                COutput.isPrint = true;
                ConcurrentSkipListMap<String, Integer> params = ((InputResultMatrixOp)result).getParams();
                String s = "";

                for (String key : params.keySet()) {
                    if (key.equals("op")) {
                        continue;
                    }

                    s += key + ": " + params.get(key) + " | ";
                }

                COutput.printMessage("Op: " + params.get("op") + ", params = " + s + "\n");

                COutput.printMessage("Actual [" + (currentIndex.get() + 1) + "]: ");
                COutput.printMatrix(currentMatrix.get());

                COutput.printMessage("Expected [" + (currentIndexResults + 1) + "]: ");
                COutput.printMatrix(matrixResults.get(currentIndexResults));

                System.out.println("\n-------------\n");

                COutput.isPrint = false;

                Assertions.assertTrue(currentMatrix.get().equals(matrixResults.get(currentIndexResults)));
                currentIndexResults++;
            }
        }

        CInput.scanner.close();
    }
}
